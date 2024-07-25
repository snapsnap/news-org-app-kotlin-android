package com.example.newsorgapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.newsorgapp.databinding.CustomToolbarBinding
import com.example.newsorgapp.databinding.FragmentHomeBinding
import com.example.newsorgapp.source.news.ArticleModel
import com.example.newsorgapp.source.news.CategoryModel
import com.example.newsorgapp.ui.detail.DetailActivity
import com.example.newsorgapp.ui.news.CategoryAdapter
import com.example.newsorgapp.ui.news.NewsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module
import timber.log.Timber

val homeModule = module {
    factory { HomeFragment() }
}

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var bindingToolbar: CustomToolbarBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        bindingToolbar = binding.toolbar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

//        bindingToolbar.textTitle.text = viewModel.title
        bindingToolbar.title = viewModel.title
        binding.listCategory.adapter = categoryAdapter
        viewModel.category.observe( viewLifecycleOwner, Observer {
            viewModel.fetch()
        })

        binding.listNews.adapter = newsAdapter
        viewModel.news.observe( viewLifecycleOwner, Observer {
            Timber.e(it.articles.toString())
//            binding.imageAlert.visibility = if( it.articles.isEmpty() ) View.VISIBLE else View.GONE
//            binding.textAlert.visibility = if( it.articles.isEmpty() ) View.VISIBLE else View.GONE
            newsAdapter.add( it.articles )
        })

        viewModel.message.observe( viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText( requireContext(), it, Toast.LENGTH_SHORT ).show()
            }
        })
    }

    private val categoryAdapter by lazy {
        CategoryAdapter(
            viewModel.categories,
            object: CategoryAdapter.OnAdapterListener{
                override fun onClick(category: CategoryModel) {
                    viewModel.category.postValue( category.id )
                }
            }
        )
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