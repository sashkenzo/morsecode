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
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
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
            '9' to "----.",  '0' to "-----"
        )

        checkBoxVibration.setOnClickListener {
            if(!checkBoxTorch.isChecked and !checkBoxVibration.isChecked) {
                checkBoxTorch.isChecked=true;
            }
        }

        checkBoxTorch.setOnClickListener {
            if(!checkBoxTorch.isChecked and !checkBoxVibration.isChecked) {
             checkBoxVibration.isChecked=true;
            }
        }

        buttonStart.setOnClickListener {
            var inputTextValue = inputText.getText().toString().lowercase()
            //TODO функция обработки в код морзу. вывод в поле текста и включение фонарика
            var textInMorse: String = "";
            for (i in inputTextValue.indices) {
                textInMorse += morseCodes[inputTextValue[i]].toString()+ " ";
                textView.text = textInMorse.toString()
            }

            for (i in textInMorse.indices) {

                if (textInMorse[i] == '.') {
                    val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    vibratorService.vibrate(500)
                    sleep(500L)
                } else {
                    if (textInMorse[i] == '-') {
                        val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        vibratorService.vibrate(1500)
                        sleep(500L)
                    } else {
                        if (textInMorse[i] == ' ') {
                            sleep(500L)
                        }
                    }

                }


                buttonReset.setOnClickListener {

                }
                //TODO функция обработки в код морзу. вывод в поле текста и включение фонарика


            }


        }}
}