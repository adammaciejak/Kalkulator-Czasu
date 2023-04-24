package edu.put.inf151756

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun showSecondActivity()
    {
        val i = Intent(this,SecondActivity::class.java)
        startActivity(i)
    }

    private fun showThirdActivity()
    {
        val i = Intent(this,ThirdActivity::class.java)
        startActivity(i)
    }

    fun dateClick (v: View)
    {
        showSecondActivity()
    }

    fun timeClick (v: View)
    {
        showThirdActivity()
    }
}