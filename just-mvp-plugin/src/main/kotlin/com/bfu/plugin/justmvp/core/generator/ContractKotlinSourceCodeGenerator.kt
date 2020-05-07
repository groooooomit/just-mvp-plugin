package com.bfu.plugin.justmvp.core.generator

/**
 * XxxContract.kt
 */
object ContractKotlinSourceCodeGenerator : SourceCodeGenerator {

    override fun generateFileName(prefixName: String) = "${prefixName}Contract.kt"

    override fun generateCode(packageName: String, prefixName: String) = """
package $packageName 

import just.mvp.base.IPresenter
import just.mvp.base.IView

interface ${prefixName}Contract {

    interface View : IView 

    interface Presenter : IPresenter<View>
    
}
""".trim()

}