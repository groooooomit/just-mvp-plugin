package com.bfu.plugin.justmvp.core.generator

import com.bfu.plugin.justmvp.core.GenerateOptions
import com.bfu.plugin.justmvp.core.humpToUnderline
import com.intellij.ide.highlighter.XmlFileType
import com.intellij.openapi.fileTypes.FileType

/**
 * 布局文件
 */
class LayoutSourceCodeGenerator(options: GenerateOptions) : AbstractSourceCodeGenerator(options) {

    override val needContinue = options.isGenerateLayout

    override val dir = options.layoutDir

    override val fileName = "${options.viewType.layoutPrefix}${options.prefixName.humpToUnderline()}.xml"

    override val fileType: FileType = XmlFileType.INSTANCE

    override val sourceCode = """
        |<?xml version="1.0" encoding="utf-8"?>
        |<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        |        xmlns:app="http://schemas.android.com/apk/res-auto"
        |        xmlns:tools="http://schemas.android.com/tools"
        |        android:layout_width="match_parent"
        |        android:layout_height="match_parent"
        |        tools:context="${options.packageName}.${options.prefixName}${options.viewType.nick}">
        |
        |    <TextView
        |            android:id="@+id/txt_token"
        |            android:layout_width="wrap_content"
        |            android:layout_height="wrap_content"
        |            android:text="Hello World!"
        |            app:layout_constraintBottom_toBottomOf="parent"
        |            app:layout_constraintLeft_toLeftOf="parent"
        |            app:layout_constraintRight_toRightOf="parent"
        |            app:layout_constraintTop_toTopOf="parent" />
        |
        |</androidx.constraintlayout.widget.ConstraintLayout>
        """.trimMargin()

}