package com.pluto.plugins.datastore.pref

import androidx.fragment.app.Fragment
import com.pluto.plugin.DeveloperDetails
import com.pluto.plugin.Plugin
import com.pluto.plugin.PluginConfiguration
import com.pluto.plugins.datastore.pref.internal.BaseFragment

class PlutoDatastorePreferencesPlugin(identifier: String) : Plugin(identifier) {

    override fun getConfig() = PluginConfiguration(
        name = "DataStore Preferences",
        version = BuildConfig.VERSION_NAME,
        icon = R.drawable.pluto_dts___ic_logo
    )

    override fun getDeveloperDetails(): DeveloperDetails {
        return DeveloperDetails(
            website = "https://plutolib.com",
            vcsLink = "https://github.com/plutolib/pluto",
            twitter = "https://twitter.com/pluto_lib"
        )
    }

    override fun getView(): Fragment {
        return BaseFragment()
    }

    override fun onPluginInstalled() {}

    override fun onPluginDataCleared() {}
}
