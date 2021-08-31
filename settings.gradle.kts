pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
        gradlePluginPortal()
    }

    resolutionStrategy {
        repositories {
            mavenLocal()
            maven("https://plugins.gradle.org/m2/")
            maven("https://s3.amazonaws.com/mirego-maven/public")
            maven("https://jitpack.io")
        }

        eachPlugin {
            val kotlinPluginNames = listOf("kotlin-multiplatform", "kotlin-platform-native")
            if (kotlinPluginNames.contains(requested.id.id)) {
                useModule("org.jetbrains.kotlin:${requested.id.id}:${requested.version}")
            }
            if (requested.id.namespace == "mirego") {
                useModule("mirego:${requested.id.name}-plugin:${requested.version}")
            }
        }
    }
}

rootProject.name = "TrikotSample"

include(":common")
