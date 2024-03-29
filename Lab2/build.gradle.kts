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
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}



tasks.getByName<Test>("test") {
    useJUnitPlatform()
}