package com.github.lamba92.gradle.utils

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import org.gradle.kotlin.dsl.*
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.internal.artifact.FileBasedMavenArtifact

typealias AndroidLibraryPlugin = LibraryPlugin
typealias AndroidLibraryExtension = LibraryExtension

typealias AndroidApplicationPlugin = AppPlugin
typealias AndroidApplicationExtension = AppExtension

@Suppress("ObjectPropertyName")
val TRAVIS_TAG
    get() = System.getenv("TRAVIS_TAG").run {
        if (isNullOrBlank()) null else this
    }

fun <T> Action<T>.asLambda(): T.() -> Unit = {
    execute(this)
}

fun <T> (T.() -> Unit).asAction() = Action<T> {
    this@asAction(it)
}

fun AndroidLibraryExtension.alignSourcesForKotlinMultiplatformPlugin(project: Project) =
    sourceSets.all {
        it.java.srcDirs(project.file("src/android${it.name.capitalize()}/kotlin"))
        it.res.srcDirs(project.file("src/android${it.name.capitalize()}/res"))
        it.resources.srcDirs(project.file("src/android${it.name.capitalize()}/resources"))
        it.manifest.srcFile(project.file("src/android${it.name.capitalize()}/AndroidManifest.xml"))
    }

fun Project.prepareForPublication(
    bintrayUsername: String = searchPropertyOrNull("bintrayUsername", "BINTRAY_USERNAME")!!,
    bintrayApiKey: String = searchPropertyOrNull("bintrayApiKey", "BINTRAY_API_KEY")!!,
    publicationVersion: String = searchPropertyOrNull("publicationVersion") ?: project.version as String,
    publicationRepository: String = searchPropertyOrNull("publicationRepository") ?: project.group as String,
    publicationName: String = searchPropertyOrNull("publicationName") ?: rootProject.name,
    publicationVscUrl: String = searchPropertyOrNull("publicationVscUrl")
        ?: "https://github.com/lamba92/${rootProject.name}/",
    publicationIssueTrackerUrl: String = searchPropertyOrNull("publicationIssueTrackerUrl")
        ?: "https://github.com/lamba92/${rootProject.name}/issues",
    publicationNames: Iterable<String> = publishing.publications.names,
    bintrayPublish: Boolean = true,
    fixGradleMetadataPublication: Boolean = true,
    action: BintrayExtension.() -> Unit = {}
) {

    bintray {
        user = bintrayUsername
        key = bintrayApiKey
        pkg {
            version {
                name = publicationVersion
            }
            repo = publicationRepository
            name = publicationName
            setLicenses("Apache-2.0")
            vcsUrl = publicationVscUrl
            issueTrackerUrl = publicationIssueTrackerUrl
        }
        publish = bintrayPublish
        setPublications(publicationNames)
        action()
    }

    if (fixGradleMetadataPublication)
        tasks.withType<BintrayUploadTask> {
            doFirst {
                publishing.publications.withType<MavenPublication> {
                    buildDir.resolve("publications/$name/module.json").let {
                        if (it.exists())
                            artifact(object : FileBasedMavenArtifact(it) {
                                override fun getDefaultExtension() = "module"
                            })
                    }
                }
            }
        }

}
