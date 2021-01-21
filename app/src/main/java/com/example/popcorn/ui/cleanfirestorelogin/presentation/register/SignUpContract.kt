package com.example.popcorn.ui.cleanfirestorelogin.presentation.register

interface SignUpContract {
    interface RegisterView{
        fun navigateToMain()
        fun signUp()
        fun showProgress()
        fun hideProgress()
        fun showError(msgError: String)
    }

    interface RegisterPresenter{
        fun attachView(view: RegisterView)
        fun isViewAttached(): Boolean
        fun detachView()
        fun checkEmptyName(fullName: String): Boolean
        fun checkValidEmail(email: String): Boolean
        fun checkEmptyPasswords(pw1: String, pw2: String): Boolean
        fun checkPasswordsMatch(pw1: String, pw2: String): Boolean
        fun signUp(fullName: String, email: String, password: String)
    }
}