package com.bfu.plugin.justmvp.core.generator

import com.bfu.plugin.justmvp.core.ActionEventContext
import com.bfu.plugin.justmvp.core.GenerateOptions
import com.bfu.plugin.justmvp.core.LanguageType

class PresenterSourceCodeGenerator(context: ActionEventContext, options: GenerateOptions) : AbstractSourceCodeGenerator(context) {

    override val needContinue = options.isGeneratePresenter

    override val dirPath = context.triggerDir.path

    override val fileName = "${options.prefixName}Presenter${options.languageType.ext}"

    override val fileType = options.languageType.fileType

    override val sourceCode = when (options.languageType) {
        LanguageType.KOTLIN -> """
            |package ${context.triggerPackageName}
            |
            |import just.mvp.BasePresenter
            |
            |class ${options.prefixName}Presenter : BasePresenter<${options.prefixName}Contract.View>(), ${options.prefixName}Contract.Presenter {
            |
            |    override fun onInitialize() {
            |        // Presenter is ready, now you can initialize model resource.
            |    }
            |    
            |    override fun afterViewCreate() {
            |        // View is ready, now you can update view or get data from view. 
            |    }
            |
            |}
            """.trimMargin()
        LanguageType.JAVA -> """
            |package ${context.triggerPackageName};
            |
            |import just.mvp.BasePresenter;
            |
            |public class ${options.prefixName}Presenter extends BasePresenter<${options.prefixName}Contract.View> implements ${options.prefixName}Contract.Presenter {
            |
            |    @Override
            |    public void onInitialize() {
            |       // Presenter is ready, now you can initialize model resource.
            |    }
            |    
            |    @Override
            |    public void afterViewCreate() {
            |       // View is ready, now you can update view or get data from view.
            |    }
            |    
            |}
            """.trimMargin()
    }

}