package uz.tezpos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.tezpos.databinding.ItemRvBinding
import uz.tezpos.databinding.ItemRvProductBinding
import uz.tezpos.models.Order
import java.text.NumberFormat
import java.util.Locale

class RvPos(
    private val itemList: ArrayList<Order>,
    val onItemClick: OnItemClick
) :
    RecyclerView.Adapter<RvPos.ExampleViewHolder>() {

    inner class ExampleViewHolder(private val binding: ItemRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: Order) {

            binding.imgPlus.setOnClickListener {
                onItemClick.onClick(news,true)
            }
            binding.imgMinus.setOnClickListener {
                onItemClick.onClick(news,false)
            }
            Glide.with(binding.root).load(news.img).into(binding.imgProduct)
            binding.txtName.text = news.name
            binding.txtSum.text= formatNumberWithDots(news.price!!*news.count!!)
            binding.txtCount.text = news.count!!.toString()

        }
    }
    fun formatNumberWithDots(number: Int): String {
        val formatter = NumberFormat.getInstance(Locale.getDefault())
        return formatter.format(number.toLong())
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val binding = ItemRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExampleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

interface OnItemClick {
    fun onClick(newsResponse: Order,isPlus:Boolean)
}
