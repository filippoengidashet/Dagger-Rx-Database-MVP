/*
 * Copyright (c) 2016 Filippo Engidashet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dalol.dagger_rx_database_mvp.mvp.presenter;

import org.dalol.dagger_rx_database_mvp.api.CakeApiService;
import org.dalol.dagger_rx_database_mvp.base.BasePresenter;
import org.dalol.dagger_rx_database_mvp.mapper.CakeMapper;
import org.dalol.dagger_rx_database_mvp.mvp.model.Cake;
import org.dalol.dagger_rx_database_mvp.mvp.model.CakesResponse;
import org.dalol.dagger_rx_database_mvp.mvp.model.Storage;
import org.dalol.dagger_rx_database_mvp.mvp.view.MainView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 9/24/2016
 */
public class CakePresenter extends BasePresenter<MainView> implements Observer<CakesResponse> {

    @Inject protected CakeApiService mApiService;
    @Inject protected CakeMapper mCakeMapper;
    @Inject protected Storage mStorage;

    @Inject
    public CakePresenter() {
    }

    public void getCakes() {
        getView().onShowDialog("Loading cakes....");
        Observable<CakesResponse> cakesResponseObservable = mApiService.getCakes();
        subscribe(cakesResponseObservable, this);
    }

    @Override
    public void onCompleted() {
        getView().onHideDialog();
        getView().onShowToast("Cakes loading complete!");
    }

    @Override
    public void onError(Throwable e) {
        getView().onHideDialog();
        getView().onShowToast("Error loading cakes " + e.getMessage());
    }

    @Override
    public void onNext(CakesResponse cakesResponse) {
        List<Cake> cakes = mCakeMapper.mapCakes(mStorage, cakesResponse);
        getView().onClearItems();
        getView().onCakeLoaded(cakes);
    }

    public void getCakesFromDatabase() {
        List<Cake> cakes = mStorage.getSavedCakes();
        getView().onClearItems();
        getView().onCakeLoaded(cakes);
    }
}
