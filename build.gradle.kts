buildscript {
    repositories {
        mavenLocal()
        google()
        maven(url = "https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.KOTLIN}")
        classpath("com.google.gms:google-services:${Versions.GOOGLE_SERVICES_PLUGIN}")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:${Versions.KTLINT}")
    }
}

allprojects {
    buildscript {
        repositories {
            mavenLocal()
            google()
            mavenCentral()
        }
    }
    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }
}
