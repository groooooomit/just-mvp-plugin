package com.bfu.plugin.justmvp.core.generator

import com.bfu.plugin.justmvp.core.GenerateOptions
import com.bfu.plugin.justmvp.core.LanguageType

class PresenterSourceCodeGenerator(options: GenerateOptions) : AbstractSourceCodeGenerator(options) {

    override val needContinue = options.isGeneratePresenter

    override val dir = options.targetDir

    override val fileName = "${options.prefixName}Presenter${options.languageType.ext}"

    override val fileType = options.languageType.fileType

    override val sourceCode = when (options.languageType) {
        LanguageType.KOTLIN -> """
            |package ${options.packageName}
            |
            |import just.mvp.BasePresenter
            |
            |class ${options.prefixName}Presenter : BasePresenter<${options.prefixName}Contract.View>(), ${options.prefixName}Contract.Presenter {
            |
            |    override fun afterViewCreate() {
            |    
            |    }
            |
            |}
            """.trimMargin()
        LanguageType.JAVA -> """
            |package ${options.packageName};
            |
            |import just.mvp.BasePresenter;
            |
            |public class ${options.prefixName}Presenter extends BasePresenter<${options.prefixName}Contract.View> implements ${options.prefixName}Contract.Presenter {
            |
            |    @Override
            |    public void afterViewCreate() {
            |
            |    }
            |    
            |}
            """.trimMargin()
    }

}