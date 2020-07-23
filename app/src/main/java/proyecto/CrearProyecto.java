package proyecto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.plataformafinallab3.R;

import java.util.ArrayList;
import java.util.List;

import models.Proyecto;


public class CrearProyecto extends Fragment
{

    private View root;

    private ProyectoViewModel vm;

    private VideoView vvTrailer, vvThumbnail;

    private Button crearProyecto,btnAgregarFotoCarousel1,btnAgregarFotoCarousel2,btnAgregarFotoCarousel3,btnAgregarPortada;

    private EditText titulo,resumen,textoCompleto,categoria,plataforma,status;

    private ImageView ivPortada,ivCarousel1, ivCarousel2,ivCarousel3;

    private static  final int  IMG_REQUESTPORTADA = 777;

    private static  final int  IMG_REQUESTCAROUSEL1 = 888;

    private static  final int  IMG_REQUESTCAROUSEL2 = 999;

    private static final int IMG_REQUESTCAROUSEL3 = 555;

    private static final int VID_REQUESTVIDEOTRAILER = 111;

    private static final int VID_REQUESTVIDEOTHUMB = 222;

    private Uri auxVideoTrailer, auxVideoThumbnail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        vm = ViewModelProviders.of(this).get(ProyectoViewModel.class);

       root = inflater.inflate(R.layout.fragment_crear_proyecto, container, false);

        elementosVista();

        vm.getProyectocreadoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Proyecto>()
        {
            @Override
            public void onChanged(Proyecto proyecto)
            {
                Toast.makeText(getContext(), "Proyecto Creado Correctamente", Toast.LENGTH_LONG).show();

                Navigation.findNavController(root).navigate(R.id.nav_home);
            }
        });
       return root;

    }

    public void elementosVista()
    {
        vvTrailer = root.findViewById(R.id.vvTrailerCrearProyecto);

        ivPortada = root.findViewById(R.id.ivPortadaCrearProyecto);

        ivCarousel1 = root.findViewById(R.id.ivImagenCrearProyecto1);

        ivCarousel2 = root.findViewById(R.id.ivImagenCrearProyecto2);

        ivCarousel3 = root.findViewById(R.id.ivImagenCrearProyecto3);


        crearProyecto = root.findViewById(R.id.btnCrearProyecto);

        vvThumbnail = root.findViewById(R.id.vvVideoThubnailCrearProyecto);

        vvTrailer.setBackgroundResource(R.color.transparentetotal);

        vvThumbnail.setBackgroundResource(R.color.transparentetotal);

        titulo = root.findViewById(R.id.etTituloCrearProyecto);

        titulo.requestFocus();

        resumen = root.findViewById(R.id.etResumenCrearProyecto);

        textoCompleto = root.findViewById(R.id.etTextoCompletoCrearProyecto);

        categoria = root.findViewById(R.id.etGeneroCrearProyecto);

        status = root.findViewById(R.id.etStatusCrearProyecto);

        plataforma = root.findViewById(R.id.etPlataformaCrearProyecto);

        btnAgregarFotoCarousel1 = root.findViewById(R.id.btnImagenCrearProyecto1);

        btnAgregarFotoCarousel2 = root.findViewById(R.id.btnImagenCrearProyecto2);

        btnAgregarFotoCarousel3 = root.findViewById(R.id.btnImagenCrearProyecto3);

        btnAgregarPortada = root.findViewById(R.id.btnPortadaCrearProyecto);

        btnAgregarPortada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();

                i.setType("image/*");

                i.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(i,IMG_REQUESTPORTADA);
            }
        });

        btnAgregarFotoCarousel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();

                i.setType("image/*");

                i.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(i,IMG_REQUESTCAROUSEL1);
            }
        });

        btnAgregarFotoCarousel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();

                i.setType("image/*");

                i.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(i,IMG_REQUESTCAROUSEL2);
            }
        });

        btnAgregarFotoCarousel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();

                i.setType("image/*");

                i.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(i,IMG_REQUESTCAROUSEL3);
            }
        });

        crearProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //-----Carousel------//

                List<String> carouselBitmapTo64 = new ArrayList<>();

                Bitmap bitmap1 = ((BitmapDrawable)ivCarousel1.getDrawable()).getBitmap();

                String img641 = vm.imgto64(bitmap1);

                carouselBitmapTo64.add(img641);

                Bitmap bitmap2 = ((BitmapDrawable)ivCarousel2.getDrawable()).getBitmap();

                String img642 = vm.imgto64(bitmap2);

                carouselBitmapTo64.add(img642);

                Bitmap bitmap3 = ((BitmapDrawable)ivCarousel3.getDrawable()).getBitmap();

                String img643 = vm.imgto64(bitmap3);

                carouselBitmapTo64.add(img643);

                //-- Portada--//

                Bitmap bitmapPortada = ((BitmapDrawable)ivPortada.getDrawable()).getBitmap();

                String portadaImg64 = vm.imgto64(bitmapPortada);

                //-- Video Trailer --//

                String videoTrailer64 = vm.videoto64(auxVideoTrailer,getContext());

                // -- Video thumbnail --//

                String videoCorto64 = vm.videoto64(auxVideoThumbnail,getContext());

                vm.crearProyecto(0,titulo,categoria,status,plataforma,portadaImg64,videoTrailer64,
                        0,resumen,textoCompleto,videoCorto64,carouselBitmapTo64,getContext());


            }
        });

        vvTrailer.setOnClickListener(new View.OnClickListener() {
            private int touchs = 0;
            @Override
            public void onClick(View v) {

                touchs ++;

                if( touchs > 2)
                {
                Intent i = new Intent();

                i.setType("video/*");

                i.setAction(Intent.ACTION_GET_CONTENT);

                touchs = 0;

                startActivityForResult(i,VID_REQUESTVIDEOTRAILER);

                }
            }
        });

        vvThumbnail.setOnClickListener(new View.OnClickListener() {
            private int touchs = 0;

            @Override
            public void onClick(View v)
            {
                touchs ++;

                if( touchs > 2)
                {
                    Intent i = new Intent();

                    i.setType("video/*");

                    i.setAction(Intent.ACTION_GET_CONTENT);

                    touchs = 0;

                    startActivityForResult(i,VID_REQUESTVIDEOTHUMB);

                }
            }
        });
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUESTPORTADA && resultCode == getActivity().RESULT_OK && data != null)
        {
            Uri path = data.getData();

            Glide.with(getActivity()).load(path).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivPortada);

        }
        if(requestCode == IMG_REQUESTCAROUSEL1 && resultCode == getActivity().RESULT_OK && data != null)
        {
            Uri path = data.getData();

            Glide.with(getContext()).load(path).into(ivCarousel1);

        }
        if(requestCode == IMG_REQUESTCAROUSEL2 && resultCode == getActivity().RESULT_OK && data != null)
        {
            Uri path = data.getData();

            Glide.with(getContext()).load(path).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivCarousel2);

        }

        if(requestCode == IMG_REQUESTCAROUSEL3 && resultCode == getActivity().RESULT_OK && data != null)
        {
            Uri path = data.getData();

            Glide.with(getContext()).load(path).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivCarousel3);

        }

        if(requestCode == VID_REQUESTVIDEOTRAILER && resultCode == getActivity().RESULT_OK && data != null)
        {
            Uri path = data.getData();

            auxVideoTrailer = path;

            vvTrailer.setVideoURI(path);

            MediaController mediaController = new MediaController(getContext());

            mediaController.setAnchorView(vvTrailer);

            vvTrailer.setMediaController(mediaController);

        }

        if(requestCode == VID_REQUESTVIDEOTHUMB && resultCode == getActivity().RESULT_OK && data != null)
        {
            Uri path = data.getData();

            auxVideoThumbnail = path;

            vvThumbnail.setVideoURI(path);

            MediaController mediaController = new MediaController(getContext());

            mediaController.setAnchorView(vvThumbnail);

            vvThumbnail.setMediaController(mediaController);

        }

    }
}
