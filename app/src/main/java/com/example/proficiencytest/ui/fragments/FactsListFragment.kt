package com.example.proficiencytest.ui.fragments

import android.os.Bundle
import android.text.BoringLayout.make
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.proficiencytest.R
import com.example.proficiencytest.databinding.FragmentFactsListBinding
import com.example.proficiencytest.model.response.Row
import com.example.proficiencytest.ui.MainActivity
import com.example.proficiencytest.ui.adapters.FactRecyclerViewAdapter
import com.example.proficiencytest.util.Resource
import com.example.proficiencytest.viewmodel.FactsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FactsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FactsListFragment : Fragment() {
    // TODO Add DI later
    lateinit var fragmentBinding : FragmentFactsListBinding
    lateinit var factsViewModel: FactsViewModel
    lateinit var factAdapter : FactRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentBinding = FragmentFactsListBinding.inflate(inflater)
        factsViewModel = (activity as MainActivity).factsViewModel

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

    private fun setObserver() {
        factsViewModel.factsLocalLiveData.observe(viewLifecycleOwner, Observer { userList ->
            Log.i("....", "........")
            userList?.let {
                setListToAdapter(it)
                factAdapter.submitList(it)
            }
        })


        // Observer server data
        factsViewModel.factResponseLiveData.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Loading -> {
                    // we can show progress here
                    Snackbar.make(fragmentBinding.rvFacts.rootView, "Getting Data From Server", Snackbar.LENGTH_SHORT).show()
                }

                is Resource.Error -> {
                    // We can show error for connections here
                    Snackbar.make(fragmentBinding.rvFacts.rootView, "Getting Data From Server", Snackbar.LENGTH_SHORT).show()
                }

                is Resource.Success -> {
                    // Lets handle response
                    // As we have made custome model than the actual response
                    // lets build those models
                    response.data.let { factResponse->
                        // lets store this locally
                        factResponse?.let { fact ->
                            fact.rows?.let {
                                setListToAdapter(it)
                            }
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