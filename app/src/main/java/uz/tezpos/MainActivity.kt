package uz.tezpos

import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import uz.tezpos.databinding.ActivityMainBinding
import uz.tezpos.livedata.LiveDataMahsulotlar
import uz.tezpos.livedata.LiveDataOrder
import uz.tezpos.models.Order
import uz.tezpos.models.Product
import uz.tezpos.models.User


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val barcodeLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            val originalIntent = result.originalIntent
            if (originalIntent == null) {
                Log.d("MainActivity", "Cancelled scan")
                Toast.makeText(this@MainActivity, "Cancelled", Toast.LENGTH_LONG).show()
            } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                Log.d(
                    "MainActivity",
                    "Iltimos kamera uchun ruxsat bering!"
                )
                Toast.makeText(
                    this@MainActivity,
                    "Iltimos kamera uchun ruxsatnoma bering",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            var checkavailable = true
            for (mahsulot in listMahsulot) {
                if (mahsulot.qr == result.contents) {
                    var check = true
                    for (i in list){
                        if (i.productId==mahsulot.id){
                            val newcount = i.count!!+1
                            i.count = newcount
                            reference.child(i.id!!).setValue(i).addOnSuccessListener {
                                binding.container.findNavController().navigate(R.id.fragmentPos)
                            }
                            check = false
                            break
                        }
                    }
                    if (check){
                        val id = reference.push().key.toString()
                        val order =
                            Order(id, mahsulot.id, 1, mahsulot.narxi, mahsulot.img, mahsulot.nomi)
                        reference.child(id).setValue(order).addOnSuccessListener {
                            binding.container.findNavController().navigate(R.id.fragmentPos)
                            updateDesign(2)
                            navigate(2)
                        }
                    }
              checkavailable = false
                    break
                }
            }
            if (checkavailable){
                val snackbar = Snackbar.make(binding.root,"bunday mahsulot bazada mavjud emas!",2000)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }
        }
    }

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var referenceMahsulot: DatabaseReference

    private lateinit var list: ArrayList<Order>
    private lateinit var listMahsulot: ArrayList<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("order")
        referenceMahsulot = firebaseDatabase.getReference("mahsulot")
        list = ArrayList()
        listMahsulot = ArrayList()
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                list.clear()
                for (child in children) {
                    val value = child.getValue(Order::class.java)
                    if (value != null) {
                        list.add(value)
                    }
                }
                LiveDataOrder.set(list)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        referenceMahsulot.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                listMahsulot.clear()
                for (child in children) {
                    val value = child.getValue(Product::class.java)
                    if (value != null) {
                        listMahsulot.add(value)
                    }
                }
                binding.splash.visibility = View.INVISIBLE
                LiveDataMahsulotlar.set(listMahsulot)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })




        binding.savat.setOnClickListener {
            binding.container.findNavController().navigate(R.id.fragmentPos)
        }

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
        binding.menu.setOnClickListener {
            binding.drawer.openDrawer(Gravity.END)
        }
        binding.skanner.setOnClickListener {
            scanBarcode(it)
        }
    }

    fun scanBarcode(view: View?) {
        barcodeLauncher.launch(ScanOptions())
    }
//    private fun skanner(){
//        val options = ScanOptions()
//        options.setPrompt("Volumn up to flash on")
//        options.setBeepEnabled(true)
//        options.setOrientationLocked(true)
//        options.setCaptureActivity(CaptureAct::class.java)
//
//    }

    private fun navigate(choose: Int) {
        when (choose) {
            1 -> {
                binding.container.findNavController().navigate(R.id.fragmentMain)
            }

            2 -> {
                binding.container.findNavController().navigate(R.id.fragmentPos)
            }

            3 -> {
                binding.container.findNavController().navigate(R.id.fragmentMahsulotlar)
            }

            4 -> {
                binding.container.findNavController().navigate(R.id.fragmentSklad)
            }

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