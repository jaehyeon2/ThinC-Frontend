package com.example.heaven.myrecipeboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.heaven.R

class MyrecipeBoardListLVAdapter(private val boardList : MutableList<MyrecipeBoardModel>) : BaseAdapter() {

    override fun getCount(): Int {
        return boardList.size
    }

    override fun getItem(position: Int): Any {
        return boardList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView

        view = LayoutInflater.from(parent?.context).inflate(R.layout.board_list_item, parent, false)

        val itemLinearLayoutView = view?.findViewById<LinearLayout>(R.id.itemView)
        val title = view?.findViewById<TextView>(R.id.titleArea)
        val ingredient = view?.findViewById<TextView>(R.id.integrateArea)
        val process = view?.findViewById<TextView>(R.id.progressArea)
        val category = view?.findViewById<TextView>(R.id.catespinner)

//        if(boardList[position].uid.equals(FBAuth.getUid())) {
//            itemLinearLayoutView?.setBackgroundColor(Color.parseColor("#ffa500"))
//        }

        title!!.text = boardList[position].title
        ingredient!!.text = boardList[position].ingredient
        process!!.text = boardList[position].process
        category!!.text = boardList[position].category

        return view!!
    }
}