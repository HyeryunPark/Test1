package com.example.test1.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.R
import kotlinx.android.synthetic.main.item_community.view.*

class CommunityAdapter : RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    var items: MutableList<CommunityData> = mutableListOf(
        CommunityData("유저1","1일 전","유저1의 제목1","유저1의 내용1\n 내용1\n 내용"),
        CommunityData("유저2","3일 전","유저2의 제목2","유저2의 내용2\n 내용2\n 내용"),
        CommunityData("유저3","4일 전","유저3의 제목3","유저3의 내용3\n 내용3\n 내용"),
        CommunityData("유저4","5일 전","유저4의 제목4","유저4의 내용4\n 내용4\n 내용")

    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CommunityViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        items[position].let { item ->
            with(holder){
                tvName.text = item.user_name
                tvTime.text = item.writing_time
                tvTitle.text = item.writing_title
                tvContent.text = item.writing_content
            }
        }
    }

    inner class CommunityViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_community, parent, false)) {
            val tvName = itemView.tv_community_user_name
            val tvTime = itemView.tv_community_writing_time
            val tvTitle = itemView.tv_community_writing_title
            val tvContent = itemView.tv_community_writing_content
    }
}