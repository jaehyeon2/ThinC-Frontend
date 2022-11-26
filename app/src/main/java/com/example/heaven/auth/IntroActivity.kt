package com.example.heaven.auth
//
//import android.content.Intent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.heaven.auth.JoinActivity
import com.example.heaven.auth.LoginActivity
import com.example.heaven.databinding.ActivityIntroBinding

//import android.os.Bundle
//import android.widget.Toast
//import androidx.databinding.DataBindingUtil
//import com.example.heaven.MainActivity
//import com.example.heaven.R
//import com.example.heaven.databinding.ActivityIntroBinding
//import com.google.firebase.auth.FirebaseAuth
////import com.google.firebase.auth.ktx.auth
////import com.google.firebase.ktx.Firebase
//
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
//
////    private lateinit var auth: FirebaseAuth
//
////    private lateinit var binding: ActivityIntroBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        super.onCreate(savedInstanceState)
//
////        auth = Firebase.auth
//
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)
//
//        binding.loginBtn.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.joinBtn.setOnClickListener {
//            val intent = Intent(this, JoinActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.noAccountBtn.setOnClickListener {
//            auth.signInAnonymously()
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//
//                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show()
//
//                        val intent = Intent(this, MainActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        startActivity(intent)
//
//                    } else {
//
//                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_LONG).show()
//
//                    }
//                }
//        }
//    }
}