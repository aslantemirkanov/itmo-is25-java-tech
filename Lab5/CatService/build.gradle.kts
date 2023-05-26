plugins {
    id("java")
}

group = "ru.aslantemirkanov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":Lab5:DataService")))
    runtimeOnly("org.postgresql:postgresql:42.6.0")
    implementation("org.projectlombok:lombok:1.18.22")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.0.6")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.6")
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.6")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.6")
    implementation("org.springframework.amqp:spring-rabbit:3.0.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.6")
    testImplementation("junit:junit:4.13.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}