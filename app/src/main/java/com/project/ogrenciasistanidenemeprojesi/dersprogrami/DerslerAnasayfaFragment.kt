package com.project.bitirmeprojedenemedersler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.project.ogrenciasistanidenemeprojesi.R
import com.project.ogrenciasistanidenemeprojesi.databinding.FragmentDerslerAnasayfaBinding

class DerslerAnasayfaFragment : Fragment() {
    private lateinit var tasarim: FragmentDerslerAnasayfaBinding
    private lateinit var derslerListesi:ArrayList<Dersler>
    private lateinit var adapter: DerslerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        tasarim= DataBindingUtil.inflate(inflater, R.layout.fragment_dersler_anasayfa,container,false)
        tasarim.rvders.setHasFixedSize(true)
        tasarim.rvders.layoutManager= LinearLayoutManager(context) //?

        val d1=Dersler(1,"BIL452/English/1\nArtificial Intelligence","Kredi:6,00","\tSalı (TUE) 14:00 - 14:59   Uzaktan Eğitim\n" +
                "Salı (TUE) 15:00 - 15:59   Uzaktan Eğitim\n" +
                "Salı (TUE) 16:00 - 16:59   Uzaktan Eğitim\n","Akademik Personel:\nMETİN TURAN")
        val d2=Dersler(2,"BIL458/Türkçe/1\nBiyoinformatik","Kredi:5,00","Pazartesi (MON) 14:00 - 14:59   Uzaktan Eğitim\n" +
                "Pazartesi (MON) 15:00 - 15:59   Uzaktan Eğitim\n" +
                "Pazartesi (MON) 16:00 - 16:59   Uzaktan Eğitim\n","Akademik Personel:\nARZU KAKIŞIM")
        val d3=Dersler(3,"BIL462/Türkçe/1\nYapay Sinir Ağları","Kredi:5,00","\tÇarşamba (WED) 14:00 - 14:59   Uzaktan Eğitim\n" +
                "Çarşamba (WED) 15:00 - 15:59   Uzaktan Eğitim\n" +
                "Çarşamba (WED) 16:00 - 16:59   Uzaktan Eğitim","Akademik Personel:\nRIFAT YAZICI")
        val d4=Dersler(4,"BIL467/Türkçe/1\nDerleyici Tasarımı","Kredi:5,00","Salı (TUE) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Salı (TUE) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Salı (TUE) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nALİ BOYACI")
        val d5=Dersler(5,"ENG402/Türkçe/4\nBitirme Projesi","Kredi: 9,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nFATMA NUR AKI")

        val d6=Dersler(6,"BIL465/Türkçe/1\nBulut Bilişim","Kredi: 5,00","Salı (TUE) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nALPER ÖZPINAR")

        val d7=Dersler(7,"BIL461/Türkçe/4\nParalel Bilgisayarlar","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nTURGAY ÖZALTILAR")

        val d8=Dersler(8,"BIL460/Türkçe/4\nVeri Madenciliği","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nARZU KAKIŞIM")

        val d9=Dersler(9,"ENG402/Türkçe/4\nBilgisayar Sistemleri Lab.","Kredi: 3,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nRIFAT YAZICI")

        val d10=Dersler(10,"BIL451/Türkçe/4\nBilişim Tasarım Projesi","Kredi: 7,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nARZU KAKIŞIM")

        val d11=Dersler(11,"BIL441/English/4\nComputer Networks","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nABDÜL HALİM ZAİM")
        val d12=Dersler(12,"BIL455/Türkçe/3\nOtomata Teorisi","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nM.CEM KASAPBAŞI")
        val d13=Dersler(13,"GNL102/Türkçe/3\nAtatürk İlkeleri ve İnkılap Tarihi 2","Kredi: 2,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel: -")
        val d14=Dersler(14,"BIL382/Türkçe/3\nMikroişlemcili Sistem Lab.","Kredi: 4,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nRIFAT YAZICI")
        val d15=Dersler(15,"BIL352/Türkçe/3\nBilgisayar Mimarisi","Kredi: 8,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nRIFAT YAZICI")
        val d16=Dersler(16,"BIL342/English/3\nOperating Systems","Kredi: 6,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nMETİN TURAN")
        val d17=Dersler(17,"BIL354/Türkçe/3\nVeri Güvenliği","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nM.CEM KASAPBAŞI")
        val d18=Dersler(18,"BIL336/Türkçe/3\nMobil Programlama","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nM.CEM KASAPBAŞI")
        val d19=Dersler(19,"GNL101/Türkçe/2\nAtatürk İlkeleri ve İnkılap Tarihi 1","Kredi: 2,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel: -")
        val d20=Dersler(20,"BIL333/Türkçe/2\nBilgisayar Grafikleri","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nRIFAT YAZICI")
        val d21=Dersler(21,"BIL331/English/2\nMicroprocessor Systems","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nRIFAT YAZICI")
        val d22=Dersler(22,"BIL321/Türkçe/2\nVeri İletişimi","Kredi: 4,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nABDÜL HALİM ZAİM")
        val d23=Dersler(23,"BIL317/English/2\nDiscrete Mathematics","Kredi: 4,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nMETİN TURAN")
        val d24=Dersler(24,"BIL327/Türkçe/2\nWeb Programlama","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nALİ BOYACI")
        val d25=Dersler(25,"ENG232/Türkçe/2   Olasılık ve İstatistik","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nFATMA NUR AKI")
        val d26=Dersler(26,"ENG227/Türkçe/2   Mühendislik Matematiği 2","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nDOĞAN KAYA")
        val d27=Dersler(27,"EEE214/English/2   Signals and Systems","Kredi: 6,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nFATMA NUR AKI")
        val d28=Dersler(28,"BIL242/Türkçe/2\nVeri Tabanı Sistemleri","Kredi: 6,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nMETİN TURAN")
        val d29=Dersler(29,"BIL234/English/2\nProgramming Languages","Kredi: 2,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nM.CEM KASAPBAŞI")
        val d30=Dersler(30,"BIL226/Türkçe/2\nElektroteknik Lab.","Kredi: 2,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nFATMA NUR AKI")
        val d31=Dersler(31,"ENG211/Türkçe/2\nSayısal Çözümleme","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nOĞUZ BORAT")
        val d32=Dersler(32,"ENG126/English/2\nEngineering Mathematics 1","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nABDULLAH YENER")
        val d33=Dersler(33,"BIL233/English/2\nObject Oriented Programming","Kredi: 7,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nM.CEM KASAPBAŞI")
        val d34=Dersler(34,"BIL231/English/2\nAlgorithms","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nALİ BOYACI")
        val d35=Dersler(35,"BIL225/Türkçe/2\nMantıksal Tasarım Lab.","Kredi: 3,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nRIFAT YAZICI")
        val d36=Dersler(36,"BIL223/Türkçe/2\nElektronik Devreler","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nFATMA NUR AKI")
        val d37=Dersler(37,"MAT122/English/1\nMathematical Analysis 2","Kredi: 7,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nABDULLAH YENER")
        val d38=Dersler(38,"LNG102/English/1\nGeneral English 2","Kredi: 2,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nFATİH TANRIVERDİ")
        val d39=Dersler(39,"GNL112/Türkçe/1\nBir Kültür Şehri:İstanbul","Kredi: 3,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel: - ")
        val d40=Dersler(40,"GNL106/Türkçe/1\nTürk Dili ve Yazım Kuralları 2","Kredi: 2,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel: -")
        val d41=Dersler(41,"FEF112/English/1\nPhysics 2","Kredi: 4,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nCUMALİ TAV")
        val d42=Dersler(42,"BIL252/Türkçe/1\nMantıksal Sistem Tasarımı","Kredi: 5,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel: RIFAT YAZICI")
        val d43=Dersler(43,"BIL122/English/1\nData Structures","Kredi: 4,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nMETİN TURAN")
        val d44=Dersler(44,"MAT121/English/1\nMathematical Analysis 1","Kredi: 7,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nABDULLAH YENER")
        val d45=Dersler(45,"LNG101/English/1\nGeneral English 1","Kredi: 2,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nFATİH TANRIVERDİ")
        val d46=Dersler(46,"GNL105/Türkçe/1\nTürk Dili","Kredi: 2,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nARZU ÇİFTOĞLU ÇABUK")
        val d47=Dersler(47,"FEF121/English/1\nPhysics Lab. 1","Kredi: 3,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nFATMA NUR AKI")
        val d48=Dersler(48,"FEF111/English/1\nPhysics 1","Kredi: 4,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nCUMALİ TAV")
        val d49=Dersler(49,"BIL111/English/1\nProgramming","Kredi: 8,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nMETİN TURAN")
        val d50=Dersler(50,"BIL101/Türkçe/1\nBilgisayar Mühendisliğine Giriş","Kredi: 4,00","Cumartesi (SAT) 09:00 - 09:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 10:00 - 10:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 11:00 - 11:59   Uzaktan Eğitim\n" +
                "Cumartesi (SAT) 12:00 - 12:59   Uzaktan Eğitim\n","Akademik Personel:\nMETİN TURAN")




        derslerListesi= ArrayList()
        derslerListesi.add(d1)
        derslerListesi.add(d2)
        derslerListesi.add(d3)
        derslerListesi.add(d4)
        derslerListesi.add(d5)
        derslerListesi.add(d6)
        derslerListesi.add(d7)
        derslerListesi.add(d8)
        derslerListesi.add(d9)
        derslerListesi.add(d10)
        derslerListesi.add(d11)
        derslerListesi.add(d12)
        derslerListesi.add(d13)
        derslerListesi.add(d14)
        derslerListesi.add(d15)
        derslerListesi.add(d16)
        derslerListesi.add(d17)
        derslerListesi.add(d18)
        derslerListesi.add(d19)
        derslerListesi.add(d20)
        derslerListesi.add(d21)
        derslerListesi.add(d22)
        derslerListesi.add(d23)
        derslerListesi.add(d24)
        derslerListesi.add(d25)
        derslerListesi.add(d26)
        derslerListesi.add(d27)
        derslerListesi.add(d28)
        derslerListesi.add(d29)
        derslerListesi.add(d30)
        derslerListesi.add(d31)
        derslerListesi.add(d32)
        derslerListesi.add(d33)
        derslerListesi.add(d34)
        derslerListesi.add(d35)
        derslerListesi.add(d36)
        derslerListesi.add(d37)
        derslerListesi.add(d38)
        derslerListesi.add(d39)
        derslerListesi.add(d40)
        derslerListesi.add(d41)
        derslerListesi.add(d42)
        derslerListesi.add(d43)
        derslerListesi.add(d44)
        derslerListesi.add(d45)
        derslerListesi.add(d46)
        derslerListesi.add(d47)
        derslerListesi.add(d48)
        derslerListesi.add(d49)
        derslerListesi.add(d50)





        adapter=DerslerAdapter(requireContext(),derslerListesi)

        tasarim.rvders.adapter=adapter

        return tasarim.root    }

}