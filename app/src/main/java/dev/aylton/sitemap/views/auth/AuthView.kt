package dev.aylton.sitemap.views.auth


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.aylton.sitemap.R
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthView : BaseView() {

    private lateinit var presenter: AuthPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = initPresenter(AuthPresenter(this)) as AuthPresenter

        btnSignUp.setOnClickListener {
            showProgress(progressBar)
            toggleEnable(false)
            val email = inputEmail.text.toString()
            val password = inputPass.text.toString()
            if (email == "" || password == "") {
                showSnackbar("Please type an email and password", Color.RED)
                hideProgress(progressBar)
                toggleEnable(true)
            } else {
                presenter.doSignUp(email, password)
            }
        }

        btnSignIn.setOnClickListener {
            showProgress(progressBar)
            toggleEnable(false)
            val email = inputEmail.text.toString()
            val password = inputPass.text.toString()
            if (email == "" || password == "") {
                showSnackbar("Please type an email and password", Color.RED)
                hideProgress(progressBar)
                toggleEnable(true)
            } else {
                presenter.doSignIn(email, password)
            }
        }
    }

    override fun toggleEnable(isEnabled: Boolean){
        btnSignUp.isEnabled = isEnabled
        btnSignIn.isEnabled = isEnabled
        textLayoutEmail.isEnabled = isEnabled
        textLayoutPass.isEnabled = isEnabled
    }
}
