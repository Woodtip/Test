package com.example.test

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isInvisible
import com.example.test.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        uri: Uri? -> binding.ivImage.setImageURI(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonListeners()
    }

    fun setButtonListeners() {
        binding.apply {
            btnAddNumbers.setOnClickListener { addNumbers() }
            btnChooseImage.setOnClickListener { chooseImage() }
            btnDisplayImage.setOnClickListener { displayImage() }
            btnSubmitOrder.setOnClickListener { submitOrder() }
            btnNextActivity.setOnClickListener { startActivity(nextActivityIntent()) }

            Log.d("Main",binding.spBunTypes.selectedItem.toString())
            //Log.d("Main",BunOptions.valueOf(binding.spBunTypes.selectedItem.toString()).description)
        }
    }

    fun addNumbers() {
        try {
            val number1 = binding.etNumber1.text.toString().toDouble()
            val number2 = binding.etNumber2.text.toString().toDouble()
            val result = number1 + number2
            binding.tvAdditionResult.text = result.toString()
        } catch (e: Exception) {
            Log.d(
                object {}.javaClass.enclosingClass.simpleName +
                        ": " + object {}.javaClass.enclosingMethod?.name,
                "${getString(R.string.exception_intro)} ${e.message}"
            )
            binding.tvAdditionResult.text = getString(R.string.addition_error)
        }
    }

    fun chooseImage() {
        getContent.launch("image/*")
    }

    fun displayImage() {
        if (binding.ivImage.isInvisible) binding.ivImage.visibility = View.VISIBLE
        else binding.ivImage.visibility = View.INVISIBLE
    }

    @SuppressLint("SetTextI18n")
    fun submitOrder() {
        val meat = binding.root.findViewById<RadioButton>(binding.rgMeats.checkedRadioButtonId)
        val bun = binding.spBunTypes.selectedItem.toString()
        val lettuce = binding.cbLettuce
        val tomato = binding.cbTomato
        val onion = binding.cbOnion

        binding.tvOrderStatus.text = this.getString(R.string.order_submitted)
    }

    fun nextActivityIntent(): Intent {
        val order = Order(
            MeatOptions.valueOf(
                "${
                    binding.root.findViewById<RadioButton>(
                        binding.rgMeats.checkedRadioButtonId
                    ).text
                }"
            ),
            BunOptions.convertSpinnerText(binding.spBunTypes.selectedItem.toString()),
            binding.cbLettuce.isChecked,
            binding.cbTomato.isChecked,
            binding.cbOnion.isChecked
        )
        return Intent(this, SecondActivity::class.java).also {
            it.putExtra("EXTRA_ORDER", order)
        }
    }

    fun requestPermissions() {
        var permissionsToRequest = mutableListOf<String>()

        val p0 =Manifest.permission.READ_EXTERNAL_STORAGE
        if(!hasPermission(p0)) permissionsToRequest.add(p0)

        val p1 = Manifest.permission.WRITE_EXTERNAL_STORAGE
        if(!hasPermission(p1)) permissionsToRequest.add(p1)

        val p2 = Manifest.permission.READ_MEDIA_IMAGES
        if(!hasPermission(p2)) permissionsToRequest.add(p2)

        val p3 = Manifest.permission.ACCESS_COARSE_LOCATION
        if(!hasPermission(p3)) permissionsToRequest.add(p3)

        val p4 = Manifest.permission.ACCESS_BACKGROUND_LOCATION
        if(!hasPermission(p4)) permissionsToRequest.add(p4)

        if(permissionsToRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(this,
                permissionsToRequest.toTypedArray(),
                0
            )
            generateSnackbar(R.string.permissions_requested,Snackbar.LENGTH_SHORT)
        }
        generateSnackbar(R.string.no_permissions_requested,Snackbar.LENGTH_SHORT)
    }

    fun hasPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(this, permission) ==
                PackageManager.PERMISSION_GRANTED
    }

    fun generateSnackbar(message: Int, length: Int) {
        Snackbar.make(binding.root, message, length).show()
    }

    fun generateToast(message: Int, length: Int) {
        Toast.makeText(this, message, length).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.miAddContact -> generateSnackbar(R.string.coming_soon, Snackbar.LENGTH_SHORT)
            R.id.miFavorites -> generateSnackbar(R.string.coming_soon, Snackbar.LENGTH_SHORT)
            R.id.miSettings -> generateSnackbar(R.string.coming_soon, Snackbar.LENGTH_SHORT)
            R.id.miRequestPermissions -> requestPermissions()
            R.id.miFeedback -> generateSnackbar(R.string.coming_soon, Snackbar.LENGTH_SHORT)
            R.id.miClose -> finish()
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0 && grantResults.isNotEmpty()) {
            for(i in grantResults.indices) {
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED)
                    Log.d("Permissions Request","${permissions[i]} granted.")
            }
        }
    }

}