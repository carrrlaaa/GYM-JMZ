package com.example.appgymjmz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var corr : String=""
    var pwd : String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        var admin = AdminBD(this)
        var result = admin.Consulta("select corrUsr, pwdUsr from cuentaUsr")
        if (result != null) {
            if (result.moveToFirst()) {
                corr = result.getString(0)
                pwd = result.getString(1)
                val act = Intent(this, Clientes::class.java)
                startActivity(act)
            }
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            if (etCorrUsr.text.toString().isEmpty()) {
                etCorrUsr.setError("Error no se puede quedar vació")
                etCorrUsr.requestFocus()
            } else if (etPwdUsr.text.toString().isEmpty()) {
                etPwdUsr.setError("Error no puede quedar vació")
                etPwdUsr.requestFocus()
            } else {
                corr = etCorrUsr.text.toString()
                pwd = etPwdUsr.text.toString()
                //Armamos el JSON de entrada
                var jsonEnt = JSONObject()
                jsonEnt.put("corrUsr", corr)
                jsonEnt.put("pwdUsr", pwd)
                registraUsr(jsonEnt)
            }
        }
    }
   fun registraUsr( jsonEntrada: JSONObject) {
            val sURL = "http://192.168.1.68/WSAndroid/gymRegistro.php"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, sURL, jsonEntrada,
                Response.Listener { response ->
                    val succ = response["success"].toString()
                    val msg = response["message"].toString()
                    if (succ=="200"){
                        val admin = AdminBD(this)
                        val sentencia = "Insert into cuentaUsr(corrUsr, pwdUsr) values ('$corr', '$pwd')"
                        (admin.Ejecuta(sentencia))

                        Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                        val act = Intent(this, Clientes::class.java)
                        act.putExtra("corr", corr)
                        act.putExtra("pwd", pwd)
                        startActivity(act)

                    }else{
                        Toast.makeText(this, "Error: {$msg}", Toast.LENGTH_LONG).show();
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, error.message.toString(), Toast.LENGTH_LONG).show();
                    Log.d("Chicana", error.message.toString())
                }
            )
            VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
        }
    }