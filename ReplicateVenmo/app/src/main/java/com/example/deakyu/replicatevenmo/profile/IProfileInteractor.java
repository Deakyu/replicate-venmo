package com.example.deakyu.replicatevenmo.profile;

import rx.Single;

public interface IProfileInteractor {
    Single<User> getUserById(int id);
}
