package com.bfu.plugin.justmvp.core

enum class ViewType(val nick: String, val layoutPrefix: String) {
    ACTIVITY("Activity", "activity_"),
    FRAGMENT("Fragment", "fragment_"),
    DIALOG_FRAGMENT("DialogFragment", "fragment_dialog_");
}