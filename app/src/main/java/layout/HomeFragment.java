package layout;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lucas.juridex_v13.MainActivity;
import com.example.lucas.juridex_v13.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lucas on 02/10/2017.
 */

public class HomeFragment extends BaseFragment {


    Button btnClickMe;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        if (args != null) {
            fragCount = args.getInt(ARGS_INSTANCE);
        }

      //  View view = inflater.inflate(R.layout.fragment_home, container, false);

     //   ButterKnife.bind(this, view);

      //  ( (MainActivity)getActivity()).updateToolbarTitle("Home");




        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}