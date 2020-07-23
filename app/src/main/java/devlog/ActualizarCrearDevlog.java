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
import android.widget.Toast;

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

            if(getArguments().getString("accion") == null)
            {

                vm.traerDevlogSeleccionado(getContext(),id);
            }

            vm.getDevlogseleccionadoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<DevLog>() {
                @Override
                public void onChanged(DevLog devLog) {
                    titulo.setText(devLog.getTitulo());

                    resumen.setText(devLog.getResumen());

                    idDevlogaActualizar = devLog.getIdDevlog();

                }
            });


            vm.getDevlogactualizadoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<DevLog>() {
                @Override
                public void onChanged(DevLog devLog)
                {
                    Bundle bundle = new Bundle();

                    bundle.putInt("id", devLog.getIdProyecto());

                    Navigation.findNavController(root).navigate(R.id.devLogMain,bundle);

                }
            });

            vm.getDevlogcreadoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<DevLog>() {
                @Override
                public void onChanged(DevLog devLog)
                {
                    Bundle bundle = new Bundle();

                    bundle.putInt("id", devLog.getIdProyecto());

                    Toast.makeText(getContext(), "DevLog Creado", Toast.LENGTH_LONG).show();

                    Navigation.findNavController(root).navigate(R.id.devLogMain,bundle);
                }
            });

            btnActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(getArguments().getString("accion") == null)
                    {
                        vm.actualizarDevlog(titulo,resumen,idDevlogaActualizar,getContext());
                    }
                    else
                    {
                        vm.crearDevlog(titulo,resumen,id,getContext());
                    }
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
