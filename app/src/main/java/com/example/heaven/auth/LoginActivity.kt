package com.example.heaven.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.heaven.MainActivity
import com.example.heaven.databinding.ActivityLoginBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.os.Looper


class LoginActivity : AppCompatActivity() {

    private val bindingLogin by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingLogin.root)

        bindingLogin.loginBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        Log.w("login", "login process")
        var id = bindingLogin.idArea.text.toString()
        var pw = bindingLogin.passwordArea.text.toString()
        val url = URL("http://10.0.2.2:8080/login?id=$id&pw=$pw")
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
                if(content.toString().equals("no member")){
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed(Runnable {
                        Toast.makeText(this, "회원 정보가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
                    }, 0)
                } else{
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nick", content.toString())
                    startActivity(intent)
                    finish()
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }.start()
    }
}