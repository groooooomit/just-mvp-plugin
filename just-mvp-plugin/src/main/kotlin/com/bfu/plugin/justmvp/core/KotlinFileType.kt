package com.bfu.plugin.justmvp.core

import com.intellij.icons.AllIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

/**
 * 参考 [com.intellij.ide.highlighter.JavaFileType]
 */
object KotlinFileType : LanguageFileType(KotlinLanguage) {

    override fun getIcon(): Icon = AllIcons.FileTypes.Java

    override fun getName() = "KOTLIN"

    override fun getDefaultExtension() = "kt"

    override fun getDescription() = "just kotlin"

}