package edu.put.inf151756

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ThirdActivity : AppCompatActivity() {
    private fun addTime (hours: Int, minutes: Int, seconds: Int): Int
    {
        return hours*3600 + minutes*60 + seconds
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val h1: EditText = findViewById(R.id.h1Edit)
        val m1: EditText = findViewById(R.id.m1Edit)
        val s1: EditText = findViewById(R.id.s1Edit)

        val h2: EditText = findViewById(R.id.h2Edit)
        val m2: EditText = findViewById(R.id.m2Edit)
        val s2: EditText = findViewById(R.id.s2Edit)

        val addButton: Button = findViewById(R.id.addHMSButton)
        val subButton: Button = findViewById(R.id.subHMSButton)
        val resetButton: Button = findViewById(R.id.resetButton)

        fun getTotalSeconds1(): Int
        {
            val hours1 = h1.text.toString().toInt()
            val minutes1 = m1.text.toString().toInt()
            val seconds1 = s1.text.toString().toInt()
            return addTime(hours1, minutes1, seconds1)
        }

        fun getTotalSeconds2(): Int
        {
            val hours2 = h2.text.toString().toInt()
            val minutes2 = m2.text.toString().toInt()
            val seconds2 = s2.text.toString().toInt()
            return addTime(hours2, minutes2, seconds2)
        }

        fun clear1()
        {
            h1.setText("0")
            m1.setText("0")
            s1.setText("0")
        }

        fun clear2()
        {
            h2.setText("0")
            m2.setText("0")
            s2.setText("0")
        }

        addButton.setOnClickListener {
            val totalSeconds = getTotalSeconds1() + getTotalSeconds2()
            val hours = totalSeconds / 3600
            val minutes = (totalSeconds % 3600) / 60
            val seconds = totalSeconds % 60
            clear2()
            h1.setText(hours.toString())
            m1.setText(minutes.toString())
            s1.setText(seconds.toString())
        }

        subButton.setOnClickListener {
            val totalSeconds = getTotalSeconds1() - getTotalSeconds2()
            val hours = totalSeconds / 3600
            val minutes = (totalSeconds % 3600) / 60
            val seconds = totalSeconds % 60
            clear2()
            h1.setText(hours.toString())
            m1.setText(minutes.toString())
            s1.setText(seconds.toString())
        }
        resetButton.setOnClickListener {
            clear1()
            clear2()
        }
    }
}