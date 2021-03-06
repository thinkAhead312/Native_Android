package com.example.josephandrade.article_detail_transition.adapter;

import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.josephandrade.article_detail_transition.R;
import com.example.josephandrade.article_detail_transition.model.Article;

import java.util.List;

/**
 * Created by Joseph Andrade on 1/6/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private List<Article> mArticles;

    private OnArticleClickedListener mOnArticleClickedListener;

    private OnTutorialButtonClickListener onTutorialButtonClickListener;

    /**
     * ArticleAdapter's constructor.
     *
     * @param articles Articles to display.
     */
    public ArticleAdapter(final List<Article> articles) {
        this.mArticles = articles;
    }

    Context mContext = null;

    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lessons, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Article item = mArticles.get(position);
        holder.ivThumbnail.setImageResource(item.getHeader());
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.ivCoveringImage.setImageResource(item.getHeader());
            holder.ivLessonImage.setImageResource(item.getLessonImage());
        }

//        Picasso.with(mContext).load(item.getLessonImage()).into( holder.ivLessonImage);
        holder.rootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (mOnArticleClickedListener != null) {
                        mOnArticleClickedListener.onArticleClicked(item, holder, new PointF(event.getX(), event.getY()));
                    }
                }
                return true;
            }
        });

        holder.btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTutorialButtonClickListener != null) {
                    onTutorialButtonClickListener.onTutorialButtonClick();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    /**
     * Sets on card_lessons clicked listener.
     *
     * @param onArticleClickedListener Article clicked listener.
     */
    public void setOnArticleClickedListener(final OnArticleClickedListener onArticleClickedListener) {
        this.mOnArticleClickedListener = onArticleClickedListener;
    }

    public void setTutorialButtonClickedListener(final OnTutorialButtonClickListener onTutorialButtonClickListener) {
        this.onTutorialButtonClickListener = onTutorialButtonClickListener;
    }





    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View rootView;

        public final ImageView ivThumbnail;
        public final ImageView ivCoveringImage;
        public final ImageView ivLessonImage;

        public final Button btnTutorial;

        public ViewHolder(final View view) {
            super(view);
            this.rootView = view;
            this.ivThumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            this.ivCoveringImage = (ImageView) view.findViewById(R.id.covering_image);
            this.ivLessonImage = (ImageView)view.findViewById(R.id.lesson_image);
            this.btnTutorial = (Button) view.findViewById(R.id.btn_tutorial);
        }
    }

    /**
     * On item clicked callback.
     */
    public interface OnArticleClickedListener {

        void onArticleClicked(Article article, ViewHolder articleViewHolder, PointF touchPoint);

    }

    public interface OnTutorialButtonClickListener {
        void onTutorialButtonClick();
    }

}
