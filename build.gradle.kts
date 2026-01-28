import net.ltgt.gradle.errorprone.errorprone
import net.ltgt.gradle.nullaway.nullaway
import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    java
    `maven-publish`
    id("net.ltgt.errorprone") version "4.4.0" apply false
    id("net.ltgt.nullaway") version "2.4.1" apply false
    id("com.diffplug.spotless") version "7.0.2" apply false
}

allprojects {
    group = "com.redactado"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "net.ltgt.errorprone")
    apply(plugin = "net.ltgt.nullaway")
    apply(plugin = "com.diffplug.spotless")

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
        withSourcesJar()
        withJavadocJar()
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
        compileOnly("org.jetbrains:annotations:24.1.0")
        compileOnly("com.google.errorprone:error_prone_annotations:2.35.1")

        add("errorprone", "com.google.errorprone:error_prone_core:2.35.1")
        add("errorprone", "com.uber.nullaway:nullaway:0.11.3")

        testImplementation(platform("org.junit:junit-bom:5.11.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        testImplementation("org.mockito:mockito-core:5.14.2")
        testImplementation("org.mockito:mockito-junit-jupiter:5.14.2")
        testImplementation("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    }

    configure<SpotlessExtension> {
        java {
            palantirJavaFormat("2.50.0")
            removeUnusedImports()
            trimTrailingWhitespace()
            endWithNewline()
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(21)
        options.compilerArgs.addAll(listOf("-Xlint:all", "-Xlint:-processing", "-Werror"))

        options.errorprone {
            disableWarningsInGeneratedCode.set(true)
            disable("MissingSummary", "UnusedVariable")
        }

        options.errorprone.nullaway {
            annotatedPackages.add("com.redactado.raisu")
        }
    }

    tasks.test {
        useJUnitPlatform()
        testLogging.events("passed", "skipped", "failed")
    }

    tasks.javadoc {
        (options as StandardJavadocDocletOptions).addBooleanOption("Xdoclint:none", true)
    }

    publishing {
        publications.create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set("Raisu")
                description.set("Lightning-fast debugging library for Paper")
                url.set("https://github.com/redactado/raisu")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("redactado")
                        name.set("Redactado")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/redactado/raisu.git")
                    developerConnection.set("scm:git:ssh://github.com/redactado/raisu.git")
                    url.set("https://github.com/redactado/raisu")
                }
            }
        }
    }
}
