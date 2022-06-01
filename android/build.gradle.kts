plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    defaultConfig {
        applicationId = "com.trikot.sample"
        versionCode = 1
        versionName = "1.0"
        versionName = "1.0"

        compileSdk = Versions.COMPILE_SDK
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }

        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
        }
    }

    packagingOptions {
        pickFirst("META-INF/androidx.customview_customview.version")
        pickFirst("META-INF/androidx.legacy_legacy-support-core-ui.version")
        exclude("META-INF/*.kotlin_module")
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    lint {
        checkReleaseBuilds = true
        abortOnError = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    repositories {
        maven("https://s3.amazonaws.com/mirego-maven/public")
        mavenLocal()
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs +
            "-Xopt-in=kotlin.time.ExperimentalTime" +
            "-Xopt-in=kotlin.ExperimentalStdlibApi"
    }

    sourceSets {
        getByName("main") {
            resources.srcDir("../common/src/commonMain/resources/")
        }
    }
}

configurations.all {
    exclude(group = "org.reactivestreams")
}

dependencies {
    api(project(":common"))
    implementation(Libs.Kotlin.Stdlib)
    implementation(Libs.AndroidX.AppCompat)
    implementation(Libs.AndroidX.ConstraintLayout)
    implementation(Libs.AndroidX.LifecycleExtensions)
    implementation(Libs.AndroidX.LifecycleReactiveStreams)
    implementation(Libs.AndroidX.Material)
    implementation(Libs.Picasso)
}

ktlint {
    android.set(true)
}
