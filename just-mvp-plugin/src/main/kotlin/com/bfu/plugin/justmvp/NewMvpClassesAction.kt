package com.bfu.plugin.justmvp

import com.bfu.plugin.justmvp.core.ActionEventContext
import com.bfu.plugin.justmvp.core.generator.ContractSourceCodeGenerator
import com.bfu.plugin.justmvp.core.generator.LayoutSourceCodeGenerator
import com.bfu.plugin.justmvp.core.generator.PresenterSourceCodeGenerator
import com.bfu.plugin.justmvp.core.generator.ViewSourceCodeGenerator
import com.bfu.plugin.justmvp.ui.NewMvpClassesOptionsDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.fileEditor.OpenFileDescriptor

/**
 * 一键创建 Mvp 的 Kotlin 源码文件
 */
class NewMvpClassesAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val context = ActionEventContext(event)

        NewMvpClassesOptionsDialog.showDialog(context) {
            /* 生成 contract 并打开. */
            ContractSourceCodeGenerator(context, it).generate()?.let { file -> OpenFileDescriptor(context.project, file) }?.navigate(true)

            /* 生成 presenter 并打开. */
            PresenterSourceCodeGenerator(context, it).generate()?.let { file -> OpenFileDescriptor(context.project, file) }?.navigate(true)

            /* 生成 ui 并打开. */
            ViewSourceCodeGenerator(context, it).generate()?.let { file -> OpenFileDescriptor(context.project, file) }?.navigate(true)

            /* 生成布局并打开. */
            LayoutSourceCodeGenerator(context, it).generate()?.let { file -> OpenFileDescriptor(context.project, file) }?.navigate(true)
        }
    }

    private val regex = Regex("(?:[\\w:\\-/]+)?src/[\\w-]+/java(?:[\\w/\\-.]+)?")

    override fun update(event: AnActionEvent) {
        /* 只在 Android 项目的源码目录中显示新建选项. */
        event.presentation.isVisible = event.getData(DataKeys.VIRTUAL_FILE)?.path?.matches(regex) ?: false
    }

}