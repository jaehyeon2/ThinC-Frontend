package com.example.heaven.board2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.heaven.R
import com.example.heaven.board.BoardInsideActivity
import com.example.heaven.board.BoardWriteActivity
import com.example.heaven.databinding.ActivityBoardTwoBinding
import com.example.heaven.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class BoardTwoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardTwoBinding

    private val boardDataList = mutableListOf<BoardModel2>()
    private val boardKeyList = mutableListOf<String>()

    private val TAG = BoardTwoActivity::class.java.simpleName

    private lateinit var boardRVAdapter : BoardListLV2Adapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_two)

        boardRVAdapter = BoardListLV2Adapter(boardDataList)
        binding.boardListView2.adapter = boardRVAdapter

        binding.boardListView2.setOnItemClickListener { parent, view, position, id ->


            val intent = Intent(this, BoardInside2Activity::class.java)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)
        }

        binding.writeBtn.setOnClickListener {
            val intent = Intent(this, BoardWrite2Activity::class.java)
            startActivity(intent)
        }

        getFBBoard2Data()

    }

    private fun getFBBoard2Data(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardDataList.clear()

                for (dataModel2 in dataSnapshot.children) {

                    Log.d(TAG, dataModel2.toString())
//                    dataModel.key

                    val item = dataModel2.getValue(BoardModel2::class.java)
                    boardDataList.add(item!!)
                    boardKeyList.add(dataModel2.key.toString())

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
        FBRef.boardRef2.addValueEventListener(postListener)

    }
}