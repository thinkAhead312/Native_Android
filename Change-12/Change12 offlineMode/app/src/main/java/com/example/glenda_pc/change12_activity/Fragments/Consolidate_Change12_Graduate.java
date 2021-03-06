package com.example.glenda_pc.change12_activity.Fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glenda_pc.change12_activity.Adapter.StaggeredAdapter_Change12;
import com.example.glenda_pc.change12_activity.Add_CalendarSched;
import com.example.glenda_pc.change12_activity.Add_Consolidates_Module;
import com.example.glenda_pc.change12_activity.Add_Disciple_Module;
import com.example.glenda_pc.change12_activity.Change12_Activity;
import com.example.glenda_pc.change12_activity.Model.Config;
import com.example.glenda_pc.change12_activity.Database.DatabaseAccess;
import com.example.glenda_pc.change12_activity.Model.Disciple_Set_Get;
import com.example.glenda_pc.change12_activity.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class Consolidate_Change12_Graduate extends Fragment {

    private DatabaseAccess databaseAccess;
    private List<Disciple_Set_Get> fetchDiscipleList= new ArrayList<>();;
    private RecyclerView recyclerView; //initialize recycleview
    private RecyclerView.LayoutManager layoutManager; //layout manager
    private RecyclerView.Adapter adapter; //adapter
    private TextView txtCloseCell;
    Context context;

    FloatingActionButton fab;
    public ArrayList<String> listName;
    public ArrayList<String> listContactNumber;
    View rootView;
    public Consolidate_Change12_Graduate() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.databaseAccess = DatabaseAccess.getInstance(context);
        rootView = inflater.inflate(R.layout.fragment_consolidate__change12__graduate, container, false);
        context = rootView.getContext();
        txtCloseCell = (TextView) rootView.findViewById(R.id.txtAllCell);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));// Here 2 is no. of columns to be displayed
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        WindowManager windowManager = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
        int height = windowManager.getDefaultDisplay().getHeight();
        recyclerView.setMinimumHeight((int) (height * .70));

        getData();
        popUpMenu(); //setUP popUPMenu

        return rootView;
    }

    private void popUpMenu() {
        final PopupMenu popupMenu = new PopupMenu(context, fab);
        popupMenu.inflate(R.menu.fab_view_disciples_menu);
        fab.setOnClickListener(new View.OnClickListener() {
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
            }
        });

        popupMenu.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.myDisciples:
                                Intent intent = new Intent(context,Add_Disciple_Module.class);
                                startActivity(intent);
                                break;
                            case R.id.myConsolidates:
                                intent = new Intent(context,Add_Consolidates_Module.class);
                                intent.putExtra("dipFlag", 0);
                                startActivity(intent);
                                break;
                            case R.id.myCalendar:
                                getlist();
                                intent = new Intent(context, Add_CalendarSched.class);
                                intent.putExtra("dipFlag", 1);
                                intent.putExtra("cellStr", "All Cell");
                                intent.putStringArrayListExtra("key", listContactNumber);
                                startActivity(intent);
                                break;
                            case R.id.mySms:
                                getlist();
                                initiatePopupWindow();
                                //Toast.makeText(context, listName.toString() +" "+listContactNumber.toString(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
    }

    public void getlist(){
        databaseAccess.open();
        databaseAccess.queryAllDisciples();
        listName = new ArrayList<String>();
        listContactNumber =new ArrayList<String>();
        for(int i = 0; i<databaseAccess.resultSet.length(); i++) {
            Disciple_Set_Get disciple_set_get = new Disciple_Set_Get();
            JSONObject json = null;
            try {

                json = databaseAccess.resultSet.getJSONObject(i);
                disciple_set_get.setFirst_name(json.getString("first_name"));
                disciple_set_get.setContact_num(json.getString(Config.DISCIPLE_CONTACT_NUM));
                listName.add(disciple_set_get.getFirst_name());
                listContactNumber.add(disciple_set_get.getContact_num());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        databaseAccess.close();
    }

    private PopupWindow pwindo;

    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            final PopupMenu popupMenu = new PopupMenu(context, fab);
            popupMenu.inflate(R.menu.menu_send__sm);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.sending_sms,
                    (ViewGroup) rootView.findViewById(R.id.popup_element));
            WindowManager windowManager = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
            int width = windowManager.getDefaultDisplay().getWidth();
            int height = windowManager.getDefaultDisplay().getHeight();
            Toast.makeText(context, String.valueOf(width)+ " " +String.valueOf(height), Toast.LENGTH_SHORT).show();
            pwindo = new PopupWindow(layout, width-(width/6), (int)(height*0.80), true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            EditText edParticipants = (EditText) layout.findViewById(R.id.edParticipants);
            final EditText edMessage = (EditText) layout.findViewById(R.id.edMessage);
            FloatingActionButton fabSMS = (FloatingActionButton) layout.findViewById(R.id.fabSMS);
            edParticipants.setText(listName.toString());




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
                        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.myCancel:
                                    pwindo.dismiss();
                                    break;
                                case R.id.mySms:
                                    SmsManager smsManager = SmsManager.getDefault();
                                    ArrayList<String> parts = smsManager.divideMessage( edMessage.getText().toString().trim());
                                    for (String contacts : listContactNumber) {
                                        smsManager.sendMultipartTextMessage(contacts, null, parts, null, null);
                                    }
                                    databaseAccess.open();
                                    smsManager.sendMultipartTextMessage(databaseAccess.contact_num, null, parts, null, null);
                                    databaseAccess.close();
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

    private void getData() {
        try{
            databaseAccess.open();
            databaseAccess.queryChange12("Graduate");
            txtCloseCell.setText(String.valueOf(databaseAccess.totalRows)); //get t
            databaseAccess.close();
            parseData();
        }catch (Exception e){
            Intent intent = new Intent(context,Change12_Activity.class);
            intent.putExtra("dipFlag",1);
            startActivity(intent);
        }
    }

    private void parseData() {
        StringBuilder buffer = new StringBuilder();
        // Disciple_Set_Get superHero = new Disciple_Set_Get();
        databaseAccess.open();
        for(int i = 0; i<databaseAccess.resultSet.length(); i++) {
            Disciple_Set_Get disciple_set_get = new Disciple_Set_Get();
            JSONObject json = null;
            try {
                json = databaseAccess.resultSet.getJSONObject(i);
                disciple_set_get.setPhoto(json.getString("photo"));
                disciple_set_get.setFirst_name(json.getString("first_name"));
                disciple_set_get.setUsers_id(json.getString(Config.USERS_USERS_ID));
                disciple_set_get.setContact_num(json.getString(Config.DISCIPLE_CONTACT_NUM));
                disciple_set_get.setSur_name(json.getString("last_name"));
                disciple_set_get.setPhotoFlag(json.getString("photo_updateFlag"));
                disciple_set_get.setEmail(json.getString("email_address"));
                disciple_set_get.setClose_cell_flag(json.getString("close_cell_flag"));
                disciple_set_get.setConsolidates_flag(json.getString("consolidates_flag"));
                disciple_set_get.setChange12_stat(json.getString("change12_status"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            buffer.append("" + disciple_set_get.getImageUrl()+"\n");
            fetchDiscipleList.add(disciple_set_get);
        }
        databaseAccess.close();

        //Finally initializing our adapter
        adapter = new StaggeredAdapter_Change12(fetchDiscipleList, context);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
