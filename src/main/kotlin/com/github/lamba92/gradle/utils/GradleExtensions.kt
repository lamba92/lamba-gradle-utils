package com.github.lamba92.gradle.utils

import com.jfrog.bintray.gradle.BintrayExtension
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

val Project.kotlinMultiplatform
    get() = extensions.getByType<KotlinMultiplatformExtension>()

fun Project.kotlinMultiplatform(action: KotlinMultiplatformExtension.() -> Unit) =
    extensions.configure(action)

val Project.publishing
    get() = extensions.getByType<PublishingExtension>()

fun Project.bintray(action: BintrayExtension.() -> Unit) =
    extensions.configure(action)

val Project.bintray
    get() = extensions.getByType<BintrayExtension>()

fun Project.publishing(action: PublishingExtension.() -> Unit) =
    extensions.configure(action)

fun Project.androidLibrary(action: AndroidLibraryExtension.() -> Unit) =
    extensions.configure(action)

val Project.androidLibrary
    get() = extensions.getByType<AndroidLibraryExtension>()

fun Project.androidApplication(action: AndroidApplicationExtension.() -> Unit) =
    extensions.configure(action)

val Project.androidApplication
    get() = extensions.getByType<AndroidApplicationExtension>()
