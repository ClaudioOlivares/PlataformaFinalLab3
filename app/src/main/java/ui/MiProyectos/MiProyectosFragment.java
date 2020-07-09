package ui.MiProyectos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.plataformafinallab3.R;

import java.util.List;

import models.Proyecto;



public class MiProyectosFragment extends Fragment {

 private VideoView vv;
 private ImageView iv;
 private TextView titulo, plataforma, estado, categoria;
 private ListView lvMisProyectos;
 private View root;
 private List<Proyecto> list;


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

        vv = v.findViewById(R.id.vvMisProyectos);

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

            ComponentesVistas(itemView);

            final Proyecto proyecto = lista.get(position);

            titulo.setText(proyecto.getTitulo());

            plataforma.setText(proyecto.getPlataforma());

            estado.setText(proyecto.getStatus());

            categoria.setText(proyecto.getGenero());




            return itemView;
        }
    }
}
