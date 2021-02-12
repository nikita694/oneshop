package com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.activeitzone.activeecommercecms.Network.response.AppSettingsResponse;
import com.activeitzone.activeecommercecms.Network.response.AuthResponse;
import com.activeitzone.activeecommercecms.Network.response.LogoutResponse;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.LoginActivity;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.AccountView;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.activeitzone.activeecommercecms.Utils.CustomToast;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.activeitzone.activeecommercecms.domain.interactors.AppSettingsInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.AppSettingsInteractorImpl;
import com.bumptech.glide.Glide;

public class LogoutFragment extends Fragment implements AccountView, AppSettingsInteractor.CallBack {

    private View v;
    private ImageView account_image;
    private TextView account_name;
    private AuthResponse authResponse;


    private RelativeLayout  logout;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_account, null);

      //  initViews();
        account_name = v.findViewById(R.id.account_name);
        authResponse = new UserPrefs(requireContext()).getAuthPreferenceObjectJson("auth_response");
        if(authResponse != null && authResponse.getUser() != null){
            account_name.setText(authResponse.getUser().getName());

            //Glide.with(requireContext()).load(AppConfig.ASSET_URL+authResponse.getUser().getAvatarOriginal()).placeholder(R.drawable.icons8_male_user_100).into(account_image);
            account_name.setText(authResponse.getUser().getName());
        }
        else {
            account_name.setText("SIGN IN / REGISTER");
            logout.setVisibility(View.GONE);
        }


        account_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(account_name.getText().equals("SIGN IN / REGISTER")){
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), 100);
                }
            }
        });

        account_image = v.findViewById(R.id.account_image);
        account_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account_name.getText().equals("SIGN IN / REGISTER")){
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), 100);
                }
            }
        });

        logout= v.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(authResponse != null && authResponse.getUser() != null){
                new UserPrefs(getContext()).clearPreference();

                new AppSettingsInteractorImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), LogoutFragment.this).execute();

                account_name.setText("SIGN IN / REGISTER");
                logout.setVisibility(View.GONE);
                account_image.setImageResource(R.drawable.icons8_male_user_100);
                requireActivity().finish();
                startActivity(requireActivity().getIntent());

            }

        }
    });

        return v;
}

     @SuppressLint("RestrictedApi")

    @Override
    public void showLogoutMessage(LogoutResponse logoutResponse) {
         CustomToast.showToast(getActivity(), logoutResponse.getMessage(), R.color.colorInfo);
         new UserPrefs(getContext()).clearPreference();

         new AppSettingsInteractorImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).execute();

         account_name.setText("SIGN IN / REGISTER");
         logout.setVisibility(View.GONE);
         account_image.setImageResource(R.drawable.icons8_male_user_100);

     }

    @Override
    public void onAppSettingsLoaded(AppSettingsResponse appSettingsResponse) {
        UserPrefs userPrefs = new UserPrefs(getContext());
        userPrefs.setAppSettingsPreferenceObject(appSettingsResponse, "app_settings_response");

    }

    @Override
    public void onAppSettingsLoadedError() {

    }
}
