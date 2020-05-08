package com.bfu.plugin.justmvp.core

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.xml.XmlDocument

/**
 * 提供当前 Action 的一些环境信息
 */
class ActionEventContext(event: AnActionEvent) {

    /**
     * 全局项目
     */
    val project: Project = event.project!!

    /**
     * 事件触发时的所在文件目录，在哪里右键，目录就是那里
     * ...src/main/java/com/bfu/a/b/c
     */
    val triggerDir: VirtualFile = event.getData(DataKeys.VIRTUAL_FILE)!!.let { if (it.isDirectory) it else it.parent }

    /**
     * 目录包名文件路径
     * com/bfu/a/b/c
     */
    val triggerPackagePath = triggerDir.path.substringAfter("/java/")

    /**
     * 包路径名
     * com.bfu.a.b.c
     */
    val triggerPackageName: String = triggerPackagePath.replace('/', '.')

    /**
     * Java 源码父目录
     * ...src/main/java
     */
    private val javaSourceDir: VirtualFile = triggerDir.fileSystem.findFileByPath(triggerDir.path.substringBefore("/${triggerPackagePath}"))!!

    /**
     * 变体目录文件
     * ...src/main
     */
    private val variantDir: VirtualFile = javaSourceDir.parent

    /**
     * layout 文件夹路径
     */
    val layoutDirPath: String = "${variantDir.path}/res/layout"

    /**
     * Project 名称
     */
    private val outFolderName: String = variantDir.parent.parent.parent.name

    /**
     * app/src/main/java/com/bfu/a/b/c
     */
    val simpleTriggerPath: String = triggerDir.path.substringAfter("$outFolderName/")

    /**
     * src/main/res/layout
     */
    val simpleLayoutDirPath: String = layoutDirPath.substringAfter("$outFolderName/")

    /**
     * 当前应用程序的包名，通过解析 Manifest 文件得到，在获取布局文件 ID 等场景中使用
     */
    val appPackageName: String? = "${variantDir.path}/AndroidManifest.xml"
        .let { triggerDir.fileSystem.findFileByPath(it) }
        ?.let { PsiManager.getInstance(project).findFile(it) }
        ?.let { it.firstChild as? XmlDocument }?.rootTag?.getAttribute("package")?.value
}