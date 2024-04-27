package uz.tezpos

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import uz.tezpos.databinding.FragmentMainBinding


class FragmentMain : Fragment() {


    private lateinit var binding:FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater)

        // Create sample data entries
        binding.linechart.description.isEnabled = false
        binding.linechart.animateY(1000)

        val entries = listOf(
            Entry(0f, 2561f),
            Entry(1f, 1450f),
            Entry(2f, 1261f),
            Entry(3f, 1225f),
            Entry(4f, 1598f),
            Entry(5f, 1298f)
        )

        val dataSet = LineDataSet(entries, "месяц 2023")
        dataSet.color = Color.BLUE

        val lineData = LineData(dataSet)
        binding.linechart.data = lineData

        binding.linechart.invalidate()

        return binding.root
    }


}