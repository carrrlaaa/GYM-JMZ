package com.example.appgymjmz

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminBD(context: Context): SQLiteOpenHelper(context,"clientesgym",null,1) {
    override fun onCreate(bd: SQLiteDatabase?) {
        bd?.execSQL("Create Table Clientes(" +
                "corrUsr TEXT,"+
                "idCliente INTEGER primary key," +
                "idPago INTEGER,"+
                "nomCliente TEXT," +
                "telCliente INTEGER," +
                "fechaPago DATE)")
        bd?.execSQL("Create Table cuentaUsr(" +
                "corrUsr TEXT primary key,"+
                "nomUsr TEXT,"+
                "pwdUsr TEXT)")
        bd?.execSQL("Create Table tipoPago(" +
                "idPago INTEGER primary key,"+
                "nomPago TEXT," +
                "precio INTEGER)")
    }

    // Permite ejecutar Insert, Update o Delete
    fun Ejecuta(sentencia: String): Boolean{
        try {
            val bd = this.writableDatabase
            bd.execSQL(sentencia)
            bd.close()
            return true
        }
        catch (ex:Exception){
            return false
        }
    }

    // Permir ejecutar una consulta
    fun Consulta(select: String):Cursor?{
        try {
            val bd = this.readableDatabase
            return bd.rawQuery(select, null)
        } catch (ex: Exception) {
            return null
        }
    }



    override fun onUpgrade(bd: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

