package com.manokero.vividly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

public class FullscreenActivity extends AppCompatActivity {
    ImageView imageView;
    MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        toolbar = findViewById(R.id.full_inner_toolbar);
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });

        imageView = findViewById(R.id.myZoomageView);
        Glide.with(this)
                .load(getIntent().getStringExtra("image"))
                .into(imageView);
    }
}