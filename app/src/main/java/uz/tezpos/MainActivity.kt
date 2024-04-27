package uz.tezpos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import uz.tezpos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.linearAsosiy.setOnClickListener {
            updateDesign(1)
            navigate(1)
        }
        binding.linearPos.setOnClickListener {
            updateDesign(2)
            navigate(2)
        }
        binding.linearMaxsulot.setOnClickListener {
            updateDesign(3)
            navigate(3)
        }
        binding.linearOmbor.setOnClickListener {
            updateDesign(4)
            navigate(4)
        }
    }

    private fun navigate(choose:Int){
        when (choose){
            1->{binding.container.findNavController().navigate(R.id.fragmentMain)}
            2->{binding.container.findNavController().navigate(R.id.fragmentPos)}
            3->{binding.container.findNavController().navigate(R.id.fragmentMahsulotlar)}
            4->{binding.container.findNavController().navigate(R.id.fragmentSklad)}

        }
    }
    private fun updateDesign(choose: Int) {
        when (choose) {
            1 -> {
                binding.imgViewAsosiy.setImageResource(R.drawable.menu) //asosiy
                binding.imgViewPos.setImageResource(R.drawable.post_unselected) //pos
                binding.imgViewMaxsulot.setImageResource(R.drawable.maxsulotlar_unselected) //maxsulotlar
                binding.imgViewOmbor.setImageResource(R.drawable.chegirmalar_unselected) //ombor
            }

            2 -> {
                binding.imgViewAsosiy.setImageResource(R.drawable.menu_unselected) //asosiy
                binding.imgViewPos.setImageResource(R.drawable.pos) //pos
                binding.imgViewMaxsulot.setImageResource(R.drawable.maxsulotlar_unselected) //maxsulotlar
                binding.imgViewOmbor.setImageResource(R.drawable.chegirmalar_unselected) //ombor
            }

            3 -> {
                binding.imgViewAsosiy.setImageResource(R.drawable.menu_unselected) //asosiy
                binding.imgViewPos.setImageResource(R.drawable.post_unselected) //pos
                binding.imgViewMaxsulot.setImageResource(R.drawable.maxsulotlar) //maxsulotlar
                binding.imgViewOmbor.setImageResource(R.drawable.chegirmalar_unselected) //ombor
            }

            4 -> {
                binding.imgViewAsosiy.setImageResource(R.drawable.menu_unselected) //asosiy
                binding.imgViewPos.setImageResource(R.drawable.post_unselected) //pos
                binding.imgViewMaxsulot.setImageResource(R.drawable.maxsulotlar_unselected) //maxsulotlar
                binding.imgViewOmbor.setImageResource(R.drawable.chegirmalar) //ombor
            }

        }

    }
}