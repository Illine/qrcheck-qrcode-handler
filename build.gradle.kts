import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.util.*

plugins {
    kotlin("jvm") version "1.6.10-RC"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.springframework.boot") version "2.6.1"
    id("org.sonarqube") version "3.1.1"
    id("org.liquibase.gradle") version "2.0.4"
    kotlin("plugin.spring") version "1.6.10-RC"
    kotlin("plugin.jpa") version "1.6.10-RC"
    kotlin("kapt") version "1.6.10-RC"
    jacoco
}

group = "ru.softdarom.qrcheck"

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

repositories {
    mavenLocal()
    maven {
        url = uri("https://maven.pkg.github.com/softdarom/packages")
        credentials {
            username = (project.findProperty("gpr.user") ?: System.getenv("GPR_USERNAME")) as String?
            password = (project.findProperty("gpr.token") ?: System.getenv("GPR_TOKEN")) as String?
        }
    }
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2021.0.0")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-kubernetes-client-config")
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.1.RELEASE")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.20")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("io.swagger.core.v3:swagger-annotations:2.1.6")
    implementation("org.springdoc:springdoc-openapi-ui:1.5.10")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.5.10")
    implementation("ru.softdarom.security:qrcheck-security-starter:1.2.0")
    implementation("io.micrometer:micrometer-registry-prometheus:1.8.2")
    implementation("p6spy:p6spy:3.9.1")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("net.ttddyy:datasource-proxy:1.7")
    implementation("net.logstash.logback:logstash-logback-encoder:7.0.1")
    implementation("org.zalando:logbook-spring-boot-starter:2.14.0")
    implementation("net.minidev:json-smart:2.4.7")

    liquibaseRuntime("org.liquibase:liquibase-core:3.6.3")
    liquibaseRuntime("org.liquibase:liquibase-groovy-dsl:2.0.1")
    liquibaseRuntime("ch.qos.logback:logback-classic:1.2.10")
    liquibaseRuntime("org.postgresql:postgresql:42.2.18")

    kapt("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("org.postgresql:postgresql:42.2.18")
    runtimeOnly("org.codehaus.groovy:groovy:3.0.9")
    runtimeOnly("org.codehaus.groovy:groovy-json:3.0.9")

    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.jar {
    enabled = false
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    this.archiveFileName.set("qrcode-handler.jar")
}

liquibase {
    val file = File(".deploy/ansible/configs/liquibase/liquibase.properties")
    val properties = Properties()
    if (file.exists()) {
        properties.load(FileInputStream(file))
    }

    activities.register("main") {
        this.arguments = mapOf(
            "changeLogFile" to properties.getOrDefault("changeLogFile", "${projectDir}/.liquibase/changelog.yaml"),
            "url" to properties.getOrDefault("url", "jdbc:postgresql://localhost:5432/qrcheck"),
            "username" to properties.getOrDefault("username", "liquibase"),
            "password" to properties.getOrDefault("password", "liquibase"),
            "outputFile" to "${buildDir}/.liquibase/sql-migration.sql",
            "defaultSchemaName" to "qrcodes",
            "contexts" to properties.getOrDefault("context", "local")
        )
    }
    runList = "main"
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        xml.destination = file("${buildDir}/jacoco/coverage.xml")
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.0".toBigDecimal()
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeTags("spring-mock", "spring-system", "unit")
    }
    finalizedBy(tasks.jacocoTestReport)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}
