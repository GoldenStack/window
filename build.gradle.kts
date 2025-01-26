import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
    id("com.vanniktech.maven.publish") version "0.30.0"
}

group = "net.goldenstack.window"
version = "1.1"
description = "Useful API for Minestom inventories"

repositories {
    mavenCentral()
}

dependencies {
    val minestom = "net.minestom:minestom-snapshots:96543a894f"
    compileOnly(minestom)
    testImplementation(minestom)

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates("net.goldenstack", "window", version.toString())

    pom {
        name.set("window")
        description.set("Useful API for Minestom inventories")
        url.set("https://github.com/goldenstack/window")
        licenses {
            license {
                name.set("MIT")
                url.set("https://github.com/goldenstack/window/blob/master/LICENSE")
            }
        }
        developers {
            developer {
                id.set("goldenstack")
                name.set("GoldenStack")
                email.set("git@goldenstack.net")
            }
        }
        scm {
            connection.set("scm:git:git://github.com/goldenstack/window.git")
            developerConnection.set("scm:git:git@github.com:goldenstack/window.git")
            url.set("https://github.com/goldenstack/window")
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}
