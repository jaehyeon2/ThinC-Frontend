package com.example.heaven.fragments

import android.content.Intent
import android.os.Bundle
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
            val intent = Intent(context, FreeBoardActivity::class.java)
            startActivity(intent)
        }

        binding.talk2.setOnClickListener {
            val intent = Intent(context, RecipeBoardActivity::class.java)
            startActivity(intent)
        }

        binding.talk3.setOnClickListener {
            val intent = Intent(context, MyrecipeBoardActivity::class.java)
            startActivity(intent)
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

}