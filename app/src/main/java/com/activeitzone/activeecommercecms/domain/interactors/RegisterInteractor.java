package com.activeitzone.activeecommercecms.domain.interactors;

import com.activeitzone.activeecommercecms.Network.response.RegistrationResponse;

public interface RegisterInteractor {
    interface CallBack {

        void onRegistrationDone(String registrationResponse);

        void onRegistrationError();
    }
}
