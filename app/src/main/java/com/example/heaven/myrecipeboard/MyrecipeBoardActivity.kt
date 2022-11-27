package com.example.heaven.myrecipeboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.heaven.R
import com.example.heaven.databinding.ActivityMyrecipeBoardBinding
import com.example.heaven.recipeBoard.RecipeBoardInsideActivity
import com.example.heaven.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MyrecipeBoardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMyrecipeBoardBinding

    private val boardDataList = mutableListOf<MyrecipeBoardModel>()
    private val boardKeyList = mutableListOf<String>()

    private val TAG = MyrecipeBoardActivity::class.java.simpleName

    private lateinit var boardRVAdapter : MyrecipeBoardListLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_myrecipe_board)

        boardRVAdapter = MyrecipeBoardListLVAdapter(boardDataList)
        binding.boardListView3.adapter = boardRVAdapter


        binding.boardListView3.setOnItemClickListener { parent, view, position, id ->


//            val intent = Intent(this, MyrecipeBoardInsideActivity::class.java)
//            intent.putExtra("key", boardKeyList[position])
//            startActivity(intent)

            MyrecipeBoardDetail(id)
        }

        binding.writeBtn.setOnClickListener {
            val intent = Intent(this, MyrecipeBoardWriteActivity::class.java)
            startActivity(intent)
        }

        getFBBoard3Data()

    }

    private fun getFBBoard3Data(){

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
//                    val item = dataModel.getValue(MyrecipeBoardModel::class.java)
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
//        FBRef.boardRef3.addValueEventListener(postListener)


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