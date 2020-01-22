package dev.aylton.sitemap.views.account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.UserModel
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_account.*

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

        init(upEnabled = true, optionsMenu = false)
    }


    override fun showUserData(user: UserModel, numPublicSites: Int, numPrivateSites: Int){
        userEmail.text = String.format(resources.getString(R.string.user_email), user.email)
        userPass.text = String.format(resources.getString(R.string.user_pass), user.password)
        publicSites.text = String.format(resources.getString(R.string.public_sites), numPublicSites)
        privateSites.text = String.format(resources.getString(R.string.private_sites), numPrivateSites)
        visitedSites.text = String.format(resources.getString(R.string.visited_sites), user.visitedSites.size)
    }

    override fun toggleEnable(isEnabled: Boolean) {
        if (!isEnabled){
            super.showProgress(progressBar)
            userEmail.text = String.format(resources.getString(R.string.user_email), "...")
            userPass.text = String.format(resources.getString(R.string.user_pass), "...")
            publicSites.text = String.format(resources.getString(R.string.public_sites), 0)
            privateSites.text = String.format(resources.getString(R.string.private_sites), 0)
            visitedSites.text = String.format(resources.getString(R.string.visited_sites), 0)
        }else
            super.hideProgress(progressBar)
    }

}
