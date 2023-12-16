plugins {
    id("cloud-spring.base-conventions")
    alias(libs.plugins.spring.plugin.boot)
}

dependencies {
    api(project(":cloud-spring"))
}
