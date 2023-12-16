plugins {
    `kotlin-dsl`
    alias(libs.plugins.spotless)
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.spotless)
    implementation(libs.indraCommon)
    implementation(libs.gradleKotlinJvm)
    implementation(libs.spring.plugin.depman)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

spotless {
    kotlinGradle {
        target("*.gradle.kts", "src/*/kotlin/**.gradle.kts", "src/*/kotlin/**.kt")
        ktlint(libs.versions.ktlint.get())
            .editorConfigOverride(
                mapOf(
                    "ktlint_standard_filename" to "disabled",
                    "ktlint_standard_trailing-comma-on-call-site" to "disabled",
                    "ktlint_standard_trailing-comma-on-declaration-site" to "disabled"
                )
            )
    }
}
