import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

buildscript {
    dependencies {
        classpath("com.github.nbaztec:coveralls-jacoco-gradle-plugin:1.2.5") {
            // The plugin is using an older version (1.3.72) which prevent us from using 1.4+
            // https://github.com/nbaztec/coveralls-jacoco-gradle-plugin/blob/main/build.gradle.kts#L35
            exclude(group = "org.jetbrains.kotlin", module = "kotlin-gradle-plugin")
        }
    }
}

plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
    id("kotlinx-serialization")
    id("org.jlleitschuh.gradle.ktlint")
    id("mirego.kword").version(Versions.TRIKOT_KWORD_PLUGIN)
    id("jacoco")
}

// We must use this approach to load the plugin since we enforced the exclusion of a transitive dependency
apply(plugin = "com.github.nbaztec.coveralls-jacoco")

repositories {
    google()
    jcenter()
    mavenCentral()
    mavenLocal()
    maven(url = "https://kotlin.bintray.com/kotlinx")
    maven(url = "https://jitpack.io")
    maven(url = "https://s3.amazonaws.com/mirego-maven/public")
}

//group("com.trikot.sample")

android {
    compileSdkVersion(Versions.COMPILE_SDK)
    defaultConfig {
        minSdkVersion(Versions.MIN_SDK)
    }
    sourceSets {
        val main by getting {
            resources.srcDir("src/commonMain/resources/")
        }
    }
//    testOptions {
//        unitTests {
//            isReturnDefaultValues = true
//            isIncludeAndroidResources = true
//        }
//    }
}

kword {
    translationFile = file("src/commonMain/resources/translations/translation.en.json")
    enumClassName = "com.trikot.sample.localization.KWordTranslation"
    generatedDir = file("src/commonMain/generated")
}

kotlin {
    android {
        publishAllLibraryVariants()
    }

    ios {
        binaries {
            framework {
                embedBitcode("disable")
                baseName = Const.TRIKOT_FRAMEWORK_NAME
                transitiveExport = true
                export(Libs.TRIKOT_FOUNDATION)
                export(Libs.TRIKOT_STREAMS)
                export(Libs.TRIKOT_VIEWMODELS)
                export(Libs.TRIKOT_HTTP)
                export(Libs.TRIKOT_KWORD)
            }
        }
    }

    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.Experimental")
            languageSettings.useExperimentalAnnotation("kotlin.time.ExperimentalTime")
        }

        val commonMain by getting {
            dependencies {
                api(Libs.TRIKOT_FOUNDATION)
                api(Libs.TRIKOT_STREAMS)
                api(Libs.TRIKOT_VIEWMODELS)
                api(Libs.TRIKOT_HTTP)
                api(Libs.TRIKOT_KWORD)
                api(Libs.KOTLINX_SERIALIZATION_JSON)
            }
            kotlin.srcDir(kword.generatedDir)
        }

        val commonTest by getting {
            dependencies {
                implementation(Libs.KOTLIN_TEST_COMMON)
                implementation(Libs.KOTLIN_TEST_ANNOTATION_COMMON)
                implementation(Libs.MOCKK_COMMON)
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(Libs.ANDROIDX_LIFECYCLE_VIEWMODEL)
                implementation(Libs.ANDROIDX_LIFECYCLE_VIEWMODEL_KTX)
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(Libs.KOTLIN_TEST)
                implementation(Libs.KOTLIN_TEST_JUNIT)
                implementation(Libs.MOCKK)
            }
        }

        val iosMain by getting {
            dependsOn(commonMain)
        }
    }
}

// This task attaches native framework built from ios module to Xcode project
// (see iosApp directory). Don't run this task directly,
// Xcode runs this task itself during its build process.
// Before opening the project from iosApp directory in Xcode,
// make sure all Gradle infrastructure exists (gradle.wrapper, gradlew).
val copyFramework by tasks.creating(Copy::class) {
    val buildType = project.findProperty("kotlin.build.type")?.toString() ?: "RELEASE"
    val target = project.findProperty("kotlin.target")?.toString() ?: "iosArm64"
    val kotlinNativeTarget = kotlin.targets.getByName<KotlinNativeTarget>(target)
    val framework = kotlinNativeTarget.binaries.getFramework(buildType)
    dependsOn(framework.linkTask)

    doLast {
        val srcFile = framework.outputFile
        val targetDir = property("configuration.build.dir")
        val frameworkDir = "${targetDir}/${Const.TRIKOT_FRAMEWORK_NAME}.framework"
        val translationDir = "${projectDir}/../common/src/commonMain/resources/translations"
        copy {
            from(srcFile.parent)
            into(targetDir)
            include("${Const.TRIKOT_FRAMEWORK_NAME}.framework/**")
            include("${Const.TRIKOT_FRAMEWORK_NAME}.framework.dSYM/**")
        }
        copy {
            from(translationDir)
            into(frameworkDir)
            include("**")
        }
    }
}

project.afterEvaluate {
    project.tasks.filter { task -> task.name.startsWith("compile") && task.name.contains("Kotlin") }.forEach { task ->
        task.dependsOn("kwordGenerateEnum")
    }
}

//jacoco {
//    toolVersion = "0.8.2"
//    reportsDir = file("build/reports")
//}
//
//task jacocoTestReport(type: JacocoReport, dependsOn: "test") {
//    group = "Reporting"
//    description = "Generate Jacoco coverage reports"
//
//    reports {
//        xml.enabled = true
//        html.enabled = true
//    }
//
//    def excludes = [
//            '**/serializer.class',
//            '**/factories**'
//    ]
//    getClassDirectories().setFrom(fileTree(
//            dir: "build/intermediates/classes/debug",
//            excludes: excludes
//    ) + fileTree(
//            dir: "build/tmp/kotlin-classes/debug",
//            excludes: excludes
//    ))
//    getExecutionData().setFrom(files("build/jacoco/testDebugUnitTest.exec"))
//    getSourceDirectories().setFrom(files([
//            "src/commonMain/kotlin"
//    ]))
//}
//tasks.find { it.name.find('coverallsJacoco') }.mustRunAfter jacocoTestReport
//
//coverallsJacoco {
//    reportPath = "${buildDir}/reports/jacocoTestReport/jacocoTestReport.xml"
//    reportSourceSets = files([
//            "src/commonMain/kotlin"
//    ])
//}
