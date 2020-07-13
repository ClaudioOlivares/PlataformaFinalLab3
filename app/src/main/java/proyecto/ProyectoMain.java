package proyecto;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.plataformafinallab3.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import models.Proyecto;
import request.ApiClient;


public class ProyectoMain extends Fragment {

    private ProyectoViewModel vm;
    private int id;
    private View root;
    private ImageView iv;
    private String urlImagen;
    private VideoView vv;
    private String urlVideo;
    private EditText textoCompleto,titulo,textoResumen;
    private ApiClient ap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        vm = ViewModelProviders.of(this).get(ProyectoViewModel.class);

        id= getArguments().getInt("id");

        root = inflater.inflate(R.layout.fragment_proyecto_main, container, false);

        elementosVista();

        vm.traerProyecto(getContext(),id);

        vm.getProyectoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Proyecto>()
        {
            @Override
            public void onChanged(Proyecto proyecto)
            {
                textoCompleto.setText(proyecto.getTextoCompleto());

                titulo.setText(proyecto.getTitulo());

                textoResumen.setText(proyecto.getTextoResumen());

                generadorImagen(proyecto.getPortada());

                generadorVideo(proyecto.getVideoTrailer());

            }
        });

        return  root;
    }

    public void elementosVista()
    {
        textoCompleto = root.findViewById(R.id.etTextoCompletoProyectoMain);

        textoResumen = root.findViewById(R.id.etTextoResumenProyectoMain);

        titulo = root.findViewById(R.id.etTituloProyectoMain);

        vv = root.findViewById(R.id.vvProyectoMain);

        iv = root.findViewById(R.id.ivPortadaProyectoMain);
    }

    public void generadorImagen(String url)
    {
        urlImagen = ap.getPATHRECURSOS() + url;

        Picasso.with(getContext()).load(urlImagen).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
                iv.setBackground(ob);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });


    }

    public void generadorVideo(String url)
    {
        urlVideo = ap.getPATHRECURSOS() + url;

        vv.setVideoURI(Uri.parse(urlVideo));

        MediaController mediaController = new MediaController(getContext());

        vv.setMediaController(mediaController);

        mediaController.setAnchorView(vv);
    }
}
