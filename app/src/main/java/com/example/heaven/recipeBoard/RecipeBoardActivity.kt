package com.example.heaven.recipeBoard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.heaven.R
import com.example.heaven.databinding.ActivityRecipeBoardBinding
import com.example.heaven.freeBoard.FreeBoardInsideActivity
import com.example.heaven.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
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

        binding.writeBtn.setOnClickListener {
            val intent = Intent(this, RecipeBoardWriteActivity::class.java)
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
//                for (dataModel2 in dataSnapshot.children) {
//
//                    Log.d(TAG, dataModel2.toString())
////                    dataModel.key
//
//                    val item = dataModel2.getValue(RecipeBoardModel::class.java)
//                    boardDataList.add(item!!)
//                    boardKeyList.add(dataModel2.key.toString())
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
//        FBRef.boardRef2.addValueEventListener(postListener)

    }

    private fun RecipeBoardDetail(id:Long) {
        Log.w("BoardDetail", "RecipeBoardDetail")
        val url = URL("http://10.0.2.2:8080/recipeboard_detail?id=$id")
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
                val intent = Intent(this, RecipeBoardInsideActivity::class.java)
                intent.putExtra("id",id)
                startActivity(intent)
                finish()

            }catch (e:Exception){
                e.printStackTrace()
            }
        }.start()

    }
}