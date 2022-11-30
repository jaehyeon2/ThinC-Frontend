package com.example.heaven.myrecipeboard

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.heaven.R
import com.example.heaven.databinding.ActivityMyrecipeBoardEditBinding
import com.google.gson.Gson
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class MyrecipeBoardEditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMyrecipeBoardEditBinding

    private lateinit var profileImageBase64:String

    private var isImageUpload = false

    private val json = intent.getStringExtra("json")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_myrecipe_board_edit)
        binding.imageArea.setOnClickListener {
            /** 이미지 추가 버튼  */
            openGallery()
            isImageUpload = true
        }
        binding.editBtn.setOnClickListener {

            editRecipe()
            Toast.makeText(this, "게시글 입력 완료", Toast.LENGTH_LONG).show()
            finish()

        }

    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)

        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/*"
        startActivityForResult(intent, 102)
    }

    private fun editRecipe(){
        val title = binding.titleArea.text.toString()
        val ingredient = binding.ingredientArea.text.toString()
        val process = binding.progressArea.text.toString()

//        val category = binding.c
//        val category = binding.categoryArea.text.toString()

        val url = URL("http://10.0.2.2:8080/write-post")
        val connection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Accept", "application/json")
        connection.doInput = true
        connection.doOutput = true

        val jsonString = "{\"title\":$title, \"ingredient\":$ingredient, \"process\":$process, \"image\":$profileImageBase64}"

        // Send the JSON we created
        val outputStreamWriter = OutputStreamWriter(connection.outputStream)
        outputStreamWriter.write(jsonString)
        outputStreamWriter.flush()

        val streamReader = InputStreamReader(connection.inputStream)
        val buffered = BufferedReader(streamReader)

        val responseJson = StringBuilder()
        while (true) {
            val data = buffered.readLine() ?: break
            responseJson.append(data)
        }

        Log.w("json", responseJson.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == 102 && resultCode == Activity.RESULT_OK){
            var currentImageURL = intent?.data
            // Base64 인코딩부분
            val ins: InputStream? = currentImageURL?.let {
                applicationContext.contentResolver.openInputStream(
                    it
                )
            }
            val img: Bitmap = BitmapFactory.decodeStream(ins)
            ins?.close()
            val resized = Bitmap.createScaledBitmap(img, 256, 256, true)
            val byteArrayOutputStream = ByteArrayOutputStream()
            resized.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream)
            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
            val outStream = ByteArrayOutputStream()
            val res: Resources = resources
            profileImageBase64 = Base64.encodeToString(byteArray, Base64.NO_WRAP)
            // 여기까지 인코딩 끝

            // 이미지 뷰에 선택한 이미지 출력
            val imageview: ImageView = findViewById(binding.imageArea.id)
            imageview.setImageURI(currentImageURL)
            try {
                //이미지 선택 후 처리
            }catch (e: Exception){
                e.printStackTrace()
            }
        } else{
            Log.d("ActivityResult", "something wrong")
        }
    }
}