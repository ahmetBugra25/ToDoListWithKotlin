package com.balkaya.todolist.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.balkaya.todolist.model.Gorev
import com.balkaya.todolist.databinding.FragmentGorevEkleBinding
import com.balkaya.todolist.roomdb.GorevDAO
import com.balkaya.todolist.roomdb.GorevDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class GorevEkle : Fragment() {


    private  var _binding: FragmentGorevEkleBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: GorevDatabase
    private lateinit var gorevDao : GorevDAO

    private  val mDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(requireContext(),GorevDatabase::class.java,"Tarifler").build()
        gorevDao = db.gorevDAO()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGorevEkleBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.KayitButton.setBackgroundColor(Color.parseColor("#051666"))
        binding.KayitButton.setTextColor(Color.WHITE)



        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.KayitButton.setOnClickListener{
            Kaydet(it)
        }


    }
    private fun Kaydet(view: View){
        val gorevBaslik = binding.editGorevBaslik.text.toString()
        val gorevKonu = binding.editGorevKonu.text.toString()
        val gorevDurum = binding.editGorevDurumu.isChecked
        val gorevLastDate = binding.editTextDate.text.toString()
        val gorevAciklama = binding.editGorevAciklama.text.toString()
        if (gorevBaslik != null && gorevKonu != null && gorevDurum != null && gorevLastDate != null && gorevAciklama != null ){
            val gorev = Gorev(gorevBaslik,gorevKonu,gorevDurum,gorevLastDate,gorevAciklama)
            mDisposable.add(
                gorevDao.insert(gorev)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponseForInsert))

            Toast.makeText(requireContext(),"Gorev Eklendi",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(),"Boşlukları doldurunuz",Toast.LENGTH_SHORT).show()
        }


    }
    private fun handleResponseForInsert(){
        findNavController().popBackStack()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mDisposable.clear()
    }

}