# Just Mvp Generator
快速生成 [just-mvp](https://github.com/groooooomit/just-mvp) 模版代码的 AndroidStudio 插件，项目由 gradle 构建，除 UI 外其他功能均由 kotlin 实现。

## 添加到你的 AndroidStudio
> File -> Settings -> Plugins -> Marketplace -> 搜索 [Just Mvp Generator](https://plugins.jetbrains.com/plugin/14273-just-mvp-generator)，点击 install 进行插件安装，安装完成后根据提示重启 AndroidStudio。

## 开始使用
> 在想要放置代码的源码目录或同级文件名上依次点击右键 -> New -> Just Mvp Classes 打开选项对话框，按需勾选需要生成的源码文件，默认包括布局在内全部生成，在 Preview 一栏显示的绿色的文件名列表是即将生成的文件情况。  
如果当前目录已经存在同名文件，那么该文件将不会被重新生成，以免误将现有文件覆盖，在 Preview 一栏文件名显示为灰色。  

![一键生成 MVP 模板代码](https://raw.githubusercontent.com/groooooomit/just-mvp-plugin/master/screenshots/just-mvp-plugin-demo.gif)
