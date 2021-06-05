package com.project.bitirmeprojedenemedersler

import java.io.Serializable
//veri kümem
data class Dersler(var ders_id:Int
                   ,var ders_adi:String
                   ,var ders_kredi:String
                   ,var ders_gun:String
                   ,var ders_ogretmen:String): Serializable {
}