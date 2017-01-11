package com.example.andradejoseph.bluetoothconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.example.andradejoseph.bluetoothconnect.LedControl;
import com.example.andradejoseph.bluetoothconnect.R;
import com.example.andradejoseph.bluetoothconnect.model.BluetoothModel;

import java.util.ArrayList;

/**
 * Created by ANDRADEJOSEPH on 1/11/2017.
 */
public class ItemAdapter  extends BaseAdapter {
    Context mContext;
    ArrayList<BluetoothModel> list = new ArrayList();;

    public ItemAdapter(Context context, ArrayList list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public BluetoothModel getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        }
        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("mTranstateY", position + "");
                return false;
            }
        });

        final BluetoothModel bluetoothModel = getItem(position);

        final View view = convertView;
        TextView textView = (TextView) convertView.findViewById(R.id.textview);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.item_image_view);
        final String text = "I Am Number " + position;
        textView.setText(bluetoothModel.getmBlueToothName() );
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = LedControl.newIntent(mContext, bluetoothModel.getmBlueToothAddress());
                mContext.startActivity(i);
            }
        });
        SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe);
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {

            @Override
            public void onStartOpen(SwipeLayout layout) {
                if (Build.VERSION.SDK_INT >= 21) {
                    view.setElevation(dp2px(4));
                } else {
                    // Implement this feature without material design
                }
            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }


            @Override
            public void onClose(SwipeLayout layout) {

                if (Build.VERSION.SDK_INT >= 21) {
                    view.setElevation(dp2px(1));
                } else {
                    // Implement this feature without material design
                }
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });
        return convertView;
    }

    private float dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public void updateAdapter(ArrayList list) {

        this.list = list;
        notifyDataSetChanged();
    }


}
