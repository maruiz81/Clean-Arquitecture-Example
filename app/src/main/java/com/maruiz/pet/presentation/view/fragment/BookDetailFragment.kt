package com.maruiz.pet.presentation.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maruiz.pet.R
import com.maruiz.pet.databinding.FragmentBookDetailBinding
import com.maruiz.pet.presentation.utils.viewBinding

class BookDetailFragment : Fragment(R.layout.fragment_book_detail) {

    private val args by navArgs<BookDetailFragmentArgs>()

    private val binding: FragmentBookDetailBinding? by viewBinding(FragmentBookDetailBinding::bind) {
        model = args.book
        lifecycleOwner = this@BookDetailFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.arrowBack?.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}