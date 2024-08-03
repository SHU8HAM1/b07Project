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
   public void checkAdminLogin(){
        when(email.getText()).thenReturn(emailEditable);
        when(password.getText()).thenReturn(passwordEditable);
        when(emailEditable.toString()).thenReturn(" 123@gmail.com ");
        when(passwordEditable.toString()).thenReturn(" 987654321 ");
       AdminFragmentPresenter presenter = new AdminFragmentPresenter(view, model);
       presenter.onClickLogin(email, password);
        verify(model).verify("123@gmail.com", "987654321", auth);


   }
    /*when(email.getText().toString().trim()).thenReturn("123@gmail.com");
       when(password.getText().toString().trim()).thenReturn("987654321");

       AdminFragmentModel.isAdmin = false;
       AdminFragmentPresenter presenter = new AdminFragmentPresenter(view, model);
       presenter.onClickLogin(email, password);
       verify(view).showLoginSuccess();
       verify(view).back();*/

}
