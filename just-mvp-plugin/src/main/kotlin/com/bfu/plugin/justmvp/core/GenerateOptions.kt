package com.bfu.plugin.justmvp.core

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kotlin.properties.Delegates

class GenerateOptions(event: AnActionEvent, private val callback: () -> Unit) {

    /**
     * 全局项目
     */
    val project: Project = event.project!!

    /**
     * 事件触发时的所在文件目录
     */
    val targetDir: VirtualFile = event.getData(DataKeys.VIRTUAL_FILE)!!.let { if (it.isDirectory) it else it.parent }

    /**
     * 布局目录
     */
    val layoutDir: VirtualFile = targetDir.fileSystem.findFileByPath(targetDir.path.substringBefore("java/") + "res/layout")!!

    /**
     * 包路径名
     */
    val packageName: String = targetDir.path.replace('/', '.').substringAfter(".java.")

    /**
     * 变体名称
     */
    val variantName: String = targetDir.path.substringAfter("/src/").substringBefore("/java/")

    /**
     * 类名前缀
     */
    var prefixName by Delegates.observable<String?>(null) { _, _, _ -> callback() }

    /**
     * 是否生成 Contract 类
     */
    var isGenerateContract by Delegates.observable(false) { _, _, _ -> callback() }

    var isGeneratePresenter by Delegates.observable(false) { _, _, _ -> callback() }

    var isGenerateView by Delegates.observable(false) { _, _, _ -> callback() }

    var viewType by Delegates.observable(ViewType.ACTIVITY) { _, _, _ -> callback() }

    var isGenerateLayout by Delegates.observable(false) { _, _, _ -> callback() }

    var languageType by Delegates.observable(LanguageType.KOTLIN) { _, _, _ -> callback() }

}