package com.fkammediacenter.testpsak

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.fkammediacenter.testpsak.daoResponse.DonaturKotakList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.empty_view.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    val donatur = "Donatur Kotak"
    val id_fr = "F5-SLO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.newSearch(donatur, id_fr,"")
        setupViews()
        registerObservables()


    }

    private fun setupViews() {
        adapter = MainAdapter(
            object : MainAdapter.ItemClickListener {
                override fun onItemClicked(donatur: DonaturKotakList?) {
                    if (!donatur?.alamatPemilik.isNullOrBlank()) {
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(donatur?.alamatPemilik)
                        startActivity(i)
                    }
                }
            }
        )

        main_recyclerView.layoutManager = LinearLayoutManager(this)
        main_recyclerView.adapter = adapter

        main_editText.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {



                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
            if(newText == ""){
                viewModel.newSearch(donatur,id_fr,"")
                Toast.makeText(this@MainActivity, "true", Toast.LENGTH_SHORT).show()
            }else
                viewModel.newSearch(donatur,id_fr,newText.toString())

                return false
            }

        })

    }

    private fun registerObservables() {
        // We start by submiting the list to the adapter initally
        submitItems()

        // Toast for API Errors
        viewModel.errorToastEvent.observe(this,
            Observer { Toast.makeText(this, getString(R.string.err_search), Toast.LENGTH_LONG) }
        )
        Log.d("EXECUTING","EXECUTING-ERROR")

        // Clearing the data of the adapter when doing a new search
        viewModel.clearDataEvents.observe(this,
            Observer {
                viewModel.clearDataSource()
                submitItems()
                adapter.notifyDataSetChanged()
            }
        )
        Log.d("EXECUTING","EXECUTING-clearDataEvents")

        // Showing an empty view on the screen
        viewModel.emptyVisibilityEvents.observe(this,
            Observer { show ->
                if(show != null) {
                    var visibility = if (show.peek()){
                        View.VISIBLE
                    }
                    else {
                        View.GONE
                    }

                    this.empty_view_imageView.visibility = visibility
                }
            }

        )
        Log.d("EXECUTING","EXECUTING-emptyVisibilityEvents")


        // Display the recyclerview loading item
        viewModel.recyclerViewLoadingEvents.observe(this,
            Observer { show ->
                if(show != null) {
                    adapter.loading = show.peek()
                }
            })
        Log.d("EXECUTING","EXECUTING-recyclerViewLoadingEvents")
    }

    @SuppressLint("CheckResult")
    private fun submitItems() {
        viewModel.getItems()!!
            .subscribe(
                { items -> adapter.submitList(items)
                },
                {

                }
            )

    }
}
