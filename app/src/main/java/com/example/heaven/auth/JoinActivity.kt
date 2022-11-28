package com.example.heaven.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.heaven.databinding.ActivityJoinBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class JoinActivity : AppCompatActivity() {
    private val bindingJoin by lazy { ActivityJoinBinding.inflate(layoutInflater) }

    var checkNick = true
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(bindingJoin.root)

        bindingJoin.joinBtn.setOnClickListener {

            join()

        }
    }

    private fun join(){
        var isGoToJoin = true
        var mess:String


        val id = bindingJoin.emailArea.text.toString()
        val pw = bindingJoin.passwordArea1.text.toString()
        val pw_re = bindingJoin.passwordArea2.text.toString()
        val nickname = bindingJoin.nicknameArea.text.toString()

        // 저기 값이 비어있는지 확인
        if(id.isEmpty()) {
            Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_LONG).show()
            isGoToJoin = false
        }

        if(pw.isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
            isGoToJoin = false
        }

        if(pw_re.isEmpty()) {
            Toast.makeText(this, "비밀번호를 다시 입력해주세요", Toast.LENGTH_LONG).show()
            isGoToJoin = false
        }

        // 비밀번호 2개가 같은지 확인
        if(!pw.equals(pw_re)) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show()
            isGoToJoin = false
        }

        // 비밀번호가 6자 이상인지
        if (pw.length < 6) {
            Toast.makeText(this, "비밀번호를 6자리 이상으로 입력해주세요", Toast.LENGTH_LONG).show()
            isGoToJoin = false
        }

        if (nickname.isEmpty()){
            Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_LONG).show()
            isGoToJoin = false
        }



        val url = URL("http://10.0.2.2:8080/join?id=$id&pw=$pw&nick=$nickname")


        Thread{
            try{
                if(isGoToJoin&&checkNick) {

                    val connection = url.openConnection() as HttpURLConnection

                    val streamReader = InputStreamReader(connection.inputStream)
                    val buffered = BufferedReader(streamReader)

                    val content = StringBuilder()
                    while (true) {
                        val data = buffered.readLine() ?: break
                        content.append(data)
                    }

                    Log.w("message", content.toString())
                    if (content.toString().equals("join success")) {
                        Log.w("test", "success")
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        val handler = Handler(Looper.getMainLooper())
                        handler.postDelayed(Runnable {
                            Toast.makeText(this, "이미 존재하는 아이디입니다.", Toast.LENGTH_LONG).show()
                        }, 0)
                    }
                }
            } catch (e:Exception){
                e.printStackTrace()
            }
        }.start()
    }
}