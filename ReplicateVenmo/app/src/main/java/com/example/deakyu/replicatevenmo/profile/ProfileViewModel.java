package com.example.deakyu.replicatevenmo.profile;

import rx.Scheduler;
import rx.Single;

public class ProfileViewModel {
    private IProfileInteractor interactor;
    private Scheduler scheduler;

    public ProfileViewModel(IProfileInteractor interactor, Scheduler scheduler) {
        this.interactor = interactor;
        this.scheduler = scheduler;
    }

    public Single<User> getUserById(int id) {
        return interactor.getUserById(id).observeOn(scheduler);
    }
}
