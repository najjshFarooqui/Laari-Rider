package com.laari.rider.views.activity.registration

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.PermissionChecker
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.models.RegistrationModel
import com.laari.rider.utility.*

import com.laari.rider.views.activity.HomeDashboardActivity
import kotlinx.android.synthetic.main.activity_add_photo.*
import kotlinx.android.synthetic.main.choose_photo_sheet.*
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File


class AddPhotoActivity : HomeBaseActivity() {
    private val TAKE_PICTURE = 1
    private val CHOOSE_PIC = 2
    private var imageUri: Uri? = null
    private var event = 0
    private var userId: String = ""
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private var bitmap: Bitmap? = null
    private var filePath: Uri? = null
    var number: String? = ""
    private var firstName: String? = ""
    private var lastName: String? = ""
    var email: String? = ""
    private var nId: String? = ""
    private var homeCity: String = ""
    private var residentialAddress: String? = ""
    private var storageReference: StorageReference? = null
    var storage = FirebaseStorage.getInstance()
    var imageUrl = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)
        val currentFirebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        userId = currentFirebaseUser.uid

        storageReference = storage.getReferenceFromUrl(FIREBASE_STORAGE_PATH)

        val bundle = intent.extras
        if (bundle != null) {
            firstName = bundle.getString(Name)!!
            lastName = bundle.getString(LastName)!!
            email = bundle.getString(Email)!!
            nId = bundle.getString(Id)!!
            homeCity = bundle.getString(Home)!!
            residentialAddress = bundle.getString(Address)!!
        }



        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_layout)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        addPhotoIv.setOnClickListener {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
        }



        takePhoto.setOnClickListener {
            event = 1001
            callPermissions()
        }
        chooseFromGallery.setOnClickListener {
            event = 1002
            callPermissions()
        }
        imageUploadFinish.setOnClickListener {
            startNewActivity(ReferralCodeActivity())
        }

        ivBack.setOnClickListener {

            onBackPressed()

        }
    }


    private fun callPermissions() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        Permissions.check(
            this,
            permissions,
            "camera permission is required to take photo",
            null,
            object : PermissionHandler() {
                override fun onGranted() {
                    if (event == 1001) {
                        takePhotoFromCamera()
                    } else if (event == 1002) {
                        choosePhotoFromGallery()
                    }

                }

            })
    }

    private fun takePhotoFromCamera() {
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PermissionChecker.PERMISSION_GRANTED) and (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PermissionChecker.PERMISSION_GRANTED) and (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PermissionChecker.PERMISSION_GRANTED)
        ) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photo = File(Environment.getExternalStorageDirectory(), "Pic.jpg")
            intent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo)
            )
            imageUri = FileProvider.getUriForFile(
                this,
                this.applicationContext.packageName + ".provider",
                photo
            )
            startActivityForResult(intent, TAKE_PICTURE)
        } else {
            callPermissions()
        }

    }


    private fun choosePhotoFromGallery() {
        var intent = Intent()
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "select picture"), CHOOSE_PIC)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TAKE_PICTURE -> if (resultCode == Activity.RESULT_OK) {
                val selectedImage = imageUri
                contentResolver.notifyChange(selectedImage!!, null)
                val cr = contentResolver

                try {
                    bitmap = android.provider.MediaStore.Images.Media
                        .getBitmap(cr, selectedImage)

                    addPhotoIv.setImageBitmap(bitmap)
                    filePath = getImageUri(applicationContext, bitmap!!)
                    if (filePath != null) {
                        showProgress()
                        uploadImageToFirebase()

                    }


                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

                } catch (e: Exception) {
                    Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                        .show()
                    Log.e("Camera", e.toString())
                }

            }
            CHOOSE_PIC -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        return
                    }
                    val inputStream =
                        applicationContext.contentResolver
                            .openInputStream(data.data!!)
                    val bufferedInputStream = BufferedInputStream(inputStream)

                    bitmap = BitmapFactory.decodeStream(bufferedInputStream)
                    filePath = data.data
                    if (filePath != null) {
                        showProgress()
                        uploadImageToFirebase()
                    }
                    addPhotoIv.setImageBitmap(bitmap)
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

                }
            }

        }
    }


    fun uploadImageToFirebase() {

        val riversRef: StorageReference =
            storageReference!!.child(PhotoUrl + System.currentTimeMillis().toString() + ".jpg")
        riversRef.putFile(filePath!!)
            .addOnSuccessListener {
                val result = it.metadata!!.reference!!.downloadUrl
                result.addOnSuccessListener { it ->
                    imageUrl = it.toString()

                    val userData = RegistrationModel
                    userData.profilePhotoUrl = imageUrl
                    Toast.makeText(
                        this,
                        getString(R.string.image_added_successfully),
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    dismissProgress()
                }
            }
            .addOnFailureListener { exception ->

                Toast.makeText(applicationContext, exception.message, Toast.LENGTH_LONG).show()
            }
            .addOnProgressListener { taskSnapshot ->
                val progress =
                    100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
            }
    }


    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }


}

