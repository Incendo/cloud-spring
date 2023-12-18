import com.diffplug.gradle.spotless.FormatExtension

plugins {
    id("net.kyori.indra")
    id("net.kyori.indra.checkstyle")
    id("com.diffplug.spotless")
}

indra {
    javaVersions {
        minimumToolchain(17)
        target(17)
        testWith(17)
    }
    checkstyle(libs.versions.checkstyle.get())
}

spotless {
    fun FormatExtension.applyCommon(spaces: Int = 4) {
        indentWithSpaces(spaces)
        trimTrailingWhitespace()
        endWithNewline()
    }
    java {
        licenseHeaderFile(rootProject.file("HEADER"))
        importOrderFile(rootProject.file(".spotless/cloud-spring.importorder"))
        applyCommon()

        targetExclude("**/generated/**")
    }
    kotlin {
        licenseHeaderFile(rootProject.file("HEADER"))
        applyCommon()
    }
    kotlinGradle {
        ktlint(libs.versions.ktlint.get())
    }
    format("configs") {
        target("**/*.yml", "**/*.yaml", "**/*.json")
        applyCommon(2)
    }
}

// Common dependencies.
dependencies {
    compileOnlyApi(libs.checkerQual)
    compileOnlyApi(libs.apiguardian)

    testDependencies()
}

fun DependencyHandlerScope.testDependencies() {
    testImplementation(libs.truth)
    testImplementation(libs.awaitility)
}
