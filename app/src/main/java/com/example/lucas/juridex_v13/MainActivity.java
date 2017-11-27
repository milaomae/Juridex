package com.example.lucas.juridex_v13;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;


import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lucas.juridex_v13.Game.GameActivity;

import layout.CadastroFragment;

public class MainActivity extends AppCompatActivity {


    Button btnEntrar, btnCadastrar;
    Intent principal, cadastro;
    TextView txtTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtTitulo = (TextView)findViewById(R.id.txtTitulo);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/BerkshireSwash-Regular.ttf");

        txtTitulo.setTypeface(custom_font);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                principal = new Intent(MainActivity.this, GameActivity.class);
                startActivity(principal);
            }
        });

        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                Fragment cadastro = new CadastroFragment();
                // essa linha é responsável por adicionar o fragment ao stack
                transaction.addToBackStack(null);
                transaction.replace(R.id.principal_container, cadastro);
                transaction.commit();



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


