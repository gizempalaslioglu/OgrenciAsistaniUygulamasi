package com.project.bitirmeprojedenemedersler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.project.ogrenciasistanidenemeprojesi.R
import com.project.ogrenciasistanidenemeprojesi.databinding.FragmentDerslerDetayBinding


class DerslerDetayFragment : Fragment() {
private lateinit var tasarim: FragmentDerslerDetayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tasarim= DataBindingUtil.inflate(inflater, R.layout.fragment_dersler_detay, container, false)

        //gönderilen nesneyi yakalama
        val b:DerslerDetayFragmentArgs by navArgs()
        val gelenDers=b.dersNesne //gelenDers içinde tüm bilgilerimiz var

        tasarim.textViewDersAdi.text=gelenDers.ders_adi
        tasarim.textViewDersKredisi.text=gelenDers.ders_kredi
        tasarim.textViewDersGunu.text=gelenDers.ders_gun
        tasarim.textViewDersAkademikPersonel.text=gelenDers.ders_ogretmen
        return tasarim.root
    }

}