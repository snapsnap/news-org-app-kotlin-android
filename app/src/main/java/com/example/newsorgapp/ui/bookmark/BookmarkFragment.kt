package com.example.newsorgapp.ui.bookmark

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsorgapp.databinding.CustomToolbarBinding
import com.example.newsorgapp.databinding.FragmentBookmarkBinding
import com.example.newsorgapp.source.news.ArticleModel
import com.example.newsorgapp.ui.detail.DetailActivity
import com.example.newsorgapp.ui.news.NewsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val bookmarkModule = module {
    factory { BookmarkFragment() }
}

class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var bindingToolbar: CustomToolbarBinding
    private val viewModel: BookmarkViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        bindingToolbar = binding.toolbar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        bindingToolbar.title = viewModel.title

        binding.listBookmark.adapter = newsAdapter
        viewModel.articles.observe(viewLifecycleOwner, {
            newsAdapter.add( it )
        })
    }

    private val newsAdapter by lazy {
        NewsAdapter(
            arrayListOf(),
            object: NewsAdapter.OnAdapterListener{
                override fun onClick(article: ArticleModel) {
                    startActivity(
                        Intent(requireActivity(), DetailActivity::class.java)
                            .putExtra("intent_detail", article)
                    )
                }
            }
        )
    }
}