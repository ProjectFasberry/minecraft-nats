plugins {
  kotlin("jvm") version "2.0.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  maven {
    name = "papermc"
    url = uri("https://repo.papermc.io/repository/maven-public/")
  }
}

dependencies {
  compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
  implementation("io.nats:jnats:2.16.12")
  implementation("org.json:json:20210307")
  implementation("io.github.cdimascio:dotenv-kotlin:6.5.0")
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }
}

tasks {
  jar {
    archiveBaseName.set("NatsBridgePlugin")
    archiveVersion.set("")
    from(sourceSets.main.get().output)

    from({
      configurations.runtimeClasspath.get()
        .filter { it.name.endsWith("jar") }
        .map { zipTree(it) }
    })
  }
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(17)
}