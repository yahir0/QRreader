package com.example.qrreader

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button = findViewById<Button>(R.id.button)
        var button2 = findViewById<Button>(R.id.button2)
        var button3 = findViewById<Button>(R.id.button3)
        var button4 = findViewById<Button>(R.id.button4)
        var textview = findViewById<TextView>(R.id.textView2)

        button.setOnClickListener{
            val intentIntegrator = IntentIntegrator(this).apply {
                setPrompt(getString(R.string.CameraMessage))
                setBeepEnabled(false)
                captureActivity = MyCaptureActivity::class.java
            }
            intentIntegrator.initiateScan()
        }

        button2.setOnClickListener{
            val ClipCopy = Clipboard()

            ClipCopy.Copy(textview.text.toString(),this)
            Toast.makeText(applicationContext,R.string.Clipboard,Toast.LENGTH_SHORT).show()

        }

        button3.setOnClickListener{
            textview.setText(R.string.message)
        }

        button4.setOnClickListener {
            Toast.makeText(applicationContext,"実装予定",Toast.LENGTH_SHORT).show()
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var textView = findViewById<TextView>(R.id.textView2)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        when{
            result != null -> {
                textView.text = result.contents
            }
            else ->{
                textView.setText(R.string.message)
            }
        }

        val URLjudge = URLjudge()

        URLjudge.Judge(result.contents,this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when(item.itemId){
            R.id.menu_item1 -> {
                val intent = Intent(this, SaveActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_item2 -> {

            }


        }
        return true
    }

}


