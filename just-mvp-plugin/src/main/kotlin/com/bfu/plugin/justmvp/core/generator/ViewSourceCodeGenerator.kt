package com.bfu.plugin.justmvp.core.generator

import com.bfu.plugin.justmvp.core.*

class ViewSourceCodeGenerator(context: ActionEventContext, options: GenerateOptions) : AbstractSourceCodeGenerator(context) {

    override val needContinue = options.isGenerateView

    override val dirPath = context.triggerDir.path

    override val fileName = "${options.prefixName}${options.viewType.nick}${options.languageType.ext}"

    override val fileType = options.languageType.fileType

    private val layoutName = "${options.viewType.layoutPrefix}${options.prefixName.humpToUnderline()}"

    override val sourceCode = when (options.languageType) {
        LanguageType.KOTLIN -> {
            when (options.viewType) {
                ViewType.ACTIVITY -> """
                    |package ${context.triggerPackageName}
                    |
                    |import android.os.Bundle
                    |import just.mvp.PresenterActivity
                    |${if (options.isGenerateLayout && !context.appPackageName.isNullOrEmpty()) "import ${context.appPackageName}.R" else ""}
                    |
                    |class ${options.prefixName}Activity : PresenterActivity<${options.prefixName}Presenter>(), ${options.prefixName}Contract.View {
                    |
                    |    override fun onCreate(savedInstanceState: Bundle?) {
                    |        super.onCreate(savedInstanceState)
                    |        ${if (options.isGenerateLayout) "setContentView(R.layout.${layoutName})" else ""}
                    |     }
                    |
                    |}
                    """.trimMargin()

                ViewType.FRAGMENT -> """
                    |package ${context.triggerPackageName}
                    |
                    |import android.os.Bundle
                    |import android.view.View
                    |import just.mvp.PresenterFragment
                    |${if (options.isGenerateLayout && !context.appPackageName.isNullOrEmpty()) "import ${context.appPackageName}.R" else ""}
                    |
                    |class ${options.prefixName}Fragment : PresenterFragment<${options.prefixName}Presenter>(), ${options.prefixName}Contract.View {
                    |
                    |    override fun getLayoutResId() = ${if (options.isGenerateLayout) "R.layout.${layoutName}" else "0"}
                    |
                    |    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                    |
                    |    }
                    |
                    |}
                    """.trimMargin()

                ViewType.DIALOG_FRAGMENT -> """
                    |package ${context.triggerPackageName}
                    |
                    |import android.os.Bundle
                    |import android.view.View
                    |import just.mvp.PresenterDialogFragment
                    |${if (options.isGenerateLayout && !context.appPackageName.isNullOrEmpty()) "import ${context.appPackageName}.R" else ""}
                    |
                    |class ${options.prefixName}DialogFragment : PresenterDialogFragment<${options.prefixName}Presenter>(), ${options.prefixName}Contract.View {
                    |
                    |    override fun getLayoutResId() = ${if (options.isGenerateLayout) "R.layout.${layoutName}" else "0"}
                    |
                    |    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                    |
                    |    }
                    |
                    |}
                    """.trimMargin()
            }
        }
        LanguageType.JAVA -> {
            when (options.viewType) {
                ViewType.ACTIVITY -> """
                    |package ${context.triggerPackageName};
                    |
                    |import android.os.Bundle;
                    |import androidx.annotation.Nullable;
                    |import just.mvp.PresenterActivity;
                    |${if (options.isGenerateLayout && !context.appPackageName.isNullOrEmpty()) "import ${context.appPackageName}.R;" else ""}
                    |
                    |public class ${options.prefixName}Activity extends PresenterActivity<${options.prefixName}Presenter> implements ${options.prefixName}Contract.View {
                    |
                    |    @Override
                    |    protected void onCreate(@Nullable Bundle savedInstanceState) {
                    |        super.onCreate(savedInstanceState);
                    |       ${if (options.isGenerateLayout) "setContentView(R.layout.${layoutName});" else ""}
                    |    }
                    |
                    |}
                    """.trimMargin()

                ViewType.FRAGMENT -> """
                    |package ${context.triggerPackageName};
                    |
                    |import android.os.Bundle;
                    |import android.view.View;
                    |
                    |import androidx.annotation.NonNull;
                    |import androidx.annotation.Nullable;
                    |${if (options.isGenerateLayout && !context.appPackageName.isNullOrEmpty()) "import ${context.appPackageName}.R;" else ""}
                    |
                    |import just.mvp.PresenterFragment;
                    |
                    |public class ${options.prefixName}Fragment extends PresenterFragment<${options.prefixName}Presenter> implements ${options.prefixName}Contract.View {
                    |
                    |    @Override
                    |    protected int getLayoutResId() {
                    |        return ${if (options.isGenerateLayout) "R.layout.${layoutName}" else "0"};
                    |    }
                    |
                    |    @Override
                    |    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
                    |
                    |    }
                    |
                    |}
                    """.trimMargin()

                ViewType.DIALOG_FRAGMENT -> """
                    |package ${context.triggerPackageName};
                    |
                    |import android.os.Bundle;
                    |import android.view.View;
                    |
                    |import androidx.annotation.NonNull;
                    |import androidx.annotation.Nullable;
                    |${if (options.isGenerateLayout && !context.appPackageName.isNullOrEmpty()) "import ${context.appPackageName}.R;" else ""}
                    |
                    |import just.mvp.PresenterDialogFragment;
                    |
                    |public class ${options.prefixName}DialogFragment extends PresenterDialogFragment<${options.prefixName}Presenter> implements ${options.prefixName}Contract.View {
                    |
                    |    @Override
                    |    protected int getLayoutResId() {
                    |        return ${if (options.isGenerateLayout) "R.layout.${layoutName}" else "0"};
                    |    }
                    |
                    |    @Override
                    |    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
                    |
                    |    }
                    |    
                    |}
                    """.trimMargin()
            }
        }
    }
}