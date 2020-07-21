package DevlogItem;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.plataformafinallab3.R;

import java.util.ArrayList;
import java.util.List;

import devlog.DevLogMain;
import devlog.DevLogViewModel;
import models.DevLog;
import models.DevLogItem;
import request.ApiClient;

public class DevlogItemfragment extends Fragment {

    private View root;

    private ApiClient ap;

    private TextView titulo;

    private DevlogItemViewModel vm;

    private int id;

    private ListView lv;

    private boolean actualizar;

    private List<DevLogItem> lista = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        vm = ViewModelProviders.of(this).get(DevlogItemViewModel.class);

        vm.getCheckeodevlogitemMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                actualizar = true;
            }
        });

        vm.checkearDevLogsItems(getContext(),id);

        root =  inflater.inflate(R.layout.fragment_devlog_item, container, false);

   //     titulo = root.findViewById(R.id.tvTituloDevlogItem);

        id = getArguments().getInt("id");

       lv = root.findViewById(R.id.lvDevlogItem);

       vm.getDevlogitemMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<DevLogItem>>()
       {
           @Override
           public void onChanged(List<DevLogItem> devLogItems)
           {
               if(devLogItems.size() != 0)
               {
                   lista = devLogItems;



             //      titulo.setText(devLogItems.get(0).getDevLog().getTitulo());

                   generarVista();

               }

           }
       });




       vm.traerDevlogsitems(getContext(),id);

       return root;
    }

    public void generarVista()
    {

        ArrayAdapter<DevLogItem> adapter = new listAdapterDevlogsItems(root.getContext(),R.layout.item_devlogitem,lista,getLayoutInflater());

        lv.setDivider(this.getResources().getDrawable(R.drawable.transperent));

        lv.setAdapter(adapter);
    }

    public class listAdapterDevlogsItems extends ArrayAdapter<DevLogItem> {
        Context ct;

        List<DevLogItem> lista;

        LayoutInflater li;

        String pathCompleto;

        public listAdapterDevlogsItems(@NonNull Context context, int resource, @NonNull List<DevLogItem> objects, LayoutInflater li) {
            super(context, resource, objects);

            this.ct = context;

            this.lista = objects;

            this.li = li;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


            View itemView = convertView;

            if (itemView == null)
            {
                itemView = li.inflate(R.layout.item_devlogitem, parent, false);
            }

            Button btnActualizar;

            btnActualizar = itemView.findViewById(R.id.btnActualizarITEMDEVLOG);

            ImageView iv = itemView.findViewById(R.id.ivDevlogitemITEM);

            TextView texto = itemView.findViewById(R.id.tvTextoDevlogitemITEM);

            TextView tituloitem = itemView.findViewById(R.id.tituloDevLogItemITEM);

            final DevLogItem dvi = lista.get(position);

            tituloitem.setText(dvi.getTitulo());

            texto.setText(dvi.getTexto());

            if(actualizar)
            {
                btnActualizar.setVisibility(View.VISIBLE);
            }

            pathCompleto = ap.getPATHRECURSOS() + dvi.getMultimedia();

            Glide.with(getContext()).load(pathCompleto).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);

            btnActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Bundle bundle = new Bundle();

                    bundle.putInt("id",dvi.getIdDevlogItem());

                    Navigation.findNavController(v).navigate(R.id.actualizarCrearDevLogItem,bundle);
                }
            });


            return itemView;
        }
    }
}
