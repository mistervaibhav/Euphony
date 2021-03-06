package com.apps.developer_cults.euphony.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.apps.developer_cults.euphony.adapters.MainScreenAdapter.Statified.stopPlayingCalled

import com.apps.developer_cults.euphony.R
import com.apps.developer_cults.euphony.Songs
import com.apps.developer_cults.euphony.activities.MainActivity
import com.apps.developer_cults.euphony.fragments.FavoriteFragment
import com.apps.developer_cults.euphony.fragments.MainScreenFragment
import com.apps.developer_cults.euphony.fragments.SongPlayingFragment


/*This adapter class also serves the same function to act as a bridge between the single row view and its data. The implementation is quite similar to the one we did
* for the navigation drawer adapter*/
class MainScreenAdapter(_songDetails: ArrayList<Songs>, _context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<MainScreenAdapter.MyViewHolder>() {

    /*Local variables used for storing the data sent from the fragment to be used in the adapter
    * These variables are initially null*/
    var songDetails: ArrayList<Songs>? = null
    var mContext: Context? = null

    object Statified{
        var stopPlayingCalled = false
    }

    /*In the init block we assign the data received from the params to our local variables*/
    init {
        this.songDetails = _songDetails
        this.mContext = _context
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val songObject = songDetails?.get(position)

        /*The holder object of our MyViewHolder class has two properties i.e
        * trackTitle for holding the name of the song and
        * trackArtist for holding the name of the artist*/

            holder.trackTitle?.text = songObject?.songTitle
            holder.trackArtist?.text = songObject?.artist

        if(holder.trackTitle?.text!!.equals("<unknown>"))
            holder.trackTitle?.text="unknown"

        if( holder.trackArtist?.text !!.equals("<unknown>"))
            holder.trackArtist?.text ="unknown"


        /*Handling the click event i.e. the action which happens when we click on any song*/
        holder.contentHolder?.setOnClickListener {
            val songPlayingFragment = SongPlayingFragment()

            var args = Bundle()
            args.putString("songArtist", songObject?.artist)
            args.putString("songTitle", songObject?.songTitle)
            args.putString("path", songObject?.songData)
            args.putLong("SongID", songObject?.songID!!)
            args.putInt("songPosition", position)

            args.putParcelableArrayList("songData",songDetails)  // sending the details as a parcel to the bundle

            songPlayingFragment.arguments=args


            stopPlaying()

            //checking if it already exists in the backstack
//            var popped:Boolean = (mContext  as MainActivity).supportFragmentManager.popBackStackImmediate("SongPlayingFragment",0)
//
//            // if doesn't exist, then only replace it
//            if(!popped) {
            (mContext as MainActivity).supportFragmentManager        // casting as mainactivity such that it is called by the main activity
                    .beginTransaction()
                    .replace(R.id.details_fragment, songPlayingFragment)
                    .addToBackStack("SongPlayingFragment")
                    .commit()
//            }
//            else{}

        }
    }

    /*This has the same implementation which we did for the navigation drawer adapter*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_custom_mainscreen_adapter, parent, false)
        return MyViewHolder(itemView)
    }
    override fun getItemCount(): Int {

        /*If the array list for the songs is null i.e. there are no songs in your device
        * then we return 0 and no songs are displayed*/
        if (songDetails == null) {
            return 0
        }
        /*Else we return the total size of the song details which will be the total number of song details*/
        else {
            return (songDetails as ArrayList<Songs>).size
        }
    }

    /*Every view holder class we create will serve the same purpose as it did when we created it for the navigation drawer*/
    class MyViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

        /*Declaring the widgets and the layout used*/
        var trackTitle: TextView? = null
        var trackArtist: TextView? = null
        var contentHolder: RelativeLayout? = null

        /*Constructor initialisation for the variables*/
        init {
            trackTitle = view.findViewById(R.id.tracktitle) as TextView
            trackArtist = view.findViewById(R.id.trackartist) as TextView
            contentHolder = view.findViewById(R.id.content_row) as RelativeLayout
        }
    }


    private fun stopPlaying() {
        if (SongPlayingFragment.Statified.mediaPlayer != null) {

            stopPlayingCalled=true

            SongPlayingFragment.Statified.mediaPlayer?.stop()
            SongPlayingFragment.Statified.mediaPlayer?.reset()

            FavoriteFragment.Statified.mediaPlayer?.stop()
            FavoriteFragment.Statified.mediaPlayer?.reset()

            MainScreenFragment.Statified.mediaPlayer?.stop()
            MainScreenFragment.Statified.mediaPlayer?.reset()

        }


    }


    fun filter_data(newList : ArrayList<Songs>?){


        if(newList!=null) {
//            songDetails?.removeAll(ArrayList<Songs>())

            songDetails = ArrayList<Songs>()
            songDetails?.addAll(newList)

            notifyDataSetChanged()
        }



    }
}