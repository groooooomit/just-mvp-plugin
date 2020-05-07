package com.bfu.plugin.justmvp.core.generator

/**
 * 源码构造器
 */
interface SourceCodeGenerator {

    /**
     * 构造文件名
     */
    fun generateFileName(prefixName: String): String

    /**
     * 构造源码
     *
     */
    fun generateCode(packageName: String, prefixName: String): String

}