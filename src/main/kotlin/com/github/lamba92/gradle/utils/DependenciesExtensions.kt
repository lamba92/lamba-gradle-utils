@file:Suppress("unused")

package com.github.lamba92.gradle.utils

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

fun KotlinDependencyHandler.ktor(module: String, version: String? = null) =
    "io.ktor:ktor-$module${version?.let { ":$version" } ?: ""}"

fun KotlinDependencyHandler.lamba(module: String, version: String? = null) =
    "com.github.lamba92:$module${version?.let { ":$version" } ?: ""}"

fun DependencyHandler.ktor(module: String, version: String? = null) =
    "io.ktor:ktor-$module${version?.let { ":$version" } ?: ""}"

fun DependencyHandler.lamba(module: String, version: String? = null) =
    "com.github.lamba92:$module${version?.let { ":$version" } ?: ""}"

fun KotlinDependencyHandler.kotlinx(module: String, version: String? = null, prefix: Boolean = true) =
    "org.jetbrains.kotlinx:${if (prefix) "kotlinx-" else ""}$module${version?.let { ":$version" } ?: ""}"

fun KotlinDependencyHandler.serialization(module: String, version: String? = null) =
    "org.jetbrains.kotlinx:kotlinx-serialization-$module${version?.let { ":$version" } ?: ""}"

fun DependencyHandler.kotlinx(module: String, version: String? = null, prefix: Boolean = true) =
    "org.jetbrains.kotlinx:${if (prefix) "kotlinx-" else ""}$module${version?.let { ":$version" } ?: ""}"

fun DependencyHandler.serialization(module: String, version: String? = null) =
    "org.jetbrains.kotlinx:kotlinx-serialization-$module${version?.let { ":$version" } ?: ""}"
