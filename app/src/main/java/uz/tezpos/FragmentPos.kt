package uz.tezpos

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import uz.tezpos.adapter.OnItemClick
import uz.tezpos.adapter.RvPos
import uz.tezpos.databinding.FragmentPosBinding
import uz.tezpos.livedata.LiveDataOrder
import uz.tezpos.models.Order
import java.text.NumberFormat
import java.util.Locale


class FragmentPos : Fragment(), OnItemClick {


    private lateinit var binding: FragmentPosBinding
    private lateinit var rvPos: RvPos
    private lateinit var list: ArrayList<Order>


    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPosBinding.inflate(layoutInflater)
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("order")

        list = ArrayList()
        rvPos = RvPos(list, this)
        binding.rv.adapter = rvPos

        LiveDataOrder.get().observe(requireActivity()) {
            if (it != null) {
                list.clear()
                list.addAll(it)
                rvPos.notifyDataSetChanged()
                setColculation(list)
            }
        }

        binding.tasdiqlash.setOnClickListener {
//            val builder = AlertDialog.Builder(binding.root.context)
//            builder.setTitle("Maxsulotni sotish?")
//                .setMessage("Rostdan ham ushbu maxsulotni sotmoqchimisiz yubormoqchimisiz?")
//                .setPositiveButton("Ha") { dialog: DialogInterface, which: Int ->
//                    // User clicked Delete
//                reference.removeValue()
//                    .addOnSuccessListener {
//                        val snackbar = Snackbar.make(binding.root,"Muvaffaqiyatli yakunlandi!",1000)
//                        snackbar.setBackgroundTint(Color.GREEN)
//                        snackbar.setTextColor(Color.WHITE)
//                        snackbar.show()
//                    }
//
//                }
//                .setNegativeButton("Yoq") { dialog: DialogInterface, which: Int ->
//                    // User clicked Cancel
//                    dialog.dismiss()
//                }
//
//
//            val dialog: AlertDialog = builder.create()
//            dialog.show()
            reference.removeValue()
                .addOnSuccessListener {
                    val snackbar = Snackbar.make(binding.root, "Muvaffaqiyatli yakunlandi!", 1000)
                    snackbar.setBackgroundTint(Color.GREEN)
                    snackbar.setTextColor(Color.WHITE)
                    snackbar.show()
                }
        }



        return binding.root
    }

    override fun onClick(newsResponse: Order, isPlus: Boolean) {
        if (isPlus) {
            val newCount = newsResponse.count!! + 1
            newsResponse.count = newCount
            reference.child(newsResponse.id!!).setValue(newsResponse)
        } else {
            if (newsResponse.count!! != 1) {
                val newCount = newsResponse.count!! - 1
                newsResponse.count = newCount
                reference.child(newsResponse.id!!).setValue(newsResponse)
            } else {
                reference.child(newsResponse.id!!).removeValue()
            }
        }
        setColculation(list)
    }

    fun setColculation(list: ArrayList<Order>): String {
        var jami = 0
        if (list.isNullOrEmpty()) {
            binding.txtUmumiyQiymat.text = "0" + " so'm"
            binding.txtUmumiy.text = "0" + " so'm"
        } else {

            list.forEach {
                jami += if (it.count != 1) {
                    it.count!! * it.price!!
                } else {
                    it.price!!
                }
            }
            binding.txtUmumiyQiymat.text = formatNumberWithDots(jami) + " so'm"
            binding.txtUmumiy.text = formatNumberWithDots(jami) + " so'm"
        }
        return formatNumberWithDots(jami) + " so'm"
    }

    fun formatNumberWithDots(number: Int): String {
        val formatter = NumberFormat.getInstance(Locale.getDefault())
        return formatter.format(number.toLong())
    }
}