package com.example.saveme.missing

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.saveme.R
import com.example.saveme.model.MissingModel
import kotlinx.android.synthetic.main.item_missing.view.*
import java.text.SimpleDateFormat

class MissingAdapter(val context: Context, private val missingList: List<MissingModel>) :
    RecyclerView.Adapter<MissingAdapter.MissingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MissingViewHolder(parent)

    override fun getItemCount(): Int = missingList.size

    override fun onBindViewHolder(holder: MissingViewHolder, position: Int) {
        val outputText: String

        missingList[position].let { item ->
            with(holder) {
                tvMissingSpecies.text = "[" + item.species + "]"
                tvMissingBreed.text = item.breed
                tvMissingGender.text = item.gender
                tvMissingAge.text = item.age
                if (item.weight == "모름") {
                    tvMissingWeight.text = item.weight
                } else
                    tvMissingWeight.text = item.weight + "kg"


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

            (context as MissingActivity).startActivity(intent)

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
        val tvMissingSpecies = itemView.tv_missing_species
        val tvMissingBreed = itemView.tv_missing_breed
        val tvMissingGender = itemView.tv_missing_gender
        val tvMissingAge = itemView.tv_missing_age
        val tvMissingWeight = itemView.tv_missing_weight
        val tvMissingPattern = itemView.tv_missing_pattern
        val tvMissingDate = itemView.tv_missing_date
        val tvMissingLocation = itemView.tv_missing_detail_location
    }
}