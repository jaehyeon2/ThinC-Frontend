package com.example.heaven.freeBoard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.heaven.R
import com.example.heaven.comment.CommentLVAdapter
import com.example.heaven.comment.CommentModel
import com.example.heaven.utils.FBAuth
import com.example.heaven.utils.FBRef
import com.bumptech.glide.Glide
import com.example.heaven.board.FreeBoardEditActivity
import com.example.heaven.databinding.ActivityFreeBoardInsideBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class FreeBoardInsideActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFreeBoardInsideBinding

    private val commentDataList = mutableListOf<CommentModel>()

    private lateinit var commentAdapter : CommentLVAdapter

    val id = intent.getStringExtra("id").toString().toLong()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_free_board_inside)

        binding.boardSettingIcon.setOnClickListener {
            showDia(id)
        }

        commentAdapter = CommentLVAdapter(commentDataList)
        binding.commentLV.adapter = commentAdapter

    }

    private fun showDia(id: Long){

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {
            Toast.makeText(this, "수정 버튼을 눌렀습니다", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FreeBoardEditActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }

        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {

            deletePost(id)
            Toast.makeText(this, "삭제완료", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun deletePost(id:Long){
        val url = URL("http://10.0.2.2:8080/delete-free-post?id=$id")
        Thread{
            try{
                Log.w("delete-free", "deleteFreeBoard")

                val connection = url.openConnection() as HttpURLConnection

                val streamReader = InputStreamReader(connection.inputStream)
                val buffered = BufferedReader(streamReader)

                val content = StringBuilder()
                while (true) {
                    val data = buffered.readLine() ?: break
                    content.append(data)
                }

                Log.w("deleteFreeBoard", content.toString())

            }catch (e:Exception){
                e.printStackTrace()
            }
        }.start()
    }

}
