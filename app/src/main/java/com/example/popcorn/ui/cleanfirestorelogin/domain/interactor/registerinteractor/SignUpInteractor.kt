package com.example.popcorn.ui.cleanfirestorelogin.domain.interactor.registerinteractor

interface SignUpInteractor {
    interface RegisterCallback{
        fun onRegisterSuccess()
        fun onRegisterFailure(errorMsg: String)
    }

    fun signUp(fullName: String, email: String, password: String, listener: RegisterCallback)
}