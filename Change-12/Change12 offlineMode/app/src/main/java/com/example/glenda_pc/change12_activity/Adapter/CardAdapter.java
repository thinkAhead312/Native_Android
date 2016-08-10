package com.example.glenda_pc.change12_activity.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.glenda_pc.change12_activity.Consolidates_Profile;
import com.example.glenda_pc.change12_activity.Disciples_Profile;
import com.example.glenda_pc.change12_activity.Model.Disciple_Set_Get;
import com.example.glenda_pc.change12_activity.Model.RoundImage;
import com.example.glenda_pc.change12_activity.R;

import java.util.List;

/**
 * Created by Glenda-PC on 12/26/2015.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context context;
    List<Disciple_Set_Get> fetchImageList=null;


    RoundImage roundedImage = new RoundImage();
    RoundedBitmapDrawable drawable;

    public CardAdapter(List<Disciple_Set_Get> fetchImageList, Context context) {
        super();
        //Getting all the List
        this.fetchImageList = fetchImageList;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Disciple_Set_Get fetch_image = fetchImageList.get(position);
        Bitmap bitmapImage = null; //bitmap for image

        WindowManager windowManager = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();

        holder.textViewName.setText(fetch_image.getFirst_name()); //Disply the Disciple Name
        holder.txtContactNum.setText(fetch_image.getContact_num()); //display the Disciple Contact Number
        holder.txtUserID.setText(fetch_image.getUsers_id()); //get the Disciple user id but txtUSErID is hidden
        holder.tvConsolidatesFlag.setText(fetch_image.getConsolidates_flag());
        holder.txtPhotoFlag.setText(fetch_image.getPhoto()); //get the disciplePhotoFlag txtPhotoFlag is Hidden
        holder.txtAffiliation.setMinimumWidth((int) (width * 0.20));
        holder.txtAffiliation.setText(fetch_image.getAffliation());
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.pic);
        /*try { //if the URL of the Image is already stored in the internal media storage,
            bitmapImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(fetch_image.getPhoto()));
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(bitmapImage);
            holder.imageView.setImageDrawable(drawable);
        } catch (IOException e) { //if the image is downloaded from the server,
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(icon);
            holder.imageView.setImageDrawable(drawable);
        }*/

        if(!holder.txtPhotoFlag.getText().toString().trim().equals("")) {
            byte[] imageAsBytes = Base64.decode(holder.txtPhotoFlag.getText().toString().trim().getBytes(), Base64.DEFAULT);
            bitmapImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(bitmapImage);
            holder.imageView.setImageDrawable(drawable);
        }else{
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(icon);
            holder.imageView.setImageDrawable(drawable);
        }
    }




    @Override
    public int getItemCount() {
        return fetchImageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;

        public TextView textViewName, txtPhotoFlag;
        public CardView cr;
        public TextView txtAffiliation,tvConsolidatesFlag;
        public TextView txtUserID,txtContactNum;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgNetworkBack);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            txtUserID = (TextView) itemView.findViewById(R.id.txtUserId);
            txtPhotoFlag = (TextView) itemView.findViewById(R.id.txtPhotoFlag);
            txtContactNum = (TextView)itemView.findViewById(R.id.txtViewContactNum);
            txtAffiliation = (TextView)itemView.findViewById(R.id.txtViewAffiliation);
            tvConsolidatesFlag = (TextView) itemView.findViewById(R.id.txtConsolidateFlag);
            cr = (CardView) itemView.findViewById(R.id.card);
            cr.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            //Toast.makeText(context, "" + txtUserID.getText().toString() +"-" + txtPhotoFlag.getText().toString(), Toast.LENGTH_SHORT).show();
            if(v.equals(cr)){
               // removeAt(getPosition(), txtUserID.getText().toString().trim());
                if(tvConsolidatesFlag.getText().toString().trim().equals("1")){
                    Intent intent = new Intent(context,Consolidates_Profile.class);
                    intent.putExtra("Users_ID", txtUserID.getText().toString().trim());
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context,Disciples_Profile.class);
                    intent.putExtra("Users_ID", txtUserID.getText().toString().trim());
                    context.startActivity(intent);
                }
            }
        }


    }



    public void removeAt(int position, String users_id) {
        // sql.deleteDisciple(users_id);
        fetchImageList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, fetchImageList.size());
    }



}
