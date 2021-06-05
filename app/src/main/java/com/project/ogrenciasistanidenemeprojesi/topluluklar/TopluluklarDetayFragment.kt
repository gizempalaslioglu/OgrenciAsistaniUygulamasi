package com.project.bitirmeprojedenemetopluluklar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.project.ogrenciasistanidenemeprojesi.R
import com.project.ogrenciasistanidenemeprojesi.databinding.FragmentTopluluklarDetayBinding

class TopluluklarDetayFragment : androidx.fragment.app.Fragment() {
    private lateinit var tasarim: FragmentTopluluklarDetayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        tasarim= DataBindingUtil.inflate(inflater, R.layout.fragment_topluluklar_detay, container, false)
        //gönderilen nesneyi yakalama
        val b:TopluluklarDetayFragmentArgs by navArgs()

        val gelenTopluluk=b.toplulukNesne //gelenTopluluk içinde tüm bilgilerimiz var

        tasarim.textViewToplulukDetayAdi.text=gelenTopluluk.topluluk_adi
        tasarim.textViewToplulukDetayAciklama.text=gelenTopluluk.topluluk_detay

        return tasarim.root

    }

}