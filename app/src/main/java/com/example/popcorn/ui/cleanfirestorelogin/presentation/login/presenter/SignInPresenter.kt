package com.example.popcorn.ui.cleanfirestorelogin.presentation.login.presenter

import com.example.popcorn.ui.cleanfirestorelogin.domain.interactor.logininteractor.SignInInteractor
import com.example.popcorn.ui.cleanfirestorelogin.presentation.login.SignInContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

/**
 * Created by Josías de la Cruz on 19 Jan 2021
 */

class SignInPresenter(signInInteractor: SignInInteractor): SignInContract.LoginPresenter, CoroutineScope {

    var signInInteractor: SignInInteractor? = null
    var view: SignInContract.LoginView? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    init {
        this.signInInteractor = signInInteractor
    }

    override fun attachView(view: SignInContract.LoginView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun detachJob() {
        coroutineContext.cancel()
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }

    override fun signInUserWithUserAndPassword(email: String, password: String) {
        view?.showProgressBar()
        signInInteractor?.signInWithEmailAndPassword(email, password, object : SignInInteractor.SignInCallback{
            override fun onSingInSuccess() {
                if (isViewAttached()){
                    view?.hideProgressBar()
                    view?.navigateToMain()
                }
            }
            override fun onSignInFailure(errorMsg: String) {
                if (isViewAttached()){
                    view?.hideProgressBar()
                    view?.showError(errorMsg)
                }
            }

        })
    }

    override fun checkEmptyFields(email: String, password: String): Boolean {
        return email.isNotEmpty() || password.isNotEmpty()
    }
}