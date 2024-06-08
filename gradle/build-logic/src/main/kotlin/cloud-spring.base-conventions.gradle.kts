plugins {
    id("org.incendo.cloud-build-logic")
    id("org.incendo.cloud-build-logic.spotless")
}

indra {
    javaVersions {
        minimumToolchain(21)
        target(21)
        testWith().set(setOf(21))
    }
    checkstyle().set(libs.versions.checkstyle)
}

cloudSpotless {
    ktlintVersion = libs.versions.ktlint
}

spotless {
    java {
        importOrderFile(rootProject.file(".spotless/cloud-spring.importorder"))
    }
}

// Common dependencies.
dependencies {

    // test dependencies
    testImplementation(libs.truth)
    testImplementation(libs.awaitility)
}
