package proyecto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.plataformafinallab3.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import models.ImagenProyecto;
import models.Proyecto;
import request.ApiClient;


public class ProyectoMain extends Fragment {

    private ProyectoViewModel vm;
    private int id;
    private View root;
    private ImageView iv;
    private String urlImagen;
    private VideoView vv, vvTrailer;
    private String urlVideo;
    private List<String> urlimgsProyecto = new ArrayList<>();
    private CarouselView carouselView;
    private EditText textoCompleto, titulo, textoResumen,creador,status,genero,plataforma;
    private ApiClient ap;
    private ImageButton btnAceptarActualizar, btnCancelarActualizacion;
    private Button  btnDevlogs, btnActualizar;



    //----------------------------------------ATRIBUTOS ACTUALIZACION-------------------------------------//
    private boolean actualizando = false;
    private static  final int  IMG_REQUEST = 777;
    private static final int IMG_REQUESTTITULO = 888;
    private static final int VID_REQUESTVIDEO = 555;
    private static final int VID_REQUESTTRAILER = 666;
    private int imagencarouselclickeada;


    //-------- AUXILIARES ACTUALIZACION -----

    private String auxTitulo, auxResumen, auxTextoCompleto , auxUriVideo, auxUriVideoTrailer, auxGenero, auxStatus, auxPlataforma;
    private Bitmap auxIvPortada;
    private List<Bitmap> auxCarouselBitMaps = new ArrayList<>();
    private List<String> auxCarouselStrings = new ArrayList<>();
    private List<Integer> idsImages = new ArrayList<>();
    private boolean resetcarousel;
    private Uri actualVideoTrailerUri, actualVideoCortoUri;

    public ProyectoMain() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vm = ViewModelProviders.of(this).get(ProyectoViewModel.class);

        id = getArguments().getInt("id");

        root = inflater.inflate(R.layout.fragment_proyecto_main, container, false);


        elementosVista();



        //Toast.makeText(getContext(), getActivity().getCurrentFocus().toString(), Toast.LENGTH_LONG).show();

        btnDevlogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putInt("id", id);

                Navigation.findNavController(v).navigate(R.id.devLogMain, bundle);
            }
        });

        vm.traerProyecto(getContext(), id);

        vm.getProyectoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<ImagenProyecto>>() {
            @Override
            public void onChanged(final List<ImagenProyecto> imagenProyectos) {
                textoCompleto.setText(imagenProyectos.get(0).getProyecto().getTextoCompleto());

                titulo.setText(imagenProyectos.get(0).getProyecto().getTitulo());

                textoResumen.setText(imagenProyectos.get(0).getProyecto().getTextoResumen());

                creador.setText(imagenProyectos.get(0).getProyecto().getUser().getNick());

                genero.setText(imagenProyectos.get(0).getProyecto().getGenero());

                status.setText(imagenProyectos.get(0).getProyecto().getStatus());

                plataforma.setText(imagenProyectos.get(0).getProyecto().getPlataforma());

                generadorImagen(imagenProyectos.get(0).getProyecto().getPortada());

                generadorVideo(imagenProyectos.get(0).getProyecto().getVideoTrailer());

                auxGenero = genero.getText().toString();

                auxStatus = status.getText().toString();

                auxPlataforma = plataforma.getText().toString();

                auxTitulo = titulo.getText().toString();

                auxResumen = textoResumen.getText().toString();

                auxTextoCompleto = textoCompleto.getText().toString();

                auxUriVideoTrailer = imagenProyectos.get(0).getProyecto().getVideo();

                for (ImagenProyecto item : imagenProyectos) {
                    String pathCompleto = ap.getPATHRECURSOS() + item.getUrl();

                    idsImages.add(item.getIdImagenProyecto());

                    urlimgsProyecto.add(pathCompleto);

                    auxCarouselStrings.add(pathCompleto);

                }

                carouselView.setImageListener(imageListener);

                carouselView.setPageCount(urlimgsProyecto.size());


            }
        });

        //-------------------------------------------ACTUALIZAR------------------------------------------------------//


        vm.getCheckeoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s == "true") {
                    btnActualizar.setVisibility(View.VISIBLE);
                }
            }
        });

        vm.checkearSiEsProyectoDelUsuario(getContext(), id);

        clicklistenersActualizar();

        vm.getProyectoactualizadoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Proyecto>()
        {
            @Override
            public void onChanged(Proyecto proyecto)
            {

                Toast.makeText(getContext(), "Proyecto Actualizado", Toast.LENGTH_SHORT).show();

                Navigation.findNavController(root).navigate(R.id.nav_home);

            }
        });

        return root;
    }

  //----------------------------------------------------------------------------------------------------------------------//



    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, final ImageView imageView) {


            if(auxCarouselBitMaps.size() == 3)
            {
                auxCarouselBitMaps.clear();
            }

            if(!resetcarousel)
            {
                    Glide.with(getActivity()).asBitmap().load(urlimgsProyecto.get(position)).diskCacheStrategy(DiskCacheStrategy.NONE).into(new CustomTarget<Bitmap>()
                    {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageView.setImageBitmap(resource);

                            auxCarouselBitMaps.add(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
            });
            }
            else
            {
                Glide.with(getActivity()).asBitmap().load(auxCarouselStrings.get(position)).diskCacheStrategy(DiskCacheStrategy.NONE).into(new CustomTarget<Bitmap>()
                {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);

                        auxCarouselBitMaps.add(resource);

                        resetcarousel = false;
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });


            }
        }
    };

    public void elementosVista() {
        textoCompleto = root.findViewById(R.id.etTextoCompletoProyectoMain);

        textoResumen = root.findViewById(R.id.etTextoResumenProyectoMain);

        titulo = root.findViewById(R.id.etTituloProyectoMain);

        titulo.requestFocus();

        creador = root.findViewById(R.id.etCreadorProyectoMain);

        status = root.findViewById(R.id.etStatusProyectoMain);

        plataforma = root.findViewById(R.id.etPlataformaProyectoMain);

        genero = root.findViewById(R.id.etGeneroProyectoMain);

        vvTrailer = root.findViewById(R.id.vvTrailerMiProyecto);

        vv = root.findViewById(R.id.vvProyectoMain);

        iv = root.findViewById(R.id.ivPortadaProyectoMain);

        carouselView = root.findViewById(R.id.carouselView);

        btnDevlogs = root.findViewById(R.id.btnDevLogs);

        btnActualizar = root.findViewById(R.id.btnActualizarProyecto);

        btnAceptarActualizar = root.findViewById(R.id.btnAceptarActualizarMiProyecto);

        btnCancelarActualizacion = root.findViewById(R.id.btnCancelarActualizarMiProyecto);

        btnActualizar.setVisibility(root.INVISIBLE);

        btnAceptarActualizar.setVisibility(View.INVISIBLE);

        btnCancelarActualizacion.setVisibility(View.INVISIBLE);


        vvTrailer.setBackgroundResource(R.color.transparentetotal);

        vv.setBackgroundResource(R.color.transparentetotal);


    }


//-----------------------------------------------------PORTADA-------------------------------------------------------//


    public void generadorImagen(String url) {
        urlImagen = ap.getPATHRECURSOS() + url;

        Glide.with(getContext()).asBitmap().load(urlImagen).diskCacheStrategy(DiskCacheStrategy.NONE).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                BitmapDrawable ob = new BitmapDrawable(getResources(), resource);

                auxIvPortada = resource;

                iv.setBackground(ob);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });

    }
    //--------------------------------------------------------------------------------------------------------------//



//-------------------------------------------------VIDEO---------------------------------------------------------//
    public void generadorVideo(String url) {
        urlVideo = ap.getPATHRECURSOS() + url;

        auxUriVideo = urlVideo;

        vv.setVideoURI(Uri.parse(urlVideo));

        MediaController mediaController = new MediaController(getContext());

        mediaController.setAnchorView(vv);

        vv.setMediaController(mediaController);

    }

    public void generadorVideoCorto(String url)
    {

        urlVideo = ap.getPATHRECURSOS() + url;

        auxUriVideoTrailer = url;

        vvTrailer.setVideoURI(Uri.parse(urlVideo));

        MediaController mediaController = new MediaController(getContext());

        mediaController.setAnchorView(vvTrailer);

        vvTrailer.setMediaController(mediaController);

    }





//-----------------------------------------------------------------------------------------------------------//


//------------------------------------------------REsult------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUEST && resultCode == getActivity().RESULT_OK && data != null)
        {
             Uri path = data.getData();

            urlimgsProyecto.set(imagencarouselclickeada, path.toString());

            auxCarouselBitMaps.clear();

            carouselView.setImageListener(imageListener);

            carouselView.setPageCount(urlimgsProyecto.size());
        }

        if(requestCode == IMG_REQUESTTITULO && resultCode == getActivity().RESULT_OK && data != null)
        {
            Uri path = data.getData();

            Glide.with(getContext()).asBitmap().load(path).diskCacheStrategy(DiskCacheStrategy.NONE).into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    BitmapDrawable ob = new BitmapDrawable(getResources(), resource);
                    iv.setBackground(ob);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            });
        }
        if(requestCode == VID_REQUESTVIDEO && resultCode == getActivity().RESULT_OK && data != null)
        {
            Uri path = data.getData();

            actualVideoTrailerUri = path;

            vv.setVideoURI(Uri.parse(path.toString()));

            MediaController mediaController = new MediaController(getContext());

            vv.setMediaController(mediaController);

            mediaController.setAnchorView(vv);


        }

        if(requestCode == VID_REQUESTTRAILER && resultCode == getActivity().RESULT_OK && data != null)
        {
            Uri path = data.getData();

            actualVideoCortoUri = path;

            vvTrailer.setVideoURI(Uri.parse(path.toString()));

            MediaController mediaController = new MediaController(getContext());

            vvTrailer.setMediaController(mediaController);

            mediaController.setAnchorView(vv);



        }

    }
//------------------------------------------------------------------------------------------------------





    public void clicklistenersActualizar()
    {
        btnActualizar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                actualizando = true;

                btnAceptarActualizar.setVisibility(View.VISIBLE);

                btnCancelarActualizacion.setVisibility(View.VISIBLE);

                textoCompleto.setEnabled(true);

                textoResumen.setEnabled(true);

                titulo.setEnabled(true);

                status.setEnabled(true);

                genero.setEnabled(true);

                plataforma.setEnabled(true);

                textoCompleto.setBackgroundResource(R.color.transparenteColor);

                textoResumen.setBackgroundResource(R.color.transparenteColor);

                titulo.setBackgroundResource(R.color.transparenteColor);

                status.setBackgroundResource(R.color.transparenteColor);

                genero.setBackgroundResource(R.color.transparenteColor);

                plataforma.setBackgroundResource(R.color.transparenteColor);

            //    flvideoCorto.setVisibility(View.VISIBLE);

                vvTrailer.setVisibility(View.VISIBLE);

                generadorVideoCorto(auxUriVideoTrailer);

            }
        });

        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position)
            {
                if(actualizando)
                {
                    imagencarouselclickeada = position;

                    Intent i = new Intent();

                    i.setType("image/*");

                    i.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(i,IMG_REQUEST);
                }
            }
        });

        iv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(actualizando)
                {
                    Intent i = new Intent();

                    i.setType("image/*");

                    i.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(i, IMG_REQUESTTITULO);
                }
            }
        });

        vv.setOnClickListener(new View.OnClickListener() {

            private int touchs = 0;
            @Override
            public void onClick(View v)
            {
                touchs ++;
                if(actualizando && touchs > 2)
                {
                    Intent i = new Intent();

                    i.setType("video/*");

                    i.setAction(Intent.ACTION_GET_CONTENT);

                    touchs = 0;

                    startActivityForResult(i, VID_REQUESTVIDEO);
                }
            }
        });

        vvTrailer.setOnClickListener(new View.OnClickListener() {
            private int touchs = 0;

            @Override
            public void onClick(View v)
            {
                touchs ++;
                if(actualizando && touchs > 2)
                {
                    Intent i = new Intent();

                    i.setType("video/*");

                    i.setAction(Intent.ACTION_GET_CONTENT);

                    touchs = 0;

                    startActivityForResult(i, VID_REQUESTTRAILER);
                }
            }
        });



        btnCancelarActualizacion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //------------------------- RESET EDIT TEXTS----------------------------//
                actualizando = false;

                btnAceptarActualizar.setVisibility(View.INVISIBLE);

                btnCancelarActualizacion.setVisibility(View.INVISIBLE);

                textoCompleto.setEnabled(false);

                textoResumen.setEnabled(false);

                titulo.setEnabled(false);

                creador.setEnabled(false);

                genero.setEnabled(false);

                plataforma.setEnabled(false);

                status.setEnabled(false);

                titulo.setText(auxTitulo);

                textoResumen.setText(auxResumen);

                textoCompleto.setText(auxTextoCompleto);

                genero.setText(auxGenero);

                plataforma.setText(auxPlataforma);

                status.setText(auxStatus);

                genero.setBackgroundResource(R.color.EditTextNOBG);

                plataforma.setBackgroundResource(R.color.EditTextNOBG);

                status.setBackgroundResource(R.color.EditTextNOBG);

                textoCompleto.setBackgroundResource(R.drawable.rectangle_shape);

                textoResumen.setBackgroundResource(R.drawable.rectangle_shape);

                titulo.setBackgroundResource(R.color.EditTextNOBG);

                vvTrailer.setVisibility(View.GONE);


         //       flvideoCorto.setVisibility(View.GONE);


                //----------------------------Reset Portada----------------------------------------//


                BitmapDrawable ob = new BitmapDrawable(getResources(), auxIvPortada);

                iv.setBackground(ob);


              //----------------------------- Reset Carousel---------------------------------------//


                resetcarousel = true;

                carouselView.setImageListener(imageListener);

                carouselView.setPageCount(urlimgsProyecto.size());


                //----------------------------Reset VideoView------------------------------------//


                vv.setVideoURI(Uri.parse(auxUriVideo));

                MediaController mediaController = new MediaController(getContext());

                vv.setMediaController(mediaController);

                mediaController.setAnchorView(vv);

                vv.seekTo(0);

            }
        });

        btnAceptarActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                //-----Carousel------//

                List<String> carouselBitmapTo64 = new ArrayList<>();

                for (Bitmap item: auxCarouselBitMaps)
                {
                    String img64 = vm.imgto64(item);

                    carouselBitmapTo64.add(img64);
                }

                //-----Portada-----//


                Bitmap bitmap = ((BitmapDrawable)iv.getBackground().getCurrent()).getBitmap();

                String portadaImg64 = vm.imgto64(bitmap);



                //-----VideoTrailer-----//

                String videoTrailer64 = null;

                if(actualVideoTrailerUri != null)
                {
                    videoTrailer64 = vm.videoto64(actualVideoTrailerUri,getContext());
                }

                //-----VideoCorto-----//

                String videoCorto64 = null;

                if(actualVideoCortoUri != null)
                {
                    videoCorto64 = vm.videoto64(actualVideoCortoUri, getContext());
                }

                vm.actualizarProyecto(id,titulo,genero,status,plataforma,portadaImg64,videoTrailer64,0,textoResumen,
                        textoCompleto,videoCorto64,carouselBitmapTo64,getContext());

            }

        });
    }


}