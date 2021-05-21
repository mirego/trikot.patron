plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("org.jlleitschuh.gradle.ktlint")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Versions.COMPILE_SDK)

    defaultConfig {
        applicationId = "com.trikot.sample"
        versionCode = 1
        versionName = "1.0"
//        archivesBaseName = "PatronProjectName-$versionCode"

        minSdkVersion(Versions.MIN_SDK)
        targetSdkVersion(Versions.TARGET_SDK)

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

    lintOptions {
        isCheckReleaseBuilds = true
        isAbortOnError = true
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
    }
}

configurations.all {
    exclude(group = "org.reactivestreams")
}

dependencies {
    api(project(":common"))
    implementation(Libs.KOTLIN_STDLIB)
    implementation(Libs.KOTLINX_SERIALIZATION_RUNTIME)
    implementation(Libs.ANDROIDX_APP_COMPAT)
    implementation(Libs.ANDROIDX_CONSTRAINT_LAYOUT)
    implementation(Libs.ANDROIDX_LIFECYCLE_EXTENSIONS)
    implementation(Libs.ANDROIDX_LIFECYCLE_REACTIVE_STREAMS)
    implementation(Libs.ANDROID_MATERIAL)
    implementation(Libs.PICASSO)
}

ktlint {
    android.set(true)
}
