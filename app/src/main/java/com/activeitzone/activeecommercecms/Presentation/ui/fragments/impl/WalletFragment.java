package com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeitzone.activeecommercecms.Models.Wallet;
import com.activeitzone.activeecommercecms.Network.response.AuthResponse;
import com.activeitzone.activeecommercecms.Presentation.presenters.WalletPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.WalletView;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.LoginActivity;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.WalletHistoryAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.WishlistAdapter;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.activeitzone.activeecommercecms.Utils.RecyclerViewMargin;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;

import java.util.List;

public class WalletFragment extends Fragment implements WalletView {
    private AuthResponse authResponse;
    private TextView tv_balance;

    private  View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_wallet, container, false);
        tv_balance = view.findViewById(R.id.balance);
        authResponse = new UserPrefs(requireActivity()).getAuthPreferenceObjectJson("auth_response");
        if(authResponse != null && authResponse.getUser() != null){
            new WalletPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).getWalletBalance(authResponse.getUser().getId(), authResponse.getAccessToken());
        }
        else {
            startActivityForResult(new Intent(requireActivity(), LoginActivity.class), 100);
          //  getActivity().setTitle("My Wallet");
        }

        return  view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wallet);
//
//        initializeActionBar();
//        setTitle("My Wallet");
//
//
//
//    }

    private void initviews(){

    }

    @Override
    public void setWalletBalance(Double balance) {
        tv_balance.setText(AppConfig.convertPrice(requireActivity(), balance));
        new WalletPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).getWalletHistory(authResponse.getUser().getId(), authResponse.getAccessToken());
    }

    @Override
    public void setWalletHistory(List<Wallet> walletList) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_wallet_history);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        RecyclerViewMargin decoration = new RecyclerViewMargin(AppConfig.convertDpToPx(requireActivity(),10), 1);
        recyclerView.addItemDecoration(decoration);
        WalletHistoryAdapter adapter = new WalletHistoryAdapter(requireActivity(), walletList);
        recyclerView.setAdapter(adapter);
    }
}