package uz.tezpos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import uz.tezpos.adapter.OnItemClickSklad
import uz.tezpos.adapter.RvAdapterSklad
import uz.tezpos.databinding.FragmentSkladBinding
import uz.tezpos.livedata.LiveDataMahsulotlar
import uz.tezpos.models.Product

class FragmentSklad : Fragment() {

    private lateinit var binding: FragmentSkladBinding
    private lateinit var rvAdapterSklad: RvAdapterSklad
    private lateinit var list: ArrayList<Product>

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSkladBinding.inflate(layoutInflater)
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("mahsulot")
        list = ArrayList()
        rvAdapterSklad = RvAdapterSklad(list, object : OnItemClickSklad {
            override fun newsClick(newsResponse: Product) {
                val newCount = newsResponse.soni!! + 1
                newsResponse.soni = newCount
                reference.child(newsResponse.id!!).setValue(newsResponse)
            }
        })
        binding.rvOmbor.adapter = rvAdapterSklad

        LiveDataMahsulotlar.get().observe(requireActivity()){
            if (it!=null){
                list.clear()
                list.addAll(it)
            }
        }

        return binding.root
    }


}