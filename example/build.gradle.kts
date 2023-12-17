plugins {
    id("cloud-spring.base-conventions")
    alias(libs.plugins.spring.plugin.boot)
}
apply(plugin = "io.spring.dependency-management")

dependencies {
    implementation(project(":cloud-spring"))
    implementation(libs.cloud.annotations)
}
