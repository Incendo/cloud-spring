plugins {
    id("cloud-spring.base-conventions")
    alias(libs.plugins.spring.plugin.boot)
    alias(libs.plugins.graal.native.buildtools)
}

plugins.apply("io.spring.dependency-management")

graalvmNative {
    binaries.all {
        resources.autodetect()
    }
    binaries {
        getByName("main") {
            sharedLibrary = false
            mainClass = "org.incendo.cloud.spring.example.ExampleApplication"
            useFatJar = true
            javaLauncher =
                javaToolchains.launcherFor {
                    languageVersion = JavaLanguageVersion.of(21)
                    vendor = JvmVendorSpec.matching("GraalVM Community")
                }
        }
    }
    testSupport = false
    toolchainDetection = false
}

dependencies {
    implementation(project(":cloud-spring"))
    implementation(libs.cloud.annotations)
}

spotless {
    java {
        targetExclude("build/generated/**")
    }
}

tasks.compileAotJava {
    // I couldn't figure out the warnings in generated code
    options.compilerArgs.clear()
}
