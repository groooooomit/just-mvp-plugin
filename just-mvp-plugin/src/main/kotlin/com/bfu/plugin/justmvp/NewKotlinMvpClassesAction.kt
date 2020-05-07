package com.bfu.plugin.justmvp

import com.bfu.plugin.justmvp.core.ClassNameValidator
import com.bfu.plugin.justmvp.core.KotlinFileType
import com.bfu.plugin.justmvp.core.createChildSourceFileIfNotExists
import com.bfu.plugin.justmvp.core.generator.ActivityKotlinSourceCodeGenerator
import com.bfu.plugin.justmvp.core.generator.ContractKotlinSourceCodeGenerator
import com.bfu.plugin.justmvp.core.generator.PresenterKotlinSourceCodeGenerator
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiNameHelper

/**
 * 一键创建 Mvp 的 Kotlin 源码文件
 */
class NewKotlinMvpClassesAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {

        /* 获取 Project 对象. */
        val project = event.project ?: return

        /* 获取事件触发时的所在文件目录. */
        val targetDir = event.getData(DataKeys.VIRTUAL_FILE)?.let { if (it.isDirectory) it else it.parent } ?: return

        /* 提示用户输入名称. */
        val prefixName = Messages.showInputDialog(project, "Contract Name", "New Kotlin Mvp Classes", Messages.getInformationIcon(), null, ClassNameValidator(PsiNameHelper.getInstance(project), "Invalid Kotlin classes name.")) ?: return

        /* 包名. */
        val packageName = targetDir.path.replace('/', '.').substringAfter(".java.")

        /* 生成 XxxContract.kt 文件并打开. */
        val contractCode = ContractKotlinSourceCodeGenerator.generateCode(packageName, prefixName)
        val contractFileName = ContractKotlinSourceCodeGenerator.generateFileName(prefixName)
        WriteCommandAction.runWriteCommandAction(project) { targetDir.createChildSourceFileIfNotExists(project, contractFileName, contractCode, KotlinFileType)?.let { OpenFileDescriptor(project, it) }?.navigate(true) }

        /* 生成 XxxPresenter.kt 文件并打开. */
        val presenterCode = PresenterKotlinSourceCodeGenerator.generateCode(packageName, prefixName)
        val presenterFileName = PresenterKotlinSourceCodeGenerator.generateFileName(prefixName)
        WriteCommandAction.runWriteCommandAction(project) { targetDir.createChildSourceFileIfNotExists(project, presenterFileName, presenterCode, KotlinFileType)?.let { OpenFileDescriptor(project, it) }?.navigate(true) }

        /* 生成 XxxActivity.kt 文件并打开. */
        val activityCode = ActivityKotlinSourceCodeGenerator.generateCode(packageName, prefixName)
        val activityFileName = ActivityKotlinSourceCodeGenerator.generateFileName(prefixName)
        WriteCommandAction.runWriteCommandAction(project) { targetDir.createChildSourceFileIfNotExists(project, activityFileName, activityCode, KotlinFileType)?.let { OpenFileDescriptor(project, it) }?.navigate(true) }

    }

}