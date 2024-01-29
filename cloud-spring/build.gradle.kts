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

    compileOnly(libs.cloud.annotations)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.shell.test)
    testImplementation(libs.spring.shell.test.autoconfiguration)
}

configurations.javadocLinksJavadoc {
    // Messed up metadata(?), Gradle won't resolve the Javadocs
    exclude("io.projectreactor")
    exclude("org.springframework")
    exclude("org.springframework.boot")
    exclude("org.springframework.shell")
}

javadocLinks {
    // No Javadocs on central
    excludes.addAll(
        "org.hibernate.validator",
        "org.springframework.boot:spring-boot-starter",
        "org.springframework.boot:spring-boot-starter-logging",
        "org.springframework.shell:spring-shell-table",
        "org.springframework.shell:spring-shell-starter",
        "org.springframework.shell:spring-shell-standard-commands",
        "org.apache.tomcat.embed",
        "org.jline:jline-native",
        "org.apache.logging.log4j:log4j-to-slf4j",
    )
}
