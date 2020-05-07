package com.bfu.plugin.justmvp

import com.bfu.plugin.justmvp.core.ClassNameValidator
import com.bfu.plugin.justmvp.core.createChildSourceFileIfNotExists
import com.bfu.plugin.justmvp.core.generator.ActivityJavaSourceCodeGenerator
import com.bfu.plugin.justmvp.core.generator.ContractJavaSourceCodeGenerator
import com.bfu.plugin.justmvp.core.generator.PresenterJavaSourceCodeGenerator
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiNameHelper

/**
 * 一键创建 Mvp 的 Java 源码文件
 */
class NewJavaMvpClassesAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {

        /* 获取 Project 对象. */
        val project = event.project ?: return

        /* 获取事件触发时的所在文件目录. */
        val targetDir = event.getData(DataKeys.VIRTUAL_FILE)?.let { if (it.isDirectory) it else it.parent } ?: return

        /* 提示用户输入名称. */
        val prefixName = Messages.showInputDialog(project, "Contract Name", "New Java Mvp Classes", Messages.getInformationIcon(), null, ClassNameValidator(PsiNameHelper.getInstance(project), "Invalid Java classes name.")) ?: return

        /* 包名. */
        val packageName = targetDir.path.replace('/', '.').substringAfter(".java.")

        /* 生成 XxxContract.java 文件并打开. */
        val contractCode = ContractJavaSourceCodeGenerator.generateCode(packageName, prefixName)
        val contractFileName = ContractJavaSourceCodeGenerator.generateFileName(prefixName)
        WriteCommandAction.runWriteCommandAction(project) { targetDir.createChildSourceFileIfNotExists(project, contractFileName, contractCode, JavaFileType.INSTANCE)?.let { OpenFileDescriptor(project, it) }?.navigate(true) }

        /* 生成 XxxPresenter.java 文件并打开. */
        val presenterCode = PresenterJavaSourceCodeGenerator.generateCode(packageName, prefixName)
        val presenterFileName = PresenterJavaSourceCodeGenerator.generateFileName(prefixName)
        WriteCommandAction.runWriteCommandAction(project) { targetDir.createChildSourceFileIfNotExists(project, presenterFileName, presenterCode, JavaFileType.INSTANCE)?.let { OpenFileDescriptor(project, it) }?.navigate(true) }

        /* 生成 XxxActivity.java 文件并打开. */
        val activityCode = ActivityJavaSourceCodeGenerator.generateCode(packageName, prefixName)
        val activityFileName = ActivityJavaSourceCodeGenerator.generateFileName(prefixName)
        WriteCommandAction.runWriteCommandAction(project) { targetDir.createChildSourceFileIfNotExists(project, activityFileName, activityCode, JavaFileType.INSTANCE)?.let { OpenFileDescriptor(project, it) }?.navigate(true) }

    }


}