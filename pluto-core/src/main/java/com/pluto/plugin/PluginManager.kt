package com.pluto.plugin

import android.app.Application
import com.pluto.plugin.utilities.DebugLog

internal class PluginManager {

    private var plugins = arrayListOf<Plugin>()
    internal val installedPlugins: List<Plugin>
        get() {
            val list = arrayListOf<Plugin>()
            list.addAll(plugins)
            return list
        }

    fun install(application: Application, plugins: ArrayList<Plugin>) {
        plugins.forEach {
            if (it.shouldInstallPlugin()) {
                it.install(application)
                this.plugins.add(it)
            } else {
                DebugLog.e("pluto_plugin", "${it.getConfig().name} not installed (reason: condition mismatch).")
            }
        }
    }

    fun get(identifier: String): Plugin? {
        return plugins.firstOrNull {
            it.getConfig().identifier == identifier
        }
    }
}
