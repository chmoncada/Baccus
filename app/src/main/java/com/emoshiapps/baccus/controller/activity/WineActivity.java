package com.emoshiapps.baccus.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.emoshiapps.baccus.R;
import com.emoshiapps.baccus.controller.fragment.WineFragment;
import com.emoshiapps.baccus.model.Wine;

public class WineActivity extends FragmentContainerActivity {

    public static final String EXTRA_WINE = "com.emoshiapps.baccus.controller.activity.WineActivity.EXTRA_WINE";


    @Override
    protected Fragment createFragment() {
        Bundle arguments = new Bundle();
        arguments.putSerializable(WineFragment.ARG_WINE,getIntent().getSerializableExtra(EXTRA_WINE));

        WineFragment fragment = new WineFragment();
        fragment.setArguments(arguments);

        return fragment;
    }
}
