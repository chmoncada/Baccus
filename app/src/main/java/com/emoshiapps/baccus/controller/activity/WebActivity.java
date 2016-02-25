package com.emoshiapps.baccus.controller.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.emoshiapps.baccus.R;
import com.emoshiapps.baccus.controller.fragment.WebFragment;
import com.emoshiapps.baccus.model.Wine;

public class WebActivity extends FragmentContainerActivity {

    public static final String EXTRA_WINE = "com.emoshiapps.baccus.controller.activity.WebActivity.extra_wine";

    @Override
    protected Fragment createFragment() {
        Bundle arguments = new Bundle();
        arguments.putSerializable(WebFragment.ARG_WINE,getIntent().getSerializableExtra(EXTRA_WINE));

        WebFragment fragment = new WebFragment();
        fragment.setArguments(arguments);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){

            NavUtils.navigateUpFromSameTask(this);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
