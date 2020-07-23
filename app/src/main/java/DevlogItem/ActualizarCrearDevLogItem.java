package DevlogItem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
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

private static  final int  IMG_REQUEST = 777;



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
            if(getArguments().getString("accion") == null )
            {
                vm.traerDevlogsitemSeleccionado(getContext(), idSeleccionado);
            }

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    Intent i = new Intent();

                    i.setType("image/*");

                    i.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(i,IMG_REQUEST);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUEST && resultCode == getActivity().RESULT_OK && data != null)
        {

            Uri path = data.getData();

            Glide.with(getContext()).load(path).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);

        }


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
