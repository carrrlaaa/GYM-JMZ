package com.example.appgymjmz

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class AdminBD(context: Context): SQLiteOpenHelper(context,"clientesgym",null,1) {
    override fun onCreate(bd: SQLiteDatabase?) {
        bd?.execSQL("Create Table Clientes(" +
                "corrUsr TEXT NOT NULL foreign key,"+
                "idCliente INTEGER NOT NULL primary key," +
                "idPago INTEGER NOT NULL foreign key,"+
                "nomCliente TEXT," +
                "telCliente INTEGER," +
                "fechaPago DATE)")
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
    fun Consulta(query: String):Cursor?{
        try {
            val bd = this.readableDatabase
            return bd.rawQuery(query,null)
        }
        catch (ex:Exception){
            return null
        }
    }



    override fun onUpgrade(bd: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

