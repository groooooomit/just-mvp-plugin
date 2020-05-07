package com.bfu.plugin.justmvp.core.generator

/**
 * XxxDialogFragment.kt
 */
object DialogFragmentKotlinSourceCodeGenerator : SourceCodeGenerator {

    override fun generateFileName(prefixName: String) = "${prefixName}DialogFragment.kt"

    override fun generateCode(packageName: String, prefixName: String) = """
package $packageName

import android.os.Bundle
import android.view.View
import just.mvp.PresenterDialogFragment

class ${prefixName}Fragment : PresenterDialogFragment<${prefixName}Presenter>(), ${prefixName}Contract.View {

    override fun getLayoutResId() = TODO("not implemented")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}
""".trim()

}