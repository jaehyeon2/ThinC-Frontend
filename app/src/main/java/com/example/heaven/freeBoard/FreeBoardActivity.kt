package com.example.heaven.freeBoard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.heaven.R

import com.example.heaven.databinding.ActivityFreeBoardBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class FreeBoardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFreeBoardBinding

    private val boardDataList = mutableListOf<FreeBoardModel>()

    private lateinit var boardRVAdapter : FreeBoardListLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_free_board)

        boardRVAdapter = FreeBoardListLVAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter


        binding.boardListView.setOnItemClickListener { parent, view, position, id ->

            freeBoardDetail(id)

        }

        binding.writeBtn.setOnClickListener {
            val intent = Intent(this, FreeBoardWriteActivity::class.java)
            startActivity(intent)
        }

    }

    private fun freeBoardDetail(id:Long) {
        Log.w("BoardDetail", "FreeBoardDetail")
        val url = URL("http://10.0.2.2:8080/freeboard_detail?id=$id")
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

                val intent = Intent(this, FreeBoardInsideActivity::class.java)
                startActivity(intent)
                intent.putExtra("id",id)
                finish()

            }catch (e:Exception){
                e.printStackTrace()
            }
        }.start()

    }
}