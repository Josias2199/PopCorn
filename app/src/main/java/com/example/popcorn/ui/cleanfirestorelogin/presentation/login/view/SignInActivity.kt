package com.example.popcorn.ui.cleanfirestorelogin.presentation.login.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.popcorn.R
import com.example.popcorn.ui.MainActivity
import com.example.popcorn.ui.cleanfirestorelogin.base.BaseActivity
import com.example.popcorn.ui.cleanfirestorelogin.domain.interactor.logininteractor.SignInInteractorImpl
import com.example.popcorn.ui.cleanfirestorelogin.presentation.login.SignInContract
import com.example.popcorn.ui.cleanfirestorelogin.presentation.login.presenter.SignInPresenter
import com.example.popcorn.ui.cleanfirestorelogin.presentation.register.view.SignUpActivity
import kotlinx.android.synthetic.main.activity_auth.*

class SignInActivity : BaseActivity(), SignInContract.LoginView {

    private lateinit var presenter: SignInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = SignInPresenter(SignInInteractorImpl())
        presenter.attachView(this)
        btn_signIn.setOnClickListener{
            signIn()
        }
        etxt_register.setOnClickListener {
            navigateToRegister()
        }
    }

    override fun getLayout(): Int = R.layout.activity_auth

    override fun showError(msgError: String?) {
        toast(this, msgError)
    }

    override fun showProgressBar() {
        progressBar_signIn.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar_signIn.visibility = View.GONE
    }

    override fun signIn() {
        val email = etxt_email.editText?.text.toString().trim()
        val password = etxt_password.editText?.text.toString().trim()
        if(presenter.checkEmptyFields(email, password))
            toast(this, "Empty fields")
        else
            presenter.signInUserWithUserAndPassword(email, password)
    }

    override fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun navigateToRegister() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.detachView()
        presenter.detachJob()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        presenter.detachJob()
    }
}