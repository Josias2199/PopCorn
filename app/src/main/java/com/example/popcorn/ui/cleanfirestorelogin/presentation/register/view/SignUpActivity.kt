package com.example.popcorn.ui.cleanfirestorelogin.presentation.register.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.popcorn.R
import com.example.popcorn.ui.MainActivity
import com.example.popcorn.ui.cleanfirestorelogin.base.BaseActivity
import com.example.popcorn.ui.cleanfirestorelogin.domain.interactor.registerinteractor.SignUpInteractorImpl
import com.example.popcorn.ui.cleanfirestorelogin.presentation.register.SignUpContract
import com.example.popcorn.ui.cleanfirestorelogin.presentation.register.presenter.SignUpPresenter
import kotlinx.android.synthetic.main.activity_register.*

class SignUpActivity : BaseActivity(), SignUpContract.RegisterView {

    private lateinit var presenter: SignUpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = SignUpPresenter(SignUpInteractorImpl())
        presenter.attachView(this)

        btn_signUp.setOnClickListener {
            signUp()
        }

    }

    override fun getLayout(): Int =  R.layout.activity_register

    override fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun signUp() {
        val fullName = etxt_fullName.editText?.text.toString().trim()
        val email = etxt_email.editText?.text.toString().trim()
        val pw1 = etxt_password.editText?.text.toString().trim()
        val pw2 = etxt_confirm_password.editText?.text.toString().trim()

        if(presenter.checkEmptyName(fullName)){
            etxt_fullName.error = "The name is empty"
            return
        }
        if(!presenter.checkValidEmail(email)){
            etxt_email.error = "Invalid Email"
            return
        }
        if (presenter.checkEmptyPasswords(pw1, pw2)){
            etxt_password.error = "Empty field"
            etxt_confirm_password.error = "Empty field"
            return
        }
        if (!presenter.checkPasswordsMatch(pw1, pw2)){
            etxt_password.error = "Passwprd dont match"
            etxt_confirm_password.error = "Password dont match"
            return
        }
        presenter.signUp(fullName, email, pw1)
    }

    override fun showProgress() {
        progressBar_signUp.visibility = View.VISIBLE
        btn_signUp.isEnabled = false
    }

    override fun hideProgress() {
        progressBar_signUp.visibility = View.GONE
        btn_signUp.isEnabled = true
    }

    override fun showError(msgError: String) {
        toast(this, msgError)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}