plugins {
    id("java")
}

group = "ru.aslantemirkanov.lab3"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.6")
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.6")
    testImplementation("junit:junit:4.13.1")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql:42.6.0")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.0.6")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.6")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.projectlombok:lombok:1.18.22")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.springframework.boot:spring-boot-starter-security:3.0.6")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.6")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}