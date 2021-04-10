plugins {
    kotlin("jvm") version "1.4.32"
    `maven-publish`
}

repositories {
    jcenter()
}

dependencies {
    implementation("com.google.guava:guava:18.0")
}

publishing {
    publications {
        create<MavenPublication>("kotlin") {
            from(components["kotlin"])
        }
    }
}
