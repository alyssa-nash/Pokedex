package com.syriusdevelopment.pokedex.ui.pokedex.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syriusdevelopment.pokedex.data.model.PokemonStub
import com.syriusdevelopment.pokedex.databinding.ListItemPokemonBinding

class PokemonStubAdapter :
    PagingDataAdapter<PokemonStub, PokemonStubAdapter.PokemonStubViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonStubViewHolder {
        return PokemonStubViewHolder(
            ListItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonStubViewHolder, position: Int) {
        val pokemonStub = getItem(position)
        if (pokemonStub != null) {
            holder.bind(pokemonStub)
        }
    }

    class PokemonStubViewHolder(private val binding: ListItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(stub: PokemonStub) {
            binding.name.text = stub.name

            Glide.with(binding.root)
                .load(stub.getImageUrl())
                .into(binding.sprite)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PokemonStub>() {
            override fun areItemsTheSame(oldItem: PokemonStub, newItem: PokemonStub): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PokemonStub, newItem: PokemonStub): Boolean {
                return oldItem == newItem
            }
        }
    }
}