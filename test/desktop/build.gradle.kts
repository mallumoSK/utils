plugins {
    kotlin("jvm")
    application
}

group = "tk.mallumo"
version = "1.7.20-16.0.1"

dependencies {
    implementation(project(":utils"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application.mainClass.set("tk.mallumo.MainKt")
