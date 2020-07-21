package devlog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.plataformafinallab3.R;

import models.DevLog;


public class ActualizarCrearDevlog extends Fragment {

private View root;
private DevLogViewModel vm;
private EditText titulo,resumen;
private Button btnActualizar;
private int id;
private int idDevlogaActualizar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        vm = ViewModelProviders.of(this).get(DevLogViewModel.class);
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_actualizar_devlog, container, false);

        elementosVista();


        if(getArguments() != null)
        {
            id = getArguments().getInt("id");

            vm.getDevlogseleccionadoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<DevLog>() {
                @Override
                public void onChanged(DevLog devLog)
                {
                    titulo.setText(devLog.getTitulo());

                    resumen.setText(devLog.getResumen());

                    idDevlogaActualizar = devLog.getIdDevlog();
                }
            });

            vm.traerDevlogSeleccionado(getContext(),id);

            vm.getDevlogactualizadoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<DevLog>() {
                @Override
                public void onChanged(DevLog devLog)
                {
                    Bundle bundle = new Bundle();

                    bundle.putInt("id", devLog.getIdProyecto());

                    Navigation.findNavController(root).navigate(R.id.devLogMain,bundle);

                }
            });

            btnActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    vm.actualizarDevlog(titulo,resumen,idDevlogaActualizar,getContext());
                }
            });


        }



        return  root;
    }

    public void elementosVista()
    {
        titulo = root.findViewById(R.id.etTituloDevlogExtra);

        resumen = root.findViewById(R.id.etResumenDevLogExtra);

        btnActualizar = root.findViewById(R.id.btnActualizarDevLogEXTRA);
    }
}
