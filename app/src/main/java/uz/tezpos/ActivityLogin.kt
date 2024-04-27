package uz.tezpos

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.dynamic.IFragmentWrapper
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import uz.tezpos.cache.Cache
import uz.tezpos.databinding.ActivityLoginBinding
import uz.tezpos.models.User

class ActivityLogin : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var firebase:FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var list:ArrayList<User>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebase = FirebaseDatabase.getInstance()
        reference = firebase.getReference("user")
        list = ArrayList()

        val registered = Cache.readStringFromCache(this,"token")
        if (registered!=null&&registered=="true"){
            updateUi()
        }

        reference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                list.clear()
                for (child in children){
                    val value = child.getValue(User::class.java)
                    if(value!=null){
                        list.add(value)
                    }
                }
                binding.splash.visibility  = View.INVISIBLE
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        binding.login.setOnClickListener {
            val phone = binding.edtPhone.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (phone.isNotBlank()&&password.isNotBlank()){
                var check = true
                for(i in list) {
                    if (i.phone == phone && i.password==password){
                        check = false
                        Cache.writeStringToCache(this,"token","true")
                        updateUi()
                        break
                    }
                }
                if (check){
                    tryAgain()
                }



            }

        }

    }

    private fun updateUi(){
        val snackbar = Snackbar.make(binding.root,"Muvaffaqiyatli",1000)
        snackbar.setTextColor(Color.GREEN)
        snackbar.show()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
    private fun tryAgain(){
        val snackbar = Snackbar.make(binding.root,"User mavjud emas yoki parol noto'g'ri",1500)
        snackbar.setTextColor(Color.RED)
        snackbar.show()
    }
}