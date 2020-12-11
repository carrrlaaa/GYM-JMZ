package com.example.appgymjmz

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.content_main_activity_agregar_cli.*
import org.json.JSONObject

class MainActivityAgregarCli : AppCompatActivity() {

    val IP = "http://192.168.1.68"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_agregar_cli)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            if (etNomCli.text.toString().isEmpty()||
                etTelCli.text.toString().isEmpty()||
                etFec.text.toString().isEmpty()||
                rbMes.text.toString().isEmpty()||
                rbSem.text.toString().isEmpty()||
                rbProEstu.text.toString().isEmpty()||
                rbPro3x2.text.toString().isEmpty())
            {
                Toast.makeText(this, "Falta información de Ingresar", Toast.LENGTH_LONG).show();
                etNomCli.requestFocus()
            }
            else
            {
                val nom = etNomCli.text.toString()
                val tel = etTelCli.text.toString()
                val fec = etFec.text.toString()
                if(rbMes.isChecked()==true){

                }else if(rbSem.isChecked()==true){

                } else if(rbProEstu.isChecked()==true){

                }else if(rbPro3x2.isChecked()==true){

                }
                var jsonEntrada = JSONObject()
                jsonEntrada.put("nomCliente", etNomCli.text.toString())
                jsonEntrada.put("telCliente", etTelCli.text.toString())
                jsonEntrada.put("fechaPago",etFec.text.toString())
                sendRequest(IP + "/WSAndroid/agregarCliente.php",jsonEntrada)
            }

        }
    }


    fun sendRequest( wsURL: String, jsonEnt: JSONObject){
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, wsURL,jsonEnt,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                if (succ == 200){
                    etNomCli.setText("")
                    etTelCli.setText("")
                    etFec.setText("")
                    etNomCli.requestFocus()
                    Toast.makeText(this, "Success:${succ}  Message:${msg} Se Inserto el Cliente en el Servidor Web", Toast.LENGTH_SHORT).show();
                }
            },
            Response.ErrorListener{ error ->
                Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show();
                Log.d("ERROR","${error.message}");
                Toast.makeText(this, "Error de capa 8 checa URL", Toast.LENGTH_SHORT).show();
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}
