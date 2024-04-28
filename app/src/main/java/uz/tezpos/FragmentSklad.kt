package uz.tezpos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.tezpos.databinding.FragmentSkladBinding

class FragmentSklad : Fragment() {

    private lateinit var binding:FragmentSkladBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentSkladBinding.inflate(layoutInflater)
        return binding.root
    }


}