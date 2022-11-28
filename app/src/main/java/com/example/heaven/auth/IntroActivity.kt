package com.example.heaven.auth


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.heaven.auth.JoinActivity
import com.example.heaven.auth.LoginActivity
import com.example.heaven.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private val bindingIntro by lazy { ActivityIntroBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingIntro.root)

        bindingIntro.loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        bindingIntro.joinBtn.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
    }
}