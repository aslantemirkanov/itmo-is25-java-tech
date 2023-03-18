plugins {
    id("java")
}

group = "ru.aslantemirkanov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":Lab2:Application")))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("com.h2database:h2:2.1.214")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}