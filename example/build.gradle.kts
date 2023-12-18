plugins {
    id("cloud-spring.base-conventions")
    alias(libs.plugins.spring.plugin.boot)
    alias(libs.plugins.graal.native.buildtools)
}

apply(plugin = "io.spring.dependency-management")

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

java {
    toolchain {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":cloud-spring"))
    implementation(libs.cloud.annotations)
}
