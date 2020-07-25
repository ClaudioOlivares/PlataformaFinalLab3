package devlog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.view.menu.MenuView;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.plataformafinallab3.R;

import java.util.ArrayList;
import java.util.List;

import DevlogItem.DevlogItemfragment;
import models.DevLog;
import models.Proyecto;
import request.ApiClient;
import ui.MiProyectos.MiProyectosFragment;
import ui.miperfil.MiPerfilViewModel;


public class DevLogMain extends Fragment {
    private int id;

    private ListView lv;

    private List<DevLog> lista = new ArrayList<>();

    private ApiClient ap;

    private View root;

    private DevLogViewModel vm;

    private boolean actualizable = false;

    private Button btnCrear;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vm = ViewModelProviders.of(this).get(DevLogViewModel.class);

        root = inflater.inflate(R.layout.fragment_dev_log_main, container, false);

        lv = root.findViewById(R.id.lvDevLogs);

        btnCrear = root.findViewById(R.id.btnCrearDevLog);


        id = getArguments().getInt("id");



        vm.getRespuestaCheckeoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                    actualizable = true;

            }
        });

        vm.checkearDevLogs(getContext(),id);


        vm.getDevlogMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<DevLog>>() {
            @Override
            public void onChanged(List<DevLog> devLogs)
            {
                if(devLogs.size() != 0)
                {
                    lista = devLogs;

                    generarVista();
                }
            }
        });


        vm.traerDevLogs(getContext(), id);



        btnCrear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Bundle bundle = new Bundle();

                bundle.putInt("id", id);

                bundle.putString("accion","crear");

                Navigation.findNavController(v).navigate(R.id.actualizarDevlog, bundle);

            }
        });

        vm.getDevlogborradoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<DevLog>() {
            @Override
            public void onChanged(DevLog devLog) {

                Toast.makeText(getContext(), "Devlog Borrado", Toast.LENGTH_SHORT).show();

                Navigation.findNavController(root).navigate(R.id.nav_home);
            }
        });
        return root;
    }


  public void generarVista()
  {

      ArrayAdapter<DevLog> adapter = new listAdapterDevlogs(root.getContext(),R.layout.devlog_item,lista,getLayoutInflater());

      lv.setDivider(this.getResources().getDrawable(R.drawable.transperent));

      lv.setDividerHeight(18);

      lv.setAdapter(adapter);
  }

    public class listAdapterDevlogs extends ArrayAdapter<DevLog> {
        Context ct;

        List<DevLog> lista;

        LayoutInflater li;

        String pathCompleto;

        public listAdapterDevlogs(@NonNull Context context, int resource, @NonNull List<DevLog> objects, LayoutInflater li) {
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
                itemView = li.inflate(R.layout.devlog_item, parent, false);
            }
            TextView tvTitulo;

            final ImageView iv;

            Button btnActualizar;

            btnActualizar = itemView.findViewById(R.id.btnActualizarDevlog);

            tvTitulo = itemView.findViewById(R.id.tvTituloDevlog);

            iv = itemView.findViewById(R.id.ivImagenDevLog);

            final DevLog devlog = lista.get(position);

            final int iddevlog = devlog.getIdDevlog();

            Button btnBorrar = itemView.findViewById(R.id.btnBorrarDevLog);

            tvTitulo.setText(devlog.getTitulo());


            if(actualizable)
            {
                btnActualizar.setVisibility(View.VISIBLE);

                btnActualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Bundle bundle = new Bundle();

                        bundle.putInt("id", devlog.getIdDevlog());

                        Navigation.findNavController(v).navigate(R.id.actualizarDevlog, bundle);
                    }
                });

                btnBorrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        vm.borrarDevlog(devlog.getIdDevlog(),getContext());
                    }
                });
            }

            pathCompleto =  ap.getPATHRECURSOS() + devlog.getProyecto().getPortada();

            Glide.with(getContext()).asBitmap().load(pathCompleto).diskCacheStrategy(DiskCacheStrategy.NONE).into(new CustomTarget<Bitmap>() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition)
                {
                        Bitmap bmap = toGrayscale(resource);

                        iv.setBackground(new BitmapDrawable(getResources(), bmap));

                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder)
                {

                }
            });

            iv.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Bundle bundle = new Bundle();

                    bundle.putInt("id",iddevlog);

                    Navigation.findNavController(v).navigate(R.id.devlogItem,bundle);
                }
            });


            return  itemView;
        }
    }


    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;

        height = bmpOriginal.getHeight();

        width = bmpOriginal.getWidth();


        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        Canvas c = new Canvas(bmpGrayscale);

        Paint paint = new Paint();

        ColorMatrix cm = new ColorMatrix();

        cm.setSaturation(0);

        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);

        paint.setColorFilter(f);

        c.drawBitmap(bmpOriginal, 0, 0, paint);

        return bmpGrayscale;
    }
}