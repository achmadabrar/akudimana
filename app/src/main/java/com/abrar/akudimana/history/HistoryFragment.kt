package com.abrar.akudimana.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.abrar.akudimana.R
import com.abrar.akudimana.ViewModel
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryFragment : Fragment() {

    private val viewModel: ViewModel by viewModel()
    lateinit var adapter: HistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel.getLocationList()
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.title = "Halaman - History"

        Log.d("historyDatanya", viewModel.getLocationList().toString())
        if (!viewModel.history.isNullOrEmpty()) {
            adapter = HistoryAdapter(viewModel.history)
            recycler_history.adapter = adapter
            recycler_history.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
}