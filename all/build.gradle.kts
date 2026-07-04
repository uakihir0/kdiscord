import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.swiftpackage)
    id("module.publications")
}

kotlin {
    jvmToolchain(11)
    jvm()

    js {
        nodejs()
        browser()
        binaries.library()
        compilerOptions {
            generateTypeScriptDefinitions()
            freeCompilerArgs.add("-Xes-long-as-bigint")
        }
    }

    val xcf = XCFramework("kdiscord")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        macosArm64(),
    ).forEach {
        it.binaries.framework {
            export(project(":core"))
            export(project(":stream"))
            baseName = "kdiscord"
            xcf.add(this)
        }
    }

    cocoapods {
        name = "kdiscord"
        version = "0.0.1"
        summary = "kdiscord is Discord library for Kotlin Multiplatform."
        homepage = "https://github.com/uakihir0/kdiscord"
        authors = "Akihiro Urushihara"
        license = "MIT"
        framework { baseName = "kdiscord" }
    }

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.js.ExperimentalJsExport")
            }
        }
        commonMain.dependencies {
            api(project(":core"))
            api(project(":stream"))
        }
    }
}

multiplatformSwiftPackage {
    swiftToolsVersion("5.7")
    targetPlatforms {
        // baseline 2020
        iOS { v("15") }
        macOS { v("12.0") }
    }
}

tasks.configureEach {
    // Fix implicit dependency between XCFramework and FatFramework tasks
    if (name.contains("assembleKdiscord") && name.contains("XCFramework")) {
        mustRunAfter(tasks.matching { it.name.contains("FatFramework") })
    }
}

tasks.podPublishXCFramework {
    doLast {
        providers.exec {
            executable = "sh"
            args = listOf(project.projectDir.path + "/../tool/rename_podfile.sh")
        }.standardOutput.asText.get()
    }
}
