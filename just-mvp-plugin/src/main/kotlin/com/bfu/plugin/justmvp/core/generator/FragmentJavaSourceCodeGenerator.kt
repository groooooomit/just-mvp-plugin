package com.bfu.plugin.justmvp.core.generator

/**
 * XxxFragment.java
 */
object FragmentJavaSourceCodeGenerator : SourceCodeGenerator {

    override fun generateFileName(prefixName: String) = "${prefixName}Fragment.java"

    override fun generateCode(packageName: String, prefixName: String) = """
package ${packageName};

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import just.mvp.PresenterFragment;

public class ${prefixName}Fragment extends PresenterFragment<${prefixName}Presenter> implements ${prefixName}Contract.View {

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

}
""".trim()

}