package com.example.heaven.freeBoard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.heaven.R
import com.example.heaven.databinding.ActivityFreeBoardBinding
import com.example.heaven.recipeBoard.RecipeBoardInsideActivity
import com.example.heaven.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class FreeBoardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFreeBoardBinding

    private val boardDataList = mutableListOf<FreeBoardModel>()
    private val boardKeyList = mutableListOf<String>()

    private val TAG = FreeBoardActivity::class.java.simpleName

    private lateinit var boardRVAdapter : FreeBoardListLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_free_board)

        boardRVAdapter = FreeBoardListLVAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter


        binding.boardListView.setOnItemClickListener { parent, view, position, id ->

//            val intent = Intent(this, FreeBoardInsideActivity::class.java)
//            intent.putExtra("key", boardKeyList[position])
//            startActivity(intent)

            FreeBoardDetail(id)

        }

        binding.writeBtn.setOnClickListener {
            val intent = Intent(this, FreeBoardWriteActivity::class.java)
            startActivity(intent)
        }

        getFBBoardData()

    }

    private fun getFBBoardData(){

//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                boardDataList.clear()
//
//                for (dataModel in dataSnapshot.children) {
//
//                    Log.d(TAG, dataModel.toString())
////                    dataModel.key
//
//                    val item = dataModel.getValue(FreeBoardModel::class.java)
//                    boardDataList.add(item!!)
//                    boardKeyList.add(dataModel.key.toString())
//
//                }
//                boardKeyList.reverse()
//                boardDataList.reverse()
//                boardRVAdapter.notifyDataSetChanged()
//
//                Log.d(TAG, boardDataList.toString())
//
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//        FBRef.boardRef.addValueEventListener(postListener)

    }

    private fun FreeBoardDetail(id:Long) {
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