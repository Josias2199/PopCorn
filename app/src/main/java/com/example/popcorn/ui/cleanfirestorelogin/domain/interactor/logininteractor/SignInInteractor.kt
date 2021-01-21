package com.example.popcorn.ui.cleanfirestorelogin.domain.interactor.logininteractor

interface SignInInteractor {

    interface SignInCallback{
        fun onSingInSuccess()
        fun onSignInFailure(errorMsg: String)
    }

    fun signInWithEmailAndPassword(email: String, password: String, listener: SignInCallback)

}