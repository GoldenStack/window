plugins {
    `java-library`
    `maven-publish`
}

group = "dev.goldenstack.window"
version = "1.0"
description = "Useful API for Minestom inventories"

java {
    withJavadocJar()
    withSourcesJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()

    maven("https://jitpack.io")
}

dependencies {
    compileOnly("net.minestom:minestom-snapshots:96bf14500a")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testImplementation("net.minestom:minestom-snapshots:96bf14500a")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

configure<JavaPluginExtension> {
    withSourcesJar()
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
