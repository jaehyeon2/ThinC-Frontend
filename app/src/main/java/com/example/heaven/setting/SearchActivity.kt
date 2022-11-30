package com.example.heaven.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.heaven.MainActivity
import com.example.heaven.R
import com.example.heaven.contentsList.ContentListActivity
import com.example.heaven.databinding.ActivitySearchBinding
import com.example.heaven.recipeBoard.RecipeBoardActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        findViewById<Button>(R.id.searchBtn).setOnClickListener {

            search()
        }

        findViewById<Button>(R.id.backBtn).setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }

    }

    private fun search() {
        val search = binding.searchArea.text.toString()
        val url = URL("http://10.0.2.2:8080/recipe/search?keyword=$search")
        Thread{
            try{
                Log.w("connect", "success")

                val connection = url.openConnection() as HttpURLConnection

                val streamReader = InputStreamReader(connection.inputStream)
                val buffered = BufferedReader(streamReader)

                val json = StringBuilder()
                while (true) {
                    val data = buffered.readLine() ?: break
                    json.append(data)
                }

                Log.w("message", json.toString())

                val intent = Intent(this, RecipeBoardActivity::class.java)
                intent.putExtra("json",json.toString())
                startActivity(intent)
                finish()

            }catch (e:Exception){
                e.printStackTrace()
            }
        }.start()

    }
}