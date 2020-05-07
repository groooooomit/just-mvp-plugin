package com.bfu.plugin.justmvp.core

import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.fileTypes.FileType

enum class LanguageType(val ext: String, val fileType: FileType) {
    KOTLIN(".kt", KotlinFileType),
    JAVA(".java", JavaFileType.INSTANCE);

    fun isKotlin() = this == KOTLIN

    fun isJava() = this == JAVA

}