package com.example.fitnessapp.loginAndRegisterAnimated;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitnessapp.R;

public class LoginAniFragment extends Fragment {

    private LoginAniViewModel mViewModel;
    private TextView tvToRegister;

    public static LoginAniFragment newInstance() {
        return new LoginAniFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_ani_fragment, container, false);

        tvToRegister = v.findViewById(R.id.tv_register_click_to_login);

        tvToRegister.setOnClickListener(view->{
            getFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.enter_top_to_bottom,R.anim.exite_bottom_to_top,R.anim.enter_bottom_to_top,R.anim.exite_top_to_bottom).
                    replace(R.id.mFragment, new RegisterAniFragment()).commit();
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginAniViewModel.class);
        // TODO: Use the ViewModel
    }

}
