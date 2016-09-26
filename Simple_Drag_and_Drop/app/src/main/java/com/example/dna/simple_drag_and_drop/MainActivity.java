package com.example.dna.simple_drag_and_drop;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //items stored in ListView
    public class Item {
        //Drawable ItemDrawable;
        String ItemString;
        Item(/*Drawable drawable,*/ String t){
            //ItemDrawable = drawable;
            ItemString = t;
        }
    }

    //objects passed in Drag and Drop operation
    class PassObject{
        View view;
        Item item;

        PassObject(View v, Item i){
            view = v;
            item = i;
        }
    }


    static class ViewHolder {
        ImageView icon;
        TextView text;
        LinearLayout ll;
    }

    public class ItemsListAdapter extends BaseAdapter {

        private Context context;
        private List<Item> list;
        int layOut;



        public ItemsListAdapter(Context c, List<Item> l, int simple_list_item_1) {
            context = c;
            list = l;
            layOut =simple_list_item_1;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            // reuse views
            if (rowView == null) {

                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.row,null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.text = (TextView)rowView.findViewById(R.id.rowTextView);
                //viewHolder.text = new TextView(context);
                viewHolder.ll = new LinearLayout(context);
                rowView.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) rowView.getTag();
            //holder.icon.setImageDrawable(list.get(position).ItemDrawable);



            holder.ll.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));

            TextView textView = new TextView(context);

            textView.setTextColor(Color.BLACK);
            holder.text.setText(list.get(position).ItemString);

            holder.ll.addView(textView);
            return rowView;
        }
        public List<Item> getList(){
            return list;
        }
    }

    List<Item> items1, items2;
    ListView  list, list2;
    ItemsListAdapter myItemsListAdapter1, myItemsListAdapter2;
    LinearLayout  _area1, _area2, _parent;
    TextView prompt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout);

        ll.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        ll.setOrientation(LinearLayout.VERTICAL);

         _parent = new LinearLayout(this);
        _parent.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 500));
        _parent.setOrientation(LinearLayout.HORIZONTAL);
        _parent.setBackgroundColor(Color.YELLOW);
        ll.addView(_parent);

        _area1 = new LinearLayout(this);
        _area1.setLayoutParams(new FrameLayout.LayoutParams(550, FrameLayout.LayoutParams.MATCH_PARENT));
        _area1.setOrientation(LinearLayout.VERTICAL);
        _parent.addView(_area1);

        _area2 = new LinearLayout(this);
        _area2.setLayoutParams(new FrameLayout.LayoutParams(550, FrameLayout.LayoutParams.MATCH_PARENT));
        _area2.setOrientation(LinearLayout.VERTICAL);
        _parent.addView(_area2);


        list = new ListView(this);
        list.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        _area1.addView(list);


        list2 = new ListView(this);
        list2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        _area2.addView(list2);

        _area1.setOnDragListener(myOnDragListener);
        _area2.setOnDragListener(myOnDragListener);
        initItems();


        myItemsListAdapter1 = new ItemsListAdapter(this, items1,android.R.layout.simple_list_item_1);
        myItemsListAdapter2 = new ItemsListAdapter(this, items2,android.R.layout.simple_list_item_1);
        list.setAdapter(myItemsListAdapter1);
        list2.setAdapter(myItemsListAdapter2);
        //listView2.setAdapter(myItemsListAdapter2);

        //Auto scroll to end of ListView
        list.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list2.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        list.setOnItemClickListener(listOnItemClickListener);
        list2.setOnItemClickListener(listOnItemClickListener);

        list.setOnItemLongClickListener(myOnItemLongClickListener);
        list2.setOnItemLongClickListener(myOnItemLongClickListener);

        prompt = (TextView) findViewById(R.id.prompt);
        // make TextView scrollable
        prompt.setMovementMethod(new ScrollingMovementMethod());
        //clear prompt area if LongClick
        prompt.setOnLongClickListener(new OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                prompt.setText("");
                return true;
            }});

    }

    OnItemLongClickListener myOnItemLongClickListener = new OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            Item selectedItem = (Item)(parent.getItemAtPosition(position));
            PassObject passObj = new PassObject(view, selectedItem);
            ClipData data = ClipData.newPlainText("", "");
            DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, passObj, 0);

            return true;
        }
    };

    OnDragListener myOnDragListener = new OnDragListener() {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            String area;
            if(v == _area1){
                area = "area1";
            }else if(v == _area2){
                area = "area2";
            }else{
                area = "unknown";
            }

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    prompt.append("ACTION_DRAG_STARTED: " + area  + "\n");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    prompt.append("ACTION_DRAG_ENTERED: " + area  + "\n");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    prompt.append("ACTION_DRAG_EXITED: " + area  + "\n");
                    break;
                case DragEvent.ACTION_DROP:
                    prompt.append("ACTION_DROP: " + area  + "\n");

                    PassObject passObj = (PassObject)event.getLocalState();
                    View view = passObj.view;
                    Item passedItem = passObj.item;
                    ListView oldParent = (ListView)view.getParent();
                    LinearLayout newParent = (LinearLayout)v;

                    if(oldParent.equals(list)){
                        prompt.append("Drag source: listView1" + "\n");

                        if(newParent.equals(_area2)){
                            prompt.append("Drop target: area2" + "\n");

                            if(removeItemToList(items1, passedItem)){
                                addItemToList(items2, passedItem);
                            }
                            myItemsListAdapter1.notifyDataSetChanged();
                            myItemsListAdapter2.notifyDataSetChanged();

                        }else{
                            prompt.append("Invalid Drop target" + "\n");
                        }

                    }else if(oldParent.equals(list2)){
                        prompt.append("Drag source: listView2" + "\n");

                        if(newParent.equals(_area1)){
                            prompt.append("Drop target: area1" + "\n");

                            if(removeItemToList(items2, passedItem)){
                                addItemToList(items1, passedItem);
                            }
                            myItemsListAdapter1.notifyDataSetChanged();
                            myItemsListAdapter2.notifyDataSetChanged();
                        }else{
                            prompt.append("Invalid Drop target" + "\n");
                        }

                    }else{
                        prompt.append("Invalid Drag source: unknown" + "\n");
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    prompt.append("ACTION_DRAG_ENDED: " + area  + "\n");
                default:
                    break;
            }

            return true;
        }

    };

    OnItemClickListener listOnItemClickListener = new OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Toast.makeText(MainActivity.this,
                    ((Item)(parent.getItemAtPosition(position))).ItemString,
                    Toast.LENGTH_SHORT).show();
        }

    };

    private void initItems(){



        items1 = new ArrayList<Item>();
        items2 = new ArrayList<Item>();

        //TypedArray arrayDrawable = getResources().obtainTypedArray(R.array.resicon);
        TypedArray arrayText = getResources().obtainTypedArray(R.array.restext);

        for(int i=0; i<arrayText.length(); i++){

            String s = arrayText.getString(i);
            Item item = new Item( s);
            items1.add(item);
        }


        //arrayDrawable.recycle();
        arrayText.recycle();
    }
    private boolean removeItemToList(List<Item> l, Item it){
        boolean result = l.remove(it);
        return result;
    }

    private boolean addItemToList(List<Item> l, Item it){
        boolean result = l.add(it);
        return result;
    }


}
