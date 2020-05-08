package com.bfu.plugin.justmvp.core.generator

import com.intellij.openapi.vfs.VirtualFile

/**
 * 源码构造器
 */
interface SourceCodeGenerator {

    /**
     * 生成源码文件
     */
    fun generate(): VirtualFile?

}