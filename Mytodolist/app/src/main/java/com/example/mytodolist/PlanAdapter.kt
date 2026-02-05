package com.example.mytodolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlanAdapter(private val listData: List<Map<String, Any>>) :
    RecyclerView.Adapter<PlanAdapter.PlanViewHolder>() {

    class PlanViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.btnIcon) // Sesuaikan ID di item_plan.xml
        val txt: TextView = v.findViewById(R.id.tulis) // Sesuaikan ID di item_plan.xml
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fragment_addplane, parent, false)
        return PlanViewHolder(v)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val item = listData[position]
        holder.txt.text = item["judul"].toString()
        holder.img.setImageResource(item["ikon"] as Int)
    }

    override fun getItemCount() = listData.size
}