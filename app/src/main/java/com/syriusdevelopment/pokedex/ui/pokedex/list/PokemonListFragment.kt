package com.syriusdevelopment.pokedex.ui.pokedex.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.syriusdevelopment.pokedex.databinding.FragmentPokemonListBinding
import com.syriusdevelopment.pokedex.ui.pokedex.PokedexActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonListFragment : Fragment() {

    @Inject lateinit var factory: PokemonListViewModelFactory
    private val viewModel: PokemonListViewModel by activityViewModels(factoryProducer = { factory })
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as PokedexActivity).pokedexSubcomponent.inject(this)
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