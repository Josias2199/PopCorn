package com.example.popcorn.ui.cleanfirestorelogin.presentation.login.presenter

import com.example.popcorn.ui.cleanfirestorelogin.domain.interactor.logininteractor.SignInInteractor
import com.example.popcorn.ui.cleanfirestorelogin.exceptions.FirebaseLoginException
import com.example.popcorn.ui.cleanfirestorelogin.presentation.login.SignInContract
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * Created by Josías de la Cruz on 19 Jan 2021
 */

class SignInPresenter(signInInteractor: SignInInteractor) : SignInContract.LoginPresenter,
    CoroutineScope {

    var signInInteractor: SignInInteractor? = null
    var view: SignInContract.LoginView? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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
        launch {
            view?.showProgressBar()
            try {
                signInInteractor?.signInWithEmailAndPassword(email, password)
                if (isViewAttached()) {
                    view?.hideProgressBar()
                    view?.navigateToMain()
                }
            } catch (exception: FirebaseLoginException) {
                if (isViewAttached()) {
                    view?.showError(exception.message)
                    view?.hideProgressBar()
                }
            }
        }
    }

    override fun checkEmptyFields(email: String, password: String): Boolean {
        return email.isEmpty() || password.isEmpty()
    }
}