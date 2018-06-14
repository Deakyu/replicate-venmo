package com.example.deakyu.replicatevenmo.profile;

import com.example.deakyu.replicatevenmo.VenmoAPIService;
import com.example.deakyu.replicatevenmo.network.VenmoRetrofit;

import rx.Single;
import rx.schedulers.Schedulers;

public class ProfileInteractor implements IProfileInteractor {

    private VenmoAPIService service;

    public ProfileInteractor() {
        this.service = VenmoRetrofit.getInstance().getVenmoService();
    }

    @Override
    public Single<User> getUserById(int id) {
        return service.getUserById(id).subscribeOn(Schedulers.io());
    }
}
