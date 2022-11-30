package com.example.heaven.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.heaven.R
import com.example.heaven.myrecipeboard.MyrecipeBoardActivity
import com.example.heaven.freeBoard.FreeBoardActivity
import com.example.heaven.recipeBoard.RecipeBoardActivity
import com.example.heaven.databinding.FragmentTalkBinding
import com.example.heaven.myrecipeboard.MyrecipeBoardInsideActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class TalkFragment : Fragment() {

    private lateinit var binding : FragmentTalkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)

        binding.talk1.setOnClickListener {
//            val intent = Intent(context, FreeBoardActivity::class.java)
//            startActivity(intent)
            BoardPage()
        }

        binding.talk2.setOnClickListener {
            RecipePage()
        }

        binding.talk3.setOnClickListener {
            MyRecipePage()
        }

        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }

        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)
        }

        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)
        }

        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)
        }

        return binding.root

    }

    private fun MyRecipePage() {
        val url = URL("http://10.0.2.2:8080/recipe_owner_list?owner=test")
        Thread{
            try{
                Log.w("connect", "success")

                val connection = url.openConnection() as HttpURLConnection

                val streamReader = InputStreamReader(connection.inputStream)
                val buffered = BufferedReader(streamReader)

                val json = StringBuilder()
                while (true) {
                    val data = buffered.readLine() ?: break
                    json.append(data)
                }

                Log.w("message", json.toString())

                val intent = Intent(context, MyrecipeBoardInsideActivity::class.java)
                intent.putExtra("json",json.toString())
                startActivity(intent)

            }catch (e:Exception){
                e.printStackTrace()
            }
        }.start()

    }

    private fun RecipePage() {
        val url = URL("http://10.0.2.2:8080/recipe")
        Thread{
            try{
                Log.w("connect", "success")

                val connection = url.openConnection() as HttpURLConnection

                val streamReader = InputStreamReader(connection.inputStream)
                val buffered = BufferedReader(streamReader)

                val json = StringBuilder()
                while (true) {
                    val data = buffered.readLine() ?: break
                    json.append(data)
                }

                Log.w("message", json.toString())

                val intent = Intent(context, RecipeBoardActivity::class.java)
                intent.putExtra("json",json.toString())
                startActivity(intent)

            }catch (e:Exception){
                e.printStackTrace()
            }
        }.start()

    }

    private fun BoardPage() {
        val url = URL("http://10.0.2.2:8080/board")
        Thread{
            try{
                Log.w("connect", "success")

                val connection = url.openConnection() as HttpURLConnection

                val streamReader = InputStreamReader(connection.inputStream)
                val buffered = BufferedReader(streamReader)

                val json = StringBuilder()
                while (true) {
                    val data = buffered.readLine() ?: break
                    json.append(data)
                }

                Log.w("message", json.toString())

                val intent = Intent(context, FreeBoardActivity::class.java)
                intent.putExtra("json",json.toString())
                startActivity(intent)

            }catch (e:Exception){
                e.printStackTrace()
            }
        }.start()

    }

}