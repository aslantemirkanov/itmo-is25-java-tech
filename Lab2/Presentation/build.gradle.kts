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
    testImplementation(project(mapOf("path" to ":Lab2:DataAccess")))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation("org.mockito:mockito-core:5.2.0")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}