package com.example.appgymjmz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var corr : String=""
    var nom : String=""
    var pwd : String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun btnOk_clic(view: View) {
        if(etCorrUsr.text.toString().isEmpty()){
            etCorrUsr.setError("Error no se puede quedar vació")
            etCorrUsr.requestFocus()
        } else if (etNomUsr.text.toString().isEmpty()){
            etNomUsr.setError("Error no puede quedar vació")
            etNomUsr.requestFocus()
        } else if (etPwdUsr.text.toString().isEmpty()){
            etPwdUsr.setError("Error no puede quedar vació")
            etPwdUsr.requestFocus()
        } else {
            corr = etCorrUsr.text.toString()
            nom = etNomUsr.text.toString()
            pwd = etPwdUsr.text.toString()
            //Armamos el JSON de entrada
            var jsonEnt = JSONObject()
            jsonEnt.put("corrUsr", corr)
            jsonEnt.put("nomUsr", nom)
            jsonEnt.put("pwdUsr", pwd)
            registraUsr(jsonEnt)

        }
    }
    fun registraUsr(jsonEntrada: JSONObject){
        val sURL = "http://192.168.1.68/WSAndroid/gymRegistro.php"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, sURL, jsonEntrada,
            Response.Listener { response ->
                val succ = response["success"].toString()
                val msg = response["message"].toString()
                if (succ=="200"){
                    val clienteJSON = response.getJSONArray("clientes")
                    val admin = AdminBD(this)
                    val sentencia = "Insert into cuentaUsr(correoUsr, nomUsr, pwdUsr) values ('$corr', '$nom', '$pwd')"
                    if (admin.Ejecuta(sentencia))
                        for (i in 0 until clienteJSON.length()){
                            val correoU = clienteJSON.getJSONObject(i).getString("corrUsr")
                            val idC =  clienteJSON.getJSONObject(i).getString("idCliente")
                            val idP =  clienteJSON.getJSONObject(i).getString("idPago")
                            val nomC =  clienteJSON.getJSONObject(i).getString("nomCliente")
                            val telC =  clienteJSON.getJSONObject(i).getString("telCliente")
                            val fec =  clienteJSON.getJSONObject(i).getString("fechaPago")
                            val sentencia = "Insert into Clientes(corrUsr, idCliente, idPago, nomCliente, telCliente, fechaPago) values ($correoU, $idC, $idP, $nomC, $telC, $fec)"
                            admin.Ejecuta(sentencia)
                        }
                    Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    val act = Intent(this, ClienteActi::class.java)
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

    fun btnNuevo_clic(view: View) {
        val intent = Intent(this, MainActivityNuevoUsuario::class.java)
        startActivity(intent)
    }
}