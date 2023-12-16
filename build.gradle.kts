plugins {
    id("cloud-spring.parent-build-logic")
}

spotlessPredeclare {
    kotlin { ktlint(libs.versions.ktlint.get()) }
    kotlinGradle { ktlint(libs.versions.ktlint.get()) }
}

tasks {
    spotlessCheck {
        dependsOn(gradle.includedBuild("build-logic").task(":spotlessCheck") )
    }
    spotlessApply {
        dependsOn(gradle.includedBuild("build-logic").task(":spotlessApply"))
    }
}
