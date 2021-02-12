package com.activeitzone.activeecommercecms.domain.interactors.impl;

import android.util.Log;

import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.RegistrationResponse;
import com.activeitzone.activeecommercecms.Network.services.RegisterApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.RegisterInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;
import com.braintreepayments.api.Json;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterInteractorImpl extends AbstractInteractor {
    private RegisterInteractor.CallBack mCallback;
    private RegisterApiInterface apiService;
    private JsonObject jsonObject;

    public RegisterInteractorImpl(Executor threadExecutor, MainThread mainThread, RegisterInteractor.CallBack callBack, JsonObject jsonObject) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.jsonObject = jsonObject;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(RegisterApiInterface.class);

       // String modifiedJson = jsonObject.toString().substring(1, jsonObject.toString().length() - 1);

        Call<String> call = apiService.sendResgisterRequest(jsonObject);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.d("Test", String.valueOf(response.body()));
                    mCallback.onRegistrationDone(response.message());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //Log.d("Test", String.valueOf(call.isExecuted()));
                mCallback.onRegistrationError();
            }
        });

    }
}
