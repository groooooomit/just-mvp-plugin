package com.bfu.plugin.justmvp.core.generator

import com.bfu.plugin.justmvp.core.ActionEventContext
import com.bfu.plugin.justmvp.core.GenerateOptions
import com.bfu.plugin.justmvp.core.LanguageType

class ContractSourceCodeGenerator(context: ActionEventContext, options: GenerateOptions) : AbstractSourceCodeGenerator(context) {

    override val needContinue = options.isGenerateContract

    override val dirPath = context.triggerDir.path

    override val fileName = "${options.prefixName}Contract${options.languageType.ext}"

    override val fileType = options.languageType.fileType

    override val sourceCode = when (options.languageType) {
        LanguageType.KOTLIN -> """
            |package ${context.triggerPackageName}
            |
            |import just.mvp.base.IPresenter
            |import just.mvp.base.IView
            |
            |interface ${options.prefixName}Contract {
            |
            |    interface View : IView
            |
            |    interface Presenter : IPresenter<View>
            |
            |}
            """.trimMargin()
        LanguageType.JAVA -> """
            |package ${context.triggerPackageName};
            |
            |import just.mvp.base.IPresenter;
            |import just.mvp.base.IView;
            |
            |public interface ${options.prefixName}Contract {
            |
            |    interface View extends IView {
            |    
            |    }
            |    
            |    interface Presenter extends IPresenter<View> {
            |    
            |    }
            |    
            |}
            """.trimMargin()
    }

}