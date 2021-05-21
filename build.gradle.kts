buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.KOTLIN}")
        classpath("com.google.gms:google-services:4.3.4")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:9.1.1")
    }
}

repositories {
    google()
    jcenter()
}

allprojects {
    buildscript {
        repositories {
            google()
        }
    }
    repositories {
        google()
        jcenter()
        mavenLocal()
    }
}