import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("cloud-spring.base-conventions")
    id("cloud-spring.publishing-conventions")
    alias(libs.plugins.spring.plugin.boot)
}
apply(plugin = "io.spring.dependency-management")

tasks.named<BootJar>("bootJar") {
    enabled = false
}

dependencies {
    api(libs.cloud.core)
    api(libs.spring.boot.autoconfigure)
    api(libs.spring.shell)
    api(platform(libs.spring.shell.dependencies))

    compileOnlyApi(libs.cloud.annotations)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.shell.test)
    testImplementation(libs.spring.shell.test.autoconfiguration)
}
