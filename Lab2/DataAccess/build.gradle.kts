plugins {
    id("java")
    id("io.freefair.lombok") version "8.0.0-rc2"
}

group = "ru.aslantemirkanov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    // https://mvnrepository.com/artifact/org.hibernate/hibernate-core
    implementation("org.hibernate:hibernate-core:6.1.7.Final")
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.5.4")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}