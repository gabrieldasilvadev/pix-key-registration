@file:Suppress("DEPRECATION")

group = "com.itau"
version = "0.0.1-SNAPSHOT"
val sourceSets = the<SourceSetContainer>()
val basePackage = "com.itau.pixkeyregistration"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.openapi.generator") version "6.6.0"
}

// Arch Test
sourceSets {
    create("archTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}
val archTest = tasks.create("archTest", Test::class) {
    description = "Runs the architecture tests."
    group = "verification"

    testClassesDirs = sourceSets["archTest"].output.classesDirs
    classpath = sourceSets["archTest"].runtimeClasspath
}
val archTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get())
    extendsFrom(configurations.testImplementation.get())
}
configurations["archTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

// Integration Test
sourceSets {
    create("integrationTest") {
        compileClasspath += sourceSets.main.get().output + sourceSets.test.get().output
        runtimeClasspath += sourceSets.main.get().output + sourceSets.test.get().output
    }
}
val integrationTestTask = tasks.create("integrationTest", Test::class) {
    description = "Runs the component tests."
    group = "verification"

    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
}
val integrationTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get())
    extendsFrom(configurations.testImplementation.get())
}
configurations["integrationTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

// Open Api CodeGen
tasks.compileJava { dependsOn("openApiGenerate") }
openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("$rootDir/src/main/resources/static/api-docs.yaml")
    outputDir.set("$buildDir/generated/openapi")
    modelNameSuffix.set("Dto")
    configOptions.set(
            mapOf(
                    "dateLibrary" to "java8",
                    "gradleBuildFile" to "false",
                    "basePackage" to "$basePackage.application.web.api",
                    "apiPackage" to "$basePackage.application.web.api",
                    "modelPackage" to "$basePackage.application.web.dto",
                    "interfaceOnly" to "true",
                    "hideGenerationTimestamp" to "true",
                    "openApiNullable" to "false",
                    "useTags" to "true",
                    "useJakartaEe" to "true"
            )
    )
}
sourceSets { getByName("main") { java { srcDir("$buildDir/generated/openapi/src/main/java") } } }

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    // Spring Starter
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jetty")
    modules {
        module("org.springframework.boot:spring-boot-starter-tomcat") {
            replacedBy("org.springframework.boot:spring-boot-starter-jetty")
        }
    }
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Database
    implementation("org.flywaydb:flyway-core")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    archTestImplementation("com.tngtech.archunit:archunit:1.2.1")

    // Swagger
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.20")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")

    // Others
    implementation("de.huxhorn.sulky:de.huxhorn.sulky.ulid:8.3.0")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
