plugins {
    java
    kotlin("jvm") version "2.1.21"
    id("com.gradleup.shadow") version "8.3.6"
    `maven-publish`
}

group = "ru.allexs82"
version = "2.2"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:3.9.0")
    implementation("ch.qos.logback:logback-classic:1.5.13")
    implementation("net.dv8tion:JDA:5.5.1") {
        exclude(module = "opus-java")
        exclude(module="tink")
    }
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

java {
    withSourcesJar()
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ru.allexs82.MainKt"
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

tasks.shadowJar{
    archiveBaseName.set(rootProject.name)
    archiveVersion.set("")

    manifest {
        attributes["Main-Class"] = "ru.allexs82.MainKt"
    }

    mergeServiceFiles()
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.test {
    useJUnitPlatform()
}
