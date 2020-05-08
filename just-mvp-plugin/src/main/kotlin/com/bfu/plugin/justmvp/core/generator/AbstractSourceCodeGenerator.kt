package com.bfu.plugin.justmvp.core.generator

import com.bfu.plugin.justmvp.core.ActionEventContext
import com.bfu.plugin.justmvp.core.createChildSourceFileIfNotExists
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.util.Computable
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile

abstract class AbstractSourceCodeGenerator(private val context: ActionEventContext) : SourceCodeGenerator {

    final override fun generate(): VirtualFile? =
        if (needContinue) {
            WriteCommandAction.runWriteCommandAction(context.project, Computable {
                VfsUtil
                    /* 先创建文件所在目录. */
                    .createDirectories(dirPath)

                    /* 当文件不存在时才创建文件，防止覆盖原有的文件. */
                    .createChildSourceFileIfNotExists(context.project, fileName, sourceCode, fileType)
            })
        } else null

    /**
     * 源码生成的目标目录
     */
    abstract val dirPath: String

    /**
     * 文件名
     */
    abstract val fileName: String

    /**
     * 文件类型
     */
    abstract val fileType: FileType

    /**
     * 是否需要创建
     */
    abstract val needContinue: Boolean

    /**
     * 源码内容
     */
    abstract val sourceCode: String

}