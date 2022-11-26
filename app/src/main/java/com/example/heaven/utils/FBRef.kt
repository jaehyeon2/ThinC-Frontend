package com.example.heaven.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {

    companion object {

        private val database = Firebase.database

        val category1 = database.getReference("contents")
        val category2 = database.getReference("contents2")

        val bookmarkRef = database.getReference("bookmark_list")

        val boardRef = database.getReference("board")
        val boardRef2 = database.getReference("board2")
        val boardRef3 = database.getReference("board3")

        val commentRef = database.getReference("comment")
        val commentRef2 = database.getReference("comment2")
        val commentRef3 = database.getReference("comment3")

    }
}