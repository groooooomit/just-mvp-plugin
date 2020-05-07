package com.bfu.plugin.justmvp.core.generator

import com.bfu.plugin.justmvp.core.GenerateOptions
import com.bfu.plugin.justmvp.core.createChildSourceFileIfNotExists
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.vfs.VirtualFile

abstract class AbstractSourceCodeGenerator(private val options: GenerateOptions) : SourceCodeGenerator {

    final override fun generate() {
        if (needContinue) {
            WriteCommandAction.runWriteCommandAction(options.project) {
                dir.createChildSourceFileIfNotExists(options.project, fileName, sourceCode, fileType)
                    ?.let { OpenFileDescriptor(options.project, it) }?.navigate(true)
            }
        }
    }

    abstract val dir: VirtualFile

    abstract val fileName: String

    abstract val fileType: FileType

    abstract val needContinue: Boolean

    abstract val sourceCode: String

}