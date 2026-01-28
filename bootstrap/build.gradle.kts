tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("raisu.properties") {
        expand("version" to project.version)
    }
}

publishing.publications.named<MavenPublication>("maven") {
    artifactId = "raisu-bootstrap"
}
