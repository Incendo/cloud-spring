plugins {
    id("cloud-spring.base-conventions")
}

dependencies {
    api(libs.cloud.core)
    compileOnlyApi(libs.cloud.annotations)

    compileOnly(libs.immutables)
    annotationProcessor(libs.immutables)

    testImplementation(libs.spring.boot.starter.test)
}
