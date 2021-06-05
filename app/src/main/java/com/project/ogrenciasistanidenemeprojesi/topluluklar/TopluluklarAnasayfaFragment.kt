package com.project.bitirmeprojedenemetopluluklar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.project.ogrenciasistanidenemeprojesi.R
import com.project.ogrenciasistanidenemeprojesi.databinding.FragmentTopluluklarAnasayfaBinding

class TopluluklarAnasayfaFragment : Fragment() {
    private lateinit var tasarim: FragmentTopluluklarAnasayfaBinding //databinding
    private lateinit var topluluklarListesi:ArrayList<Topluluklar>
    private lateinit var adapter: TopluluklarAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        tasarim= DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_topluluklar_anasayfa,
            container,
            false
        )

        tasarim.rv.layoutManager= LinearLayoutManager(context)


        val t1=Topluluklar(1,"Atatürkçü Düşünce Kulübü","Amacı: Kulübün amacı, teoriyi pratik bilgiyle birleştirip sektörel bilgiyi üniversiteye getirmek ve sektörle iç içe olabilmek, okula ve öğrencilere yararlı bilgiler sunabilmektir.")
        val t2=Topluluklar(2,"Ayrımcılıkla Mücadele Kulübü","Amacı: İnsan haklarıyla ve demokrasiyle doğrudan ilgili olan ayrımcılık sorununa karşı kendini sorumlu hisseden öğrencilerle birlikte hareket etmektir.")
        val t3=Topluluklar(3,"Bankacılık ve Finans Kulübü","Amacı: Bankacılık ve finans alanındaki gelişmelere katkıda bulunmak, öğrencilerin istek ve ihtiyaçları doğrultusunda araştırmalar yapmak, projeler üretmek, akademik programı tamamlayıcı nitelikte faaliyetlerde bulunmaktır.")
        val t4=Topluluklar(4,"Çevre ve Doğa Dostları Kulübü","Amacı: Öğrencilere çevre ve doğa bilincini artırmak ve gençlerin çevre duyarlılığını geliştirmek amacıyla kurulmuştur.")
        val t5=Topluluklar(5,"Dağcılık ve Doğa Sporları Kulübü","Amacı: Dağcılık sporunu bilimsel bir çerçevede algılayıp, dağcılık kültürünün oluşturulması ve yayılması için çaba göstermektir. Doğa ve çevre bilincinin gelişmesine katkıda bulunmaktır.")
        val t6=Topluluklar(6,"Dış Ticaret Kulübü","Amacı: Yüksekokulumuz öğrencilerine ve kendisini dış ticaret alanında geliştirmek isteyenlere yönelik, eğitici ve yol gösterici çalışmalar, programlar düzenleyerek hem bölümümüzü tanıtmak hem de sosyal faaliyetlerle kendimizi geliştirmektir.")
        val t7=Topluluklar(7,"Endüstri Mühendisliği Kulübü","Amacı: Bilginin, bilgiyi kullanmanın, girişimciliğin ve kişisel gelişimin önem kazandığı günümüzde üniversite eğitimini akademik ve sosyal aktivitelerle zenginleştirerek, Endüstri Mühendisliği öğrencilerini iş hayatına bir adım daha önde başlamasını sağlamaktır.")
        val t8=Topluluklar(8,"Fikir ve Maarif Kulübü","Amacı: Üniversitemizin öğrencilerine kitap okuma alışkanlığı kazandırmak ve kültürel faaliyetlerde bulunmak amacıyla kurulmuştur.")
        val t9=Topluluklar(9,"Fikir ve Sanat Kulübü","Amacı: Üniversite öğrencilerinin ve gençlerin fikri ve sanatsal gelişimlerine katkı sağlamak onlara edebiyatı sevdirmek, yazılı basınla tanıştırmak, sanat hayatımızı geliştirmek amacı ile muhtelif faaliyetler yürütmektir.")
        val t11=Topluluklar(11,"Genç Hukukçular Kulübü","Amacı: Ulusal ve uluslararası çalışmalar yapmak ve hukuk alanındaki gelişmeleri öğrencilere etkinliklerle anında ulaştırmak ve üniversitenin bu alanda ismini duyurmak üzere kurulmuştur.")
        val t12=Topluluklar(12,"Girişimcilik Kulübü","Amacı: Üniversite içi ve dışı olmak üzere ders dışı sosyal ve kültürel faaliyetler için çalışma yapmak, seminer, panel, işyeri gezileri düzenlemek öğrencileri girişimciliğe yönlendirmektir.")
        val t13=Topluluklar(13,"Halk Dansları Kulübü","Amacı: Okulumuzda kültürümüzü sevdirmek için çalışmalar yapmak, gösteri düzenlemek ve halk danslarını tanımayan herkese halk danslarını sevdirmektir.")
        val t14=Topluluklar(14,"Halkla İlişkiler ve Reklamcılık","Amacı: Öğrencilerin eğitimlerini geliştirmek, iş dünyasına hazırlanmalarına katkı sağlamak amacıyla kurulmuştur.")
        val t15=Topluluklar(15,"Hukuk Kulübü","Amacı: Hukuku geliştirmek için çalışmalar yapmak ve bu konuda gerçekleşecek çalışmalara katılmaktır. Hukuk alanındaki gelişmeleri yakından takip edebilmek için konferanslar, paneller ve söyleşiler düzenlemektir.")
        val t16=Topluluklar(16,"Hürriyet ve Adalet Kulübü","Amacı: Okulumuz öğrencilerinin hürriyet ve adalet kavramları konusunda bilinçlenmeleri amacıyla kurulmuştur.")
        val t17=Topluluklar(17,"İşletmecilik","Amacı: Üniversite öğrencilerinin kendi alanlarında gelişmelerine imkân sağlamak, bilgi ve deneyimlerini arttırmaktır.")
        val t18=Topluluklar(18,"İstatistik Kulübü","Amacı: İstatistik alanındaki gelişmelere katkı sağlamak ve öğrencilerin istek ve ihtiyaçları doğrultusunda araştırmalar yapmaktır.")
        val t19=Topluluklar(19,"K-DEM Kulübü","Amacı: Üniversitemiz öğrencilerini kadın hakları konusunda bilinçlendirmek amacıyla kurulmuştur.")
        val t20=Topluluklar(20,"Kişisel Gelişim Kulübü","Amacı: Okulumuz öğrencilerine kişisel gelişim alanında gelişmelerini sağlamak, kültürel ve sosyal organizasyonlar düzenlemek amacıyla kurulmuştur.")
        val t21=Topluluklar(21,"Kültür ve Edebiyat Kulübü","Amacı: Türk kültürünü ve Türk Dilini edebiyatla geliştirmeye yönelik faaliyetlerde bulunmak, bu faaliyetlerle öğrencilerin kültürü ve edebiyatı sevmesini ve yaşatmasını sağlamaktır.")
        val t22=Topluluklar(22,"Matematik Kulübü","Amacı: Matematiğin bilimin her alanına hitap ettiğini göstermek ve bu konuda faaliyetler düzenlemek ve matematiği öğrencilere sevdirerek uygulamak ve matematik ile günlük hayatı pekiştirmektir.")
        val t23=Topluluklar(23,"Medeniyet ve Düşünce Kulübü","Amacı: Medeniyetimizin ve İstanbul’ un tanıtılması için çalışmalar yapmak, sosyal bilimler ile ilgili proje ve faaliyetleri geliştirmektir. Bu faaliyetler çerçevesinde medeniyet ve düşünce kulübünü geliştirmektir.")
        val t24=Topluluklar(24,"Mekatronik Mühendisliği Kulübü","Amaç: Ders dışı etkinliklerle mekatronik ve uygulamalarını teşvik etmek için çalışmalar yapmak, gelişmekte olan mekatronik bilimini okulumuzda tanıtmaktır.")
        val t25=Topluluklar(25,"Münazara Kulübü","Amacı: Üniversite içerisinde münazara faaliyetleri yapmak ve münazaranın üniversite içerisindeki bilinirliğini arttırmak, öğrencilerin hitabet yeteneklerini geliştirmek ve diğer üniversitelerde okulumuzu en iyi şekilde temsil etmek amacıyla kurulmuştur.")
        val t26=Topluluklar(26,"Müzik Kulübü","Amacı: Üniversitemizdeki tüm öğrencileri müzikle ortak bir çatı altında toplamak ve müzikle kampüs alanlarında sosyal bir ortam oluşturmaktır.")
        val t27=Topluluklar(27,"Psikoloji Kulübü","Amacı: Psikoloji öğrencilerini daha aktif hale getirmek için çalışmalar yapmak ve psikoloji dersleri ile güncel konuları iç içe alarak psikoloji eğitimini daha etkin hale getirmektir.")
        val t28=Topluluklar(28,"Sivil Savunma Kulübü","Amacı: Üniversite öğrencilerinin ve çalışanlarını doğal afetler konusunda bilgilendirmek ve bu konuda çeşitli eğitimlere katılarak ve çeşitli eğitimcileri davet ederek faaliyetler düzenlemektir.")
        val t29=Topluluklar(29,"Su Altı Kulübü","Amacı: Okulumuz öğrencilerine sportif dalış için çalışmalar yapmak, su altı sporunu yaygınlaştırmak ve tanıtmak amacıyla kurulmuştur.")
        val t30=Topluluklar(30,"Tarih ve Araştırmaları Kulübü","Amacı: İstanbul Ticaret Üniversitesi Öğrencilerine tarih bilinci kazandırmak, tarihe olan ilgiyi arttırmak ve bu ilgiyi işlevsel bir şekilde uygulamaya koymaktır.")
        val t31=Topluluklar(31,"Tasarım Kulübü","Amacı: Mühendislik ve Tasarım Fakültesi öğrencilerinin sosyalleşmeyi içeren tasarım faaliyetlerini öğrencilere sunmak amacı ile kurulmuştur.")
        val t32=Topluluklar(32,"Ticaret İletişim Kulübü","Amacı: Öğrencilerimizin kültürel, entelektüel ve mesleki olarak gelişimlerine katkı sağlamak amacıyla kurulmuştur.")
        val t33=Topluluklar(33,"Turizm Kulübü","Amacı: Turizm alanında çalışmalar yapmak ve turizmi eğitim ve sektörle birleştirerek öğrencilere turizmin avantajlarını sunmaktır.")
        val t34=Topluluklar(34,"Uluslararası İlişkiler Kulübü","Amacı: Uluslararası İlişkiler alanındaki gelişmelere katkıda bulunmak, öğrencilerin istek ve ihtiyaçları doğrultusunda araştırmalar yapmak, projeler üretmek, tartışma ortamı hazırlamak vb. kültürel etkinlikleri gerçekleştirmektir.")
        val t35=Topluluklar(35,"Üretim Araştırmaları Kulübü","Amacı: Kulübün amacı öğrencilerin teoride kazandıkları bilgileri, pratik becerilere çevirebilmelerini sağlamak ve iş ortamını daha yakından tanıyabilmelerine imkân vermek amacıyla çalışmalar yapmaktır.")
        val t36=Topluluklar(36,"Yelken Kulübü","Amacı: Öğrencilere denizi, rüzgârı, yelken sporunu sevdirmek ve eğitimini sağlamak için yelken yarışlarına katılmak ve üniversitemizi en iyi şekilde temsil etmek amacıyla kurulmuştur. Yelken Kulübü, yelken sporunu teşvik etmek üzere çalışmalar gerçekleştirmektedir.")
        val t37=Topluluklar(37,"Yeşilay Kulübü","Amacı: Üniversitemiz öğrencilerine sosyal sorumluluk alışkanlığı kazandırmak, sağlık ve kan bağışı gibi konularda bilgilendirmek amacıyla kurulmuştur.")

        topluluklarListesi= ArrayList()
        topluluklarListesi.add(t1)
        topluluklarListesi.add(t2)
        topluluklarListesi.add(t3)
        topluluklarListesi.add(t4)
        topluluklarListesi.add(t5)
        topluluklarListesi.add(t6)
        topluluklarListesi.add(t7)
        topluluklarListesi.add(t8)
        topluluklarListesi.add(t9)
        topluluklarListesi.add(t12)
        topluluklarListesi.add(t13)
        topluluklarListesi.add(t14)
        topluluklarListesi.add(t15)
        topluluklarListesi.add(t16)
        topluluklarListesi.add(t17)
        topluluklarListesi.add(t18)
        topluluklarListesi.add(t19)
        topluluklarListesi.add(t20)
        topluluklarListesi.add(t21)
        topluluklarListesi.add(t22)
        topluluklarListesi.add(t23)
        topluluklarListesi.add(t24)
        topluluklarListesi.add(t25)
        topluluklarListesi.add(t26)
        topluluklarListesi.add(t27)
        topluluklarListesi.add(t28)
        topluluklarListesi.add(t29)
        topluluklarListesi.add(t30)
        topluluklarListesi.add(t31)
        topluluklarListesi.add(t32)
        topluluklarListesi.add(t33)
        topluluklarListesi.add(t34)
        topluluklarListesi.add(t35)
        topluluklarListesi.add(t36)
        topluluklarListesi.add(t37)

        adapter= TopluluklarAdapter(requireContext(),topluluklarListesi)
        tasarim.rv.adapter=adapter

        return tasarim.root
    }
}