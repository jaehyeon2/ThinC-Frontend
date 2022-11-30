package com.example.heaven.myrecipeboard

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.heaven.R
import com.example.heaven.databinding.ActivityMyrecipeBoardWriteBinding
import com.example.heaven.utils.FBAuth
import com.example.heaven.utils.FBRef
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class MyrecipeBoardWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMyrecipeBoardWriteBinding

    private lateinit var profileImageBase64:String

    private var isImageUpload = false

    override fun onCreate(savedInstanceState: Bundle?) {

        val cate = arrayOf("메인","간편식","반찬","찌개","국물","디저트","빵","샐러드","기타")

        val cateSpinner: Spinner = binding.catespinner

        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, cate)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        cateSpinner.adapter = adapter

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_free_board_write)
        binding.imageArea.setOnClickListener {
            /** 이미지 추가 버튼  */
            openGallery()
            isImageUpload = true
        }
        binding.writeBtn.setOnClickListener {

            writePost()
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

    private fun writePost(){
        val title = binding.titleArea.text.toString()
        val cate = binding.catespinner.selectedItem.toString()
        val ingredient = binding.ingredientArea.text.toString()
        val progress = binding.progressArea.text.toString()

        val url = URL("http://10.0.2.2:8080/recipe/write-recipe")
        val connection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Accept", "application/json")
        connection.doInput = true
        connection.doOutput = true

        val jsonString = "{\"title\":$title, \"cate\":$cate, \"ingredient\":$ingredient, \"progress\":$progress, \"image\":$profileImageBase64}"

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