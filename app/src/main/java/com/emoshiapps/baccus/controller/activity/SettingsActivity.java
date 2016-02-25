package com.emoshiapps.baccus.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.emoshiapps.baccus.R;
import com.emoshiapps.baccus.controller.fragment.SettingsFragment;

public class SettingsActivity extends FragmentContainerActivity{

    public  static final  String EXTRA_IMAGE_SCALE_TYPE = "com.emoshiapps.baccus.controller.activity.SettingsActivity.EXTRA_IMAGE_SCALE_TYPE";


    @Override
    protected Fragment createFragment() {
        Bundle arguments = new Bundle();
        arguments.putSerializable(SettingsFragment.ARG_IMAGE_SCALE_TYPE,getIntent().getSerializableExtra(EXTRA_IMAGE_SCALE_TYPE));
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(arguments);

        return fragment;
    }
}
