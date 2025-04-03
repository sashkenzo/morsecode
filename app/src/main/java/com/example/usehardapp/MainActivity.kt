package com.example.usehardapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import java.lang.Thread.sleep


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonStart:Button= findViewById(R.id.button_start)
        val buttonReset:Button= findViewById(R.id.button_reset)
        val inputText: EditText = findViewById(R.id.input_text)
        var textView : TextView = findViewById(R.id.textView)
        val radioButtonFast: RadioButton=findViewById(R.id.radioButton_fast)
        val radioButtonNormal: RadioButton=findViewById(R.id.radioButton_normal)
        val radioButtonSlow: RadioButton=findViewById(R.id.radioButton_slow)
        val checkBoxTorch: CheckBox=findViewById(R.id.checkBox_torch)
        val checkBoxVibration:CheckBox=findViewById(R.id.checkBox_vibration)
        var radioButtonSpeed:Long=500
        var textInMorse= ""



        val morseCodes= mapOf(
            'a' to ".-",     'b' to "-...",
            'c' to "-.-.",   'd' to "-..",
            'e' to ".",      'f' to "..-.",
            'g' to "--.",    'h' to "....",
            'i' to "..",     'j' to ".---",
            'k' to "-.-",    'l' to ".-..",
            'm' to "--",     'n' to "-.",
            'o' to "---",    'p' to ".--.",
            'q' to "--.-",   'r' to ".-.",
            's' to "...",    't' to "-",
            'u' to "..-",    'v' to "...-",
            'w' to ".--",    'x' to "-..-",
            'y' to "-.--",   'z' to "--..",
            '1' to ".----",  '2' to "..---",
            '3' to "...--",  '4' to "....-",
            '5' to ".....",  '6' to "-....",
            '7' to "--...",  '8' to "---..",
            '9' to "----.",  '0' to "-----",
            ' ' to " "
        )
        radioButtonFast.setOnClickListener{
            if(radioButtonFast.isChecked)
                radioButtonSpeed=300
        }
        radioButtonNormal.setOnClickListener{
            if(radioButtonNormal.isChecked)
                radioButtonSpeed=500
        }
        radioButtonSlow.setOnClickListener{
            if(radioButtonSlow.isChecked)
                radioButtonSpeed=1000
        }
        checkBoxVibration.setOnClickListener {
            if(!checkBoxTorch.isChecked and !checkBoxVibration.isChecked) {
                checkBoxTorch.isChecked=true
            }
        }
        checkBoxTorch.setOnClickListener {
            if(!checkBoxTorch.isChecked and !checkBoxVibration.isChecked) {
             checkBoxVibration.isChecked=true
            }
        }

        fun morsecodetorch(textInMorse:String,speed:Long){
            val camManager = getSystemService(CAMERA_SERVICE) as CameraManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                var cameraId: String? = null
                try {
                    cameraId = camManager.cameraIdList[0]
                    for (i in textInMorse.indices) {
                        if (textInMorse[i] == '.') {
                            camManager.setTorchMode(cameraId, true)
                            sleep(speed+200)
                            camManager.setTorchMode(cameraId, false)
                            sleep(200)
                        }
                        if (textInMorse[i] == '-') {
                            camManager.setTorchMode(cameraId, true)
                            sleep(2*speed+200)
                            camManager.setTorchMode(cameraId, false)
                            sleep(200)
                        }
                        if (textInMorse[i] == ' ') {
                            sleep(2*speed)
                        }
                    }
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
            }
        }

        fun morsecodevibra(textInMorse:String,speed:Long){
        val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        for (i in textInMorse.indices) {
            if (textInMorse[i] == '.') {
                vibratorService.vibrate((speed))
                sleep(speed+200)
            }
            if (textInMorse[i] == '-') {
                vibratorService.vibrate(2*speed)
                sleep(2*speed+200)
            }
            if (textInMorse[i] == ' ') {
                sleep(2*speed)
            }
        }
        }
        inputText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputTextValue = inputText.getText().toString().lowercase()
                textInMorse=""
                for (i in inputTextValue.indices) {
                    if(morseCodes[inputTextValue[i]] != null){
                    textInMorse += morseCodes[inputTextValue[i]].toString() + " "
                    textView.text = textInMorse}
                    else{
                        textInMorse="no morsecode for this letter"
                        textView.text = textInMorse
                    }
                }

            }
        })

        buttonStart.setOnClickListener {
            if(textInMorse!="no morsecode for this letter"){
                if(checkBoxVibration.isChecked) {
                    morsecodevibra(textInMorse,radioButtonSpeed)
                }
                if(checkBoxTorch.isChecked) {
                    morsecodetorch(textInMorse,radioButtonSpeed)
                }
            }
        }

                buttonReset.setOnClickListener {
                    checkBoxTorch.isChecked=true
                    checkBoxVibration.isChecked=true
                    radioButtonNormal.isChecked=true
                    textView.text=""
                    inputText.text=null


                }



    }


}
