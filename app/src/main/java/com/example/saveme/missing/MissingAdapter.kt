package com.example.saveme.missing

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.saveme.R
import com.example.saveme.missing.createmissing.MissingReportActivity
import com.example.saveme.missing.missingdetail.MissingDetailActivity
import kotlinx.android.synthetic.main.item_missing.view.*
import java.text.SimpleDateFormat


class MissingAdapter(
    val context: Context,
    private val missingList: ArrayList<MissingModel>,
    private val presenterMissing: MissingPresenter
) :
    RecyclerView.Adapter<MissingAdapter.MissingViewHolder>() {

    fun addItem(item: MissingModel) {//아이템 추가
        if (missingList != null) {//널체크 해줘야함
            missingList.add(item)
        }
    }

    fun removeAt(position: Int) {
        missingList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MissingViewHolder(parent)

    override fun getItemCount(): Int {
        return missingList.size
    }

    override fun onBindViewHolder(holder: MissingViewHolder, position: Int) {
        val outputText: String

        missingList[position].let { item ->
            with(holder) {

                val tvMissingImage = itemView.findViewById<ImageView>(R.id.tv_missing_image)
                Glide.with(context as MissingActivity).load(item.image1).into(tvMissingImage)

                when (item.status) {
                    "실종" -> {
                        tvMissingStatus.text = "실종"
                        tvMissingStatus.setBackgroundColor(Color.parseColor("#7ff77345"))
                    }
                    "보호" -> {
                        tvMissingStatus.text = "보호"
                        tvMissingStatus.setBackgroundColor(Color.parseColor("#7f66CC00"))
                    }
                    "목격" -> {
                        tvMissingStatus.text = "목격"
                        tvMissingStatus.setBackgroundColor(Color.parseColor("#7fe3d21b"))
                    }
                    "완료" -> {
                        tvMissingStatus.text = "완료"
                        tvMissingStatus.setBackgroundColor(Color.parseColor("#7f0033CC"))
                    }

                    // datetime 에서 date 만 뽑아와서 출력
                }
                tvMissingSpecies.text = "[" + item.species + "]"
                tvMissingBreed.text = item.breed
                tvMissingGender.text = item.gender
                tvMissingAge.text = item.age
                if (item.weight == "모름") {
                    tvMissingWeight.text = item.weight
                } else
                    tvMissingWeight.text = item.weight


                tvMissingPattern.text = item.pattern

                // datetime 에서 date 만 뽑아와서 출력
                val outputFormat = SimpleDateFormat("yyyy-MM-dd")
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S")

                val inputText = item.date
                val date = inputFormat.parse(inputText)
                outputText = outputFormat.format(date)
                tvMissingDate.text = outputText

                tvMissingLocation.text = item.detailLocation


            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, MissingDetailActivity::class.java)
            intent.putExtra("status", missingList[position].status)
            intent.putExtra("species", missingList[position].species)
            intent.putExtra("breed", missingList[position].breed)
            intent.putExtra("gender", missingList[position].gender)
            intent.putExtra("neuter", missingList[position].neuter)
            intent.putExtra("age", missingList[position].age)
            intent.putExtra("weight", missingList[position].weight)
            intent.putExtra("pattern", missingList[position].pattern)
            intent.putExtra("feature", missingList[position].feature)
            intent.putExtra("etc", missingList[position].etc)
            intent.putExtra("phone", missingList[position].phone)
            intent.putExtra("date", outputText)
            intent.putExtra("detail_location", missingList[position].detailLocation)
            intent.putExtra("city", missingList[position].city)
            intent.putExtra("district", missingList[position].district)
            intent.putExtra("image1", missingList[position].image1)
            intent.putExtra("image2", missingList[position].image2)
            intent.putExtra("image3", missingList[position].image3)

            (context as MissingActivity).startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            val items = arrayOf<CharSequence>("수정하기", "삭제하기")
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setItems(items, DialogInterface.OnClickListener { dialog, item ->

                if (items[item] == items[0]) {  // 수정하기
                    Log.e("missingList.id", missingList[position].id.toString())
                    presenterMissing.modifyActivity(
                        missingList[position].id, MissingModel(
                            missingList[position].id,
                            missingList[position].status,
                            missingList[position].date,
                            missingList[position].city,
                            missingList[position].district,
                            missingList[position].detailLocation,
                            missingList[position].phone,
                            missingList[position].species,
                            missingList[position].breed,
                            missingList[position].gender,
                            missingList[position].neuter,
                            missingList[position].age,
                            missingList[position].weight,
                            missingList[position].pattern,
                            missingList[position].feature,
                            missingList[position].etc,
                            missingList[position].image1,
                            missingList[position].image2,
                            missingList[position].image3
                        )
                    )
                    dialog!!.dismiss()
                } else if (items[item] == items[1]) {    // 삭제하기
                    presenterMissing.deleteItems(missingList[position].id, context)
                    removeAt(position)
                    dialog!!.dismiss()
                }
            })
            builder.show()

            true
        }


    }

    inner class MissingViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_missing,
                parent,
                false
            )
        ) {
        val tvMissingImage: ImageView = itemView.tv_missing_image
        val tvMissingStatus: TextView = itemView.tv_missing_status
        val tvMissingSpecies: TextView = itemView.tv_missing_species
        val tvMissingBreed: TextView = itemView.tv_missing_breed
        val tvMissingGender: TextView = itemView.tv_missing_gender
        val tvMissingAge: TextView = itemView.tv_missing_age
        val tvMissingWeight: TextView = itemView.tv_missing_weight
        val tvMissingPattern: TextView = itemView.tv_missing_pattern
        val tvMissingDate: TextView = itemView.tv_missing_date
        val tvMissingLocation: TextView = itemView.tv_missing_detail_location
    }
}