package com.balkaya.todolist.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.balkaya.todolist.model.Gorev
import com.balkaya.todolist.R
import com.balkaya.todolist.databinding.FragmentGorevDetayBinding
import com.balkaya.todolist.databinding.ItemGorevBinding
import com.balkaya.todolist.view.GorevlerFragmentDirections

class GorevAdapter(private val gorevList: List<Gorev>) : RecyclerView.Adapter<GorevAdapter.GorevHolder>() {


    class GorevHolder(val binding: ItemGorevBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GorevHolder {
        val view = ItemGorevBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GorevHolder(view)
    }

    override fun onBindViewHolder(holder: GorevHolder, position: Int) {
         holder.binding.tvGorevBasligi.text=gorevList[position].gorevBaslik
        holder.binding.tvGorevSonTarih.text=gorevList[position].sonTarih
        if ( gorevList[position].yapilmaDurumu==true){
            holder.binding.textTamamlamaDurumu.setTextColor(Color.GREEN)
            holder.binding.textTamamlamaDurumu.text = "Tamamlandı"

        }else{
            holder.binding.textTamamlamaDurumu.setTextColor(Color.RED)
            holder.binding.textTamamlamaDurumu.text="Tamamlanmadı"
        }
        holder.itemView.setOnClickListener{
            val action = GorevlerFragmentDirections.actionGorevlerFragmentToGorevDetayFragment2(IndexBilgisi = gorevList[position].id)
            Navigation.findNavController(it).navigate(action)
        }



    }


    override fun getItemCount() :Int{
        return gorevList.size
    }
}