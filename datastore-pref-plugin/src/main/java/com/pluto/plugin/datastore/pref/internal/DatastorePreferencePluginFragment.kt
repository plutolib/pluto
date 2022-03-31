package com.pluto.plugin.datastore.pref.internal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pluto.plugin.datastore.pref.internal.compose.DataStorePrefComposable

class DatastorePreferencePluginFragment : Fragment() {

    private val viewModel by viewModels<DatastorePreferencePluginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(context = context!!).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
        setContent {
            val state = viewModel.output.collectAsState(initial = null)
            state.value?.let {
                DataStorePrefComposable(
                    it,
                    modifier = Modifier.background(Color(0xFFFFFFFF)),
                    onExit = {
                        activity?.finish()
                    },
                    updateValue = viewModel.updateValue
                )
            }
        }
    }
}