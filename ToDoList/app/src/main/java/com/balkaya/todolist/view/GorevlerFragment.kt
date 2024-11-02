package com.balkaya.todolist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.balkaya.todolist.adapter.GorevAdapter
import com.balkaya.todolist.databinding.FragmentGorevlerBinding
import com.balkaya.todolist.model.Gorev
import com.balkaya.todolist.roomdb.GorevDAO
import com.balkaya.todolist.roomdb.GorevDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class GorevlerFragment : Fragment() {
    private  var _binding: FragmentGorevlerBinding? = null
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
        _binding = FragmentGorevlerBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewGorevler.layoutManager = LinearLayoutManager(requireContext())
        VerileriAl()

    }
    private fun VerileriAl(){
        mDisposable.add(gorevDao.getAll().
        subscribeOn(Schedulers.io()).
        observeOn(AndroidSchedulers.mainThread()).
        subscribe(this::handleResponse))

    }
    private fun handleResponse(gorevListesi : List<Gorev>){
        val adapter = GorevAdapter(gorevListesi)
        binding.recyclerViewGorevler.adapter=adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mDisposable.clear()
    }
}