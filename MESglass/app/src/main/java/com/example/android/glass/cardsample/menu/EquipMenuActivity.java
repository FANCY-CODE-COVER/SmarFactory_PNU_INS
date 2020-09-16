package com.example.android.glass.cardsample.menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.android.glass.cardsample.BaseActivity;
import com.example.android.glass.cardsample.R;
import com.example.android.glass.cardsample.fragments.BaseFragment;
import com.example.android.glass.cardsample.fragments.MainLayoutFragment;
import com.example.glass.ui.GlassGestureDetector;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.example.android.glass.cardsample.menu.EquipQRScan.EQUIPMENT_RESULT;

public class EquipMenuActivity extends BaseActivity {
    private List<BaseFragment> fragments = new ArrayList<>();
    private ViewPager viewPager;
    private String qrData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equip_view_pager_layout);
        qrData = getIntent().getStringExtra(EQUIPMENT_RESULT);
        Log.d("machine name in EquipMenu", qrData);
        final EquipMenuActivity.ScreenSlidePagerAdapter screenSlidePagerAdapter = new EquipMenuActivity.ScreenSlidePagerAdapter(
                getSupportFragmentManager());
        viewPager = findViewById(R.id.equipViewPager);
        viewPager.setAdapter(screenSlidePagerAdapter);
        fragments.add(MainLayoutFragment
                .newInstance(getString(R.string.equip_info), getString(R.string.footnote_sample),
                        getString(R.string.timestamp_sample), null));
        fragments.add(MainLayoutFragment
                .newInstance(getString(R.string.equip_frequest), getString(R.string.empty_string),
                        getString(R.string.empty_string), null));
        fragments.add(MainLayoutFragment
                .newInstance(getString(R.string.equip_inspect),
                        getString(R.string.footnote_sample),getString(R.string.empty_string), null));
        fragments.add(MainLayoutFragment
                .newInstance(getString(R.string.equip_repair),
                        getString(R.string.footnote_sample),getString(R.string.empty_string), null));
        screenSlidePagerAdapter.notifyDataSetChanged();

        final TabLayout tabLayout = findViewById(R.id.equip_page_indicator);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    @Override
    public boolean onGesture(GlassGestureDetector.Gesture gesture) {
        switch (gesture) {
            case TAP:
                int page_num = viewPager.getCurrentItem();
                switch (page_num) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), EquipInfoActivity.class);
                        intent.putExtra(EQUIPMENT_RESULT, qrData);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getApplicationContext(), EquipFRequestActivity.class);
                        intent1.putExtra(EQUIPMENT_RESULT, qrData);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getApplicationContext(), EquipInspectActivity.class);
                        intent2.putExtra(EQUIPMENT_RESULT, qrData);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(getApplicationContext(), EquipRepairActivity.class);
                        intent3.putExtra(EQUIPMENT_RESULT, qrData);
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
