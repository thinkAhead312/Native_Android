package com.example.andradejoseph.profiler;

import android.support.annotation.StringRes;

/**
 * Created by ANDRADEJOSEPH on 5/10/2017.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void makeToast(@StringRes int stringId);

    void makeToast(String message);


}
