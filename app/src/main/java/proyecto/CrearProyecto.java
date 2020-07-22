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


public class CrearProyecto extends Fragment
{

    private View root;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       root = inflater.inflate(R.layout.fragment_crear_proyecto, container, false);

        elementosVista();


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

        textoCompleto = root.findViewById(R.id.etTextoCompletoProyectoMain);

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
                Toast.makeText(getContext(), getActivity().getCurrentFocus().toString(), Toast.LENGTH_LONG).show();

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

            Glide.with(getActivity()).asBitmap().load(path).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivPortada);

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

            vvTrailer.setVideoURI(path);

            MediaController mediaController = new MediaController(getContext());

            mediaController.setAnchorView(vvTrailer);

            vvTrailer.setMediaController(mediaController);

        }

        if(requestCode == VID_REQUESTVIDEOTHUMB&& resultCode == getActivity().RESULT_OK && data != null)
        {
            Uri path = data.getData();

            vvThumbnail.setVideoURI(path);

            MediaController mediaController = new MediaController(getContext());

            mediaController.setAnchorView(vvThumbnail);

            vvThumbnail.setMediaController(mediaController);

        }

    }
}
