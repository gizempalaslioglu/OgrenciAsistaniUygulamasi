package com.project.bitirmeprojedenemetopluluklar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.project.ogrenciasistanidenemeprojesi.databinding.ToplulukCardTasarimBinding

//kategoriListesi'nde Kategoriler sınıfından nesneler olacak
class TopluluklarAdapter(var mContext:Context,var topluluklarListesi:List<Topluluklar>)
    :RecyclerView.Adapter<TopluluklarAdapter.CardTasarimTutucu>(){
    inner class CardTasarimTutucu(toplulukCardTasarimBinding: ToplulukCardTasarimBinding):
        RecyclerView.ViewHolder(toplulukCardTasarimBinding.root){
        var tasarim: ToplulukCardTasarimBinding

        init {
            this.tasarim= toplulukCardTasarimBinding
        }

    }
    //data binding işlemi
    //inflater metodları görsel tasarımı kodlamaya aktarmak için kullanılır
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val layoutInflater=LayoutInflater.from(mContext)
        val tasarim=ToplulukCardTasarimBinding.inflate(layoutInflater,parent,false)
        return CardTasarimTutucu(tasarim)  //card üzerindeki görsel nesnelere erişmiş olacağız
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val topluluk=topluluklarListesi.get(position)//bu kodlamayla biz topluluk adını sırayla arayüzde göstermiş olacağız ve teker teker görmüş olacağız
        //topluluk adına veri aktarıyoruz

        holder.tasarim.textViewTopluluk.text=topluluk.topluluk_adi

        holder.tasarim.toplulukCardview.setOnClickListener{ // card'a tıkladığımda detaysayfaya veri gönderme
            val gecis=TopluluklarAnasayfaFragmentDirections.detayaGecis(topluluk)
            Navigation.findNavController(it).navigate(gecis)
        }

    }

    override fun getItemCount(): Int { //adapter kaç veri işleyecek?
        return topluluklarListesi.size
    }

}