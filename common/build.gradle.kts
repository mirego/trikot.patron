plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("kotlinx-serialization")
    id("org.jlleitschuh.gradle.ktlint")
    id("mirego.kword") version Versions.TRIKOT_KWORD_PLUGIN
}

repositories {
    mavenLocal()
    google()
    mavenCentral()
    maven(url = "https://jitpack.io")
    maven(url = "https://s3.amazonaws.com/mirego-maven/public")
}

android {
    defaultConfig {
        compileSdk = Versions.COMPILE_SDK
        minSdk = Versions.MIN_SDK
    }
}

kword {
    translationFile("src/commonMain/resources/translations/translation.en.json")
    enumClassName("com.trikot.sample.localization.KWordTranslation")
    generatedDir("src/commonMain/generated")
}

fun org.jetbrains.kotlin.gradle.plugin.mpp.Framework.configureFramework() {
    embedBitcode("disable")
    baseName = Const.TRIKOT_FRAMEWORK_NAME
    transitiveExport = true
    export(SharedLibs.Trikot.Foundation)
    export(SharedLibs.Trikot.Streams)
    export(SharedLibs.Trikot.Viewmodels)
    export(SharedLibs.Trikot.Http)
    export(SharedLibs.Trikot.Kword)
}

kotlin {
    android {
        publishAllLibraryVariants()
    }

    cocoapods {
        this.name = Const.TRIKOT_FRAMEWORK_NAME
        summary = "Trikot.Patron"
        homepage = "www.mirego.com"
        license = "BSD-3"
        extraSpecAttributes = mutableMapOf(
            "resources" to "\"src/commonMain/resources/translations/*\""
        )

        framework {
            configureFramework()
        }
    }

    ios {
        binaries.framework {
            configureFramework()
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            configureFramework()
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.ExperimentalStdlibApi")
                optIn("kotlin.time.ExperimentalTime")
            }
        }

        val commonMain by getting {
            dependencies {
                api(SharedLibs.Trikot.Foundation)
                api(SharedLibs.Trikot.Streams)
                api(SharedLibs.Trikot.Viewmodels)
                api(SharedLibs.Trikot.Http)
                api(SharedLibs.Trikot.Kword)
                implementation(Libs.Kotlinx.SerializationJson)
            }
            kotlin.srcDir(kword.generatedDir)
        }

        val commonTest by getting {
            dependencies {
                implementation(Libs.Kotlin.TestCommon)
                implementation(Libs.Kotlin.TestAnnotationCommon)
                implementation(Libs.Mockk.Common)
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(Libs.AndroidX.LifecycleViewModel)
                implementation(Libs.AndroidX.LifecycleViewModelKtx)
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(Libs.Kotlin.Test)
                implementation(Libs.Kotlin.TestJUnit)
                implementation(Libs.Mockk.Mockk)
            }
        }

        val iosMain by getting {
            dependsOn(commonMain)
        }
    }
}

project.afterEvaluate {
    project.tasks.filter { task -> task.name.startsWith("compile") && task.name.contains("Kotlin") }
        .forEach { task ->
            task.dependsOn("kwordGenerateEnum")
        }
}

ktlint {
    enableExperimentalRules.set(true)
    filter {
        exclude { element -> element.file.path.contains("generated/") }
    }
}
