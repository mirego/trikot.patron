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
    id("mirego.kword").version("0.5")
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
                baseName = "$trikot_framework_name"
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
            languageSettings {
                useExperimentalAnnotation('kotlin.Experimental')
                useExperimentalAnnotation('kotlin.time.ExperimentalTime')
            }
        }

        commonMain {
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

        commonTest {
            dependencies {
                implementation(Libs.KOTLIN_TEST_COMMON)
                implementation(Libs.KOTLIN_TEST_ANNOTATION)
                implementation(Libs.MOCKK)
            }
        }

        androidMain {
            dependsOn commonMain
            dependencies {
                implementation "com.mirego.trikot.viewmodels:android-ktx:$trikot_viewmodels_android_ktx_version"
                implementation "com.mirego.trikot.http:android-ktx:$trikot_http_android_ktx_version"
                implementation "com.mirego.trikot.kword:android-ktx:$trikot_kword_android_ktx_version"
                implementation "androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version"
                implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
            }
            dependsOn commonMain
        }

        androidTest {
            dependencies {
                implementation 'org.jetbrains.kotlin:kotlin-test'
                implementation 'org.jetbrains.kotlin:kotlin-test-junit'
                implementation "io.mockk:mockk:$mockk_version"
            }
        }

        iosMain {
            dependsOn commonMain
        }
    }
}

//// This task attaches native framework built from ios module to Xcode project
//// (see iosApp directory). Don't run this task directly,
//// Xcode runs this task itself during its build process.
//// Before opening the project from iosApp directory in Xcode,
//// make sure all Gradle infrastructure exists (gradle.wrapper, gradlew).
//task copyFramework() {
//    def buildType = project.findProperty('kotlin.build.type') ?: 'RELEASE'
//    def target = project.findProperty('kotlin.target') ?: 'iosArm64'
//    dependsOn kotlin.targets."$target".binaries.getFramework(buildType).linkTask
//
//    doLast {
//        def srcFile = kotlin.targets."$target".binaries.getFramework(buildType).outputFile
//        def targetDir = getProperty('configuration.build.dir')
//        def frameworkDir = "${targetDir}/${trikot_framework_name}.framework"
//        def translationDir = "${projectDir}/../common/src/commonMain/resources/translations"
//        copy {
//            from srcFile.parent
//            into targetDir
//            include "${trikot_framework_name}.framework/**"
//            include "${trikot_framework_name}.framework.dSYM/**"
//        }
//        copy {
//            from translationDir
//            into frameworkDir
//            include "**"
//        }
//    }
//}
//
//project.afterEvaluate {
//    project.tasks.findAll { task -> task.name.startsWith('compile') && task.name.contains('Kotlin') }.each { task ->
//        task.dependsOn('kwordGenerateEnum')
//    }
//}
//
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
