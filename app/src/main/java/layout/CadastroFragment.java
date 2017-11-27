package layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lucas.juridex_v13.MainActivity;
import com.example.lucas.juridex_v13.R;

/**
 * Created by comae on 24/11/2017.
 */

public class CadastroFragment extends Fragment {

    private Button btnVoltar, btnCadastrar;


    public CadastroFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro, container,false);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnVoltar = (Button) view.findViewById(R.id.btnvoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getFragmentManager().popBackStack();
            }
        });


        btnCadastrar = (Button) view.findViewById(R.id.btncadastrar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
