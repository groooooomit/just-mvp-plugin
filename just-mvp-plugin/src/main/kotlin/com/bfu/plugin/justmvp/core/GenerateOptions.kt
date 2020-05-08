package com.bfu.plugin.justmvp.core

import kotlin.properties.Delegates

class GenerateOptions(private val callback: () -> Unit) {

    /**
     * 类名前缀
     */
    var prefixName by Delegates.observable<String?>(null) { _, _, _ -> callback() }

    /**
     * 是否生成 Contract 类
     */
    var isGenerateContract by Delegates.observable(false) { _, _, _ -> callback() }

    /**
     * 是否生成 Presenter 类
     */
    var isGeneratePresenter by Delegates.observable(false) { _, _, _ -> callback() }

    /**
     * 是否生成 Activity | Fragment | DialogFragment
     */
    var isGenerateView by Delegates.observable(false) { _, _, _ -> callback() }

    /**
     * 界面类型
     */
    var viewType by Delegates.observable(ViewType.ACTIVITY) { _, _, _ -> callback() }

    /**
     * 是否生成对应界面布局，isGenerateView 为 false，那么 isGenerateLayout 也为 false
     */
    var isGenerateLayout by Delegates.observable(false) { _, _, _ -> callback() }

    /**
     * 代码语言类型 Kotlin | Java
     */
    var languageType by Delegates.observable(LanguageType.KOTLIN) { _, _, _ -> callback() }

}