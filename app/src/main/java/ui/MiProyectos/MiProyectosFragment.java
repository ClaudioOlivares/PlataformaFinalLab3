package ui.MiProyectos;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.plataformafinallab3.R;
import com.squareup.picasso.Picasso;

import java.util.List;


import models.Proyecto;


public class MiProyectosFragment extends Fragment {


 private MediaPlayer mediap;
 private int positionVid;
 private ImageView iv;
 private TextView titulo, plataforma, estado, categoria;
 private ListView lvMisProyectos;
 private View root;
 private List<Proyecto> list;
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

                 generarVista();
             }
         });

        return root;
    }

    public void generarVista()
    {
        ArrayAdapter<Proyecto> adapter = new listAdapterProyectos(root.getContext(),R.layout.item_misproyectos,list,getLayoutInflater());

        lvMisProyectos.setDivider(this.getResources().getDrawable(R.drawable.transperent));

        lvMisProyectos.setDividerHeight(18);

        lvMisProyectos.setAdapter(adapter);


    }

    public void ComponentesVistas(View v)
    {
        iv = v.findViewById(R.id.ivMisProyectos);



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

            ComponentesVistas(itemView);

            final Proyecto proyecto = lista.get(position);

            pathCompleto = path + proyecto.getPortada();

            Picasso.with(getContext()).load(pathCompleto).into(iv);

            iv.setColorFilter(getContext().getResources().getColor(R.color.transparenteColor));

            titulo.setText(proyecto.getTitulo());

            plataforma.setText(proyecto.getPlataforma());

            estado.setText(proyecto.getStatus());

            categoria.setText(proyecto.getGenero());

            //------------------------------------------------ Video del proyecto-----------------------------------


            pathCompletoVideo = path + proyecto.getVideo();

            vv.setVideoURI(Uri.parse(pathCompletoVideo));

            iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
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
            return itemView;
        }
    }

}
