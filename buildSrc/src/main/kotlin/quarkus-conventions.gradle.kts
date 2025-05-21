import gradle.kotlin.dsl.accessors._b7f6ea6acad8c6e09f51763fd484112c.implementation
import gradle.kotlin.dsl.accessors._b7f6ea6acad8c6e09f51763fd484112c.testImplementation

//plugins {
//    id("io.quarkus")
//}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation("io.quarkus:quarkus-messaging-kafka")
    implementation("io.quarkiverse.githubapp:quarkus-github-app:2.8.5")
    implementation("io.quarkiverse.githubapp:quarkus-github-app-events:2.8.5")
    implementation("io.quarkiverse.githubapp:quarkus-github-app-deployment:2.8.5")
    implementation("io.quarkiverse.githubapp:quarkus-github-app-ui:2.8.5")
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-resteasy-jackson")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}