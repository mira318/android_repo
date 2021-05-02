package com.example.git_repo_app

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import kotlin.jvm.internal.Intrinsics


class StartActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)
        val searchButton = findViewById<Button>(R.id.btn_search)
        val searchWindow = findViewById<TextView>(R.id.edit_search)
        val kotlinButton = findViewById<Button>(R.id.kotlin_button)
        val pythonButton = findViewById<Button>(R.id.python_button)
        val javaButton = findViewById<Button>(R.id.java_button)
        val cplusplusButton = findViewById<Button>(R.id.cplusplus_button)
        val rButton = findViewById<Button>(R.id.R_button)
        val languageButtonList = listOf<Button>(kotlinButton, javaButton, cplusplusButton,
                rButton, pythonButton)
        var chosenLanguage = LanguageEnum.Null
        activateChooseButtons(languageButtonList)

        kotlinButton.setOnClickListener{
            activateChooseButtons(languageButtonList)
            chosenLanguage = LanguageEnum.kotlin
            kotlinButton.isPressed = false
        }

        pythonButton.setOnClickListener{
            activateChooseButtons(languageButtonList)
            chosenLanguage = LanguageEnum.python
            pythonButton.isPressed = false
        }

        javaButton.setOnClickListener{
            activateChooseButtons(languageButtonList)
            chosenLanguage = LanguageEnum.java
            javaButton.isPressed = false
        }

        rButton.setOnClickListener{
            activateChooseButtons(languageButtonList)
            chosenLanguage = LanguageEnum.R
            rButton.isPressed = false
        }

        cplusplusButton.setOnClickListener{
            activateChooseButtons(languageButtonList)
            chosenLanguage = LanguageEnum.Cplusplus
            cplusplusButton.isPressed = false
        }


        searchButton.setOnClickListener{
            activateChooseButtons(languageButtonList)
            switchToMainActivity(searchWindow.text.toString(), chosenLanguage)
        }
    }

    private fun activateChooseButtons(buttonsList: List<Button>){
        for(button in buttonsList) {
            button.isPressed = true
        }
    }

    private fun switchToMainActivity(searchString: String, chosenLanguage: LanguageEnum){
        val switchActivityIntent = Intent(this@StartActivity,
                MainActivity::class.java)
        switchActivityIntent
                .putExtra("user_request", searchString)
                .putExtra("language_chosen", chosenLanguage.toString())
        startActivity(switchActivityIntent)
    }
}