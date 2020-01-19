package dev.aylton.sitemap.services.firebase

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dev.aylton.sitemap.helpers.readImageFromPath
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.services.SiteStore
import dev.aylton.sitemap.models.UserModel
import dev.aylton.sitemap.models.VisitedSite
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class SiteFireStore(val context: Context) : SiteStore, AnkoLogger {

    val publicSites = ArrayList<SiteModel>()
    val privateSites = ArrayList<SiteModel>()
    var user = UserModel()
    private lateinit var db: FirebaseFirestore
    private lateinit var st: StorageReference

    override fun create(site: SiteModel) {
        site.userId = user.id
        val key = db.collection("sites").document().id
        key.let {
            site.id = key
            db.collection("sites").document(site.id).set(site)
            getImages(site)
        }
    }


    override fun update(site: SiteModel) {
        db.collection("sites").document(site.id).set(site)
        val oldSite: SiteModel? = if (site.userId.isEmpty())
            publicSites.find { it.id == site.id }
        else
            privateSites.find { it.id == site.id }

        oldSite.let {
            if (!oldSite!!.images.containsAll(site.images))
                getImages(site)
        }
    }

    private fun getImages(site: SiteModel) {
        if (site.images.size > 0) {
            for (image in site.images) {
                val fileName = File(image)
                val imageName = fileName.name

                val imageRef = st.child("sites/${site.id}/$imageName")
                val baos = ByteArrayOutputStream()
                val bitMap = readImageFromPath(context, image)

                bitMap?.let {
                    bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val data = baos.toByteArray()
                    val uploadTask = imageRef.putBytes(data)
                    uploadTask.addOnFailureListener {
                        info { it.message }
                    }
                        .addOnSuccessListener { taskSnapshot ->
                            taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                                site.images[site.images.indexOf(image)] = it.toString()
                                db.collection("sites").document(site.id).set(site)
                            }
                        }
                }
            }
        }
    }

    override fun delete(site: SiteModel) {
        db.collection("sites").document(site.id).delete()
        st.child("sites/${site.id}").delete()
        setIsVisited(site, false)
    }

    fun setIsVisited(site: SiteModel, isVisited: Boolean = true) {
        if (isVisited)
            user.visitedSites.add(VisitedSite(site.id, Date()))
        else user.visitedSites.remove( user.visitedSites.find { it.id == site.id })
        db.collection("users").document(user.id).set(user)
    }

    override fun fetchSites(callback: () -> Unit, isPublic: Boolean) {
        val currUser = FirebaseAuth.getInstance().currentUser!!
        user.id = currUser.uid
        user.email = currUser.email!!
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
                        if (user.visitedSites.find { it.id == newSite.id } != null)
                            newSite.visited = true
                        if (isPublic) publicSites.add(newSite)
                        else
                            if (newSite.userId == user.id)
                                privateSites.add(newSite)
                    }
                    callback()
                } else {
                    info("Current data: null")
                }
            }

        db.collection("users").document(user.id).addSnapshotListener { snapshot, e ->
            if (e != null) {
                info { "Listen failed $e" }
                return@addSnapshotListener
            }
            if (snapshot != null) {
                if (snapshot.data != null) {
                    user = snapshot.toObject(UserModel::class.java)!!
                    user.email = currUser.email!!
                    user.id = currUser.uid
                }
                for (site in publicSites)
                    site.visited = user.visitedSites.find { it.id == site.id } != null
                for (site in privateSites)
                    site.visited = user.visitedSites.find { it.id == site.id } != null
                callback()
            } else
                info { "Current data: null" }
        }
    }
}