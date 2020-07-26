package ui.MiProyectos;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.plataformafinallab3.R;
import com.squareup.picasso.Picasso;

import java.util.List;


import models.Proyecto;


public class MiProyectosFragment extends Fragment {


 private MediaPlayer mediap;
 private int positionVid;
 private TextView titulo, plataforma, estado, categoria;
 private ListView lvMisProyectos;
 private View root;
 private List<Proyecto> list;
 private Button btnCrearProyecto;
 private static final String  path = "http://192.168.0.104:45455/";


    private MiProyectosViewModel miProyectosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        miProyectosViewModel = ViewModelProviders.of(this).get(MiProyectosViewModel.class);

         root = inflater.inflate(R.layout.fragment_miproyectos, container, false);

        lvMisProyectos = root.findViewById(R.id.lvMisProyectos);

         miProyectosViewModel.TraerProyectos(getContext());



         miProyectosViewModel.getProyectosMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Proyecto>>() {
             @Override
             public void onChanged(List<Proyecto> proyectos)
             {
                 list = proyectos;
                 if(list.size()!= 0)
                 {
                     generarVista();

                 }
             }
         });

        btnCrearProyecto = root.findViewById(R.id.btnCrearProyecto);


        btnCrearProyecto .setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View v)
             {
                 Navigation.findNavController(v).navigate(R.id.crearProyecto);
             }
         });


        miProyectosViewModel.getProyectoBorradoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Proyecto>() {
            @Override
            public void onChanged(Proyecto proyecto) {
                Toast.makeText(getContext(), "Proyecto Borrado", Toast.LENGTH_LONG).show();

                Navigation.findNavController(root).navigate(R.id.nav_home);
            }
        });
        return root;
    }

    public void generarVista()
    {
        ArrayAdapter<Proyecto> adapter = new listAdapterProyectos(root.getContext(),R.layout.item_misproyectos,list,getLayoutInflater());

        lvMisProyectos.setDivider(this.getResources().getDrawable(R.drawable.transperent));

        lvMisProyectos.setDividerHeight(3);

        lvMisProyectos.setAdapter(adapter);


    }

    public void ComponentesVistas(View v)
    {
        //iv = v.findViewById(R.id.ivMisProyectos);


        titulo = v.findViewById(R.id.tvTitleMisProyectos);

        plataforma = v.findViewById(R.id.tvPlataformaMisProyectos);

        estado = v.findViewById(R.id.tvEstadoMisProyectos);

        categoria = v.findViewById(R.id.tvCategoriaMisProyectos);

    }

    public class listAdapterProyectos extends ArrayAdapter<Proyecto>
    {
        Context ct;

        List<Proyecto> lista;

        LayoutInflater li;

        String pathCompleto;

        String pathCompletoVideo;


        public listAdapterProyectos(@NonNull Context context, int resource, @NonNull List<Proyecto> objects, LayoutInflater li)
        {
            super(context, resource, objects);

            this.ct = context;

            this.lista = objects;

            this.li = li;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {


            View itemView = convertView;



            if(itemView == null)
            {
                itemView = li.inflate(R.layout.item_misproyectos,parent,false);
            }

            final VideoView vv = itemView.findViewById(R.id.vvMisProyectos);

            final ImageView iv = itemView.findViewById(R.id.ivMisProyectos);

            Button btnBorrar = itemView.findViewById(R.id.btnBorrarProyecto);

            ComponentesVistas(itemView);

            final Proyecto proyecto = lista.get(position);

            pathCompleto = path + proyecto.getPortada();

            Glide.with(getContext()).load(pathCompleto).diskCacheStrategy(DiskCacheStrategy.NONE).into(new CustomTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    iv.setBackground(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            });

            iv.setColorFilter(getContext().getResources().getColor(R.color.transparenteColor));

            titulo.setText(proyecto.getTitulo());

            plataforma.setText(proyecto.getPlataforma());

            estado.setText(proyecto.getStatus());

            categoria.setText(proyecto.getGenero());

            //------------------------------------------------ Video del proyecto-----------------------------------


            pathCompletoVideo = path + proyecto.getVideo();

            vv.setBackgroundResource(R.color.transparentetotal);

            vv.setVideoURI(Uri.parse(pathCompletoVideo));

            vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediap = mp;
                    mediap.start();
                }
            });

            iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    vv.setVideoURI(Uri.parse(pathCompletoVideo));
                    vv.setVisibility(v.VISIBLE);
                    vv.start();

                    return true;
                }
            });

             iv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_UP)
                    {
                        vv.setVideoURI(Uri.parse(pathCompletoVideo));
                        vv.setVisibility(v.INVISIBLE);
                        vv.stopPlayback();




                    }
                    return false;
                }
            });

             iv.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Bundle bundle = new Bundle();

                     bundle.putInt("id",proyecto.getIdProyecto());

                     Navigation.findNavController(v).navigate(R.id.proyectoMain,bundle);

                 }
             });

             btnBorrar.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     miProyectosViewModel.BorrarProyecto(getContext(),proyecto.getIdProyecto());
                 }
             });

            return itemView;
        }
    }

}
