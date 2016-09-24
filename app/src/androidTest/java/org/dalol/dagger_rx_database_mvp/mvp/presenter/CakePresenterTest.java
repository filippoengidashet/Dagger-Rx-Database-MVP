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
import org.dalol.dagger_rx_database_mvp.mapper.CakeMapper;
import org.dalol.dagger_rx_database_mvp.mvp.model.CakesResponse;
import org.dalol.dagger_rx_database_mvp.mvp.model.Storage;
import org.dalol.dagger_rx_database_mvp.mvp.view.MainView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 9/24/2016
 */
@RunWith(MockitoJUnitRunner.class)
public class CakePresenterTest {

    @InjectMocks private CakePresenter presenter;
    @Mock private CakeApiService mApiService;
    @Mock private CakeMapper mCakeMapper;
    @Mock private Storage mStorage;
    @Mock private MainView mView;

    @Before
    public void setUp() throws Exception {
        Observable<CakesResponse> observable = mock(Observable.class);
        when(mApiService.getCakes()).thenReturn(observable);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetCakes() throws Exception {
        presenter.getCakes();
        verify(mView, atLeastOnce()).onShowDialog("Loading cakes....");
    }

    @Test
    public void testOnCompleted() throws Exception {

    }

    @Test
    public void testOnError() throws Exception {

    }

    @Test
    public void testOnNext() throws Exception {

    }

    @Test
    public void testGetCakesFromDatabase() throws Exception {

    }
}