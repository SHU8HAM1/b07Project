package com.example.b07project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.text.Editable;
import android.widget.EditText;

import com.example.b07Project.AdminFragmentModel;
import com.example.b07Project.AdminFragmentPresenter;
import com.example.b07Project.AdminFragmentView;
import com.example.b07Project.AdminLoginFragment;
import com.example.b07Project.AuthenticationCallback;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestAdminLogin {

    @Mock
    AdminFragmentModel model;
    @Mock
    AdminFragmentView view;

    @Mock
    EditText email;
    @Mock
    EditText password;
    @Mock
    AuthenticationCallback auth;
    @Mock
    Editable emailEditable;
    @Mock
    Editable passwordEditable;


   @Test
   public void checkAdminLoginSuccess(){
        when(email.getText()).thenReturn(emailEditable);
        when(password.getText()).thenReturn(passwordEditable);
        when(emailEditable.toString()).thenReturn(" 123@gmail.com ");
        when(passwordEditable.toString()).thenReturn(" 987654321 ");
       AdminFragmentPresenter presenter = new AdminFragmentPresenter(view, model);


       doAnswer(invocation -> {
           AuthenticationCallback call = invocation.getArgument(2);
           call.onSuccess();
           return null;
       }).when(model).verify(eq("123@gmail.com"), eq("987654321"), any(AuthenticationCallback.class));

       presenter.onClickLogin(email, password);
       verify(view).showLoginSuccess();
       verify(view).back();
   }
    @Test
    public void checkAdminLoginFailure(){
        when(email.getText()).thenReturn(emailEditable);
        when(password.getText()).thenReturn(passwordEditable);
        when(emailEditable.toString()).thenReturn(" 987@gmail.com ");
        when(passwordEditable.toString()).thenReturn(" acbsfd ");
        AdminFragmentPresenter presenter = new AdminFragmentPresenter(view, model);

        doAnswer(invocation -> {
            AuthenticationCallback call = invocation.getArgument(2);
            call.onFail();
            return null;
        }).when(model).verify(eq("987@gmail.com"), eq("acbsfd"), any(AuthenticationCallback.class));

        presenter.onClickLogin(email, password);
        verify(view).showLoginFailure();
    }

    @Test
    public void checkOnClickLoginEmailNull(){
        email = null;
        lenient().when(password.getText()).thenReturn(passwordEditable);
        lenient().when(passwordEditable.toString()).thenReturn(" acbsfd ");
        AdminFragmentPresenter presenter = new AdminFragmentPresenter(view, model);

        presenter.onClickLogin(email, password);
        verify(view).showLoginFailure();
    }
    @Test
    public void checkOnClickLoginPasswordNull(){
        password = null;
        lenient().when(email.getText()).thenReturn(emailEditable);
        lenient().when(emailEditable.toString()).thenReturn(" 987@gmail.com ");
        AdminFragmentPresenter presenter = new AdminFragmentPresenter(view, model);

        presenter.onClickLogin(email, password);
        verify(view).showLoginFailure();
    }

    @Test
    public void checkOnClickLoginEmailEmpty(){

        when(email.toString()).thenReturn("");

        lenient().when(passwordEditable.toString()).thenReturn(" acbsfd ");
        AdminFragmentPresenter presenter = new AdminFragmentPresenter(view, model);

        presenter.onClickLogin(email, password);
        verify(view).showLoginFailure();
    }
    @Test
    public void checkOnClickLoginPasswordEmpty(){

        when(password.toString()).thenReturn("");

        lenient().when(emailEditable.toString()).thenReturn(" 987@gmail.com ");
        AdminFragmentPresenter presenter = new AdminFragmentPresenter(view, model);

        presenter.onClickLogin(email, password);
        verify(view).showLoginFailure();
    }

}
