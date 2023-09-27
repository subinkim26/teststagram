package com.example.teststagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.teststagram.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {


    lateinit var auth : FirebaseAuth
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        binding.emailLoginButton.setOnClickListener {
            siginAndSignup()
        }

    }


    fun siginAndSignup(){
        var id = binding.edittextId.text.toString()
        var password = binding.edittextPassword.text.toString()
        auth.createUserWithEmailAndPassword(id, password).addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                //아이디 생성 -> 메인화면 이동
                moveMain(task.result?.user)
            }else{
                //아이디 중복의 경우?'
                siginEmail()

            }
        }

    }

    fun moveMain(user : FirebaseUser?){
        if(user != null){
            if(user.isEmailVerified){
                finish()//로그인 화면 종료
                startActivity(Intent(this,MainActivity::class.java))
            }else{
                user.sendEmailVerification()
            }

        }
    }

    fun siginEmail(){
        var id = binding.edittextId.text.toString()
        var password = binding.edittextPassword.text.toString()
        auth.signInWithEmailAndPassword(id,password).addOnCompleteListener {
            task->
            if(task.isSuccessful){
                moveMain(task.result?.user)
            }
        }
    }
}