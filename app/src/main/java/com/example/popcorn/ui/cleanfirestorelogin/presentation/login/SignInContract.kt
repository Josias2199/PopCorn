package com.example.popcorn.ui.cleanfirestorelogin.presentation.login

interface SignInContract {
    interface LoginView{
        fun showError(msgError: String)
        fun showProgressBar()
        fun hideProgressBar()
        fun signIn()
        fun navigateToMain()
        fun navigateToRegister()
    }

    interface LoginPresenter{
        fun attachView(view: LoginView)
        fun detachView()
        fun detachJob()
        fun isViewAttached(): Boolean
        fun signInUserWithUserAndPassword(email: String, password: String)
        fun checkEmptyFields(email: String, password: String): Boolean
    }
}