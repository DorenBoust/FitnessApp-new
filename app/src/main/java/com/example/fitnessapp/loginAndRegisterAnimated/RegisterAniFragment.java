package com.example.fitnessapp.loginAndRegisterAnimated;

import androidx.annotation.DrawableRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.models.AsynUserJSON;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class RegisterAniFragment extends Fragment {

    private RegisterAniViewModel mViewModel;
    private TextView tvToLogin;

    private TextInputLayout etUserName;
    private TextInputLayout etPass;
    private int counterPassEye = 0;

    private ImageView ivEyePass;
    private TextInputLayout etPassValidation;
    private int counterPassValidationEye = 0;

    private ImageView ivEyePassValidation;
    private TextInputLayout etIntegrationCode;
    private int counterIntegrationCodeEye = 0;

    private ImageView ivEyePassIntegrationCode;
    private Button btnRegister;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;

    private String intagrationCodeStatus = "";

    private String intagrationCode = "";
    private MutableLiveData<String> mLiveData;







    public static RegisterAniFragment newInstance() {
        return new RegisterAniFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.register_ani_fragment, container, false);



        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegisterAniViewModel.class);

        tvToLogin = getView().findViewById(R.id.tv_register_click_to_login);

        etUserName = getView().findViewById(R.id.username_login);
        etPass = getView().findViewById(R.id.password_register);
        ivEyePass = getView().findViewById(R.id.iv_register_eye_pass);
        etPassValidation = getView().findViewById(R.id.passwordvalidation_register);
        ivEyePassValidation = getView().findViewById(R.id.iv_register_eye_passvalidation);
        etIntegrationCode = getView().findViewById(R.id.validation_register);
        ivEyePassIntegrationCode = getView().findViewById(R.id.iv_register_eye_integrationcode);
        btnRegister = getView().findViewById(R.id.btn_login_create_account);
        progressBar = getView().findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

        tvToLogin.setOnClickListener(view->{
            getFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.enter_top_to_bottom,R.anim.exite_bottom_to_top,R.anim.enter_bottom_to_top,R.anim.exite_top_to_bottom).
                    replace(R.id.mFragment, new LoginAniFragment()).commit();
        });



        btnRegister.setOnClickListener(v->{

            mLiveData = new MutableLiveData<>();

            TestAsync testAsync = new TestAsync(mLiveData);
            String code = etIntegrationCode.getEditText().getText().toString();
            testAsync.execute(code);

            mLiveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    intagrationCode = s;
                    if (intagrationCode.equals("[]")){
                        etIntegrationCode.setError("קוד התממשקות לא תקין");
                    } else {
                        etIntegrationCode.setError(null);
                    }
                    System.out.println("sedgsgsgseg" + intagrationCode);
                    validationField();
                }
            });

        });

        ivEyePass.setOnClickListener(v->{
            if (counterPassEye == 0){
                ivEyePass.setImageResource(R.drawable.ic_eye_red_24dp);
                etPass.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
                counterPassEye++;
            }else{
                ivEyePass.setImageResource(R.drawable.ic_eye_white_24dp);
                etPass.getEditText().setInputType(129);
                counterPassEye--;
            }
        });

        ivEyePassValidation.setOnClickListener(v->{
            if (counterPassValidationEye == 0){
                ivEyePassValidation.setImageResource(R.drawable.ic_eye_red_24dp);
                etPassValidation.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
                counterPassValidationEye++;
            }else{
                ivEyePassValidation.setImageResource(R.drawable.ic_eye_white_24dp);
                etPassValidation.getEditText().setInputType(129);
                counterPassValidationEye--;
            }
        });

        ivEyePassIntegrationCode.setOnClickListener(v->{
            if (counterIntegrationCodeEye == 0){
                ivEyePassIntegrationCode.setImageResource(R.drawable.ic_eye_red_24dp);
                etIntegrationCode.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
                counterIntegrationCodeEye++;
            }else{
                ivEyePassIntegrationCode.setImageResource(R.drawable.ic_eye_white_24dp);
                etIntegrationCode.getEditText().setInputType(129);
                counterIntegrationCodeEye--;
            }
        });



    }

    public String getIntagrationCode() {
        return intagrationCode;
    }

    public class TestAsync extends AsyncTask<String, Integer, String>{

        MutableLiveData<String> mLiveData;

        public TestAsync(MutableLiveData<String> mLiveData) {
            this.mLiveData = mLiveData;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String finalUrl = "http://appfitness.boust.me/wp-json/acf/v3/trainers?appConnection=" + strings[0];
                URL url = new URL(finalUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = reader.readLine()) != null){
                    sb.append(line);
                }

                return sb.toString();


            }catch (IOException e){

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            mLiveData.setValue(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }


    private void validationField(){
        String userName = etUserName.getEditText().getText().toString();
        String pass = etPass.getEditText().getText().toString();
        String passValidation = etPassValidation.getEditText().getText().toString();
        String intagrationCode = etIntegrationCode.getEditText().getText().toString();


        if (TextUtils.isEmpty(userName)){
            etUserName.setError("שדה חובה");
        } else{
            etUserName.setError(null);
        }

        if (TextUtils.isEmpty(pass)){
            etPass.setError("שדה חובה");
        }else{
            etPass.setError(null);
        }

        if (TextUtils.isEmpty(passValidation)){
            etPassValidation.setError("שדה חובה");
        }else if (!pass.equals(passValidation)){
            etPassValidation.setError("הסיסמאות לא תואמות");
            etPass.setError("הסיסמאות לא תואמות");
        } else {
            etPassValidation.setError(null);
        }

        if (TextUtils.isEmpty(intagrationCode)){
            etIntegrationCode.setError("שדה חובה");
        }


    }

    private void passwordEye(){

    }
}
