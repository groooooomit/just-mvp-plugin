package com.bfu.plugin.justmvp.core

import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.InputValidatorEx
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.pom.java.LanguageLevel
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiNameHelper

/**
 * 类名验证器
 */
class ClassNameValidator internal constructor(private val myNameHelper: PsiNameHelper, private val errorMsg: String) : InputValidatorEx {
    override fun getErrorText(inputString: String?) = if (inputString.orEmpty().let { myNameHelper.isIdentifier(it, LanguageLevel.JDK_1_8) }) null else errorMsg
    override fun checkInput(inputString: String?) = getErrorText(inputString) == null
    override fun canClose(inputString: String?) = getErrorText(inputString) == null
}

/**
 * 创建子文件
 */
fun VirtualFile.createChildSourceFileIfNotExists(project: Project, fileName: String, content: String, fileType: FileType) = findChild(fileName) ?: PsiFileFactory.getInstance(project).createFileFromText(fileName, fileType, content)
    .let { PsiManager.getInstance(project).findDirectory(this)?.add(it) }
    .let { findChild(fileName) }
