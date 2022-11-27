package com.example.heaven.myrecipeboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.heaven.R
import com.example.heaven.comment.CommentLVAdapter
import com.example.heaven.comment.CommentModel
import com.example.heaven.utils.FBAuth
import com.example.heaven.utils.FBRef
import com.bumptech.glide.Glide
import com.example.heaven.databinding.ActivityMyrecipeBoardInsideBinding
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

class MyrecipeBoardInsideActivity : AppCompatActivity() {

    private val TAG = MyrecipeBoardInsideActivity::class.java.simpleName

    private lateinit var binding : ActivityMyrecipeBoardInsideBinding

    private lateinit var key:String

    private val commentDataList = mutableListOf<CommentModel>()

    private lateinit var commentAdapter : CommentLVAdapter

    val id = intent.getStringExtra("id").toString().toLong()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_myrecipe_board_inside)

        binding.boardSettingIcon.setOnClickListener {
            showDia(id)
        }

        // 두번째 방법
        key = intent.getStringExtra("key").toString()
        getBoard3Data(key)
        getImage3Data(key)


        binding.commentBtn.setOnClickListener {
            insertComment(key)
        }

        getCommentData(key)

        commentAdapter = CommentLVAdapter(commentDataList)
        binding.commentLV.adapter = commentAdapter

    }

    fun getCommentData(key : String){

//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                commentDataList.clear()
//
//                for (dataModel in dataSnapshot.children) {
//
//                    val item = dataModel.getValue(CommentModel::class.java)
//                    commentDataList.add(item!!)
//                }
//
//                commentAdapter.notifyDataSetChanged()
//
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//        FBRef.commentRef3.child(key).addValueEventListener(postListener)


    }

    fun insertComment(key : String){
        // comment
        //   - BoardKey
        //        - CommentKey
        //            - CommentData
        //            - CommentData
        //            - CommentData
//        FBRef.commentRef3
//            .child(key)
//            .push()
//            .setValue(
//                CommentModel(
//                    binding.commentArea.text.toString(),
//                    FBAuth.getTime()
//                )
//            )

        Toast.makeText(this, "댓글 입력 완료", Toast.LENGTH_SHORT).show()
        binding.commentArea.setText("")

    }

    private fun showDia(id: Long){

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {
            Toast.makeText(this, "수정 버튼을 눌렀습니다", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MyrecipeBoardEditActivity::class.java)
            intent.putExtra("key",key)
            startActivity(intent)
        }

        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {

            deletePost(id)
//            FBRef.boardRef3.child(key).removeValue()
            Toast.makeText(this, "삭제완료", Toast.LENGTH_LONG).show()
            finish()

        }



    }

    private fun deletePost(id:Long){
        val url = URL("http://10.0.2.2:8080/delete-myrecipe-post?id=$id")
        Thread{
            try{
                Log.w("delete-myrecipe", "deleteMyrecipeBoard")

                val connection = url.openConnection() as HttpURLConnection

                val streamReader = InputStreamReader(connection.inputStream)
                val buffered = BufferedReader(streamReader)

                val content = StringBuilder()
                while (true) {
                    val data = buffered.readLine() ?: break
                    content.append(data)
                }

                Log.w("deleteMyrecipeBoard", content.toString())

            }catch (e:Exception){
                e.printStackTrace()
            }
        }.start()
    }

    private fun getImage3Data(key : String){

//        // Reference to an image file in Cloud Storage
//        val storageReference = Firebase.storage.reference.child(key + ".png")
//
//        // ImageView in your Activity
//        val imageViewFromFB = binding.getImageArea
//
//        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
//            if(task.isSuccessful) {
//
//                Glide.with(this)
//                    .load(task.result)
//                    .into(imageViewFromFB)
//
//            } else {
//
//                binding.getImageArea.isVisible = false
//            }
//        })


    }


    private fun getBoard3Data(key : String){

//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                try {
//
//                    val dataModel = dataSnapshot.getValue(MyrecipeBoardModel::class.java)
//                    Log.d(TAG, dataModel!!.title)
//
//                    binding.titleArea.text = dataModel!!.title
//                    binding.textArea.text = dataModel!!.content
//                    binding.timeArea.text = dataModel!!.time
//
//                    val myUid = FBAuth.getUid()
//                    val writerUid = dataModel.uid
//
//                    if(myUid.equals(writerUid)){
//                        Log.d(TAG, "내가 쓴 글")
//                        binding.boardSettingIcon.isVisible = true
//                    } else {
//                        Log.d(TAG, "내가 쓴 글 아님")
//                    }
//
//                } catch (e : Exception){
//
//                    Log.d(TAG, "삭제완료")
//
//                }
//
//
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//        FBRef.boardRef3.child(key).addValueEventListener(postListener)



    }

}
