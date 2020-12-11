package com.example.appgymjmz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ValidacionRes : AppCompatActivity() {
    private lateinit var corr : String
    private lateinit var pwd : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validacion_res)

        val actividad = intent
        if (actividad != null && actividad.hasExtra("corr") && actividad.hasExtra("pwd")) {
            corr = actividad.getStringExtra("corr").toString()
            pwd = actividad.getStringExtra("pwd").toString()
        } else {
            var admin = AdminBD(this)
            var result = admin.Consulta("select corrUsr, pwdUsr from cuentaUsr")
            if (result == null) {
                val act = Intent(this, MainActivity::class.java)
                startActivity(act)
            } else {
                if (result.moveToFirst()) {
                    corr = result.getString(0)
                    pwd = result.getString(1)
                } else {
                    val act = Intent(this, MainActivity::class.java)
                    startActivity(act)
                }
            }
        }
    }
}
