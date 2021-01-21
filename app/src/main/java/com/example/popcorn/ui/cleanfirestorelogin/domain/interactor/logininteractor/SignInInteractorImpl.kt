package com.example.popcorn.ui.cleanfirestorelogin.domain.interactor.logininteractor

import com.google.firebase.auth.FirebaseAuth

class SignInInteractorImpl: SignInInteractor {

    override fun signInWithEmailAndPassword(
        email: String,
        password: String,
        listener: SignInInteractor.SignInCallback
    ) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    listener.onSingInSuccess()
                }else
                    listener.onSignInFailure(it.exception?.message!!)
            }
    }
}