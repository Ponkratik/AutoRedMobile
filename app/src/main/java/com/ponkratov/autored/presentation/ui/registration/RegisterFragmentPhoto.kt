package com.ponkratov.autored.presentation.ui.registration

import android.Manifest
import android.content.pm.PackageManager
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
import androidx.navigation.fragment.navArgs
import com.ponkratov.autored.BuildConfig
import com.ponkratov.autored.databinding.FragmentRegisterPhotoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class RegisterFragmentPhoto : Fragment() {
    private var _binding: FragmentRegisterPhotoBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<RegisterViewModel>()

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
                    binding.imagePassport.setImageURI(uri)
                }
            }
        }
    private var latestDriverLicensePhotoUri: Uri? = null

    private val selectAvatarFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.imageAvatar.setImageURI(uri)
            }
        }
    private val latestAvatarPhotoUri: Uri? = null

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

            }
        }
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png", requireActivity().cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
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