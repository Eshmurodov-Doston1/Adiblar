package com.programmalar.adiblar.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import com.programmalar.adiblar.R
import com.programmalar.adiblar.databinding.AlertDialogBinding
import com.programmalar.adiblar.databinding.FragmentSettingsBinding
import com.rm.rmswitch.RMSwitch.RMSwitchObserver
import com.rm.rmswitch.RMTristateSwitch
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SaveFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    lateinit var fragmentSettingsBinding: FragmentSettingsBinding
    lateinit var root:View
    lateinit var sharedPreferences: SharedPreferences
    var MYPREFERENCES="nightModePrefe"
    var KEY_ISNIGHTMODE="nightModePrefe"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
     fragmentSettingsBinding = FragmentSettingsBinding.inflate(inflater,container,false)
        root = fragmentSettingsBinding.root
        sharedPreferences = activity?.getSharedPreferences(MYPREFERENCES,Context.MODE_PRIVATE)!!
        checkNightModeActivited()
        fragmentSettingsBinding.switch10.isForceAspectRatio = false

        fragmentSettingsBinding.switch10.switchBkgNotCheckedColor = Color.parseColor("#F6F7F8");

      //  fragmentSettingsBinding.switch10.setSwitchToggleNotCheckedColor(Color.WHITE)

        fragmentSettingsBinding.switch10.addSwitchObserver(RMSwitchObserver { switchView, isChecked ->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveNightMode(true)
                //activity?.recreate()
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveNightMode(false)
               // activity?.recreate()
            }
        })
        fragmentSettingsBinding.share.setOnClickListener {
            var subject = "Adiblar hayoti va ijodi"
             var intent = Intent().apply {
                this.action = Intent.ACTION_SEND
                 this.putExtra(Intent.EXTRA_SUBJECT,subject)
                this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.mobiler.adiblar")
                this.type = "text/plain"
            }
            startActivity(intent)
        }

        fragmentSettingsBinding.infoApp.setOnClickListener {
            var alertDialog = AlertDialog.Builder(root.context, R.style.BottomSheetDialogThem)
            var alertDialogBinding = AlertDialogBinding.inflate(LayoutInflater.from(root.context),null,false)
            val create = alertDialog.create()
            create.setView(alertDialogBinding.root)
            create.show()
        }

        fragmentSettingsBinding.lan.setOnClickListener {
            Toast.makeText(root.context, "Hozirda faqatgina o`zbek tili mavjud", Toast.LENGTH_SHORT).show()
        }
        return root
    }

    private fun saveNightMode(b: Boolean) {
        var editor = sharedPreferences.edit()
        editor.putBoolean(KEY_ISNIGHTMODE,b)
        editor.apply()
    }

    private fun checkNightModeActivited(){
        if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE,false)){
            fragmentSettingsBinding.switch10.isChecked=true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            fragmentSettingsBinding.switch10.isChecked=false
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SaveFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SaveFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}