package com.aptopayments.sdk.queue

import android.webkit.WebView
import java.util.Collections
import kotlin.properties.Delegates

internal open class WebViewJSActionsQueue(private val webView: WebView) {

    private val actions = Collections.synchronizedList(mutableListOf<String>())

    var isSuspended: Boolean by Delegates.observable(true) { _, _, _ ->
        runPendingTasksIfNeeded()
    }

    open fun addAction(action: String) {
        addActionToList(action)
        runPendingTasksIfNeeded()
    }

    private fun runPendingTasksIfNeeded() {
        if (!isSuspended) {
            runPendingTasks()
        }
    }

    private fun runPendingTasks() {
        actions.firstOrNull()?.let { action ->
            webView.evaluateJavascript(action) {
                removeActionFromList(action)
                runPendingTasks()
            }
        }
    }

    private fun addActionToList(action: String) {
        synchronized(actions) {
            actions.add(action)
        }
    }

    private fun removeActionFromList(action: String) {
        synchronized(actions) {
            actions.remove(action)
        }
    }
}
