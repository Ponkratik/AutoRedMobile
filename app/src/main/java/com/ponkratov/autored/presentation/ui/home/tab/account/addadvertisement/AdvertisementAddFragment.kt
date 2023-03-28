package com.ponkratov.autored.presentation.ui.home.tab.account.addadvertisement

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ponkratov.autored.databinding.FragmentAdvertisementAddBinding
import com.ponkratov.autored.presentation.extensions.addHorisontalSpace
import com.ponkratov.autored.presentation.extensions.hideKeyboard
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class AdvertisementAddFragment : Fragment() {

    private var _binding: FragmentAdvertisementAddBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<AdvertisementAddViewModel>()

    private val imageAdapter by lazy {
        ImageUriAdapter(
            context = requireContext()
        )
    }

    private val selectImagesFromGallery =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList: List<Uri>? ->
            uriList?.let {
                imagesListUri = it.toMutableList()
                imageAdapter.submitList(it)
            }
        }
    private var imagesListUri: MutableList<Uri> = mutableListOf()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAdvertisementAddBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            initComponents()
        }
    }

    private fun initComponents() {
        with(binding) {
            progressCircular.isVisible = false
            layoutAdvertisement.isVisible = true

            photoRecyclerView.adapter = imageAdapter
            photoRecyclerView.addHorisontalSpace()

            val fuelTypes = arrayListOf("Бензин", "Дизель", "Газ/Бензин", "Электро")
            val fuelTypesAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                fuelTypes
            )
            (editTextFuel as? AutoCompleteTextView)?.setAdapter(fuelTypesAdapter)

            val bodyTypes = arrayListOf(
                "Внедорожник 3 дв.",
                "Внедорожник 5 дв.",
                "Кабриолет",
                "Купе",
                "Легковой фургон",
                "Лимузин",
                "Лифтбек",
                "Микроавтобус пассажирский",
                "Микроавтобус грузопассажирский",
                "Минивен",
                "Пикап",
                "Родстер",
                "Седан",
                "Универсал",
                "Хэтчбек 3 дв.",
                "Хэтчбек 5 дв."
            )
            val bodyTypesAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                bodyTypes
            )
            (editTextBodyType as? AutoCompleteTextView)?.setAdapter(bodyTypesAdapter)

            val transmissionTypes = arrayListOf("Автоматическая", "Механическя")
            val transmissionTypesAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                transmissionTypes
            )
            (editTextTransmisson as? AutoCompleteTextView)?.setAdapter(transmissionTypesAdapter)

            buttonBack.setOnClickListener {
                findNavController().navigateUp()
            }

            buttonAddPhotos.setOnClickListener {
                openGalleryForImage()
            }

            buttonCreate.setOnClickListener {
                if (!validateInputs()) return@setOnClickListener
                viewModel.onSendButtonClicked(
                    checkboxConditioner.isChecked,
                    checkboxAllWheelDrive.isChecked,
                    checkboxLeatherSeats.isChecked,
                    checkboxChildSeats.isChecked,
                    checkboxHeatedSeats.isChecked,
                    checkboxCooledSeats.isChecked,
                    checkboxGps.isChecked,
                    checkboxSkiRack.isChecked,
                    checkboxAudioInput.isChecked,
                    checkboxUsbInput.isChecked,
                    checkboxBluetooh.isChecked,
                    checkboxAndroidAuto.isChecked,
                    checkboxAppleCarPlay.isChecked,
                    checkboxSunRoof.isChecked,
                    editTextVin.text.toString(),
                    editTextLicensePlate.text.toString(),
                    editTextMake.text.toString(),
                    editTextModel.text.toString(),
                    requireNotNull(
                        SimpleDateFormat(
                            "yyyy",
                            Locale.US
                        ).parse(editTextMfdYear.text.toString())
                    ),
                    editTextTransmisson.text.toString(),
                    editTextFuel.text.toString(),
                    editTextDoors.text.toString().toInt(),
                    editTextSeats.text.toString().toInt(),
                    editTextBodyType.text.toString(),
                    editTextColor.text.toString(),
                    editTextLocation.text.toString(),
                    editTextLatitude.text.toString().toDouble(),
                    editTextLongitude.text.toString().toDouble(),
                    editTextPricePerDay.text.toString().toDouble(),
                    editTextPricePerWeek.text.toString().toDouble(),
                    editTextPricePerMonth.text.toString().toDouble(),
                    imagesListUri.map {
                        val filesDir = requireContext().applicationContext.filesDir
                        val tempFile = File(filesDir, "car-${UUID.randomUUID()}.png")
                        val tempInputStream = requireContext().contentResolver.openInputStream(it)
                        val tempOutputStream = FileOutputStream(tempFile)
                        tempInputStream?.copyTo(tempOutputStream)
                        tempInputStream?.close()
                        tempFile
                    }
                )
            }

            buttonClear.setOnClickListener {
                clearInputs()
            }

            viewModel
                .loadingFlow
                .onEach {
                    progressCircular.isVisible = true
                    layoutAdvertisement.isVisible = false
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .errorFlow
                .onEach {
                    progressCircular.isVisible = false
                    layoutAdvertisement.isVisible = true
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
                    layoutAdvertisement.isVisible = true
                    clearInputs()
                    AlertDialog
                        .Builder(requireContext())
                        .setTitle("Публикация объявления")
                        .setMessage(it)
                        .setPositiveButton(android.R.string.ok, null)
                        .show()
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .getResponseFlow
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun validateInputs(): Boolean {
        return imageAdapter.currentList.isNotEmpty()
    }

    private fun clearInputs() {
        with(binding) {
            editTextVin.text?.clear()
            editTextFuel.text?.clear()
            editTextTransmisson.text?.clear()
            editTextBodyType.text?.clear()
            editTextColor.text?.clear()
            editTextDoors.text?.clear()
            editTextLatitude.text?.clear()
            editTextLongitude.text?.clear()
            editTextLicensePlate.text?.clear()
            editTextModel.text?.clear()
            editTextMfdYear.text?.clear()
            editTextBodyType.text?.clear()
            editTextSeats.text?.clear()
            editTextLocation.text?.clear()
            editTextMake.text?.clear()
            editTextPricePerDay.text?.clear()
            editTextPricePerWeek.text?.clear()
            editTextPricePerMonth.text?.clear()

            checkboxAllWheelDrive.isChecked = false
            checkboxAndroidAuto.isChecked = false
            checkboxBluetooh.isChecked = false
            checkboxGps.isChecked = false
            checkboxConditioner.isChecked = false
            checkboxAudioInput.isChecked = false
            checkboxUsbInput.isChecked = false
            checkboxChildSeats.isChecked = false
            checkboxCooledSeats.isChecked = false
            checkboxHeatedSeats.isChecked = false
            checkboxLeatherSeats.isChecked = false
            checkboxSkiRack.isChecked = false
            checkboxSunRoof.isChecked = false

            imagesListUri.clear()
            imageAdapter.submitList(emptyList())
            hideKeyboard()
        }
    }

    private fun openGalleryForImage() {
        if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            selectImagesFromGallery.launch("image/*")
        } else {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
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