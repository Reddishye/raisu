dependencies {
    api(project(":platform-spigot"))
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    testImplementation("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
}

publishing.publications.named<MavenPublication>("maven") {
    artifactId = "raisu-paper"
}
