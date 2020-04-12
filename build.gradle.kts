import com.jfrog.bintray.gradle.BintrayExtension.PackageConfig
import com.jfrog.bintray.gradle.BintrayExtension.VersionConfig

plugins {
    kotlin("jvm")
    `maven-publish`
    id("com.jfrog.bintray")
}

group = "com.github.lamba92"
version = TRAVIS_TAG ?: "1.0.0"

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
    jcenter()
}

kotlin {
    target {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
}

dependencies {

    val kotlinVersion: String by project
    val sshGradlePluginVersion: String by project
    val bintrayPluginVersion: String by project
    val androidGradlePluginVersion: String by project

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation(gradleApi())
    implementation(gradleKotlinDsl())
    api("org.jetbrains.kotlin", "kotlin-gradle-plugin", kotlinVersion)
    api("org.jetbrains.kotlin", "kotlin-serialization", kotlinVersion)
    api("com.jfrog.bintray.gradle", "gradle-bintray-plugin", bintrayPluginVersion)
    api("org.hidetake", "gradle-ssh-plugin", sshGradlePluginVersion)
    api("com.android.tools.build", "gradle", androidGradlePluginVersion)

}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

val mainPublication by publishing.publications.registering(MavenPublication::class) {
    from(components["java"])
    artifact(sourcesJar.get())
}

bintray {
    user = searchPropertyOrNull("bintrayUsername")
    key = searchPropertyOrNull("bintrayApiKey")
    pkg(closureOf<PackageConfig> {
        version(closureOf<VersionConfig> {
            name = project.name
        })
        repo = "com.github.lamba92"
        name = project.name
        setLicenses("Apache-2.0")
        vcsUrl = "https://github.com/lamba92/lamba-gradle-utils"
        issueTrackerUrl = "https://github.com/lamba92/lamba-gradle-utils/issues"
    })
    publish = true
    setPublications(mainPublication.name)
}

fun Project.searchPropertyOrNull(name: String, vararg aliases: String): String? {

    fun searchEverywhere(name: String): String? =
        findProperty(name) as? String? ?: System.getenv(name)

    searchEverywhere(name)?.let { return it }

    with(aliases.iterator()) {
        while (hasNext()) {
            searchEverywhere(next())?.let { return it }
        }
    }

    return null

}

@Suppress("PropertyName")
val TRAVIS_TAG
    get() = System.getenv("TRAVIS_TAG").run {
        if (isNullOrBlank()) null else this
    }
