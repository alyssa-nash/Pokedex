package com.syriusdevelopment.pokedex.ui.pokedex.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.syriusdevelopment.pokedex.data.Resource
import com.syriusdevelopment.pokedex.databinding.FragmentPokemonListBinding
import com.syriusdevelopment.pokedex.ui.pokedex.PokedexActivity
import javax.inject.Inject

class PokemonListFragment : Fragment() {

    @Inject lateinit var factory: PokemonListViewModelFactory
    private val viewModel: PokemonListViewModel by activityViewModels(factoryProducer = { factory })
    private val stubAdapter: PokemonStubAdapter by lazy { PokemonStubAdapter() }
    private var _binding: FragmentPokemonListBinding? = null
    private val binding: FragmentPokemonListBinding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as PokedexActivity).pokedexSubcomponent.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = stubAdapter
        }

        viewModel.listLiveData.observe(
            viewLifecycleOwner,
            Observer { state ->
                when (state) {
                    is Resource.Success -> {
                        if (!state.data.isNullOrEmpty()) {
                            stubAdapter.submitList(state.data.toMutableList())
                            showLoading(false)
                        }
                    }
                    is Resource.Loading -> showLoading(true)
                    is Resource.Error -> TODO()
                }
            })

        viewModel.getList()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}