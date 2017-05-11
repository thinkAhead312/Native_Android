package com.example.andradejoseph.profiler.profilepage;

import com.example.andradejoseph.profiler.BasePresenter;
import com.example.andradejoseph.profiler.BaseView;

/**
 * Created by ANDRADEJOSEPH on 5/9/2017.
 */

public interface ProfilePageContract {

    //make interface method
    interface View extends BaseView<Presenter> {
        //it's going to force to interact the presenter through the contract
        void setPresenter(ProfilePageContract.Presenter presenter);

        void setName (String name);
        void setEmail (String email);
        void setBio (String bio);
        void setInterests (String interests);

        void setProfilePhotoUrl (String profilePhotoUrl);
        void setDefaultProfilePhoto ();

//        void startPhotoActivity();
//        void startDetailActivity();
//        void startProfileSettingsActivity ();

        void startLoginActivity();

        void showLogoutSnackbar ();

        void setThumbnailLoadingIndicator(boolean show);

        void setDetailLoadingIndicators(boolean show);


    }

    interface Presenter extends BasePresenter {
        // methods here corespond to some kind of event,
        // most of those event on someone "click" on the user Interface

        void onThumbnailClick();
        void onEditProfileClick();
        void onLogoutClick();
        void onLogoutConfirmed();
        void onAccountSettingsClick();
        void onThumbnailLoaded();
    }
}
