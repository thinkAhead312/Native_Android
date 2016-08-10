package com.example.glenda_pc.change12_activity.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.glenda_pc.change12_activity.Model.Disciple_Set_Get;
import com.example.glenda_pc.change12_activity.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by Glenda-PC on 12/26/2015.
 */
public class Staggered_Adapter extends RecyclerView.Adapter<Staggered_Adapter.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;
    List<Disciple_Set_Get> fetchImageList=null;
    public String strFetchName="";


    public Staggered_Adapter(List<Disciple_Set_Get> fetchImageList, Context context) {
        super();

        //Getting all the List
        this.fetchImageList = fetchImageList;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_disciples_staggered, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Disciple_Set_Get fetch_image = fetchImageList.get(position);
        final Bitmap[] bitmapImage = {null}; //bitmap for image
        holder.textViewName.setText(fetch_image.getFirst_name()); //Disply the Disciple Name

        holder.txtUserID.setText(fetch_image.getUsers_id()); //get the Disciple user id but txtUSErID is hidden
        holder.txtPhotoFlag.setText(fetch_image.getPhotoFlag()); //get the disciplePhotoFlag txtPhotoFlag is Hidden
        if(fetch_image.getClose_cell_flag().equals("0")){
            holder.textViewName.setBackgroundColor(Color.RED);
        }

        try { //if the URL of the Image is already stored in the internal media storage,
            bitmapImage[0] = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(fetch_image.getPhoto()));
            BitmapDrawable background = new BitmapDrawable(context.getResources(), bitmapImage[0]);
            holder.lin.setBackground(background);
        } catch (IOException e) {
            Drawable drawable = context.getResources().getDrawable(R.drawable.image_icon);

        }
    }

    @Override
    public int getItemCount() {
        return fetchImageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView textViewName, txtPhotoFlag;
        public CardView cr;
        public ImageView lin;
        public TextView txtUserID;
        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);

            lin = (ImageView) itemView.findViewById(R.id.imgNetworkBack);
            txtUserID = (TextView) itemView.findViewById(R.id.txtUserId);
            txtPhotoFlag = (TextView) itemView.findViewById(R.id.txtPhotoFlag);


            cr = (CardView) itemView.findViewById(R.id.card);
            cr.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            //Toast.makeText(context, "" + txtUserID.getText().toString() +"-" + txtPhotoFlag.getText().toString(), Toast.LENGTH_SHORT).show();
            if(v.equals(cr)){
             //   removeAt(getPosition(), txtUserID.getText().toString().trim());
            }
        }
    }
    public void removeAt(int position, String users_id) {
        //sql.deleteDisciple(users_id);
        fetchImageList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, fetchImageList.size());
    }


}
