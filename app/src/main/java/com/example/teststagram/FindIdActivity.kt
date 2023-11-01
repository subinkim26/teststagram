package com.example.teststagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.teststagram.databinding.ActivityFindIdBinding
import com.example.teststagram.model.FindIdModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FindIdActivity : AppCompatActivity() {

    lateinit var binding : ActivityFindIdBinding
    lateinit var firestore: FirebaseFirestore
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_find_id)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.findIdBtn.setOnClickListener {
            readMyId()
        }

        //비밀번호 초기화 로직
        binding.findPasswordBtn.setOnClickListener {
            var number = binding.edittextEmail.text.toString()
            auth.sendPasswordResetEmail(number)
        }
        binding.dismissBtn.setOnClickListener {
            finish()//창 닫기기
        }
    }

    //아이디 찾는 로직
    fun readMyId(){
        var number = binding.edittextPhonenumber.text.toString()
        firestore.collection("findids").whereEqualTo("phoneNumber",number).get().addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                var findIdModel = task.result?.documents?.first()!!.toObject(FindIdModel::class.java) //폰넘버로 찾아진 값중에서 첫번째 값인 id를 받아준다.
                Toast.makeText(this,findIdModel!!.id, Toast.LENGTH_LONG).show()
            }
        }

    }
}