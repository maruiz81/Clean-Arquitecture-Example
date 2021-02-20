package com.maruiz.pet.presentation.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.android.material.snackbar.Snackbar
import com.maruiz.pet.R
import com.maruiz.pet.databinding.FragmentBookListBinding
import com.maruiz.pet.presentation.utils.viewBinding
import com.maruiz.pet.presentation.viewmodel.BookListViewModel
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class BookListFragment : Fragment(R.layout.fragment_book_list) {
    private val bookListViewModel: BookListViewModel by inject()

    private val binding: FragmentBookListBinding? by viewBinding(FragmentBookListBinding::bind) {
        viewModel = bookListViewModel
        lifecycleOwner = this@BookListFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding

        bookListViewModel.failure.observe(viewLifecycleOwner) {
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        }

        bookListViewModel.detail.observe(viewLifecycleOwner) {
            get<NavController> { parametersOf(this@BookListFragment) }.navigate(
                BookListFragmentDirections.bookToDetail(it)
            )
        }
    }
}