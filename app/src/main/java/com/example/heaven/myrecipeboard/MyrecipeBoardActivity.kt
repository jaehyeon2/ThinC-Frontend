package com.example.heaven.myrecipeboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.heaven.R
import com.example.heaven.databinding.ActivityMyrecipeBoardBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MyrecipeBoardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMyrecipeBoardBinding

    private val boardDataList = mutableListOf<MyrecipeBoardModel>()

    private lateinit var boardRVAdapter : MyrecipeBoardListLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_myrecipe_board)

        boardRVAdapter = MyrecipeBoardListLVAdapter(boardDataList)
        binding.boardListView3.adapter = boardRVAdapter

        binding.boardListView3.setOnItemClickListener { parent, view, position, id ->

            MyrecipeBoardDetail(id)
        }

        binding.writeBtn.setOnClickListener {
            val intent = Intent(this, MyrecipeBoardWriteActivity::class.java)
            startActivity(intent)
        }

    }

    private fun MyrecipeBoardDetail(id:Long) {
        Log.w("BoardDetail", "MyrecipeBoardDetail")
        val url = URL("http://10.0.2.2:8080/myrecipeboard_detail?id=$id")
        Thread{
            try{
                Log.w("connect", "success")

                val connection = url.openConnection() as HttpURLConnection

                val streamReader = InputStreamReader(connection.inputStream)
                val buffered = BufferedReader(streamReader)

                val content = StringBuilder()
                while (true) {
                    val data = buffered.readLine() ?: break
                    content.append(data)
                }

                Log.w("message", content.toString())

                val intent = Intent(this, MyrecipeBoardInsideActivity::class.java)
                intent.putExtra("id",id)
                startActivity(intent)
                finish()

            }catch (e:Exception){
                e.printStackTrace()
            }
        }.start()

    }
}