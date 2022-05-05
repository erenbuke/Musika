package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    ArrayList<Music> musics;
    Context context;

    public ListAdapter(ArrayList<Music> musics, Context context) {
        this.musics = musics;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        Music song = musics.get(position);
        holder.title.setText(song.name);
        holder.artist.setText(song.artist);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MP.getInstance().reset();
                MP.index = holder.getAdapterPosition();

                Intent intent = new Intent(context, MusicPage.class);
                intent.putExtra("SONGS", musics);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return musics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title = itemView.findViewById(R.id.musiclist_text);
        TextView artist = itemView.findViewById(R.id.musiclist_artist);
        ImageView image = itemView.findViewById(R.id.musiclist_image);

        public ViewHolder(View itemView){
            super(itemView);

        }
    }
}
