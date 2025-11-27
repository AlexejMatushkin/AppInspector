package com.practicum.appinspector.ui.app_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.practicum.appinspector.R
import com.practicum.appinspector.databinding.FragmentAppDetailsBinding

class AppDetailsFragment : Fragment(R.layout.fragment_app_details) {

    private var _binding: FragmentAppDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAppDetailsBinding.bind(view)

        val packageName = requireArguments().getString("package")!!

        val pm = requireActivity().packageManager
        try {
            val appInfo = pm.getApplicationInfo(packageName, 0)
            val icon = pm.getApplicationIcon(appInfo)
            val label = pm.getApplicationLabel(appInfo).toString()

            binding.toolbar.apply {
                title = label
                navigationIcon = icon
                setNavigationOnClickListener { findNavController().popBackStack() }
            }
        } catch (e: Exception) {
            binding.toolbar.apply {
                title = getString(R.string.app_name)
                setNavigationOnClickListener { findNavController().popBackStack() }
            }
        }

        val viewModel = ViewModelProvider(
            this,
            AppDetailsFactory(requireActivity().application, packageName)
        )[AppDetailsViewModel::class.java]

        binding.appPackage.text = getString(R.string.package_label, packageName)

        viewModel.name.observe(viewLifecycleOwner) { name ->
            binding.appName.text = name
        }

        viewModel.version.observe(viewLifecycleOwner) { version ->
            binding.appVersion.text = getString(
                R.string.version_label,
                version ?: "—"
            )
        }

        viewModel.checksum.observe(viewLifecycleOwner) { checksum ->
            binding.appChecksum.text = getString(
                R.string.checksum_label,
                checksum ?: getString(R.string.calculating_checksum)
            )
        }

        binding.openBtn.apply {
            text = getString(R.string.open_app_button)
            setOnClickListener {
                val intent = pm.getLaunchIntentForPackage(packageName)
                if (intent != null) startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
