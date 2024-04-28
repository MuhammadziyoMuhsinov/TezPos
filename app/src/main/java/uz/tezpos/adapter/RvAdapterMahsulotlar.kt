package uz.tezpos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.tezpos.databinding.ItemRvProductBinding
import uz.tezpos.models.Product
import java.text.NumberFormat
import java.util.Locale

class RvAdapterMahsulot(
    private val itemList: ArrayList<Product>,
    val onItemClick: OnItemClickMahsulot
) :
    RecyclerView.Adapter<RvAdapterMahsulot.ExampleViewHolder>() {

    inner class ExampleViewHolder(private val binding: ItemRvProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: Product) {

            binding.root.setOnClickListener {
                onItemClick.newsClick(news)
            }

            binding.txtName.text = news.nomi
            binding.txtSum.text = formatNumberWithDots(news.narxi!!) + " so'm"
            Glide.with(binding.root).load(news.img).into(binding.imgProduct)



        }
    }
    fun formatNumberWithDots(number: Int): String {
        val formatter = NumberFormat.getInstance(Locale.getDefault())
        return formatter.format(number.toLong())
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val binding = ItemRvProductBinding.inflate(
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

interface OnItemClickMahsulot {
    fun newsClick(newsResponse: Product)
}
