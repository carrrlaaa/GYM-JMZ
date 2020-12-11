package com.example.appgymjmz

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity

class Clientes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val int = Intent(this, MainActivityAgregarCli::class.java)
            startActivity(int)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.pagos_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.Mes -> {
                startActivity(Intent(this, PagMes::class.java))
                 true
            }
            R.id.Sem -> {
                startActivity(Intent(this, PagSem::class.java))
                true
            }
            R.id.PromEstu -> {
                startActivity(Intent(this, PagPromoEstu::class.java))
                true
            }
            R.id.Prom32 -> {
                startActivity(Intent(this, PagProm32::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}