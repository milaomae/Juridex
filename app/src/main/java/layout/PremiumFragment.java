package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lucas.juridex_v13.InicioActivity;
import com.example.lucas.juridex_v13.MainActivity;
import com.example.lucas.juridex_v13.R;

import butterknife.ButterKnife;


public class PremiumFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_premium, container, false);

        ButterKnife.bind(this, view);

      //  ((InicioActivity)getActivity()).updateToolbarTitle("Premium");


        return view;
    }
}
