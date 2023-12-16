plugins {
    id("cloud-spring.base-conventions")
    alias(libs.plugins.spring.plugin.boot)
}

dependencies {
    implementation(project(":cloud-spring"))
    implementation(libs.cloud.annotations)
}
