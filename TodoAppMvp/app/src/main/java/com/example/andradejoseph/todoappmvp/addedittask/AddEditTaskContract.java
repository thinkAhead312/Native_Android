package com.example.andradejoseph.todoappmvp.addedittask;

import com.example.andradejoseph.todoappmvp.BasePresenter;
import com.example.andradejoseph.todoappmvp.BaseView;

/**
 * Created by ANDRADEJOSEPH on 5/16/2017.
 */

public interface AddEditTaskContract {


    interface View extends BaseView<Presenter> {

        void showEmptyTaskError();

        void showTasksList();

        void setTitle(String title);

        void setDescription(String description);

        boolean isActive();
    }



    interface Presenter extends BasePresenter {

        void saveTask(String title, String description);

        void populateTask();

        boolean isDataMissing();
    }
}
