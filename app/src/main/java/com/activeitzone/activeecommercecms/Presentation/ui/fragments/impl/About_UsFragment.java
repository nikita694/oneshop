package com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.activeitzone.activeecommercecms.R;

import mehdi.sakout.aboutpage.AboutPage;

public class About_UsFragment extends Fragment {

    private static String TAG = About_UsFragment.class.getSimpleName();



    public About_UsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new AboutPage(container.getContext())
                .isRTL(false)
                .setDescription(getString(R.string.app_description))
                .addGroup(getString(R.string.contact_group))
                .addEmail("info@shopyculture.com", "Email")
                .addFacebook("","Facebook")
                .addYoutube("" ,"You Tube")
                .addGroup(getString(R.string.application_information_group))
                .create();
    }

}
