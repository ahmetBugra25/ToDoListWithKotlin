package com.balkaya.todolist.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.balkaya.todolist.databinding.FragmentGorevDetayBinding
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.balkaya.todolist.model.Gorev
import com.balkaya.todolist.roomdb.GorevDAO
import com.balkaya.todolist.roomdb.GorevDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class GorevDetayFragment : Fragment() {
    private  var _binding: FragmentGorevDetayBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: GorevDatabase
    private lateinit var gorevDao : GorevDAO

    private  val mDisposable = CompositeDisposable()
    private var secilenGorev : Gorev? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(requireContext(),GorevDatabase::class.java,"Tarifler").build()
        gorevDao = db.gorevDAO()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGorevDetayBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.SilButton.setBackgroundColor(Color.parseColor("#051666"))
        binding.SilButton.setTextColor(Color.WHITE)

        binding.GuncelleButton.setBackgroundColor(Color.parseColor("#051666"))
        binding.GuncelleButton.setTextColor(Color.WHITE)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            binding.SilButton.setOnClickListener { GorevSilme(it) }
            binding.GuncelleButton.setOnClickListener { GorevGuncelleme(it) }
            val id = GorevDetayFragmentArgs.fromBundle(it).IndexBilgisi

            mDisposable.add(gorevDao.findById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse))

        }

    }
    private fun handleResponse(gorev: Gorev){
        binding.detayEditGorevBaslik.setText(gorev.gorevBaslik)
        binding.detayEditGorevKonu.setText(gorev.gorevKonu)
        binding.detayEditGorevDurumu.isChecked = gorev.yapilmaDurumu
        binding.detayEditTextDate.setText(gorev.sonTarih)
        binding.detayEditGorevAciklama.setText(gorev.aciklama)
        secilenGorev = gorev
    }
    private fun handleResponseForDelete(){
        findNavController().popBackStack()
    }

    fun GorevSilme(view:View){
        if (secilenGorev !=null){
            mDisposable.add(gorevDao.delete(secilenGorev!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseForDelete))

            Toast.makeText(requireContext(),"Gorev Silindi",Toast.LENGTH_SHORT).show()

        }


    }
    private fun handleResponseForUpdate(){
        findNavController().popBackStack()
    }
    fun GorevGuncelleme(view : View){
        try {

            if (secilenGorev != null){
                val gorevBaslik = binding.detayEditGorevBaslik.text.toString()
                val gorevKonu = binding.detayEditGorevKonu.text.toString()
                val gorevDurum = binding.detayEditGorevDurumu.isChecked
                val gorevLastDate = binding.detayEditTextDate.text.toString()
                val gorevAciklama = binding.detayEditGorevAciklama.text.toString()
                if (gorevBaslik != null && gorevKonu != null && gorevDurum != null && gorevLastDate != null && gorevAciklama != null ){
                    val gorev = Gorev(gorevBaslik,gorevKonu,gorevDurum,gorevLastDate,gorevAciklama)
                    mDisposable.add(
                        gorevDao.insert(gorev)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe())

                    mDisposable.add(gorevDao.delete(secilenGorev!!)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponseForUpdate))

                    Toast.makeText(requireContext(),"Güncelleme yapıldı",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(),"Boşlukları doldurunuz",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),"Gorev Bilgilerine ulaşılamadı tekrar deneyiniz",Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){
            Toast.makeText(requireContext(),"Cathe Düştü",Toast.LENGTH_SHORT).show()
            Toast.makeText(requireContext(),"Güncellemişmi Kontrol Et",Toast.LENGTH_SHORT).show()
        }



    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mDisposable.clear()
    }


}