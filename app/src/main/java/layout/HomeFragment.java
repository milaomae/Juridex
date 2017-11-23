package layout;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lucas.juridex_v13.BaseActivity;
import com.example.lucas.juridex_v13.InicioActivity;
import com.example.lucas.juridex_v13.MainActivity;
import com.example.lucas.juridex_v13.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lucas on 02/10/2017.
 */

public class HomeFragment extends Fragment {

    //@BindView(R.id.facilUm)
    //Button btnFacilUm;
    ImageButton btnfacilUm, btnmedioUm, btndificilUm;

    int fragCount;


    public static HomeFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_niveis, container, false);



        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        if (args != null) {
            fragCount = args.getInt(ARGS_INSTANCE);
        }

        //( (InicioActivity)getActivity()).updateToolbarTitle("Niveis");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnfacilUm = (ImageButton) view.findViewById(R.id.btnfacilUm);
        btnfacilUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mFragmentNavigation != null) {
                    mFragmentNavigation.pushFragment(HomeFragment.newInstance(fragCount + 1));

                }
            }
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}