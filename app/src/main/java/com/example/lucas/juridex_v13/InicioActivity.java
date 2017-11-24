package com.example.lucas.juridex_v13;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import layout.BaseFragment;
import layout.HomeFragment;
import layout.PremiumFragment;
import layout.ProfileFragment;
import utils.FragmentHistory;
import utils.Utils;
import views.FragNavController;

/**
 * Created by Lucas on 04/11/2017.
 */

public class InicioActivity extends AppCompatActivity{
    @BindView(R.id.content_frame)
    FrameLayout contentFrame;

  //  @BindView(R.id.toolbar)
  //  Toolbar toolbar;

    private static final String TAG = "Inicio activity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Starting.");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.content_frame);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.bottom_tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "home", R.drawable.ic_action_home);
        adapter.addFragment(new ProfileFragment(), "Profile", R.drawable.ic_action_perfil);
        adapter.addFragment(new PremiumFragment(), "Premium", R.drawable.ic_action_premium);
        viewPager.setAdapter(adapter);
    }


}

