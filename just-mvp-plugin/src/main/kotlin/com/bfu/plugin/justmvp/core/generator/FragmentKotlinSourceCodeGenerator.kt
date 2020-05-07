package com.bfu.plugin.justmvp.core.generator

/**
 * XxxFragment.kt
 */
object FragmentKotlinSourceCodeGenerator : SourceCodeGenerator {

    override fun generateFileName(prefixName: String) = "${prefixName}Fragment.kt"

    override fun generateCode(packageName: String, prefixName: String) = """
package $packageName

import android.os.Bundle
import android.view.View
import just.mvp.PresenterFragment

class ${prefixName}Fragment : PresenterFragment<${prefixName}Presenter>(), ${prefixName}Contract.View {

    override fun getLayoutResId() = TODO("not implemented")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}
""".trim()

}