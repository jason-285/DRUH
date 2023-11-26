package com.jason.druh.Model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jason.druh.HomeActivity;
import com.jason.druh.R;

import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.ViewHolder> {
    private List<Cinema> cinemas;
    private Context context;

    public CinemaAdapter(List<Cinema> cinemas, Context context) {
        this.cinemas = cinemas;
        this.context = context;
    }

    @NonNull
    @Override
    public CinemaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cinema,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CinemaAdapter.ViewHolder holder, int position) {
        holder.phone = cinemas.get(position).getPhone();
        holder.website = cinemas.get(position).getWeb();
        holder.nameTxtV.setText(cinemas.get(position).getName());
        holder.locationsTxtV.setText(cinemas.get(position).getLocations());
        Glide.with(context).load(cinemas.get(position).getLogo()).into(holder.logoImgV);
    }

    @Override
    public int getItemCount() {
        return cinemas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        String phone, website;

        ImageView logoImgV;
        TextView nameTxtV, locationsTxtV;
        ImageButton messageCBtn, callCBtn, webCBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            logoImgV = itemView.findViewById(R.id.logoImgV);
            nameTxtV = itemView.findViewById(R.id.nameTxtV);
            locationsTxtV = itemView.findViewById(R.id.locationsTxtV);
            messageCBtn = itemView.findViewById(R.id.messageCBtn);
            callCBtn = itemView.findViewById(R.id.callCBtn);
            webCBtn = itemView.findViewById(R.id.webCBtn);

            messageCBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(phone,null,"Hi, I wanna more information about the cinema!!!", null, null);

                    Toast.makeText(context,"Message sended succesfully",Toast.LENGTH_SHORT).show();
                }
            });

            callCBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+Integer.parseInt(phone)));
                    callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(callIntent);
                }
            });

            webCBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                    webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(webIntent);
                }
            });
        }
    }
}
