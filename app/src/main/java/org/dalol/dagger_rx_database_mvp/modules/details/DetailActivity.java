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

package org.dalol.dagger_rx_database_mvp.modules.details;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.dalol.dagger_rx_database_mvp.R;
import org.dalol.dagger_rx_database_mvp.base.BaseActivity;
import org.dalol.dagger_rx_database_mvp.helper.ImageHandler;
import org.dalol.dagger_rx_database_mvp.mvp.model.Cake;

import butterknife.Bind;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 9/24/2016
 */
public class DetailActivity extends BaseActivity {

    public static final String CAKE = "cake";

    @Bind(R.id.cakeImage) protected ImageView mCakeImage;
    @Bind(R.id.cakeTitle) protected TextView mCakeTitle;
    @Bind(R.id.cakeDescription) protected TextView mCakeDescription;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mCakeImage.setTransitionName("cakeImageAnimation");
        }

        showBackArrow();

        Cake cake = (Cake) intent.getSerializableExtra(CAKE);
        setTitle("Cake Detail");

        mCakeTitle.setText(cake.getTitle());
        mCakeDescription.setText(cake.getDetailDescription());

        Glide.with(this).load(cake.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new ImageHandler(mCakeImage));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_detail;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
