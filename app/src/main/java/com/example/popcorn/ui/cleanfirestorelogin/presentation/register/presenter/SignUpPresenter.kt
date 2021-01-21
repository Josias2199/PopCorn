package com.example.popcorn.ui.cleanfirestorelogin.presentation.register.presenter

import androidx.core.util.PatternsCompat
import com.example.popcorn.ui.cleanfirestorelogin.domain.interactor.registerinteractor.SignUpInteractor
import com.example.popcorn.ui.cleanfirestorelogin.presentation.register.SignUpContract

class SignUpPresenter(signUpInteractor: SignUpInteractor): SignUpContract.RegisterPresenter {

    var view : SignUpContract.RegisterView? = null
    private var  signUpInteractor: SignUpInteractor? = null

    init {
        this.signUpInteractor = signUpInteractor
    }


    override fun attachView(view: SignUpContract.RegisterView) {
        this.view = view
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }

    override fun detachView() {
        view = null
    }

    override fun checkEmptyName(fullName: String): Boolean {
        return fullName.isEmpty()
    }

    override fun checkValidEmail(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun checkEmptyPasswords(pw1: String, pw2: String): Boolean {
        return pw1.isEmpty() or pw2.isEmpty()
    }

    override fun checkPasswordsMatch(pw1: String, pw2: String): Boolean {
        return pw1 == pw2
    }

    override fun signUp(fullName: String, email: String, password: String) {
        view?.showProgress()
        signUpInteractor?.signUp(fullName,email, password, object : SignUpInteractor.RegisterCallback{
            override fun onRegisterSuccess() {
                view?.hideProgress()
                view?.navigateToMain()
            }
            override fun onRegisterFailure(errorMsg: String) {
                view?.showError(errorMsg)
                view?.hideProgress()
            }

        })
    }
}