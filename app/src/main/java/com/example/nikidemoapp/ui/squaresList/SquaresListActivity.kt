package com.example.nikidemoapp.ui.squaresList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.nikidemoapp.common.Constants
import com.example.nikidemoapp.R
import com.example.nikidemoapp.model.SquareData
import com.example.nikidemoapp.ui.squaresList.adapter.SquaresListAdapter
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_squares_list.*

class SquaresListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_squares_list)
        setUpToolbar()
        initialize()
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar_squares_list)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun initialize() {
        val list = Paper.book().read<ArrayList<SquareData>>(Constants.PaperKeys.KEY_SQUARE_DATA_LIST, ArrayList())
        if(list.isNullOrEmpty()){
            showEmptyListView(true)
        }else {
            val adapter = SquaresListAdapter(list)
            rv_squares_data.adapter = adapter
            showEmptyListView(false)
        }
    }

    private fun showEmptyListView(isEmpty: Boolean) {
        if(isEmpty) {
            rv_squares_data.visibility = View.GONE
            empty_list_container.visibility = View.VISIBLE
        }else{
            rv_squares_data.visibility = View.VISIBLE
            empty_list_container.visibility = View.GONE
        }
    }
}
