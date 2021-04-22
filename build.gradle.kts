import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    id("org.jetbrains.dokka") version "1.4.32"
    application
}

group = "p03w.shredder"
version = "0.0.0"

repositories {
    maven(url="https://dl.bintray.com/kotlin/dokka")
    maven(url="https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "com.github.p03w.shredder.MainKt"
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"
    manifest {
        attributes["Implementation-Title"] = "Gradle Jar File"
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = "com.github.p03w.shredder.MainKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks.dokkaHtml.configure {
    suppressInheritedMembers.set(true)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}
