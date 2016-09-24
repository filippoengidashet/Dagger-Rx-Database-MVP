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

package org.dalol.dagger_rx_database_mvp.modules.home;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.dalol.dagger_rx_database_mvp.R;
import org.dalol.dagger_rx_database_mvp.base.BaseActivity;
import org.dalol.dagger_rx_database_mvp.di.components.DaggerCakeComponent;
import org.dalol.dagger_rx_database_mvp.di.module.CakeModule;
import org.dalol.dagger_rx_database_mvp.modules.details.DetailActivity;
import org.dalol.dagger_rx_database_mvp.modules.home.adapter.CakeAdapter;
import org.dalol.dagger_rx_database_mvp.mvp.model.Cake;
import org.dalol.dagger_rx_database_mvp.mvp.presenter.CakePresenter;
import org.dalol.dagger_rx_database_mvp.mvp.view.MainView;
import org.dalol.dagger_rx_database_mvp.utilities.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements MainView {

    @Bind(R.id.cake_list) protected RecyclerView mCakeList;
    @Inject protected CakePresenter mPresenter;
    private CakeAdapter mCakeAdapter;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        initializeList();
        loadCakes();
    }

    private void loadCakes() {
        if(NetworkUtils.isNetAvailable(this)) {
            mPresenter.getCakes();
        } else {
            mPresenter.getCakesFromDatabase();
        }
    }

    private void initializeList() {
        mCakeList.setHasFixedSize(true);
        mCakeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mCakeAdapter = new CakeAdapter(getLayoutInflater());
        mCakeAdapter.setCakeClickListener(mCakeClickListener);
        mCakeList.setAdapter(mCakeAdapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reload:
                loadCakes();
                return true;
            case R.id.action_about:
                showAbout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAbout() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Developed by Filippo Engidashet on 24/09/2016. \n\nGet the code and follow me on github!")
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Get Code", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://github.com/filippella/Dagger-Rx-Database-MVP"));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerCakeComponent.builder()
        .applicationComponent(getApplicationComponent())
        .cakeModule(new CakeModule(this))
        .build().inject(this);
    }

    @Override
    public void onCakeLoaded(List<Cake> cakes) {
        mCakeAdapter.addCakes(cakes);
    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }

    @Override
    public void onShowToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClearItems() {
        mCakeAdapter.clearCakes();
    }

    private CakeAdapter.OnCakeClickListener mCakeClickListener = new CakeAdapter.OnCakeClickListener() {
        @Override
        public void onClick(View v, Cake cake, int position) {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.CAKE, cake);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, v, "cakeImageAnimation");
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    };
}
