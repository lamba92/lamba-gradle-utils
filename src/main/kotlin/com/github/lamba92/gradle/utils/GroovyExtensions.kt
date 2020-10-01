package com.github.lamba92.gradle.utils

import com.jfrog.bintray.gradle.BintrayExtension
import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.kotlin.dsl.KotlinClosure1
import org.gradle.kotlin.dsl.closureOf
import org.gradle.kotlin.dsl.delegateClosureOf
import org.hidetake.groovy.ssh.core.Remote
import org.hidetake.groovy.ssh.core.RunHandler
import org.hidetake.groovy.ssh.core.Service
import org.hidetake.groovy.ssh.session.SessionHandler
import java.io.File
import java.io.InputStream

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

fun BintrayExtension.pkg(action: BintrayExtension.PackageConfig.() -> Unit) {
    pkg(closureOf(action))
}

fun BintrayExtension.PackageConfig.version(action: BintrayExtension.VersionConfig.() -> Unit) {
    version(closureOf(action))
}

fun BintrayExtension.setPublications(names: Iterable<String>) =
    setPublications(*names.toList().toTypedArray())

fun Service.runSessions(action: RunHandler.() -> Unit) {
    run(delegateClosureOf(action))
}

fun RunHandler.session(vararg remotes: Remote, action: SessionHandler.() -> Unit) =
    remotes.forEach { remote ->
        session(remote, delegateClosureOf(action))
    }

fun SessionHandler.put(src: String, dst: String) {
    put(hashMapOf("from" to src, "into" to dst))
}

fun SessionHandler.put(src: Iterable<File>, dst: String) {
    put(hashMapOf("from" to src, "into" to dst))
}

fun SessionHandler.put(src: InputStream, dst: String) {
    put(hashMapOf("from" to src, "into" to dst))
}

fun SessionHandler.put(src: ByteArray, dst: String) {
    put(hashMapOf("from" to src, "into" to dst))
}

fun SessionHandler.put(src: File, dst: String, filter: (File) -> Boolean = { true }) {
    put(hashMapOf("from" to src, "into" to dst, "filter" to KotlinClosure1(filter)))
}

