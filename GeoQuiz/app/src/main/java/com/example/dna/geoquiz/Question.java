package com.example.dna.geoquiz;

/**
 * Created by dna on 9/26/16.
 */
public class Question {
    private int mTextResId;
    private boolean mAnserTrue;

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnserTrue = answerTrue;
    }

    public  int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnserTrue() {
        return mAnserTrue;
    }

    public void setAnserTrue(boolean answerTrue) {
        mAnserTrue = answerTrue;
    }
}
