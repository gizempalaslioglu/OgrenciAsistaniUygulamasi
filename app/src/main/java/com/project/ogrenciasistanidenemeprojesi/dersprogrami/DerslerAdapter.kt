package com.project.bitirmeprojedenemedersler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.project.ogrenciasistanidenemeprojesi.databinding.DersCardBinding

//dersListesi'nde Filmler sınıfından nesneler olacak

class DerslerAdapter(var mContext: Context, var derslerListesi:List<Dersler>)
    : RecyclerView.Adapter<DerslerAdapter.CardTasarimTutucu>() {
    inner class CardTasarimTutucu(dersCardBinding: DersCardBinding) :
        RecyclerView.ViewHolder(dersCardBinding.root) {
        var tasarim: DersCardBinding

        init {
            this.tasarim = dersCardBinding
        }
    }
    //data binding işlemi
    //inflater metodları görsel tasarımı kodlamaya aktarmak için kullanılır
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val layoutInflater = LayoutInflater.from(mContext)
        val tasarim = DersCardBinding.inflate(layoutInflater, parent, false)
        return CardTasarimTutucu(tasarim)    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val ders = derslerListesi.get(position)
        //bu kodlamayla biz dersin adını sırayla arayüzde göstermiş olacağız ve teker teker görmüş olacağız
        holder.tasarim.textViewAd.text=ders.ders_adi
        //holder.tasarim.textViewKredi.text=ders.ders_kredi.toString()
        //holder.tasarim.textViewGunSaat.text=ders.ders_gun
       // holder.tasarim.textViewAkademikPersonel.text=ders.ders_ogretmen

        holder.tasarim.dersCardview.setOnClickListener{ // card'a tıkladığımda detaysayfaya veri gönderme
          val gecis=DerslerAnasayfaFragmentDirections.dersDetayGecis(ders)
            //hem geçiş hem veri transferi
          Navigation.findNavController(it).navigate(gecis)
        }

    }

    override fun getItemCount(): Int { //adapter kaç veri işleyecek?
         return derslerListesi.size
    }
}