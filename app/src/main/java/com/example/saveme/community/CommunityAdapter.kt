package com.example.saveme.community

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.saveme.R
import kotlinx.android.synthetic.main.item_community.view.*

class CommunityAdapter(
    val context: Context,
    private val communityList: ArrayList<CommunityModel>,
    private val presenterCommunity: CommunityPresenter
) : RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    fun addItem(item: CommunityModel) {//아이템 추가
        if (communityList != null) {
            //널체크 해줘야함
            communityList.add(item)
        }
    }

    fun removeAt(position: Int) {
        communityList.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CommunityViewHolder(parent)

    override fun getItemCount(): Int {
        return communityList.size
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        communityList[position].let { item ->
            with(holder) {
                /*tvName.text = item.user_name
                tvTime.text = item.writing_time
                tvTitle.text = item.writing_title
                tvContent.text = item.writing_content*/

                tvName.text = item.user_id.toString()
                tvTitle.text = item.community_title
                tvContent.text = item.community_content
            }
        }
    }

    inner class CommunityViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_community,
                parent,
                false
            )
        ) {
        val tvName = itemView.tv_community_user_name
        val tvTime = itemView.tv_community_writing_time
        val tvTitle = itemView.tv_community_writing_title
        val tvContent = itemView.tv_community_writing_content
    }
}