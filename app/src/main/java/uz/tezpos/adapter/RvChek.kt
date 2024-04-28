package uz.tezpos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.tezpos.databinding.ItemChekProductBinding
import uz.tezpos.databinding.ItemRvBinding
import uz.tezpos.databinding.ItemRvProductBinding
import uz.tezpos.models.Order
import java.text.NumberFormat
import java.util.Locale

class RvChek(
    private val itemList: ArrayList<Order>,
) :
    RecyclerView.Adapter<RvChek.ExampleViewHolder>() {

    inner class ExampleViewHolder(private val binding: ItemChekProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: Order) {
            binding.nomi.text = news.name
            binding.count.text = news.count.toString() + "pc"
            binding.price.text = if (news.count!!>1){
                (news.price!!*news.count!!).toString() + " uzs"
            }else{
                news.price!!.toString()+ " uzs"
            }


        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val binding = ItemChekProductBinding.inflate(
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

