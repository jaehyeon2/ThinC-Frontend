package com.example.heaven.board3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.heaven.R
import com.example.heaven.board.BoardListLVAdapter
import com.example.heaven.board.BoardModel
import com.example.heaven.databinding.ActivityBoardMyBinding
import com.example.heaven.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class BoardMyActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardMyBinding

    private val boardDataList = mutableListOf<BoardModel3>()
    private val boardKeyList = mutableListOf<String>()

    private val TAG = BoardMyActivity::class.java.simpleName

    private lateinit var boardRVAdapter : BoardListLV3Adapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_my)

        boardRVAdapter = BoardListLV3Adapter(boardDataList)
        binding.boardListView3.adapter = boardRVAdapter


        binding.boardListView3.setOnItemClickListener { parent, view, position, id ->


            val intent = Intent(this, BoardInside3Activity::class.java)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)
        }

        binding.writeBtn.setOnClickListener {
            val intent = Intent(this, BoardWrite3Activity::class.java)
            startActivity(intent)
        }

        getFBBoard3Data()

    }

    private fun getFBBoard3Data(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardDataList.clear()

                for (dataModel in dataSnapshot.children) {

                    Log.d(TAG, dataModel.toString())
//                    dataModel.key

                    val item = dataModel.getValue(BoardModel3::class.java)
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
        FBRef.boardRef3.addValueEventListener(postListener)

    }
}