package com.example.saveme.community

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.saveme.R
import kotlinx.android.synthetic.main.item_community.view.*
import java.text.SimpleDateFormat

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
        val outputText: String

        communityList[position].let { item ->
            with(holder) {
                /*tvName.text = item.user_name
                tvTime.text = item.writing_time
                tvTitle.text = item.writing_title
                tvContent.text = item.writing_content*/

                tvName.text = item.user_name

                // date
                val outputFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S")

                val inputText = item.community_date
                val date = inputFormat.parse(inputText)
                outputText = outputFormat.format(date)
                tvTime.text = outputText

                tvTitle.text = item.community_title
                tvContent.text = item.community_content
                Glide.with(context as CommunityActivity).load(item.img1).into(ivImg1)

                if (item.img2 != null) {
                    ivImg2.visibility = View.VISIBLE
                    Glide.with(context as CommunityActivity).load(item.img2).into(ivImg2)
                }
                if (item.img3 != null) {
                    ivImg3.visibility = View.VISIBLE
                    Glide.with(context as CommunityActivity).load(item.img3).into(ivImg3)
                }

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
        val ivImg1 = itemView.iv_community_image1
        val ivImg2 = itemView.iv_community_image2
        val ivImg3 = itemView.iv_community_image3
    }
}