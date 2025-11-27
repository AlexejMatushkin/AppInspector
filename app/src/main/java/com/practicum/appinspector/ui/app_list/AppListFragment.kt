package com.practicum.appinspector.ui.app_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.appinspector.R
import com.practicum.appinspector.databinding.FragmentAppListBinding

class AppListFragment : Fragment(R.layout.fragment_app_list) {

    private var _binding: FragmentAppListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AppListViewModel
    private lateinit var adapter: AppListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAppListBinding.bind(view)

        viewModel = ViewModelProvider(this)[AppListViewModel::class.java]

        adapter = AppListAdapter { app ->
            findNavController().navigate(
                R.id.openDetails,
                Bundle().apply { putString("package", app.packageName) }
            )
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerView.adapter = adapter

        viewModel.apps.observe(viewLifecycleOwner) { list ->
            adapter.submit(list)
        }

        viewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
