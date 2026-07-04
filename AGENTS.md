# Agent Documentation

## Overview

This repository is a Discord API client library for Kotlin Multiplatform. It provides REST API
bindings for the Discord HTTP API and a `stream` module implementing the real-time Gateway
(WebSocket) connection. It mirrors the structure and conventions of the sibling `k*` libraries
(kbsky / kmisskey / kmastodon / kslack / kmatrix).

> [!WARNING]
> kdiscord authenticates using a **user token** (self-bot style, the Aliucord auth model), NOT a
> bot token. Automating a user account violates [Discord's Terms of Service](https://discord.com/terms)
> and may result in account termination. The library ships this capability for the user's own
> responsibility; there is no enforcement in code.

## Key Concepts

### Authentication (user token)

- REST requests send the **raw token with no prefix**: `Authorization: <token>`.
- The token is injected in exactly one place: `AbstractResourceImpl.auth()` in
  `core/.../internal/api/AbstractResourceImpl.kt`. To support bot tokens later, change that value
  to `"Bot $token"` and add an `intents` field to the Gateway IDENTIFY payload.
- Discord also requires a descriptive `User-Agent` header, which `auth()` attaches.

### REST API structure

- Base URL: `https://discord.com/api/v10` (`Discord.DEFAULT_API_HOST`).
- All bodies and responses are JSON. `InternalUtility.json` uses
  `JsonNamingStrategy.SnakeCase`, so Kotlin camelCase property names map to Discord's snake_case
  fields automatically. Add an explicit `@SerialName` only for fields that don't round-trip.
- **Snowflake IDs are always modeled as `String`** to avoid 53-bit precision loss on JS. The JS
  target sets `-Xes-long-as-bigint` to protect genuine `Long` fields (permissions bitfields,
  `heartbeat_interval`).

### Gateway (WebSocket) — `stream` module

The `stream` module implements the Discord Gateway connection lifecycle in `GatewayClient`:

1. `GET /gateway` (via the core `GatewayResource`) yields the WSS URL; `?v=10&encoding=json` is appended.
2. **HELLO (op 10)** → read `heartbeat_interval`, start a jittered heartbeat loop.
3. **Heartbeat (op 1)** is sent manually on an interval (Ktor's ~20s transport ping does NOT
   satisfy Discord's app-level heartbeat). `HEARTBEAT_ACK (op 11)` is tracked to detect zombie
   connections.
4. **IDENTIFY (op 2)** uses a user-account payload — realistic client `properties`, **no `intents`
   field**, `compress: false`.
5. **READY (dispatch)** stores `session_id` and `resume_gateway_url`.
6. On disconnect, **RESUME (op 6)** reconnects to `resume_gateway_url` with `session_id` + last
   sequence; **RECONNECT (op 7)** / **INVALID_SESSION (op 9)** are handled, with
   exponential-backoff reconnect (1s → 30s).

**Frame ordering:** khttpclient dispatches each received WS frame in a separate coroutine, so
ordering is not guaranteed. `GatewayClient` routes inbound frames through a single-consumer
`Channel<String>` so the sequence number `s` advances monotonically before any dispatch.

**Event decoding:** `GatewayEventSerializer.decode(t, d)` selects a concrete `@Serializable`
event by the `t` field. Unknown event types or decode failures fall back to `UnknownEvent`
holding the raw JSON, so new/unrecognized Discord events never break the stream.

v1 uses **uncompressed JSON** transport (no zlib-stream) and honors HTTP 429 `Retry-After` only
(no bucket-aware rate limiter).

## Architecture

### Resource-based API design

- **`Discord`** — main `@JsExport` interface with resource accessors: `users()`, `channels()`,
  `messages()`, `guilds()`, `gateway()`.
- **`DiscordFactory`** — `@JsExport` factory: `instance(token)` and `instance(token, apiHost)`.
- **`DiscordImpl`** — internal implementation that instantiates all resource implementations.
- Each API category has a **Resource interface** (e.g., `MessagesResource`) with:
  - `suspend fun name(...)` — coroutine method
  - `@JsExport.Ignore fun nameBlocking(...)` — blocking variant (JVM/Native)
- Each Resource has a **ResourceImpl** in `internal/api/` extending `AbstractResourceImpl`, which
  provides `proceed { }` / `proceedUnit { }` (wrapping a khttpclient call, mapping errors to
  `DiscordException`) plus the `auth()` and `qwn`/`pwn` request helpers.

### Platform support (expect/actual)

- **`BlockingUtil.toBlocking()`** — JVM/Native use `runBlocking`; JS throws
  `UnsupportedOperationException` (use the suspend functions instead).
- Native targets: iOS/macOS only. **mingwX64/linuxX64 are intentionally omitted** from both
  `core` and `stream` (native WebSocket support is not uniform; JVM covers those platforms).

## Directory Structure

- **`core/`** — REST API client
  - `Discord.kt`, `DiscordFactory.kt`, `DiscordException.kt`
  - `api/` — Resource interfaces + `request/{category}/` DTOs + `response/{category}/` DTOs
  - `entity/` — domain models (`User`, `Channel`, `Guild`, `Message`, `Embed`, ...) + `share/`
    (`Response<T>`, `ResponseUnit`, `RateLimit`)
  - `internal/` — `DiscordImpl`, `InternalUtility` (Json config), `AnySerializer`, `api/*ResourceImpl`
  - `util/` — `BlockingUtil` (expect/actual), `Headers`, `MediaType`
- **`stream/`** — Gateway (WebSocket) client (depends on `:core`)
  - `stream/` — `DiscordStream`, `DiscordStreamFactory`, `DiscordStreamListener`,
    `internal/GatewayClient`, `internal/GatewayEventSerializer`
  - `entity/gateway/` — `GatewayPayload`, `GatewayOpcode`, `HelloData`, `ReadyData`,
    `IdentifyPayload`, `ResumePayload`, `event/*`
- **`all/`** — aggregate for CocoaPods / XCFramework / SwiftPackage / JS library (macOS only)

## API Specification Reference

Refer to the official Discord API documentation when implementing or updating APIs:
https://discord.com/developers/docs/intro

## Adding a new REST endpoint

1. Add the `suspend fun` + `@JsExport.Ignore fun *Blocking` pair to the relevant `*Resource`
   interface (or create a new one and wire it into `Discord` / `DiscordImpl`).
2. Add request DTOs under `api/request/{category}/` and response DTOs under
   `api/response/{category}/` (or return an `entity/` model directly).
3. Implement in `internal/api/*ResourceImpl` using `proceed { HttpRequest().url(...).auth()... }`.
4. Add a `@Ignore`-gated live smoke test under `core/src/jvmTest/.../apis/`, and an offline
   serialization test under `.../unit/` where feasible.

## Testing

- `AbstractTest` loads `DISCORD_USER_TOKEN` (+ optional `DISCORD_CHANNEL_ID`, `DISCORD_GUILD_ID`,
  `DISCORD_API_HOST`) from env / system properties, falling back to the `"discord"` block of
  `../secrets.json`.
- Live REST/Gateway tests are `@Ignore`-gated so CI stays offline; remove `@Ignore` to run against
  a real account. Offline unit tests (serialization, gateway event decoding) run without network.
- Build-verify without network: `./gradlew :core:jvmJar :stream:jvmJar`,
  `./gradlew :core:compileKotlinJs :stream:compileKotlinJs`, or `./gradlew build -x test`.
