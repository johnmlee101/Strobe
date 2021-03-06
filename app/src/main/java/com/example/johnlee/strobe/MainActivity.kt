package com.example.johnlee.strobe

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import java.util.Timer
import java.util.TimerTask



class MainActivity : AppCompatActivity() {

    private lateinit var seekbar : SeekBar
    private lateinit var barText : TextView
    private lateinit var actionButton : FloatingActionButton
    private lateinit var strobeView : View

    private var timer : Timer = Timer()
    private var refreshRate : Int = 0
    private var onWhite : Boolean = false
    private var isRunning : Boolean = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekbar = findViewById(R.id.seekBar) as SeekBar
        barText = findViewById(R.id.textView) as TextView
        actionButton = findViewById(R.id.floatingActionButton) as FloatingActionButton
        strobeView = findViewById(R.id.strobeView)
        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                refreshRate = progress
                barText.text = if (progress > 0) progress.toString() else "1"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        actionButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if (isRunning) {
                    timer.cancel()
                    timer = Timer()
                    isRunning = false
                    strobeView.visibility = View.INVISIBLE
                } else {
                    isRunning = true;
                    scheduleTimer()
                    strobeView.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun scheduleTimer() {
        var closedRate = 1
        if (refreshRate > 0) {
            closedRate = refreshRate
        }

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread(object: Runnable {
                    override fun run() {
                        strobeView.setBackgroundColor(Color.parseColor(if (onWhite) "#000000" else  "#FFFFFF"))
                    }
                })
                onWhite = !onWhite
            }

        }, 0, (1000 / closedRate).toLong())
    }
}
