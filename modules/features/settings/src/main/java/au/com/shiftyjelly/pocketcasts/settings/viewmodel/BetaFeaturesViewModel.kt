package au.com.shiftyjelly.pocketcasts.settings.viewmodel

import androidx.lifecycle.ViewModel
import au.com.shiftyjelly.pocketcasts.utils.featureflag.Feature
import au.com.shiftyjelly.pocketcasts.utils.featureflag.FeatureFlag
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class BetaFeaturesViewModel @Inject constructor() : ViewModel() {

    data class State(
        val featureFlags: List<FeatureFlagWrapper>,
    )

    private val _state = MutableStateFlow(
        State(featureFlags = featureFlags),
    )
    val state: StateFlow<State> = _state

    private val featureFlags: List<FeatureFlagWrapper>
        get() = Feature.entries
            .filter { it.hasDevToggle }
            .sortedBy { it.title }
            .map {
                FeatureFlagWrapper(
                    featureFlag = it,
                    isEnabled = FeatureFlag.isEnabled(it),
                )
            }

    fun setFeatureEnabled(feature: Feature, enabled: Boolean) {
        FeatureFlag.setEnabled(feature, enabled)
        _state.value = _state.value.copy(
            featureFlags = featureFlags,
        )
    }

    data class FeatureFlagWrapper(
        val featureFlag: Feature,
        val isEnabled: Boolean,
    )
}
