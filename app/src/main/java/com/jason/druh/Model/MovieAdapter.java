//--------------------------------------------------------------------------------------------------

/*IMPORTS*/

package com.jason.druh.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jason.druh.R;

import java.util.List;

//--------------------------------------------------------------------------------------------------

/*CLASS*/

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    //----------------------------------------------------------------------------------------------

    /*VARS DEFINITION*/

    private List<Movie> movies;
    private Context context;

    private OnItemClickListener onItemClickListener;

    //----------------------------------------------------------------------------------------------

    /*CONSTRUCTOR METHOD*/

    public MovieAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    } // END CONSTURCTOR

    //----------------------------------------------------------------------------------------------

    /*VIEW HOLDER METHODS*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,
                false);
        return new ViewHolder(view);
    } // END ONCREATEVIEWHOLDER

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(movies.get(position).getTitle());
        holder.category.setText(movies.get(position).getCategory());
        Glide.with(context).load(movies.get(position).getPoster()).into(holder.poster);
    } // END ONBINDVIEWHOLDER

    @Override
    public int getItemCount() {
        return movies.size();
    } // END GETITEMCOUNT

    //----------------------------------------------------------------------------------------------

    /*ONITEMCLICKLISTENER METHODS*/

    public interface OnItemClickListener {
        void onItemClick(int position);
    } // END ONITEMCLICKLISTENER

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    } // END SETONCLICKLISTENER

    //----------------------------------------------------------------------------------------------

    /*VIEW HOLDER CLASS*/

    public class ViewHolder extends RecyclerView.ViewHolder {

        //------------------------------------------------------------------------------------------

        /*VARS DEFINITION*/

        private ImageView poster;
        private TextView title, category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //--------------------------------------------------------------------------------------

            /*VARS INIT*/

            poster = itemView.findViewById(R.id.posterIV);
            title = itemView.findViewById(R.id.titleTV);
            category = itemView.findViewById(R.id.categoryTV);

            //--------------------------------------------------------------------------------------

            /*SETONCLICKLISTENER METHOD*/

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            }); // END SETONCLICKLISTENER

            //--------------------------------------------------------------------------------------

        } // END VIEW HOLDER CONSTRUCTOR

        //------------------------------------------------------------------------------------------

    } // END VIEW HOLDER CLASS

    //----------------------------------------------------------------------------------------------

} // END CLASS

//--------------------------------------------------------------------------------------------------