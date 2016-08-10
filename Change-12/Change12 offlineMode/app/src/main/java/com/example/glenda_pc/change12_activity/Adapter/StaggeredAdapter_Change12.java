package com.example.glenda_pc.change12_activity.Adapter;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.glenda_pc.change12_activity.Consolidates_Profile;
import com.example.glenda_pc.change12_activity.Database.DatabaseAccess;
import com.example.glenda_pc.change12_activity.Disciples_Profile;
import com.example.glenda_pc.change12_activity.Model.Disciple_Set_Get;
import com.example.glenda_pc.change12_activity.R;

import java.util.List;

/**
 * Created by Glenda-PC on 12/26/2015.
 */
public class StaggeredAdapter_Change12 extends RecyclerView.Adapter<StaggeredAdapter_Change12.ViewHolder> {

    private DatabaseAccess databaseAccess;
    private ImageLoader imageLoader;
    private Context context;
    List<Disciple_Set_Get> fetchImageList=null;
    public String strFetchName="";
    String savedURL="";


    public StaggeredAdapter_Change12(List<Disciple_Set_Get> fetchImageList, Context context) {
        super();

        //Getting all the List
        this.fetchImageList = fetchImageList;
        this.context = context;
        this.databaseAccess = DatabaseAccess.getInstance(context.getApplicationContext());
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_disciples_staggered, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Disciple_Set_Get fetch_image = fetchImageList.get(position);
         Bitmap bitmapImage = null; //bitmap for image
        holder.textViewName.setText("  "+fetch_image.getFirst_name()); //Disply the Disciple Name

        holder.txtUserID.setText(fetch_image.getUsers_id()); //get the Disciple user id but txtUSErID is hidden
        holder.txtPhotoFlag.setText(fetch_image.getPhoto()); //get the disciplePhotoFlag txtPhotoFlag is Hidden
        holder.tvConsolidatesFlag.setText(fetch_image.getConsolidates_flag());

        if(holder.tvConsolidatesFlag.getText().toString().trim().equals("1")) {
            holder.tvRemark.setText(fetch_image.getChange12_stat());
            holder.tvConsolidateRemark.setText("  " + fetch_image.getRemarks());
        }
        else {
            holder.tvRemark.setText("  "+fetch_image.getPepsol_stat());
            //holder.tvConsolidateRemark.setText("  " + fetch_image.getPepsol_stat());
        }

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.pic);

        /*try { //if the URL of the Image is already stored in the internal media storage,
            bitmapImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(fetch_image.getPhoto()));
            BitmapDrawable background = new BitmapDrawable(context.getResources(), bitmapImage);
            holder.lin.setImageBitmap(bitmapImage);
        } catch (IOException e) { //if the image is downloaded from the server,
            //Drawable drawable = context.getResources().getDrawable(R.drawable.image_icon);
            holder.lin.setImageBitmap(icon);
            BitmapDrawable drawable = (BitmapDrawable)  holder.lin.getDrawable();
            Bitmap bitmap1 = drawable.getBitmap();
            //filePathSavetoSqLite(bitmap1,holder.txtUserID.getText().toString().toString());
        }*/

        if(!holder.txtPhotoFlag.getText().toString().trim().equals("")) {
            byte[] imageAsBytes = Base64.decode(holder.txtPhotoFlag.getText().toString().trim().getBytes(), Base64.DEFAULT);
            bitmapImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
            //drawable = (RoundedBitmapDrawable) roundedImage.roundImg(bitmapImage);
            holder.lin.setImageBitmap(bitmapImage);
        }else{
            holder.lin.setImageBitmap(icon);
        }
    }

    private void filePathSavetoSqLite(Bitmap bitmapImage, String consolidateID) {
        ContentResolver cr = context.getContentResolver();
        String title ="Change 12";
        String description = "My bitmap created by Android-er";
        savedURL = MediaStore.Images.Media
                .insertImage(cr, bitmapImage, title, description);

        databaseAccess.open();
        databaseAccess.updatePhoto(consolidateID ,savedURL);
        databaseAccess.close();

    }

    @Override
    public int getItemCount() {
        return fetchImageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView textViewName, txtPhotoFlag;
        public CardView cr;
        public ImageView lin;
        public TextView txtUserID ,tvConsolidatesFlag,tvChangeStat, tvRemark,tvConsolidateRemark;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            lin = (ImageView) itemView.findViewById(R.id.imgNetworkBack);
            txtUserID = (TextView) itemView.findViewById(R.id.txtUserId);
            txtPhotoFlag = (TextView) itemView.findViewById(R.id.txtPhotoFlag);
            tvConsolidatesFlag = (TextView) itemView.findViewById(R.id.txtConsolidateFlag);
            tvRemark = (TextView) itemView.findViewById(R.id.tvRemark);
            tvConsolidateRemark = (TextView)itemView.findViewById(R.id.tvConsolidateRemark);
            cr = (CardView) itemView.findViewById(R.id.card);
            cr.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            //Toast.makeText(context, "" + txtUserID.getText().toString() +"-" + txtPhotoFlag.getText().toString(), Toast.LENGTH_SHORT).show();
            if(v.equals(cr)){
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

               // addItem(getPosition(), txtUserID.getText().toString().trim());
            }
        }
    }
    public void addItem(int position, String users_id) {
        //sql.deleteDisciple(users_id);

        Disciple_Set_Get setget = new Disciple_Set_Get();
        setget.setUsers_id("2222");
        setget.setFirst_name(DatabaseAccess.first_name);
        setget.setPhoto(DatabaseAccess.photo);
        fetchImageList.add(setget);

        notifyItemInserted(fetchImageList.size()-1);

        //fetchImageList.remove(position);
        //notifyItemRemoved(position);
      //  notifyItemRangeChanged(position, fetchImageList.size());
    }


}
