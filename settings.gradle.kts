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

plugins {
    @Suppress("UnstableApiUsage")
    `gradle-enterprise`
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlwaysIf(System.getenv("CI")?.toBoolean() == true)
    }
}

rootProject.name = "lamba-gradle-utils"

