package com.bfu.plugin.justmvp.core.generator

/**
 * XxxPresenter.kt
 */
object PresenterKotlinSourceCodeGenerator : SourceCodeGenerator {

    override fun generateFileName(prefixName: String) = "${prefixName}Presenter.kt"

    override fun generateCode(packageName: String, prefixName: String) = """
package $packageName

import just.mvp.BasePresenter

class ${prefixName}Presenter : BasePresenter<${prefixName}Contract.View>(), ${prefixName}Contract.Presenter {

    override fun afterViewCreate() {
    
    }

}
""".trim()

}