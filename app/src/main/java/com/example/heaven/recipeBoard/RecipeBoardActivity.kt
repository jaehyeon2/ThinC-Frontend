package com.example.heaven.recipeBoard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.heaven.R
import com.example.heaven.databinding.ActivityRecipeBoardBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class RecipeBoardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecipeBoardBinding

    private val boardDataList = mutableListOf<RecipeBoardModel>()
    private val boardKeyList = mutableListOf<String>()

    private val TAG = RecipeBoardActivity::class.java.simpleName

    private lateinit var boardRVAdapter : RecipeBoardListLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_board)

        boardRVAdapter = RecipeBoardListLVAdapter(boardDataList)
        binding.boardListView2.adapter = boardRVAdapter

        binding.boardListView2.setOnItemClickListener { parent, view, position, id ->


//            val intent = Intent(this, RecipeBoardInsideActivity::class.java)
//            intent.putExtra("key", boardKeyList[position])
//            startActivity(intent)

            RecipeBoardDetail(id)

        }

    }


    private fun RecipeBoardDetail(id:Long) {
        Log.w("BoardDetail", "RecipeBoardDetail")
        val url = URL("http://10.0.2.2:8080/recipe/recipeboard_detail?id=$id")
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
                val intent = Intent(this, RecipeBoardInsideActivity::class.java)
                intent.putExtra("json",json.toString())
                startActivity(intent)
                finish()

            }catch (e:Exception){
                e.printStackTrace()
            }
        }.start()

    }
}