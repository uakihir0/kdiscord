> [English](./README.md)

# kdiscord cocoapods

このリポジトリは [kdiscord] の Cocoapods リポジトリです。[kdiscord] は Kotlin Multiplatform を用いて作成された Discord クライアントライブラリです。
そのため、iOS などの Apple デバイス上でビルドして利用できます。ここでは、ビルドした XCFramework を Cocoapods を通じて配布しています。
また、このリポジトリは [kdiscord] の GitHub Actions によって自動的にコミットされます。Issue や Pull Request は [kdiscord] にお願いします。

## 使い方

### Podfile

Podfile に以下の行を追加してください。
このリポジトリにはバージョンはなく、[kdiscord] のバージョンに対応するブランチが存在します。
利用する [kdiscord] のバージョンは、このリポジトリのブランチを指定することで決定できます。
対応するブランチは[ブランチ一覧](https://github.com/uakihir0/kdiscord-cocoapods/branches)から確認してください。
また、Debug ビルドと Release ビルドで異なるバージョンを使用します。

```ruby
target '{{PROJECT_NAME}}' do
  use_frameworks!

  # Pods for kdiscord
  pod 'kdiscord-debug', 
    :configuration => ['Debug'], 
    :git => 'https://github.com/uakihir0/kdiscord-cocoapods/', 
    :branch => '{{BRANCH_NAME}}'
  pod 'kdiscord-release', 
    :configuration => ['Release'], 
    :git => 'https://github.com/uakihir0/kdiscord-cocoapods/', 
    :branch => '{{BRANCH_NAME}}'
  ...
end
```

### リクエスト

Objective-C からも利用可能ですが、以下は Swift での使い方です。
詳しい使い方は [kdiscord] の README も確認してください。

```swift
WIP
```

## ライセンス

MIT License

## 作者

[Akihiro Urushihara](https://github.com/uakihir0)


[kdiscord]: https://github.com/uakihir0/kdiscord
