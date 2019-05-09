package com.example.a.zhihu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.example.neteasecloudmusic.R
import com.example.neteasecloudmusic.model.MyPlayListBean
import com.example.neteasecloudmusic.view.PlayListInfoActivity
import com.squareup.picasso.Picasso
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


class MyMusicPageAdapter(var context: Context, mainBean: MyPlayListBean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val BANNER_TYPE = 0
    private val ITEM_TYPE = 1
    var songList = mainBean

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder =
        if (p1 == BANNER_TYPE)
            HeaderHolder(LayoutInflater.from(context).inflate(R.layout.my_music_page_header, p0, false))
        else
            ItemHolder(context, LayoutInflater.from(context).inflate(R.layout.item, p0, false))


    override fun getItemViewType(position: Int): Int =
        if (position == 0) BANNER_TYPE else ITEM_TYPE


    override fun getItemCount(): Int =
        if(songList.playlist == null)  1
        else  songList.playlist!!.size + 1

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        when(p0){
            is HeaderHolder -> {
                p0.apply {
                    local_music_layout.setOnClickListener {  }
                    recently_played_layout.setOnClickListener {  }
                    my_dj_layout.setOnClickListener {  }
                    my_values_layout
                }
            }
            is ItemHolder -> {
                if(songList.playlist == null){
                    p0.name.text == "暂无歌单"
                }else{
                    p0.apply {
                        name.text = songList!!.playlist!![p1 - 1].name
                        num.text = songList!!.playlist!![p1 - 1].trackCount + "首, by " + songList!!.playlist!![p1 - 1].creator?.nickname
                        playListItem.setOnClickListener {
                            val intent = Intent(context, PlayListInfoActivity::class.java)
                            val bundle = Bundle()
                            bundle.putString("id", songList!!.playlist!![p1 - 1].id.toString())
                            intent.putExtras(bundle)
                            context.startActivity(intent)
                        }
                    }


                    Glide.with(context)
                        .load(songList!!.playlist!![p1 - 1].coverImgUrl)
                        .apply(bitmapTransform(RoundedCornersTransformation(20,0,RoundedCornersTransformation.CornerType.ALL)))
                        .into(p0.coverImg)

//                    Picasso.with(context)
//                        .load(songList!!.playlist!![p1 - 1].coverImgUrl)
//                        .fit()
//                        .centerCrop()
//                        .into(p0.coverImg)
                }
            }
        }
    }

    inner class HeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var local_music_layout: LinearLayout = itemView.findViewById(R.id.local_music)
        var recently_played_layout: LinearLayout = itemView.findViewById(R.id.recently_played)
        var my_dj_layout: LinearLayout = itemView.findViewById(R.id.my_dj)
        var my_values_layout:LinearLayout = itemView.findViewById(R.id.my_Values)
    }


    inner class ItemHolder(context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
        var playListItem: ConstraintLayout = itemView.findViewById(R.id.playList)
        var listType: TextView = itemView.findViewById(R.id.item_type)
        var name: TextView = itemView.findViewById(R.id.mySongListName)
        var num: TextView = itemView.findViewById(R.id.mySongListSongNum)
        var coverImg: ImageView = itemView.findViewById(R.id.mySongListPicture)
    }
}

