package com.example.lucas.juridex_v13;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {


    Button btnEntrar;
    Intent principal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                principal = new Intent(MainActivity.this, InicioActivity.class);
                startActivity(principal);
            }
        });

    }/*

    public class MainActivity extends BaseActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {

        @BindView(R.id.content_frame)
        FrameLayout contentFrame;

        @BindView(R.id.toolbar)
        Toolbar toolbar;

        private int[] mTabIconsSelected = {
                R.drawable.ic_action_home,
                R.drawable.ic_action_perfil,
                R.drawable.ic_action_premium
                };


        @BindArray(R.array.tab_name)
        String[] TABS;

        @BindView(R.id.bottom_tab_layout)
        TabLayout bottomTabLayout;

        private FragNavController mNavController;

        private FragmentHistory fragmentHistory;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            ButterKnife.bind(this);


            initToolbar();

            initTab();

            fragmentHistory = new FragmentHistory();


            mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.content_frame)
                    .transactionListener(this)
                    .rootFragmentListener(this, TABS.length)
                    .build();


            switchTab(0);

            bottomTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    fragmentHistory.push(tab.getPosition());

                    switchTab(tab.getPosition());


                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                    mNavController.clearStack();

                    switchTab(tab.getPosition());


                }
            });

        }

        private void initToolbar() {
            if(toolbar != null) {
                setSupportActionBar(toolbar);
            }

        }

        private void initTab() {
            if (bottomTabLayout != null) {
                for (int i = 0; i < TABS.length; i++) {
                    bottomTabLayout.addTab(bottomTabLayout.newTab());
                    TabLayout.Tab tab = bottomTabLayout.getTabAt(i);
                    if (tab != null)
                        tab.setCustomView(getTabView(i));
                }
            }
        }


        private View getTabView(int position) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_item_bottom, null);
            ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
            icon.setImageDrawable(Utils.setDrawableSelector(MainActivity.this, mTabIconsSelected[position], mTabIconsSelected[position]));
            return view;
        }


        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onStop() {

            super.onStop();
        }


        private void switchTab(int position) {
            mNavController.switchTab(position);


//        updateToolbarTitle(position);
        }


        @Override
        protected void onResume() {
            super.onResume();
        }


        @Override
        protected void onPause() {
            super.onPause();
        }


}*/
}


