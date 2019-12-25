package dev.aylton.sitemap.models.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.models.SiteStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class SiteFireStore(val context: Context) : SiteStore, AnkoLogger {

    val sites = ArrayList<SiteModel>()
    lateinit var userId: String
    lateinit var db: FirebaseFirestore
    lateinit var st: StorageReference

    override fun findAll(): List<SiteModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun create(site: SiteModel) {
        info { "Data started" }
        db.collection("sites").document().set(site)
            .addOnSuccessListener {
                info { "Data added" }
            }
            .addOnFailureListener {
                info { "Data failed: $it" }
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

    fun fetchSites(callback: (docs: ArrayList<SiteModel>) -> Unit) {
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseFirestore.getInstance()
        st = FirebaseStorage.getInstance().reference

        db.collection("sites").addSnapshotListener { snapshot, e ->
            sites.clear()
            if (e != null) {
                info("Listen Failed: $e")
                return@addSnapshotListener
            }
            if (snapshot != null) {
                for (doc in snapshot.documents)
                    sites.add(doc.toObject(SiteModel::class.java)!!)
                callback(sites)
            } else {
                info("Current data: null")
            }
        }
    }
}