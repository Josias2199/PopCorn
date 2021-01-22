package com.example.popcorn.ui.cleanfirestorelogin.domain.interactor.logininteractor

interface SignInInteractor {

    suspend fun signInWithEmailAndPassword(email: String, password: String)

}