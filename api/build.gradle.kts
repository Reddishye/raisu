dependencies {
    api(project(":bootstrap"))
    api("net.kyori:adventure-api:4.17.0")
}

publishing.publications.named<MavenPublication>("maven") {
    artifactId = "raisu-api"
}
