package com.apps.developer_cults.euphony.fragments



import android.content.pm.ActivityInfo
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.apps.developer_cults.euphony.R


/**
 * A simple [Fragment] subclass.
 *
 */

class AboutUSFragment : androidx.fragment.app.Fragment() {



    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        //removing the sorting adn searching options as they r not needed

        val item = menu?.findItem(R.id.action_sort)
        item?.isVisible=false

        val item1 = menu?.findItem(R.id.action_search)
        item1?.isVisible=false
    }




    fun AboutFragment() {
        // Required empty public constructor
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_about, container, false)

        setHasOptionsMenu(true)

        return view

        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity!!.title = "ABOUT US"
    }


    override fun onStart() {
        super.onStart()
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}
