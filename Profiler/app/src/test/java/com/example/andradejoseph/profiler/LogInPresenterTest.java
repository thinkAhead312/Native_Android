package com.example.andradejoseph.profiler;

/**
 * This is a Unit Test for our Presenter
 */

import com.example.andradejoseph.profiler.data.auth.AuthInjection;
import com.example.andradejoseph.profiler.data.auth.AuthSource;
import com.example.andradejoseph.profiler.data.scheduler.SchedulerInjection;
import com.example.andradejoseph.profiler.login.LoginContract;
import com.example.andradejoseph.profiler.login.LoginPresenter;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class LogInPresenterTest {

    @Mock
    private LoginContract.View view;

    private LoginPresenter presenter;

    private AuthSource authsource;


    public static final String VALID_PASSWORD = "123456";

    public static final String INVALID_PASSWORD = "12345";

    public static final String LONG_PASSWORD = "12345678912345678912";


    public static final String USERNAME = "Derp";

    public static final String INVALID_EMAIL = "userexample.com";

    public static final String VALID_EMAIL = "user@example.com";





    @Before
    public void Setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        authsource = AuthInjection.provideAuthSource();

        presenter = new LoginPresenter(authsource, view, SchedulerInjection.baseSchedulerProvider());


    }

    /**
     * when user clicks login button, attempt to log the user in
     */

    @Test
    public void whenuserLoginAttemptSucceeds() throws Exception{
        presenter.onLogInClick();

        verify(view).startProfileActivity();
    }

    @Test
    public void whenuserLoginAttemptFails() throws Exception{
        verify(view).makeToast(Mockito.anyString());
    }

    @Test
    public void whenUserClicksRegisterButton() throws Exception{
        verify(view).startCreateAccountActivity();
    }

    @Test
    public void whenActiveUserFound() {
        verify(view).startProfileActivity();
    }

    @Test
    public void whenEmailEmpty() throws Exception {
        Mockito.when(view.getEmail()).thenReturn("");
        Mockito.when(view.getPassword()).thenReturn(VALID_PASSWORD);
        verify(view).makeToast(Mockito.anyString());
    }

    @Test
    public void whenEmailInvalid() throws Exception {
        Mockito.when(view.getEmail()).thenReturn(INVALID_EMAIL);
        Mockito.when(view.getPassword()).thenReturn(USERNAME);

        verify(view).makeToast(Mockito.anyString());
    }

    @Test
    public void whenPasswordEmpty() throws Exception {
        Mockito.when(view.getEmail()).thenReturn(VALID_EMAIL);
        Mockito.when(view.getPassword()).thenReturn("");

        verify(view).makeToast(Mockito.anyString());
    }

    @Test
    public void whenPasswordTooLong() throws Exception {
        Mockito.when(view.getEmail()).thenReturn(VALID_EMAIL);
        Mockito.when(view.getPassword()).thenReturn(LONG_PASSWORD);

        verify(view).makeToast(Mockito.anyString());
    }
}