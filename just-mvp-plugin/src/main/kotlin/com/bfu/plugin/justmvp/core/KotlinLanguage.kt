package com.bfu.plugin.justmvp.core

import com.intellij.lang.Language
import com.intellij.lang.jvm.JvmLanguage

/**
 * 参考 [com.intellij.lang.java.JavaLanguage]
 */
object KotlinLanguage : Language("KOTLIN"), JvmLanguage {

    override fun getDisplayName() = "Kotlin"

    override fun isCaseSensitive() = true

}