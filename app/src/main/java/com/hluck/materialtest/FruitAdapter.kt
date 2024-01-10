package com.hluck.materialtest

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hluck.materialtest.databinding.FruitItemBinding

/**
 *
 * @Author： LJH
 * @Time： 2024/1/10
 * @description：水果列表适配器
 */
class FruitAdapter(private val context: Context, private val fruits:List<Fruit>) :RecyclerView.Adapter<FruitAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FruitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruits[position]
        holder.binding.apply {
            fruitName.text = fruit.name
            Glide.with(context).load(fruit.imageId).into(fruitImg)
        }
        holder.binding.root.setOnClickListener {
            val position = holder.adapterPosition
            Log.d("TAG", "onCreateViewHolder:$position")
            val fruit = fruits[position]
            val intent = Intent(context,FruitActivity::class.java).apply {
                putExtra(FruitActivity.FRUIT_NAME,fruit.name)
                putExtra(FruitActivity.FRUIT_IMG_ID,fruit.imageId)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = fruits.size

    inner class ViewHolder(val binding:FruitItemBinding):RecyclerView.ViewHolder(binding.root)

}