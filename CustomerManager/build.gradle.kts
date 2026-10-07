plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("App")
}

javafx {
    version = "21"
    modules = listOf("javafx.controls")
}