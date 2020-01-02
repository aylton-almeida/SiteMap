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

    val publicSites = ArrayList<SiteModel>()
    val privateSites = ArrayList<SiteModel>()
    private var user = UserModel()
    private lateinit var userId: String
    private lateinit var db: FirebaseFirestore
    private lateinit var st: StorageReference

    override fun findAll(): List<SiteModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun create(site: SiteModel) {
        // TODO: Finish implementation
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
        // TODO: Finish implementation
        db.collection("sites").document(site.id).delete()
            .addOnSuccessListener {
                info { "Site added" }
            }
            .addOnFailureListener {
                info { it }
            }
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

    fun fetchSites(callback: () -> Unit, isPublic: Boolean) {
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseFirestore.getInstance()
        st = FirebaseStorage.getInstance().reference

        db.collection("sites").whereEqualTo("public", isPublic)
            .addSnapshotListener { snapshot, e ->
                if (isPublic) publicSites.clear() else privateSites.clear()
                if (e != null) {
                    info("Listen Failed: $e")
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    for (doc in snapshot.documents) {
                        val newSite = doc.toObject(SiteModel::class.java)!!
                        newSite.id = doc.id
                        if (user.visitedSites.contains(newSite.id))
                            newSite.visited = true
                        if (isPublic) publicSites.add(newSite)
                        else
                            if (newSite.userId == userId)
                                privateSites.add(newSite)
                    }
                    callback()
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
                    site.visited = user.visitedSites.contains(site.id)
                for (site in privateSites)
                    site.visited = user.visitedSites.contains(site.id)
                callback()
            } else
                info { "Current data: null" }
        }
    }
}