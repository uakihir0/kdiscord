> [日本語](./README_ja.md)

# kdiscord SPM

This repository is the Swift Package repository for [kdiscord].
[kdiscord] is a Discord client library built using Kotlin Multiplatform.
As a result, it can be built and used on Apple devices such as iOS.
Here, we distribute the library built as an XCFramework via Swift Package.
Additionally, this repository is automatically committed to via GitHub Actions from [kdiscord].
Please direct any issues or pull requests to [kdiscord].

## Usage

This repository does not have its own versioning.
Instead, branches corresponding to the versions of [kdiscord] are provided.
To use a specific version of [kdiscord], specify the corresponding branch of this repository.
Check the [list of branches](https://github.com/uakihir0/kdiscord-spm/branches) to find the branch matching your desired version.

### How to Make Requests

Although it is also usable from Objective-C, below is an example of how to use it in Swift.
For more detailed usage instructions, please refer to the [kdiscord] README.

```swift
import kdiscord

let discord = KdiscordFactory.shared.instance(token: "YOUR_USER_TOKEN")
```

## License

MIT License

## Author

[Akihiro Urushihara](https://github.com/uakihir0)

[kdiscord]: https://github.com/uakihir0/kdiscord
