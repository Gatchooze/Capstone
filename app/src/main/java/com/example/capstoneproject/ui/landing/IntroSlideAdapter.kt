package com.example.capstoneproject.ui.landing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.SlideItemContainerBinding
import com.example.capstoneproject.ui.landing.IntroSlideAdapter.IntroSlideViewHolder

class IntroSlideAdapter(private val introSlide: List<IntroSlide>) :
    RecyclerView.Adapter<IntroSlideViewHolder>() {

    inner class IntroSlideViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val binding = SlideItemContainerBinding.bind(view)

        var txtTitle = binding.txtTitle
        var txtDesc = binding.txtDescription
        var imageIcon = binding.imageSlide

        fun bind(introSlide: IntroSlide) {
            binding.txtTitle.text = introSlide.title
            binding.txtDescription.text = introSlide.description
            binding.imageSlide.setImageResource(introSlide.icon)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IntroSlideViewHolder {
        return IntroSlideViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(introSlide[position])
    }

    override fun getItemCount(): Int {
        return introSlide.size
    }
}