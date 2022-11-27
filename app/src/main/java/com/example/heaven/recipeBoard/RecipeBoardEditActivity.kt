package com.example.heaven.recipeBoard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.heaven.R
import com.example.heaven.utils.FBAuth

import com.example.heaven.utils.FBRef
import com.bumptech.glide.Glide
import com.example.heaven.databinding.ActivityRecipeBoardEditBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class RecipeBoardEditActivity : AppCompatActivity() {

    private lateinit var key:String

    private lateinit var binding : ActivityRecipeBoardEditBinding

    private val TAG = RecipeBoardEditActivity::class.java.simpleName

    private lateinit var writerUid : String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_board_edit)

        key = intent.getStringExtra("key").toString()
        getBoard2Data(key)
        getImage2Data(key)

        binding.editBtn.setOnClickListener {
            editBoard2Data(key)
        }


    }

    private fun editBoard2Data(key : String){

//        FBRef.boardRef2
//            .child(key)
//            .setValue(
//                RecipeBoardModel(binding.titleArea.text.toString(),
//                    binding.contentArea.text.toString(),
//                    writerUid,
//                    FBAuth.getTime())
//            )

        Toast.makeText(this, "수정완료", Toast.LENGTH_LONG).show()

        finish()

    }

    private fun getImage2Data(key : String){

        // Reference to an image file in Cloud Storage
//        val storageReference = Firebase.storage.reference.child(key + ".png")
//
//        // ImageView in your Activity
//        val imageViewFromFB = binding.imageArea
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
//            }
//        })


    }

    private fun getBoard2Data(key : String){

//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                val dataModel = dataSnapshot.getValue(RecipeBoardModel::class.java)
//
//                binding.titleArea.setText(dataModel?.title)
//                binding.contentArea.setText(dataModel?.content)
//                writerUid = dataModel!!.uid
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//
//        FBRef.boardRef2.child(key).addValueEventListener(postListener)

    }
}