package com.bfu.plugin.justmvp.core.generator

/**
 * XxxPresenter.java
 */
object PresenterJavaSourceCodeGenerator : SourceCodeGenerator {

    override fun generateFileName(prefixName: String) = "${prefixName}Presenter.java"

    override fun generateCode(packageName: String, prefixName: String) = """
package ${packageName};

import just.mvp.BasePresenter;

public class ${prefixName}Presenter extends BasePresenter<${prefixName}Contract.View> implements ${prefixName}Contract.Presenter {

    @Override
    public void afterViewCreate() {

    }
    
}
""".trim()

}