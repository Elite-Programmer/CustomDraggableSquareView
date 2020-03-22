package com.example.nikidemoapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.nikidemoapp.common.Constants
import com.example.nikidemoapp.ui.customViews.CustomDrawerView
import com.example.nikidemoapp.R
import com.example.nikidemoapp.model.SquareData
import com.example.nikidemoapp.ui.squaresList.SquaresListActivity
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    CustomDrawerView.CustomDrawerViewListener {
    private lateinit var customDrawerView: CustomDrawerView
    private lateinit var seekbar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customDrawerView = findViewById(R.id.mcv)
        seekbar = findViewById(R.id.seekbar)

        initialize()
        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar_home)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowHomeEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
    }

    private fun initialize() {
        customDrawerView.setDrawerListener(this)
        setSeekbarChangeListener()
        restoreAndDrawSavedSquares()
        btn_view_list.setOnClickListener { startActivity(Intent(this, SquaresListActivity::class.java)) }
    }

    private fun setSeekbarChangeListener() {
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser)
                    customDrawerView.updateLength(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }


    private fun restoreAndDrawSavedSquares() {
        val savedSquaresData = Paper.book().read<List<SquareData>>(Constants.PaperKeys.KEY_SQUARE_DATA_LIST, ArrayList())
        customDrawerView.restorePreviousSquares(savedSquaresData)
    }

    // Listener methods from CustomDrawerView
    override fun displaySeekBar() {
        seekbar.visibility = View.VISIBLE
    }

    override fun initializeSeekBar() {
        seekbar.progress = 50
    }

    override fun onPause() {
        super.onPause()
        Paper.book().write(Constants.PaperKeys.KEY_SQUARE_DATA_LIST, customDrawerView.getAllSquareData())
    }

}
