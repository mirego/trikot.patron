buildscript {
    repositories {
        google()
        maven(url = "https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
        classpath("com.google.gms:google-services:4.3.8")
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
    }
}
