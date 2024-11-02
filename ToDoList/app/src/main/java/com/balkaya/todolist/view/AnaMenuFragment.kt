package com.balkaya.todolist.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.balkaya.todolist.databinding.FragmentAnaMenuBinding


class AnaMenuFragment : Fragment() {
    private  var _binding: FragmentAnaMenuBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnaMenuBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.GorevEkleButton.setBackgroundColor(Color.parseColor("#051666"))
        binding.GorevEkleButton.setTextColor(Color.WHITE)

        binding.GorevListeleButton.setBackgroundColor(Color.parseColor("#051666"))
        binding.GorevListeleButton.setTextColor(Color.WHITE)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.GorevEkleButton.setOnClickListener {
            GorevEkleGecis(it)
        }
        binding.GorevListeleButton.setOnClickListener {
            GorevlerGecis(it)
        }

    }
    fun GorevEkleGecis(view:View){
        val action = AnaMenuFragmentDirections.actionAnaMenuFragmentToGorevEkle()
        Navigation.findNavController(view).navigate(action)
    }
    fun GorevlerGecis(view:View){
        val action = AnaMenuFragmentDirections.actionAnaMenuFragmentToGorevlerFragment()
        Navigation.findNavController(view).navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}