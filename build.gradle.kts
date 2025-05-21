plugins {
    id("io.quarkus")
    `kotlin-conventions`
    `quarkus-conventions`
    kotlin("plugin.allopen") version "2.0.21"
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(project(":_internal-api"))
}

allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.inject.Singleton")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

allprojects {
    group = "pm.gh.integration.github.app"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}
