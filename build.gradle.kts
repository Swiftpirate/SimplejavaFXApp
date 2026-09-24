plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin")version "0.1.0"
}
javafx {
    version = "21"
    modules = listOf("javafx.controls")
}
application {
    mainModule.set("com.example.hellofx")
    mainClass.set("com.example.hellofx.main")
}
group = "org.example"
version = "1.0-SNAPSHOT"


group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}