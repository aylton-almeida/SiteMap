package dev.aylton.sitemap.models.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.models.SiteStore
import dev.aylton.sitemap.models.UserModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class SiteFireStore(val context: Context) : SiteStore, AnkoLogger {

    private val publicSites = ArrayList<SiteModel>()
    private var user = UserModel()
    lateinit var userId: String
    lateinit var db: FirebaseFirestore
    lateinit var st: StorageReference

    override fun findAll(): List<SiteModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun create(site: SiteModel) {
        db.collection("sites").document().set(site)
            .addOnSuccessListener {
                info { "Site added" }
            }
            .addOnFailureListener {
                info { "Site failed: $it" }
            }
    }

    override fun update(site: SiteModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(site: SiteModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(id: Long): SiteModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setIsVisited(site: SiteModel, isVisited: Boolean = true) {
        if (isVisited)
            user.visitedSites.add(site.id)
        else
            user.visitedSites.remove(site.id)
        db.collection("users").document(userId).set(user)
    }

    fun fetchSites(callback: (docs: ArrayList<SiteModel>) -> Unit) {
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseFirestore.getInstance()
        st = FirebaseStorage.getInstance().reference

        db.collection("sites").addSnapshotListener { snapshot, e ->
            publicSites.clear()
            if (e != null) {
                info("Listen Failed: $e")
                return@addSnapshotListener
            }
            if (snapshot != null) {
                for (doc in snapshot.documents) {
                    val newSite = doc.toObject(SiteModel::class.java)!!
                    newSite.id = doc.id
                    info { doc.data }
                    if (user.visitedSites.contains(newSite.id))
                        newSite.isVisited = true
                    publicSites.add(newSite)
                }
                callback(publicSites)
            } else {
                info("Current data: null")
            }
        }

        db.collection("users").document(userId).addSnapshotListener { snapshot, e ->
            user = UserModel()
            if (e != null) {
                info { "Listen failed $e" }
                return@addSnapshotListener
            }
            if (snapshot != null) {
                if (snapshot.data != null)
                    user = snapshot.toObject(UserModel::class.java)!!
                for (site in publicSites)
                    site.isVisited = user.visitedSites.contains(site.id)
                callback(publicSites)
            } else
                info { "Current data: null" }
        }
    }
}