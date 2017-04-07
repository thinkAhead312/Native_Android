package com.example.andradejoseph.change12_ver_2.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.andradejoseph.change12_ver_2.AdapterCallback;
import com.example.andradejoseph.change12_ver_2.R;
import com.example.andradejoseph.change12_ver_2.utils.C4DbHelperFunctions;
import com.example.andradejoseph.change12_ver_2.model.Change12;
import com.example.andradejoseph.change12_ver_2.model.Change12Lab;
import com.example.andradejoseph.change12_ver_2.model.Changee;
import com.example.andradejoseph.change12_ver_2.model.Disciple;
import com.example.andradejoseph.change12_ver_2.model.DiscpleLab;
import com.example.andradejoseph.change12_ver_2.utils.TouchEffect;

import java.util.List;

/**
 * Created by ANDRADEJOSEPH on 4/4/2017.
 */

public class WaveListAdapter extends RecyclerView.Adapter<WaveListAdapter.WaveHolder>{

    private List<Change12> mChange12Waves;
    private Context mContext;


    public WaveListAdapter(List<Change12> change12Waves, Context context) {
        mChange12Waves = change12Waves;
        mContext = context;

    }



    @Override
    public WaveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.card_change12_wave_list,parent, false);
        return new WaveHolder(view);
    }

    @Override
    public void onBindViewHolder(WaveHolder holder, int position) {
        Change12 change12 = mChange12Waves.get(position);
        holder.bindChange12(change12, position);
    }

    @Override
    public int getItemCount() {
        return mChange12Waves.size();
    }

    public void setChange(List<Change12> change12Waves) {


        mChange12Waves = change12Waves;
    }

    public class WaveHolder extends RecyclerView.ViewHolder{
        private Change12 mChange12;

        private TextView mChangeWaveNum;
        private TextView mChangeWaveDate;
        private TextView mTotalGraduated, mTotalConsolidates, mTotalRemainedActive, mTotalConsolidates1;
        private RoundCornerProgressBar mTotalProgessBar, mGraduatedProgressBar, mRemainedProgressBar;
        private CardView mWaveListCardView;
        private int mItemPosition;

        public WaveHolder(View itemView) {
            super(itemView);
            mChangeWaveNum = (TextView) itemView.findViewById(R.id.wave_num_title_tv);
            mChangeWaveDate = (TextView) itemView.findViewById(R.id.date_tv);
            mTotalGraduated = (TextView) itemView.findViewById(R.id.total_graduated_tv);
            mTotalConsolidates = (TextView) itemView.findViewById(R.id.total_consolidates_tv);
            mTotalRemainedActive = (TextView) itemView.findViewById(R.id.total_remained_active_tv);
            mTotalConsolidates1 = (TextView) itemView.findViewById(R.id.total_consolidates1_tv);
            mTotalProgessBar = (RoundCornerProgressBar) itemView.findViewById(R.id.total_consolidates_progress_bar);
            mGraduatedProgressBar = (RoundCornerProgressBar) itemView.findViewById(R.id.graduated_consolidates_progress_bar);
            mRemainedProgressBar = (RoundCornerProgressBar) itemView.findViewById(R.id.remained_consolidates_progress_bar);
            mWaveListCardView = (CardView) itemView.findViewById(R.id.wave_item_card_view);



        }

        public void bindChange12(Change12 change12, int position) {
            mItemPosition = position;
            mChange12 = change12;
            mChangeWaveNum.setText("Change 12 Wave " + change12.getWave_num());
            mChangeWaveDate.setText(change12.getStart_date() + " - " + mChange12.getEnd_date());


            change12WaveStatistics();

        }


        private void change12WaveStatistics() {
            final DiscpleLab discpleLab = DiscpleLab.get(mContext);
            Change12Lab change12Lab = Change12Lab.get(mContext);
            final C4DbHelperFunctions c4DbHelperFunction = new C4DbHelperFunctions(mContext);
            final List<Changee> changees = change12Lab.getWaveChangee(mChange12.getChange12_id().toString());

            final int[] countTotalChangee = {0};
            final int[] countTotalGraduate = {0};
            final int[] countRemainedActive = {0};

            new Thread() {
                @Override
                public void run() {
                    for (final Changee changee : changees) {
                        final Disciple disciple = discpleLab.getDisciple(changee.getChangee().trim());
                        boolean is_leader = c4DbHelperFunction.can_edit_and_delete_user(disciple.getDiscipler());
                        if (is_leader) {
                            countTotalChangee[0]++;
                            if (changee.getChange_5_ok().trim().equals("on")) {
                                countTotalGraduate[0]++;
                                if (discpleLab.getRemainedActiveConsolidates(String.valueOf(changee.getChangee())))
                                    countRemainedActive[0]++;
                            }
                        }
                        displayChange12Stats(countTotalChangee,countTotalGraduate,countRemainedActive);
                    }
                }
            }.start();
        }

        private void displayChange12Stats(final int[] countTotalChangee, final int[] countTotalGraduate, final int[] countRemainedActive) {
            ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTotalGraduated.setText(" " + String.valueOf(countTotalGraduate[0]));
                    mTotalConsolidates.setText(" " + String.valueOf(countTotalChangee[0]));
                    mTotalRemainedActive.setText(" " + String.valueOf(countRemainedActive[0]));
                    mTotalConsolidates1.setText(" " + String.valueOf(countTotalGraduate[0]));

                    mTotalProgessBar.setMax(countTotalChangee[0]);
                    mTotalProgessBar.setProgress(countTotalGraduate[0]);

                    mGraduatedProgressBar.setMax(countTotalChangee[0]);
                    mGraduatedProgressBar.setProgress(countTotalGraduate[0]);

                    mRemainedProgressBar.setMax(countTotalGraduate[0]);
                    mRemainedProgressBar.setProgress(countRemainedActive[0]);
                }
            });
        }

    }



}
