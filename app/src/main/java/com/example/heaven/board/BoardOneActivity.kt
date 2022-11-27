package com.example.heaven.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.heaven.R
import com.example.heaven.databinding.ActivityFreeBoardBinding
import com.example.heaven.freeBoard.FreeBoardListLVAdapter
import com.example.heaven.freeBoard.FreeBoardModel
import com.example.heaven.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
 

class BoardOneActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFreeBoardBinding

    private val boardDataList = mutableListOf<FreeBoardModel>()
    private val boardKeyList = mutableListOf<String>()

    private val TAG = BoardOneActivity::class.java.simpleName

    private lateinit var boardRVAdapter : FreeBoardListLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_free_board)

        boardRVAdapter = FreeBoardListLVAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter


        binding.boardListView.setOnItemClickListener { parent, view, position, id ->


            val intent = Intent(this, BoardInsideActivity::class.java)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)
        }

        binding.writeBtn.setOnClickListener {
            val intent = Intent(this, FreeBoardWriteActivity::class.java)
            startActivity(intent)
        }

        getFBBoardData()

    }

    private fun getFBBoardData(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardDataList.clear()

                for (dataModel in dataSnapshot.children) {

                    Log.d(TAG, dataModel.toString())
//                    dataModel.key

                    val item = dataModel.getValue(FreeBoardModel::class.java)
                    boardDataList.add(item!!)
                    boardKeyList.add(dataModel.key.toString())

                }
                boardKeyList.reverse()
                boardDataList.reverse()
                boardRVAdapter.notifyDataSetChanged()

                Log.d(TAG, boardDataList.toString())


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.addValueEventListener(postListener)

    }
}