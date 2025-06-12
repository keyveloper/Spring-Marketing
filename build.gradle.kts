plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"

    // for jpa no-arg constructor
    kotlin("plugin.jpa") version "1.9.25"

    // for query DSL
    kotlin("kapt") version "1.9.25"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // exposed
    implementation("org.jetbrains.exposed:exposed-core:0.59.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.59.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.59.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.59.0")


    // monitoring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    // cache(Redis)
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // jwt & security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // validation
    implementation("org.passay:passay:1.6.1")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Logger
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")

    // test
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Circuit Breaker : Resilience4j
    implementation("org.springframework.boot:spring-boot-starter-aop:3.3.4")
    implementation("io.github.resilience4j:resilience4j-spring-boot3:2.2.0")
    implementation("io.github.resilience4j:resilience4j-kotlin:2.1.0")
    kapt("org.springframework.boot:spring-boot-configuration-processor:3.3.4")

    // WebClient
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // ai
    implementation(platform("org.springframework.ai:spring-ai-bom:1.0.0-M7")) // ★
    implementation("org.springframework.ai:spring-ai-starter-model-openai")

    // image signature
    implementation("org.apache.tika:tika-core:2.9.0") // or latest

    // json
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    // kotlin date time
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")

    // keyword filter
    implementation("org.apache.commons:commons-text:1.13.0")   // 최신


}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
