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
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()

    maven("https://jitpack.io")
}

dependencies {
    compileOnly("com.github.Minestom:Minestom:51ef4779c9")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("com.github.Minestom:Minestom:51ef4779c9")
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
