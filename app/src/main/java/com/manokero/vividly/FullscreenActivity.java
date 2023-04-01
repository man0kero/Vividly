package com.manokero.vividly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.manokero.vividly.databinding.ActivityFullscreenBinding;

public class FullscreenActivity extends AppCompatActivity {
    private ActivityFullscreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fullscreen);

        binding.fullInnerToolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });

        Glide.with(this)
                .load(getIntent().getStringExtra("image"))
                .into(binding.zoomageView);
    }
}