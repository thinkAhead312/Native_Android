package com.example.glenda_pc.change12_activity.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.glenda_pc.change12_activity.Database.DatabaseAccess;
import com.example.glenda_pc.change12_activity.Model.Disciple_Set_Get;
import com.example.glenda_pc.change12_activity.R;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Glenda-PC on 12/26/2015.
 */
public class RemarksAdapter extends RecyclerView.Adapter<RemarksAdapter.ViewHolder> {

    private DatabaseAccess databaseAccess;
    private ImageLoader imageLoader;
    private Context context;
    List<Disciple_Set_Get> fetchImageList=null;
    public String strFetchName="";


    public RemarksAdapter(List<Disciple_Set_Get> fetchImageList, Context context) {
        super();
        //Getting all the List
        this.fetchImageList = fetchImageList;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.remarks_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        this.databaseAccess = DatabaseAccess.getInstance(context);
        return viewHolder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Disciple_Set_Get fetch_image = fetchImageList.get(position);
        WindowManager windowManager = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        holder.tvUserid.setText(fetch_image.getUsers_id());
        holder.tvRemarks.setText(fetch_image.getRemarks()); //Disply the Disciple Name
        holder.tvTime_Stamp.setText(fetch_image.getTime_stamp());
        holder.cr.setMinimumWidth(width);
    }




    @Override
    public int getItemCount() {
        return fetchImageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView tvRemarks, tvTime_Stamp,tvUserid;
        public CardView cr;

        public ViewHolder(View itemView) {
            super(itemView);

            tvRemarks = (TextView) itemView.findViewById(R.id.tvRemarks);
            tvTime_Stamp = (TextView) itemView.findViewById(R.id.tvTime_Stamp);
            tvUserid = (TextView) itemView.findViewById(R.id.txtUserId);
            cr = (CardView) itemView.findViewById(R.id.crRemarks);
            cr.setOnClickListener(this);
            cr.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    initiatePopupWindow_Remarks("addNewRemark");
                    //addItem(getPosition(), "1111");
                    return true;
                }
            });



        }



        private PopupWindow pwindo;
        private void initiatePopupWindow_Remarks(final String strFlag) {
            try {
// We need to get the instance of the LayoutInflater
                final PopupMenu popupMenu = new PopupMenu(context,cr);
                popupMenu.inflate(R.menu.menu_update_remarks);

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.update_remarks,
                        (ViewGroup) itemView.findViewById(R.id.popup_element));
                WindowManager windowManager = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
                int width = windowManager.getDefaultDisplay().getWidth();
                int height = windowManager.getDefaultDisplay().getHeight();
                // Toast.makeText(this, String.valueOf(width)+ " " +String.valueOf(height), Toast.LENGTH_SHORT).show();
                pwindo = new PopupWindow(layout, width-(width/6), (int)(height*0.80), true);
                pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

                final EditText edMessage = (EditText) layout.findViewById(R.id.edMessage);
                //edMessage.setHeight((int)(height*0.60));

                //edMessage.setText(tvRemarks.getText().toString().trim());


                FloatingActionButton fabSMS = (FloatingActionButton) layout.findViewById(R.id.fabSMS);

                fabSMS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Object menuHelper;
                        Class[] argTypes;
                        try {

                            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
                            fMenuHelper.setAccessible(true);
                            menuHelper = fMenuHelper.get(popupMenu);
                            argTypes = new Class[]{boolean.class};
                            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);

                        } catch (Exception e) {
                            popupMenu.show();
                            return;
                        }
                        popupMenu.show();
                        //
                    }
                });
                popupMenu.setOnMenuItemClickListener(
                        new PopupMenu.OnMenuItemClickListener() {

                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.myCancel:
                                        pwindo.dismiss();
                                        break;
                                    case R.id.mySms://Updating Remarks of Particular Consolidate

                                            databaseAccess.open();
                                            Disciple_Set_Get updateChange12Stat = new Disciple_Set_Get();
                                            updateChange12Stat.setRemarks(edMessage.getText().toString().trim());
                                            databaseAccess.updateConsolidatesRemarks(tvUserid.getText().toString().trim(), updateChange12Stat, tvTime_Stamp.getText().toString().trim());
                                            databaseAccess.close();
                                            tvRemarks.setText(edMessage.getText().toString().trim());
                                             pwindo.dismiss();
                                        break;
                                    case R.id.myRemarks:
                                            databaseAccess.open();
                                            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date()); //Get Time now.
                                            Disciple_Set_Get remarksTable = new Disciple_Set_Get();
                                            remarksTable.setUsers_id(tvUserid.getText().toString().trim());
                                            remarksTable.setRemarks(edMessage.getText().toString().trim());
                                            remarksTable.setTime_stamp(currentDateTimeString);
                                            databaseAccess.insertRemarks(remarksTable);
                                            databaseAccess.close();
                                            addItem(getPosition(),tvUserid.getText().toString().trim() ,edMessage.getText().toString().trim(),currentDateTimeString );
                                        pwindo.dismiss();
                                        break;
                                }
                                return true;
                            }
                        });



            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void showMessage(String title,String message)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(message + "\n\n" +"(Hold Remark View to Update or Add new Remarks)");
            builder.show();
        }


        @Override
        public void onClick(View v) {
            //Toast.makeText(context, "" + txtUserID.getText().toString() +"-" + txtPhotoFlag.getText().toString(), Toast.LENGTH_SHORT).show();
            if(v.equals(cr)){
               // removeAt(getPosition(), txtUserID.getText().toString().trim());
               /* if(tvConsolidatesFlag.getText().toString().trim().equals("1")){
                    Intent intent = new Intent(context,Consolidates_Profile.class);
                    intent.putExtra("Users_ID", txtUserID.getText().toString().trim());
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context,Disciples_Profile.class);
                    intent.putExtra("Users_ID", txtUserID.getText().toString().trim());
                    context.startActivity(intent);
                }*/
                showMessage("Remarks",tvRemarks.getText().toString().trim());
                //initiatePopupWindow_Remarks("updateRemark");

            }
        }


    }
    public void addItem(int position, String users_id, String remarks, String time_stamp) {


        Disciple_Set_Get setget = new Disciple_Set_Get();
        setget.setUsers_id(users_id);
        setget.setTime_stamp(time_stamp);
        setget.setRemarks(remarks);
        fetchImageList.add(setget);
        notifyItemInserted(fetchImageList.size()+1);

        //fetchImageList.remove(position);
        //notifyItemRemoved(position);
        //  notifyItemRangeChanged(position, fetchImageList.size());
    }





    public void removeAt(int position, String users_id) {
        // sql.deleteDisciple(users_id);
        fetchImageList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, fetchImageList.size());
    }



    private void filePathSavetoSqLite(Bitmap bitmapImage, String users_id) {//saving to filepath
        ContentResolver cr = context.getContentResolver();
       // String title = sql.first_name;
        String description = "My bitmap created by Android-er";
      //  String savedURL = MediaStore.Images.Media.insertImage(cr, bitmapImage, title, description);
      //  sql.updateStoreDisciplePhoto(savedURL, users_id);
        // Toast.makeText(context, savedURL, Toast.LENGTH_SHORT).show();
    }

}
