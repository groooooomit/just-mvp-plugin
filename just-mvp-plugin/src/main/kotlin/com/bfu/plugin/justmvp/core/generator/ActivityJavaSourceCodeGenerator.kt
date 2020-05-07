package com.bfu.plugin.justmvp.core.generator

/**
 * XxxActivity.java
 */
object ActivityJavaSourceCodeGenerator : SourceCodeGenerator {

    override fun generateFileName(prefixName: String) = "${prefixName}Activity.java"

    override fun generateCode(packageName: String, prefixName: String) = """
package ${packageName};

import android.os.Bundle;

import androidx.annotation.Nullable;

import just.mvp.PresenterActivity;

public class ${prefixName}Activity extends PresenterActivity<${prefixName}Contract.Presenter>, ${prefixName}Contract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
""".trim()

}