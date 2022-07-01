package com.umbat.skripsi_weather_app.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragment
import androidx.preference.PreferenceFragmentCompat
import com.umbat.skripsi_weather_app.R
import com.umbat.skripsi_weather_app.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        if (fragmentManager?.findFragmentById(android.R.id.content) == null) {
//            fragmentManager?.beginTransaction()
//                ?.add(android.R.id.content, SettingFragment())?.commit()
//        }

        return root
    }

    class SettingFragment : PreferenceFragment() {
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            addPreferencesFromResource(R.xml.preferences)
//        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
        }
    }
}