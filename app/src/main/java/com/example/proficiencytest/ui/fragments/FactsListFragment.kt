package com.example.proficiencytest.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.proficiencytest.R
import com.example.proficiencytest.databinding.FragmentFactsListBinding
import com.example.proficiencytest.model.response.Row
import com.example.proficiencytest.ui.adapters.FactRecyclerViewAdapter
import com.example.proficiencytest.util.Resource
import com.example.proficiencytest.viewmodel.FactsViewModel
import com.google.android.material.snackbar.Snackbar

class FactsListFragment : Fragment() {
    lateinit var fragmentBinding: FragmentFactsListBinding
    lateinit var factsViewModel: FactsViewModel
    lateinit var factAdapter: FactRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentBinding = FragmentFactsListBinding.inflate(inflater)
        factsViewModel = ViewModelProvider(requireActivity()).get(FactsViewModel::class.java)

        setRecyclerView()

        setObserver()

        setListener()

        return fragmentBinding.root
    }

    private fun setRecyclerView() {
        factAdapter = FactRecyclerViewAdapter()
        fragmentBinding.apply {
            rvFacts.adapter = factAdapter
        }
    }

    // Observe local and remote data changes
    private fun setObserver() {
        factsViewModel.factsLocalLiveData.observe(viewLifecycleOwner, Observer { userList ->
            userList?.let {
                setListToAdapter(it)
            }
        })

        factsViewModel.factResponseLiveData.observe(viewLifecycleOwner, Observer { response ->
            when (val value = response.getContentIfNotHandled()) {
                is Resource.Loading -> {
                    // we can show progress here
                    Snackbar.make(
                        fragmentBinding.rvFacts.rootView,
                        getString(R.string.msg_loading_getting_data),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Resource.Error -> {
                    Snackbar.make(
                        fragmentBinding.rvFacts.rootView,
                        value.message!!,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Resource.Success -> {
                    value.data?.let {
                        it.rows?.let { facts ->
                            setListToAdapter(facts)
                            factsViewModel.insertAllFacts(facts)
                        }
                    }
                }
            }
        })
    }

    private fun setListToAdapter(facts: List<Row>) {
        if (facts.isNotEmpty()) {
            factAdapter.submitList(facts)
            fragmentBinding.rvFacts.visibility = View.VISIBLE
            fragmentBinding.viewNoData.root.visibility = View.GONE
        } else {
            fragmentBinding.rvFacts.visibility = View.GONE
            fragmentBinding.viewNoData.root.visibility = View.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factsViewModel.getFacts()
    }

    private fun setListener() {
        fragmentBinding.swipeRefresh.setOnRefreshListener {
            if (fragmentBinding.swipeRefresh.isRefreshing) {
                fragmentBinding.swipeRefresh.isRefreshing = false
                factsViewModel.getFacts()
            }
        }
    }
}