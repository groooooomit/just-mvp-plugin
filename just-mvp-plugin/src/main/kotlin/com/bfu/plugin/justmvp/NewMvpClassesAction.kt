package com.bfu.plugin.justmvp

import com.bfu.plugin.justmvp.core.generator.ContractSourceCodeGenerator
import com.bfu.plugin.justmvp.core.generator.LayoutSourceCodeGenerator
import com.bfu.plugin.justmvp.core.generator.PresenterSourceCodeGenerator
import com.bfu.plugin.justmvp.core.generator.ViewSourceCodeGenerator
import com.bfu.plugin.justmvp.ui.NewMvpClassesOptionsDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys

/**
 * 一键创建 Mvp 的 Kotlin 源码文件
 */
class NewMvpClassesAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {

        NewMvpClassesOptionsDialog.showDialog(event) {
            /* 生成 contract 并打开. */
            ContractSourceCodeGenerator(it).generate()

            /* 生成 presenter 并打开. */
            PresenterSourceCodeGenerator(it).generate()

            /* 生成 ui 并打开. */
            ViewSourceCodeGenerator(it).generate()

            /* 生成布局并打开. */
            LayoutSourceCodeGenerator(it).generate()
        }
    }

    private val regex = Regex("(?:[\\w:\\-/]+)?src/[\\w-]+/java(?:[\\w/\\-.]+)?")

    override fun update(event: AnActionEvent) {
        /* 只在 Android 项目的源码目录中显示新建选项. */
        event.presentation.isVisible = event.getData(DataKeys.VIRTUAL_FILE)?.path?.matches(regex) ?: false
    }

}