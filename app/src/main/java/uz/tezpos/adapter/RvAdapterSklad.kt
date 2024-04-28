package uz.tezpos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.tezpos.databinding.ItemRvOmborBinding
import uz.tezpos.models.Product
import java.text.NumberFormat
import java.util.Locale

class RvAdapterSklad(
    private val itemList: ArrayList<Product>,
    val onItemClick: OnItemClickSklad
) :
    RecyclerView.Adapter<RvAdapterSklad.ExampleViewHolder>() {

    inner class ExampleViewHolder(private val binding: ItemRvOmborBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: Product) {

            binding.imgPlus.setOnClickListener {
                onItemClick.newsClick(news)
            }
            binding.name.text = news.nomi
            binding.txtCount.text = news.soni.toString()
            binding.price.text = formatNumberWithDots(news.narxi!!)+ " so'm"
            binding.info1.text = news.malumot1
            binding.info2.text = news.malumot2
            Glide.with(binding.root).load(news.img).into(binding.img)



        }
    }
    fun formatNumberWithDots(number: Int): String {
        val formatter = NumberFormat.getInstance(Locale.getDefault())
        return formatter.format(number.toLong())
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val binding = ItemRvOmborBinding.inflate(
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

interface OnItemClickSklad {
    fun newsClick(newsResponse: Product)
}
