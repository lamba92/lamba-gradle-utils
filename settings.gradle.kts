pluginManagement {
    repositories {
        mavenCentral()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
    resolutionStrategy {
        eachPlugin {
            val kotlinVersion: String by settings
            val bintrayPluginVersion: String by settings
            when  {
                "org.jetbrains.kotlin.jvm" in requested.id.id -> useVersion(kotlinVersion)
                "com.jfrog.bintray" == requested.id.id -> useVersion(bintrayPluginVersion)
            }
        }
    }
}
rootProject.name = "lamba-gradle-utils"

