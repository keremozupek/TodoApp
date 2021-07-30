package com.example.todoapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItems
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentHomeBinding
import com.example.todoapp.room.TodoEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private val adapter: TodoAdapter = TodoAdapter {
        onCheckboxClicked(it)
    }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilterFloatingButtonOnClickListener()
        setAddFloatingButtonOnClickListener()
        initRcy()
        setFlowCollectors()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRcy() {
        binding.rcvHome.layoutManager = LinearLayoutManager(context)
        binding.rcvHome.adapter = adapter
    }

    private fun onCheckboxClicked(it: TodoEntity) {
        viewModel.onDoneCheckboxClicked(it)
    }

    private fun setFilterFloatingButtonOnClickListener() {
        binding.filter.setOnClickListener {
            showFilterView()
        }
    }

    private fun showFilterView() {
        MaterialDialog(requireContext()).show {
            title(R.string.order_by)
            listItems(R.array.filter_array) { _, index, text ->
                viewModel.setOrderStateByIndex(index)
            }
            debugMode(false)
            lifecycleOwner(this@HomeFragment)
        }
    }

    private fun setAddFloatingButtonOnClickListener() {
        binding.fab.setOnClickListener {
           viewModel.onAddButtonClicked()
        }
    }

    private fun setFlowCollectors() {
        lifecycleScope.launch {
            viewModel.items.collect {
                adapter.setItems(it)
            }
        }
        viewModel.getNavController = {
            findNavController()
        }
    }

}
