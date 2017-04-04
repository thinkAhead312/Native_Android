package com.example.andradejoseph.change12_ver_2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.andradejoseph.change12_ver_2.ConsolidatesActivity;
import com.example.andradejoseph.change12_ver_2.R;
import com.example.andradejoseph.change12_ver_2.model.Change12;
import com.example.andradejoseph.change12_ver_2.model.Changee;
import com.example.andradejoseph.change12_ver_2.model.DiscpleLab;

import org.w3c.dom.Text;

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
        View view = layoutInflater.inflate(R.layout.fragment_change12_wave_list,parent, false);
        return new WaveHolder(view);
    }

    @Override
    public void onBindViewHolder(WaveHolder holder, int position) {
        Change12 change12 = mChange12Waves.get(position);
        holder.bindChange12(change12);
    }

    @Override
    public int getItemCount() {
        return mChange12Waves.size();
    }

    public void setChange(List<Change12> change12Waves) {
        mChange12Waves = change12Waves;
    }

    public class WaveHolder extends RecyclerView.ViewHolder  {
        private Change12 mChange12;

        private TextView mChangeWaveNum;
        private TextView mChangeWaveDate;
        private RoundCornerProgressBar mTotalProgessBar, mGraduatedProgressBar, mRemainedProgressBar;

        public WaveHolder(View itemView) {
            super(itemView);
            mChangeWaveNum = (TextView) itemView.findViewById(R.id.wave_num_title_tv);
            mChangeWaveDate = (TextView) itemView.findViewById(R.id.date_tv);
            mTotalProgessBar = (RoundCornerProgressBar) itemView.findViewById(R.id.total_consolidates_progress_bar);
            mGraduatedProgressBar = (RoundCornerProgressBar) itemView.findViewById(R.id.graduated_consolidates_progress_bar);
            mRemainedProgressBar = (RoundCornerProgressBar) itemView.findViewById(R.id.remained_consolidates_progress_bar);
        }

        public void bindChange12(Change12 change12) {
            mChange12 = change12;
            mChangeWaveNum.setText(change12.getWave_num());
            mChangeWaveDate.setText(change12.getStart_date() + " - " + change12.getEnd_date());

            change12WaveStatistics(change12);
//            mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    //set the crime's solved property
//                    mCrime.setSolved(isChecked);
//                }
//            });
        }

        private void change12WaveStatistics(Change12 change12) {
            DiscpleLab discpleLab = DiscpleLab.get(mContext);

            List<Changee> changees = discpleLab.getWaveChangee(change12.getWave_num().toString());
            int countTotalChangee = 0;
            int countTotalGraduate = 0;
            int countRemainedActive = 0;
            for(Changee changee: changees) {
                Log.d("Change12WaveList ", changee.getChange_12() + " " + changee.getChangee());
                countTotalChangee++;
                if(changee.getChange_5_ok().trim().equals("on")) {
                    countTotalGraduate++;
                    if(discpleLab.getRemainedActiveConsolidates(String.valueOf(changee.getChangee()))) {
                        countRemainedActive++;
                    }
                }
            }

            mTotalProgessBar.setMax(countTotalChangee);
            mTotalProgessBar.setProgress(countTotalGraduate);

            mGraduatedProgressBar.setMax(countTotalChangee);
            mGraduatedProgressBar.setProgress(countTotalGraduate);

            mRemainedProgressBar.setMax(countTotalGraduate);
            mRemainedProgressBar.setProgress(countRemainedActive);
        }


    }


}
