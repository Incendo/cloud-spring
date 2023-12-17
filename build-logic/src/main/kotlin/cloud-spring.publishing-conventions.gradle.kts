plugins {
    id("net.kyori.indra.publishing")
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
}

indra {
    github("Incendo", "cloud-spring") {
        ci(true)
    }
    mitLicense()

    configurePublications {
        pom {
            developers {
                developer {
                    id.set("Sauilitired")
                    name.set("Alexander Söderberg")
                    email.set("alexander.soderberg@incend.org")
                }
            }
        }
    }
}
