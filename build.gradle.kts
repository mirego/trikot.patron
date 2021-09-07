buildscript {
    repositories {
        google()
        maven(url = "https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("com.google.gms:google-services:4.3.10")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.KOTLIN}")

    }
}

repositories {
    google()
}

allprojects {
    buildscript {
        repositories {
            google()
        }
    }
    repositories {
        google()
        mavenLocal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
        maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-coroutines/maven")
        maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }
}
