package com.bfu.plugin.justmvp.core.generator

/**
 * XxxContract.java
 */
object ContractJavaSourceCodeGenerator : SourceCodeGenerator {

    override fun generateFileName(prefixName: String) = "${prefixName}Contract.java"

    override fun generateCode(packageName: String, prefixName: String) = """
package ${packageName};

import just.mvp.base.IPresenter;
import just.mvp.base.IView;

public interface ${prefixName}Contract {

    interface View extends IView {

    }

    interface Presenter extends IPresenter<View> {
        
    }
}
""".trim()

}