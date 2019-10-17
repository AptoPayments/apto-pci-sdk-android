package com.aptopayments.sdk.queue

import android.webkit.WebView
import kotlin.properties.Delegates

internal open class WebViewJSActionsQueue(private val webView: WebView) {
    private val actions: MutableList<String> = mutableListOf()

    var isSuspended: Boolean by Delegates.observable(true) { _, _, _ ->
        runPendingTasksIfNeeded()
    }

    open fun addAction(action: String) {
        actions.add(action)
        runPendingTasksIfNeeded()
    }

    private fun runPendingTasksIfNeeded() {
        if (!isSuspended) runPendingTasks()
    }

    private fun runPendingTasks() {
        actions.firstOrNull()?.let {
            webView.evaluateJavascript(it) {
                actions.removeAt(0)
                runPendingTasks()
            }
        }
    }
}
