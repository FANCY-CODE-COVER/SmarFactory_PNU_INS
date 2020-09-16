/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.glass.cardsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.android.glass.cardsample.fragments.BaseFragment;
import com.example.android.glass.cardsample.fragments.ColumnLayoutFragment;
import com.example.android.glass.cardsample.fragments.MainLayoutFragment;
import com.example.android.glass.cardsample.menu.EquipQRScan;
import com.example.android.glass.cardsample.menu.VoiceRecognitionActivity;
import com.example.glass.ui.GlassGestureDetector.Gesture;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.glass.cardsample.LoginActivity.ACCESS_TOKEN;
import static com.example.android.glass.cardsample.LoginActivity.QR_SCAN_RESULT_2;
import static com.example.android.glass.cardsample.menu.EquipQRScan.EQUIPMENT_RESULT;

/**
 * Main activity of the application. It provides viewPager to move between fragments.
 */
public class MainActivity extends BaseActivity {

    private List<BaseFragment> fragments = new ArrayList<>();
    private ViewPager viewPager;
    private static final int REQUEST_CODE = 105;
    private String userID = "";
    private String access_token = "token";
    public static final String base_url = "http://192.168.43.113:8080/SmartFactoryServer_MVC/";
    //"http://192.168.43.113:8080/SmartFactoryServer_MVC/";"http://192.168.43.160:8082/smartfactory/"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_layout);

        startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_CODE);

        final ScreenSlidePagerAdapter screenSlidePagerAdapter = new ScreenSlidePagerAdapter(
            getSupportFragmentManager());
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(screenSlidePagerAdapter);
        fragments.add(MainLayoutFragment
            .newInstance(getString(R.string.equip_scan_qr), null,
                null, null));
        fragments.add(MainLayoutFragment
            .newInstance(getString(R.string.send_message), null,
                null, null));
        fragments.add(MainLayoutFragment
                .newInstance(getString(R.string.log_out),
                        null, null,null));
        screenSlidePagerAdapter.notifyDataSetChanged();

        final TabLayout tabLayout = findViewById(R.id.page_indicator);
        tabLayout.setupWithViewPager(viewPager, true);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                userID = data.getStringExtra(QR_SCAN_RESULT_2);
                access_token = data.getStringExtra(ACCESS_TOKEN);
                Log.d("qrresult", userID);
                Log.d("qrresult ac", access_token);
            }
            else {
                Log.d("qrfailed", "qrfailed");
            }
        }
    }
    @Override
    public boolean onGesture(Gesture gesture) {
        switch (gesture) {
            case TAP:
                int page_num = viewPager.getCurrentItem();
                switch (page_num) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), EquipQRScan.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(getApplicationContext(), VoiceRecognitionActivity.class);
                        intent2.putExtra(ACCESS_TOKEN, access_token);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent3);
                        break;
                    default:
                        break;
                }

                //fragments.get(viewPager.getCurrentItem()).onSingleTapUp();
                //return true;
            default:
                return super.onGesture(gesture);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


}
