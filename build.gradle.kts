val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val kmongo_version: String by project

plugins {
    kotlin("jvm") version "1.7.22"
    id("io.ktor.plugin") version "2.2.1"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.22"
}

group = "com.ihcl.payment"
version = "0.0.1"
application {
    mainClass.set("com.ihcl.payment.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation ("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.litote.kmongo:kmongo:$kmongo_version")
    implementation("org.litote.kmongo:kmongo-coroutine:4.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("io.insert-koin:koin-core:3.2.0")
    implementation("io.ktor:ktor-client-core:2.2.1")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-serialization-gson-jvm:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-server-cio:$ktor_version")
    implementation("commons-codec:commons-codec:1.14")
    implementation("io.ktor:ktor-server-cors:$ktor_version")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
    implementation("org.bouncycastle:bcprov-jdk15on:1.68")
    implementation("org.bouncycastle:bcpkix-jdk15on:1.68")
    implementation("org.bouncycastle:bcprov-ext-jdk15on:1.68")
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")
    implementation("org.json:json:20210307")
    implementation("javax.xml.bind:jaxb-api:2.1")
    implementation("io.ktor:ktor-server-default-headers-jvm")
    implementation("io.ktor:ktor-client-okhttp:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    //swagger
    implementation("io.ktor:ktor-server-swagger-jvm:2.3.1")
    implementation("io.ktor:ktor-server-openapi:2.3.1")
    implementation("io.konform:konform:0.4.0")
}