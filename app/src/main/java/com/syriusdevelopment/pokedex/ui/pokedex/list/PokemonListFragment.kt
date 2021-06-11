package com.syriusdevelopment.pokedex.ui.pokedex.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.syriusdevelopment.pokedex.databinding.FragmentPokemonListBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListFragment : Fragment() {

    private val viewModel: PokemonListViewModel by viewModel()
    private val stubAdapter: PokemonStubAdapter by lazy { PokemonStubAdapter() }
    private var _binding: FragmentPokemonListBinding? = null
    private val binding: FragmentPokemonListBinding get() = _binding!!
    private var listJob: Job? = null

    private fun getList() {
        listJob?.cancel()
        listJob = lifecycleScope.launch {
            viewModel.getList().collectLatest {
                stubAdapter.submitData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = stubAdapter
        }

        getList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}