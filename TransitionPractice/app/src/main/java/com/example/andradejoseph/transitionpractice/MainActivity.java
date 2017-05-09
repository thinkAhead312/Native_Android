package com.example.andradejoseph.transitionpractice;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.andradejoseph.transitionpractice.adapter.DataManager;
import com.example.andradejoseph.transitionpractice.model.Contact;
import com.example.andradejoseph.transitionpractice.utils.RecyclerClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv); // layout reference

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true); // to improve performance


        rv.setAdapter(new DataManager()); // the data manager is assigner to the RV
        rv.addOnItemTouchListener( // and the click is handled
                new RecyclerClickListener(this, new RecyclerClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra(DetailActivity.ID, Contact.CONTACTS[position].getId());

                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                MainActivity.this,
                                new Pair<View, String>(view.findViewById(R.id.CONTACT_circle),
                                        getString(R.string.transition_name_circle)),
                                new Pair<View, String>(view.findViewById(R.id.CONTACT_name),
                                        getString(R.string.transition_name_name)),
                                new Pair<View, String>(view.findViewById(R.id.CONTACT_phone),
                                        getString(R.string.transition_name_phone))
                        );
                        ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
                    }
                }));
    }
}
