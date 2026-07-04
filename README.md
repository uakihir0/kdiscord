> [日本語](./docs/README_ja.md)

# kdiscord

kdiscord is a Discord API client library for Kotlin Multiplatform. It provides REST API bindings
for the Discord HTTP API and a `stream` module implementing the real-time Gateway (WebSocket)
connection. It targets JVM, JS (Node + Browser), and Apple (iOS / macOS) platforms.

> [!WARNING]
> kdiscord authenticates using a **user token** (self-bot style), not a bot token. Automating a
> user account violates [Discord's Terms of Service](https://discord.com/terms) and may result in
> account termination. Use this library at your own risk.

## Modules

| Module | Description |
|--------|-------------|
| `core` | REST API client (users, channels, messages, guilds, gateway) |
| `stream` | Discord Gateway (WebSocket) client for real-time events |
| `all` | Aggregate for CocoaPods / XCFramework / SwiftPackage / JS library (macOS only) |

## Usage

### REST

```kotlin
import work.socialhub.kdiscord.DiscordFactory
import work.socialhub.kdiscord.api.request.messages.MessagesCreateRequest
import work.socialhub.kdiscord.api.request.messages.MessagesListRequest

val discord = DiscordFactory.instance("YOUR_USER_TOKEN")

// Current user
val me = discord.users().getMe()
println(me.data.username)

// Read a channel's messages (a "timeline")
val messages = discord.messages().list(
    MessagesListRequest(channelId = "CHANNEL_ID").also { it.limit = 50 }
)

// Post a message
discord.messages().create(
    MessagesCreateRequest(channelId = "CHANNEL_ID").also { it.content = "Hello from kdiscord!" }
)
```

On JVM and Native, every suspend method has a `*Blocking()` variant. On JS, use the suspend
functions (they are exported as Promise-based APIs).

### Gateway (real-time)

```kotlin
import work.socialhub.kdiscord.entity.Message
import work.socialhub.kdiscord.entity.gateway.ReadyData
import work.socialhub.kdiscord.stream.DiscordStreamFactory
import work.socialhub.kdiscord.stream.DiscordStreamListener

val stream = DiscordStreamFactory.instance("YOUR_USER_TOKEN")
stream.addEventListener(object : DiscordStreamListener {
    override fun onReady(data: ReadyData) {
        println("Ready as ${data.user?.username}")
    }
    override fun onMessageCreate(message: Message) {
        println("${message.author?.username}: ${message.content}")
    }
})
stream.start()   // connects; run inside a coroutine
// ...
stream.stop()
```

## Building

```shell
./gradlew :core:jvmJar        # build the REST core (no network needed)
./gradlew :stream:jvmJar      # build the Gateway module
./gradlew build -x test       # full assemble (macOS builds the all module too)
```

## Testing

Copy `secrets.json.default` to `secrets.json` and fill in your credentials to run the live tests
(which are `@Ignore`-gated by default):

```json
{ "discord": { "DISCORD_USER_TOKEN": "...", "DISCORD_CHANNEL_ID": "...", "DISCORD_GUILD_ID": "..." } }
```

## License

MIT License

## Author

[Akihiro Urushihara](https://github.com/uakihir0)
