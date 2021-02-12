package com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.activeitzone.activeecommercecms.R;

public class Contact_usFragment extends Fragment {


    private static String TAG = Contact_usFragment.class.getSimpleName();

    private TextView tv_info;
    private ImageView iv_Call, iv_email;
    private View view;

    public Contact_usFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        tv_info = view.findViewById(R.id.tv_info);

         iv_Call = view.findViewById(R.id.iv_call);
         iv_email = view.findViewById(R.id.email);

        iv_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermissionGranted()) {
                    call_action("+917020526997");
                }
            }

        });

        iv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               email_action("info@shopyculture.com");
            }

        });
        return view;
    }


    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }

    }

    public void call_action(String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + number));

        startActivity(callIntent);

    }
 public void email_action(String email){
     Intent sendIntent = new Intent(Intent.ACTION_SEND);
     sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "email@gmail.com" });
     sendIntent.setType("message/rfc822");
     sendIntent.setData(Uri.parse("email@gmail.com"));
     sendIntent.putExtra(Intent.EXTRA_SUBJECT, "enter subject");
     sendIntent.setType("plain/text");
     sendIntent.putExtra(Intent.EXTRA_TEXT, "Insert text");
     startActivity(Intent.createChooser(sendIntent, "send email to..."));
 }
}




