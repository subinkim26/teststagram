package com.example.teststagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.teststagram.databinding.ActivityInputNumberBinding
import com.example.teststagram.model.FindIdModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class InputNumberActivity : AppCompatActivity() {

    lateinit var firestore : FirebaseFirestore
    lateinit var auth : FirebaseAuth
    lateinit var binding : ActivityInputNumberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_input_number)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.apply.setOnClickListener {
            //apply버튼을 누르면 폰 번호가 저장
           savePhoneNumber()
        }
    }
    fun savePhoneNumber(){
        var findIdModel = FindIdModel()
        findIdModel.id =auth.currentUser?.email
        findIdModel.phoneNumber = binding.edittextPhonenumber.text.toString()

        firestore.collection("findids").document().set(findIdModel).addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                finish()//폰입력화면 종료
                //이메일 인증
                auth.currentUser?.sendEmailVerification()
                //이메일 인증 넘겼으니 다시 로그인 화면으로 이동
                startActivity(Intent(this,LoginActivity::class.java))
            }
        }//s를 붙여주는건 규칙같은거
    }
}