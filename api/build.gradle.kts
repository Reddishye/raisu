dependencies {
    api(project(":bootstrap"))
}

publishing.publications.named<MavenPublication>("maven") {
    artifactId = "raisu-api"
}
