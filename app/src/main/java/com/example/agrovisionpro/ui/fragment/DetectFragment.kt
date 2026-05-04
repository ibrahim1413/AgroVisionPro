package com.example.agrovisionpro

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.agrovisionpro.helper.TFLiteHelper

class DetectFragment : Fragment(R.layout.fragment_detect) {

    private lateinit var imageView: ImageView
    private lateinit var txtResult: TextView
    private lateinit var helper: TFLiteHelper

    private var bitmap: Bitmap? = null

    // ✅ REGISTER HERE (outside onViewCreated)
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { image ->
            if (image != null) {
                bitmap = image
                imageView.setImageBitmap(image)
                txtResult.text = "📸 Image captured"
            } else {
                txtResult.text = "❌ Camera cancelled"
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.imageView)
        txtResult = view.findViewById(R.id.txtResult)

        helper = TFLiteHelper(requireContext())

        view.findViewById<Button>(R.id.btnCamera).setOnClickListener {
            cameraLauncher.launch(null)
        }

        view.findViewById<Button>(R.id.btnPredict).setOnClickListener {
            if (bitmap == null) {
                txtResult.text = "❌ আগে ছবি তুলুন"
                return@setOnClickListener
            }

            val result = helper.predict(bitmap!!)
            txtResult.text = result
        }
    }
}
