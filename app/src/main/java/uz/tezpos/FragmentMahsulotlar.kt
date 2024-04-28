package uz.tezpos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import uz.tezpos.adapter.OnItemClickMahsulot
import uz.tezpos.adapter.RvAdapterMahsulot
import uz.tezpos.databinding.FragmentMahsulotlarBinding
import uz.tezpos.livedata.LiveDataMahsulotlar
import uz.tezpos.livedata.LiveDataOrder
import uz.tezpos.models.Order
import uz.tezpos.models.Product


class FragmentMahsulotlar : Fragment(),OnItemClickMahsulot {

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    private lateinit var binding: FragmentMahsulotlarBinding
    private lateinit var rvAdapterMahsulot: RvAdapterMahsulot
    private lateinit var list:ArrayList<Product>

    private lateinit var listOrder:ArrayList<Order>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMahsulotlarBinding.inflate(layoutInflater)
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("order")

        list = ArrayList()
        listOrder = ArrayList()
        rvAdapterMahsulot = RvAdapterMahsulot(list,this)
        binding.rvProduct.adapter = rvAdapterMahsulot
        LiveDataMahsulotlar.get().observe(requireActivity()){
            if (it!=null){
                list.clear()
                list.addAll(it)
                rvAdapterMahsulot.notifyDataSetChanged()
            }
        }
        LiveDataOrder.get().observe(requireActivity()){
            if (it!=null){
                listOrder.clear()
                listOrder.addAll(it)
            }
        }




        return binding.root
    }

    override fun newsClick(newsResponse: Product) {
        var check = true
        for (order in listOrder) {
            if (order.productId==newsResponse.id){
                val newcount = order.count!!+1
                order.count = newcount
                reference.child(order.id!!).setValue(order).addOnSuccessListener {
                    findNavController().navigate(R.id.fragmentPos)
                }
               check = false
                break
            }
        }
        if (check){
            val id= reference.push().key.toString()
            val order = Order(id,newsResponse.id,1,newsResponse.narxi,newsResponse.img,newsResponse.nomi)
            reference.child(id).setValue(order).addOnSuccessListener {
                findNavController().navigate(R.id.fragmentPos)
            }
        }

    }


}