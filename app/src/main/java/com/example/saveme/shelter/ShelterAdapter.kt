package com.example.saveme.shelter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.saveme.R
import com.example.saveme.shelter.shelterdetail.ShelterDetailActivity
import kotlinx.android.synthetic.main.item_shelter.view.*

class ShelterAdapter(val context: Context, private val shelterList: List<ShelterModel>) :
    RecyclerView.Adapter<ShelterAdapter.ShelterViewHolder>() {

/*  처음 리싸이클러뷰 만들때 만들어놓은 리스트.
    var items: MutableList<ShelterData> = mutableListOf(
        ShelterData("[개]", "진돗개", "2019-10-12", "서울시 강북구", "사거리 국민은행 앞"),
        ShelterData("[고양이]", "코리아숏헤어", "2019-10-13", "서울시 성북구", "4동 주민센터 앞"),
        ShelterData("[개]", "말티즈", "2019-10-14", "서울시 마포구", "홍대 앞")
    )
*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ShelterViewHolder(parent)

    override fun getItemCount(): Int {
        return shelterList.size
    }

    override fun onBindViewHolder(holder: ShelterViewHolder, position: Int) {

        shelterList[position].let { item ->
            with(holder) {
                Glide.with(context).load(item.img).into(ivImage)
                tvKind.text = item.kind
                tvRescueDate.text = item.date
                tvRescueArea.text = item.area
                tvRescueSpot.text = item.spot
            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ShelterDetailActivity::class.java)
            intent.putExtra("img", shelterList[position].img)
            intent.putExtra("kind", shelterList[position].kind)
            intent.putExtra("date", shelterList[position].date)
            intent.putExtra("area", shelterList[position].area)
            intent.putExtra("spot", shelterList[position].spot)
            intent.putExtra("gender", shelterList[position].gender)
            intent.putExtra("neuter", shelterList[position].neuter)
            intent.putExtra("pattern", shelterList[position].pattern)
            intent.putExtra("age", shelterList[position].age)
            intent.putExtra("weight", shelterList[position].weight)
            intent.putExtra("notice_number", shelterList[position].notice_number)
            intent.putExtra("feature", shelterList[position].feature)
            intent.putExtra("protection_center", shelterList[position].protection_center)
            intent.putExtra("department_in_charge", shelterList[position].department_in_charge)
            intent.putExtra(
                "protection_center_phone",
                shelterList[position].protection_center_phone
            )
            intent.putExtra(
                "protection_center_address",
                shelterList[position].protection_center_address
            )

            (context as ShelterActivity).startActivity(intent)
        }
    }

    inner class ShelterViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_shelter,
                parent,
                false
            )
        ) {

        val ivImage = itemView.iv_shelter_img
        val tvKind = itemView.tv_kind
        val tvRescueDate = itemView.tv_rescue_date
        val tvRescueArea = itemView.tv_rescue_area
        val tvRescueSpot = itemView.tv_rescue_spot

    }


}