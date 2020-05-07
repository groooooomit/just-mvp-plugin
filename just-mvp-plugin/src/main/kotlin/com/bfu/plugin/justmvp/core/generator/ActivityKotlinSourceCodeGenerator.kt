package com.bfu.plugin.justmvp.core.generator

/**
 * XxxActivity.kt
 */
object ActivityKotlinSourceCodeGenerator : SourceCodeGenerator {

    override fun generateFileName(prefixName: String) = "${prefixName}Activity.kt"

    override fun generateCode(packageName: String, prefixName: String) = """
package $packageName

import android.os.Bundle
import just.mvp.PresenterActivity

class ${prefixName}Activity : PresenterActivity<${prefixName}Presenter>(), ${prefixName}Contract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
     }

}
""".trim()

}