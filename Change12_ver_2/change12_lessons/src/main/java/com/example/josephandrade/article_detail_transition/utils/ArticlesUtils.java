package com.example.josephandrade.article_detail_transition.utils;

import com.example.josephandrade.article_detail_transition.R;
import com.example.josephandrade.article_detail_transition.model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joseph Andrade on 1/6/2017.
 */

public class ArticlesUtils {

    /**
     * Disallow instantiation.
     */
    private ArticlesUtils() {
    }

    /**
     * Mock articles.
     *
     * @return Articles.
     */
    public static List<Article> mockArticles() {
        final List<Article> articles = new ArrayList<>();
        articles.add(new Article("Change 12 Lesson 1", "1", R.drawable.c_b, R.color.darker_darker_gray, R.drawable.destination_big));
        articles.add(new Article("Change 12 Lesson 2", "2", R.drawable.c_b, R.color.darker_darker_gray, R.drawable.direction_big));
        articles.add(new Article("Change 12 Lesson 3", "3", R.drawable.c_b, R.color.darker_darker_gray, R.drawable.heart_big));
        articles.add(new Article("Change 12 Lesson 4", "4", R.drawable.c_b, R.color.darker_darker_gray, R.drawable.relationship_big));
        articles.add(new Article("Change 12 Lesson 5", "5", R.drawable.c_b, R.color.darker_darker_gray, R.drawable.lifestyle_big));

        return articles;
    }
}
