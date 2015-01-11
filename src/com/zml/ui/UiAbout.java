package com.zml.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zml.R;
import com.zml.base.BaseUi;

public class UiAbout extends BaseUi {
    String version = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.ui_about);

        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView version_val = ((TextView)findViewById(R.id.version_val));
        
        try {
            PackageInfo pinfo = getPackageManager().getPackageInfo("com.zml", PackageManager.GET_CONFIGURATIONS);
            version = pinfo.versionName;
            version_val.setText("V"+version);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initTitle() {
        this.setTitleText(R.string.about);
        this.setLogo(R.drawable.button_selector_back);
        this.setTitleLayoutBackground(R.drawable.top_bg);
        this.setTitleTextMargin(10, 0, 0, 0);
        this.setLogoLine(R.drawable.line);
    }
}
