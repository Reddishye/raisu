dependencies {
    api(project(":api"))
    implementation("com.google.inject:guice:7.0.0")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
    implementation("org.msgpack:msgpack-core:0.9.8")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("org.json:json:20240303")
}

publishing.publications.named<MavenPublication>("maven") {
    artifactId = "raisu-core"
}
