package com.ponkratov.autored.presentation.ui.registration

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.ponkratov.autored.BuildConfig
import com.ponkratov.autored.databinding.FragmentRegisterPhotoBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

class RegisterFragmentPhoto : Fragment() {
    private var _binding: FragmentRegisterPhotoBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<RegisterPhotoViewModel>()

    private val args by navArgs<RegisterFragmentPhotoArgs>()

    private val takePassportImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestPassportPhotoUri?.let { uri ->
                    binding.imagePassport.setImageURI(uri)
                }
            }
        }
    private var latestPassportPhotoUri: Uri? = null

    private val takeDriverLicenseImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestDriverLicensePhotoUri?.let { uri ->
                    binding.imageDriverLicense.setImageURI(uri)
                }
            }
        }
    private var latestDriverLicensePhotoUri: Uri? = null

    private val selectAvatarFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.imageAvatar.setImageURI(it)
                latestAvatarPhotoUri = it
            }
        }
    private var latestAvatarPhotoUri: Uri? = null

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRegisterPhotoBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionLauncher.launch(Manifest.permission.CAMERA)
        with(binding) {
            progressCircular.isVisible = false

            buttonPhotoPassport.setOnClickListener {
                if (hasPermission(Manifest.permission.CAMERA)) {
                    getTmpFileUri().let { uri ->
                        latestPassportPhotoUri = uri
                        takePassportImageResult.launch(uri)
                    }
                } else {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }

            buttonPhotoDriverLicense.setOnClickListener {
                if (hasPermission(Manifest.permission.CAMERA)) {
                    getTmpFileUri().let { uri ->
                        latestDriverLicensePhotoUri = uri
                        takeDriverLicenseImageResult.launch(uri)
                    }
                } else {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }

            buttonPhotoAvatar.setOnClickListener {
                selectAvatarFromGalleryResult.launch("image/*")
            }

            buttonRegister.setOnClickListener {
                if (latestAvatarPhotoUri == null
                    || latestPassportPhotoUri == null
                    || latestDriverLicensePhotoUri == null
                ) {
                    AlertDialog
                        .Builder(requireContext())
                        .setTitle("Регистрация")
                        .setMessage("Добавьте все требуемые фотографии!")
                        .setPositiveButton(android.R.string.ok, null)
                        .show()
                    return@setOnClickListener
                }

                viewModel.onRegisterButtonClicked(
                    fio = args.fio,
                    email = args.email,
                    rawPassword = args.rawPassword,
                    phone = args.phone,
                    birthdate = args.birthdate,
                    passportNumber = args.driverLicenseNumber,
                    driverLicenseNumber = args.driverLicenseNumber,
                    profileDescription = args.profileDescription,
                    profilePhoto = bmpToFile((imageAvatar.drawable as BitmapDrawable).bitmap),
                    passportPhoto = bmpToFile((imagePassport.drawable as BitmapDrawable).bitmap),
                    driverLicensePhoto = bmpToFile((imageDriverLicense.drawable as BitmapDrawable).bitmap)
                )
            }

            viewModel
                .loadingFlow
                .onEach {
                    progressCircular.isVisible = true
                    layoutRegisterPhotos.isVisible = false
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .errorFlow
                .onEach {
                    progressCircular.isVisible = false
                    layoutRegisterPhotos.isVisible = true
                    Snackbar.make(
                        requireView(),
                        it.message.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .dataFlow
                .onEach {
                    progressCircular.isVisible = false
                    findNavController().navigate(RegisterFragmentPhotoDirections.actionRegisterPhotoToRegisterWait())
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .registerFlow
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun bmpToFile(bitmap: Bitmap): File {
        val wrapper = ContextWrapper(requireContext())
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")
        val stream: OutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()
        return file
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile =
            File.createTempFile("tmp_image_file", ".png", requireActivity().cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }

        return FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}