package org.dalol.dagger_rx_database_mvp.mvp.presenter;

import android.os.Looper;

import org.dalol.dagger_rx_database_mvp.api.CakeApiService;
import org.dalol.dagger_rx_database_mvp.mapper.CakeMapper;
import org.dalol.dagger_rx_database_mvp.mvp.model.Cake;
import org.dalol.dagger_rx_database_mvp.mvp.model.CakesResponse;
import org.dalol.dagger_rx_database_mvp.mvp.model.CakesResponseCakes;
import org.dalol.dagger_rx_database_mvp.mvp.model.Storage;
import org.dalol.dagger_rx_database_mvp.mvp.view.MainView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 11/28/2016
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Observable.class, AndroidSchedulers.class, Looper.class, CakesResponse.class})
public class CakePresenterTest {

    public static final String TEST_ERROR_MESSAGE = "error_message";

    @InjectMocks private CakePresenter presenter;
    @Mock private CakeApiService mApiService;
    @Mock private CakeMapper mCakeMapper;
    @Mock private Storage mStorage;
    @Mock private MainView mView;
    @Mock private Observable<CakesResponse> mObservable;

    @Captor private ArgumentCaptor<Subscriber<CakesResponse>> captor;

    private final RxJavaSchedulersHook mRxJavaSchedulersHook = new RxJavaSchedulersHook() {
        @Override
        public Scheduler getIOScheduler() {
            return Schedulers.immediate();
        }

        @Override
        public Scheduler getNewThreadScheduler() {
            return Schedulers.immediate();
        }
    };

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        ArrayList<Cake> cakes = new ArrayList<>();
        cakes.add(new Cake());
        when(mStorage.getSavedCakes()).thenReturn(cakes);
   }

    @Test
    public void getCakes() throws Exception {
        PowerMockito.mockStatic(Looper.class);
        when(AndroidSchedulers.mainThread()).thenReturn(mRxJavaSchedulersHook.getComputationScheduler());

        when(mApiService.getCakes()).thenReturn(mObservable);
        //        when(observable.subscribeOn(Schedulers.newThread())).thenReturn(observable);
        //        when(observable.observeOn(AndroidSchedulers.mainThread())).thenReturn(observable);

        presenter.getCakes();
        verify(mView, atLeastOnce()).onShowDialog("Loading cakes....");
    }

    @Test
    public void onCompleted() throws Exception {
        presenter.onCompleted();
        verify(mView, times(1)).onHideDialog();
        verify(mView, times(1)).onShowToast("Cakes loading complete!");
    }

    @Test
    public void onError() throws Exception {
        presenter.onError(new Throwable(TEST_ERROR_MESSAGE));
        verify(mView, times(1)).onHideDialog();
        verify(mView, times(1)).onShowToast("Error loading cakes " + TEST_ERROR_MESSAGE);
    }

    @Test
    public void onNext() throws Exception {
        CakesResponse response = mock(CakesResponse.class);
        CakesResponseCakes[] responseCakes = new CakesResponseCakes[1];
        when(response.getCakes()).thenReturn(responseCakes);
        presenter.onNext(response);

        verify(mCakeMapper, times(1)).mapCakes(mStorage, response);
        verify(mView, times(1)).onClearItems();
        verify(mView, times(1)).onCakeLoaded(anyList());
    }

    @Test
    public void getCakesFromDatabase() throws Exception {
        presenter.getCakesFromDatabase();
        verify(mView, times(1)).onClearItems();
        verify(mView, times(1)).onCakeLoaded(anyList());
    }
}