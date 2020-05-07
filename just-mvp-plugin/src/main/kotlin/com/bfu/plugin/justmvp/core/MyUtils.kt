package com.bfu.plugin.justmvp.core

import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.InputValidatorEx
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager

/**
 * 类名验证器
 */
class ClassNameValidator internal constructor(private val errorMsg: String) : InputValidatorEx {
    override fun getErrorText(inputString: String?) = if (inputString.isJavaIdentifier()) null else errorMsg
    override fun checkInput(inputString: String?) = getErrorText(inputString) == null
    override fun canClose(inputString: String?) = getErrorText(inputString) == null
}

/**
 * 创建子文件
 */
fun VirtualFile.createChildSourceFileIfNotExists(project: Project, fileName: String, content: String, fileType: FileType) = findChild(fileName) ?: PsiFileFactory.getInstance(project).createFileFromText(fileName, fileType, content)
    .let { PsiManager.getInstance(project).findDirectory(this)?.add(it) }
    .let { findChild(fileName) }

/**
 * 驼峰转下划线
 */
fun String?.humpToUnderline(): String =
    if (this.isNullOrEmpty()) {
        ""
    } else {
        val stringBuilder = StringBuilder(this)
        var temp = 0 //定位
        for (i in 0 until length) {
            if (Character.isUpperCase(this[i])) {
                if (i != 0 && '_' != this[i - 1]) {
                    stringBuilder.insert(i + temp, "_")
                    temp += 1
                }
            }
        }
        stringBuilder.toString().toLowerCase()
    }

/**
 * 下划线转驼峰
 * _a_b_c_d_ ABCD
 *
 */
fun String?.underlineToHump(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    val strings = this.split("_").toTypedArray()
    return if (strings.isEmpty()) {
        ""
    } else {
        val stringBuilder = java.lang.StringBuilder()
        for (content in strings) {
            if (content.isNotEmpty()) {
                stringBuilder.append(Character.toLowerCase(content[0])).append(content.substring(1))
            }
        }
        stringBuilder.toString()
    }
}

/**
 * 判断是否是合法的 Java 类名
 */
fun String?.isJavaIdentifier() = !this.isNullOrEmpty() && this[0].isJavaIdentifierStart() && toCharArray().slice(IntRange(1, length - 1)).all { it.isJavaIdentifierPart() }

fun Char.isJavaIdentifierStart() = this in 'a'..'z' || this in 'A'..'Z' || Character.isJavaIdentifierStart(this)

fun Char.isJavaIdentifierPart() = this in '0'..'9' || this in 'a'..'z' || this in 'A'..'Z' || Character.isJavaIdentifierPart(this)
