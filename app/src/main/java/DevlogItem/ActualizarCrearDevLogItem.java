package DevlogItem;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.plataformafinallab3.R;

import java.io.ByteArrayOutputStream;

import models.DevLogItem;
import request.ApiClient;


public class ActualizarCrearDevLogItem extends Fragment {

private View root;

private ApiClient ap;

private DevlogItemViewModel vm;

private EditText titulo,texto;

private ImageView iv;

private int idSeleccionado;

private Button btnDone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        vm = ViewModelProviders.of(this).get(DevlogItemViewModel.class);

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_actualizar_crear_dev_log_item, container, false);

        elementosVista();

        if(getArguments() != null)
        {
            idSeleccionado = getArguments().getInt("id");

            vm.getdevlogitemseleccionadoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<DevLogItem>() {
                @Override
                public void onChanged(DevLogItem devLogItem)
                {
                    setearElementos(devLogItem);
                }
            });

            vm.traerDevlogsitemSeleccionado(getContext(),idSeleccionado);

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ///-------------------
                }
            });

            btnDone.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Bitmap bmp = ((BitmapDrawable)iv.getDrawable()).getBitmap();

                    String bmp64 =  vm.imgto64(bmp);

                    vm.actualizarDevlogsitemSeleccionado(getContext(),idSeleccionado,texto,titulo,bmp64);
                }
            });

            vm.getdevlogitemactualizadoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<DevLogItem>() {
                @Override
                public void onChanged(DevLogItem devLogItem)
                {
                    Bundle bundle = new Bundle();

                    bundle.putInt("id",devLogItem.getIdDevlog());

                    Navigation.findNavController(root).navigate(R.id.devlogItem,bundle);
                }
            });

        }


        return  root;
    }

    public void elementosVista()
    {
        titulo = root.findViewById(R.id.etTituloCrearActualizarDLITEM);

        texto = root.findViewById(R.id.tvTextoCrearActualizarDLITEM);

        iv = root.findViewById(R.id.ivCrearActualizarDLITEM);

        btnDone = root.findViewById(R.id.btnActualizarCrearDLITEM);
    }

    public void setearElementos(DevLogItem dli)
    {
        titulo.setText(dli.getTitulo());

        texto.setText(dli.getTexto());

        String pathCompleto = ap.getPATHRECURSOS() + dli.getMultimedia();

        Glide.with(getContext()).load(pathCompleto).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
    }


}
