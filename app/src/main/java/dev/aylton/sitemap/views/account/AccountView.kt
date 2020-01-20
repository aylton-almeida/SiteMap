package dev.aylton.sitemap.views.account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.UserModel
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_account.*
import org.jetbrains.anko.info

class AccountView : BaseView() {

    lateinit var presenter: AccountPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = initPresenter(AccountPresenter(this)) as AccountPresenter

        init(toolbar, upEnabled = true, optionsMenu = false, title = "Account")

        btnSignOut.setOnClickListener { presenter.doSignOut() }
    }


    override fun showUserData(user: UserModel, numPublicSites: Int, numPrivateSites: Int){
        userEmail.text = String.format(resources.getString(R.string.user_email), user.email)
        userPass.text = String.format(resources.getString(R.string.user_pass), user.password)
        publicSites.text = String.format(resources.getString(R.string.public_sites), numPublicSites)
        privateSites.text = String.format(resources.getString(R.string.private_sites), numPrivateSites)
        visitedSites.text = String.format(resources.getString(R.string.visited_sites), user.visitedSites.size)
    }

}
