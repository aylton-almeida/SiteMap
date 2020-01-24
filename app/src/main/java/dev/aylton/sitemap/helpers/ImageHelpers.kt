package dev.aylton.sitemap.helpers

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AlertDialog.Builder
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import dev.aylton.sitemap.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


const val REQUEST_CAMERA_PERMISSION_CODE = 57

// Check for permission and ask for it if not granted
fun checkCameraPermission(parent: Fragment): Boolean {
    return if (ActivityCompat.checkSelfPermission(
            parent.context!!,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    ) true
    else {
        parent.requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION_CODE
        )
        false
    }
}

fun isPermissionGranted(code: Int, grantResults: IntArray): Boolean {
    var permissionGranted = false
    if (code == REQUEST_CAMERA_PERMISSION_CODE) {
        when {
            grantResults.isEmpty() -> Log.i("Camera", "User interaction was cancelled.")
            (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> {
                permissionGranted = true
                Log.i("Camera", "Permission Granted.")
            }
            else -> Log.i("Camera", "Permission Denied.")
        }
    }
    return permissionGranted
}

// Called if no permission
fun showImagePicker(parent: Fragment, id: Int) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_OPEN_DOCUMENT
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    val chooser = Intent.createChooser(intent, R.string.select_site_image.toString())
    parent.startActivityForResult(chooser, id)
}

// Called if persmission granted
fun showImageDialog(parent: Fragment, idLocal: Int, idCamera: Int) {
    val pictureDialog = Builder(parent.context!!)
    pictureDialog.setTitle("Select Action")
    val pictureDialogItems = arrayOf(
        "Select photo from gallery",
        "Capture photo from camera"
    )
    pictureDialog.setItems(
        pictureDialogItems
    ) { _, which ->
        when (which) {
            0 -> choosePhotoFromGallery(parent, idLocal)
            1 -> takePhotoFromCamera(parent, idCamera)
        }
    }
    pictureDialog.show()
}

fun choosePhotoFromGallery(parent: Fragment, id: Int) {
    val intent = Intent(
        Intent.ACTION_PICK,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    )
    parent.startActivityForResult(intent, id)
}

fun takePhotoFromCamera(parent: Fragment, id: Int) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    parent.startActivityForResult(intent, id)
}

fun readImageFromPath(context: Context, path: String): Bitmap? {
    var bitmap: Bitmap? = null
    val uri = Uri.parse(path)
    if (uri != null) {
        try {
            val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
            val fileDescriptor = parcelFileDescriptor?.fileDescriptor
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor?.close()
        } catch (e: Exception) {
        }
    }
    return bitmap
}

// Save camera image locally
fun createImageFile(context: Context): File? {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.GERMANY).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    var mFileTemp: File? = null
    val root: String =
        context.getDir("my_sub_dir", Context.MODE_PRIVATE).absolutePath
    val myDir = File("$root/Img")
    if (!myDir.exists()) {
        myDir.mkdirs()
    }
    try {
        mFileTemp = File.createTempFile(imageFileName, ".jpg", myDir.absoluteFile)
    } catch (e1: IOException) {
        e1.printStackTrace()
    }
    return mFileTemp
}

fun getBitmapImageURI(fragment: Fragment, currentImage: Bitmap): Uri? {
    val file = createImageFile(fragment.context!!)
    return if (file != null) {
        val fout: FileOutputStream
        try {
            fout = FileOutputStream(file)
            currentImage.compress(Bitmap.CompressFormat.PNG, 100, fout)
            fout.flush()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        Uri.fromFile(file)
    }else null
}