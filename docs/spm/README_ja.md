> [English](./README.md)

# kdiscord SPM

このリポジトリは [kdiscord] の Swift Package リポジトリです。
[kdiscord] は Kotlin Multiplatform を用いて構築された Discord クライアントライブラリです。
そのため、iOS などの Apple デバイス上でビルドして利用できます。
ここでは、XCFramework としてビルドしたライブラリを Swift Package を通じて配布しています。
また、このリポジトリは [kdiscord] の GitHub Actions によって自動的にコミットされます。
Issue や Pull Request は [kdiscord] にお願いします。

## 使い方

このリポジトリには独自のバージョン管理はありません。
代わりに、[kdiscord] のバージョンに対応するブランチが提供されています。
特定のバージョンの [kdiscord] を利用するには、このリポジトリの対応するブランチを指定してください。
対応するブランチは[ブランチ一覧](https://github.com/uakihir0/kdiscord-spm/branches)から確認してください。

### リクエストの方法

Objective-C からも利用可能ですが、以下は Swift での使い方の例です。
詳しい使い方は [kdiscord] の README を参照してください。

```swift
import kdiscord

let discord = KdiscordFactory.shared.instance(token: "YOUR_USER_TOKEN")
```

## ライセンス

MIT License

## 作者

[Akihiro Urushihara](https://github.com/uakihir0)

[kdiscord]: https://github.com/uakihir0/kdiscord
