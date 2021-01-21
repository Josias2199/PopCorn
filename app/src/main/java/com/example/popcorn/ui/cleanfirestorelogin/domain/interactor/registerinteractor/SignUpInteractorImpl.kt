package com.example.popcorn.ui.cleanfirestorelogin.domain.interactor.registerinteractor

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class SignUpInteractorImpl: SignUpInteractor {
    override fun signUp(
        fullName: String,
        email: String,
        password: String,
        listener: SignUpInteractor.RegisterCallback
    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { itSingUp ->
            if (itSingUp.isSuccessful) {
                val profileUpdate = UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName)
                    .build()
                FirebaseAuth.getInstance().currentUser?.updateProfile(profileUpdate)?.addOnCompleteListener {
                    if (it.isSuccessful){
                        listener.onRegisterSuccess()
                    }
                }
            }
            else
                listener.onRegisterFailure(itSingUp.exception?.message.toString())
        }
    }

}