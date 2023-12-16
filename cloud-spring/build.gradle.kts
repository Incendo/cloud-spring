plugins {
    id("cloud-spring.base-conventions")
}

dependencies {
    compileOnly(libs.immutables)
    annotationProcessor(libs.immutables)

    testImplementation(libs.spring.boot.starter.test)
}
