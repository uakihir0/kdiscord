> [English](../README.md)

# kdiscord

kdiscord は Kotlin Multiplatform 向けの Discord API クライアントライブラリです。Discord の HTTP API に対する
REST バインディングと、リアルタイムな Gateway（WebSocket）接続を実装した `stream` モジュールを提供します。
JVM / JS（Node・ブラウザ）/ Apple（iOS・macOS）をターゲットにしています。

> [!WARNING]
> kdiscord は Bot トークンではなく**ユーザートークン**（self-bot 方式）で認証します。ユーザーアカウントの自動化は
> [Discord の利用規約](https://discord.com/terms)に違反し、アカウント停止につながる可能性があります。
> 本ライブラリの利用は自己責任でお願いします。

## モジュール

| モジュール | 説明 |
|-----------|------|
| `core` | REST API クライアント（users / channels / messages / guilds / gateway）|
| `stream` | リアルタイムイベント用の Discord Gateway（WebSocket）クライアント |
| `all` | CocoaPods / XCFramework / SwiftPackage / JS ライブラリ向けの集約モジュール（macOS 限定）|

## 使い方

### REST

```kotlin
import work.socialhub.kdiscord.DiscordFactory
import work.socialhub.kdiscord.api.request.messages.MessagesCreateRequest
import work.socialhub.kdiscord.api.request.messages.MessagesListRequest

val discord = DiscordFactory.instance("YOUR_USER_TOKEN")

// 現在のユーザー
val me = discord.users().getMe()
println(me.data.username)

// チャンネルのメッセージを取得（「タイムライン」に相当）
val messages = discord.messages().list(
    MessagesListRequest(channelId = "CHANNEL_ID").also { it.limit = 50 }
)

// メッセージを投稿
discord.messages().create(
    MessagesCreateRequest(channelId = "CHANNEL_ID").also { it.content = "Hello from kdiscord!" }
)
```

JVM と Native では各 suspend メソッドに `*Blocking()` 版があります。JS では suspend 関数をそのまま
利用してください（Promise ベースの API としてエクスポートされます）。

### Gateway（リアルタイム）

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
stream.start()   // 接続する（コルーチン内で実行）
// ...
stream.stop()
```

## ビルド

```shell
./gradlew :core:jvmJar        # REST コアをビルド（ネットワーク不要）
./gradlew :stream:jvmJar      # Gateway モジュールをビルド
./gradlew build -x test       # フルアセンブル（macOS では all モジュールもビルド）
```

## テスト

ライブテスト（デフォルトで `@Ignore` により無効化）を実行するには、`secrets.json.default` を
`secrets.json` にコピーして認証情報を記入してください：

```json
{ "discord": { "DISCORD_USER_TOKEN": "...", "DISCORD_CHANNEL_ID": "...", "DISCORD_GUILD_ID": "..." } }
```

## ライセンス

MIT License

## 作者

[Akihiro Urushihara](https://github.com/uakihir0)
