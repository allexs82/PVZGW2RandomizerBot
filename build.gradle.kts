plugins {
    java
    id("org.jetbrains.kotlin.jvm") version "2.1.10"
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
    implementation("ch.qos.logback:logback-classic:1.3.14")
    implementation("net.dv8tion:JDA:5.3.0") {
        exclude(module = "opus-java")
    }
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

java {
    withSourcesJar()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ru.allexs82.MainKt"
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

tasks.test {
    useJUnitPlatform()
}
