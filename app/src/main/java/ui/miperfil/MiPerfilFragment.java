package ui.miperfil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.plataformafinallab3.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.Result;

import models.Usuario;
import request.ApiClient;


public class MiPerfilFragment extends Fragment {

    private MiPerfilViewModel vm;
    private EditText nick,nombre,apellido,nacionalidad,email;
    private TextView btnEditar;
    private ImageView iv;
    private ApiClient ap;
    private String urlAvatar;
    private Button btnAceptar,btnCancelar;
    private View root;
    private String auxNick,auxNombre,auxApellido,auxNacionalidad,auxEmail;
    private boolean ivStatusClick = false;
    private Bitmap imgSeleccionada;
    private Bitmap auxImagen;
    private static  final int  IMG_REQUEST = 777;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        vm = ViewModelProviders.of(this).get(MiPerfilViewModel.class);

        root = inflater.inflate(R.layout.fragment_miperfil, container, false);

        getElementosVista();



        ///----------------------------SETEA LOS DATOS-------------------------

        vm.getUsuarioMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario)
            {
                setearElementosUsuario(usuario);

                vm.checkearperfil(getContext(),email.getText().toString());

            }
        });

        ///----------------------CHECKEA SI EL PERFIL ES DEL USUARIO-------------

        vm.getStringMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s)
            {
                if(s == "true")
                {
                    perfilDelusuario();
                }

            }
        });

        ///--------------------------IMAGEN PARA ACTUALIZAR------------------------
        vm.getImageMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>()
        {
            @Override
            public void onChanged(String s)
            {
                vm.actualizarPerfil(nick,nombre,apellido,email,nacionalidad,s,getContext());
            }
        });


        //------------------------USUARIO YA ACTUALIZADO--------------------------------------
        vm.getRespuestaMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>()
        {
            @Override
            public void onChanged(String s) {

                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();

                Navigation.findNavController(root).navigate(R.id.nav_home);
            }
        });

        vm.traerusuarioLogeado(getContext());

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(ivStatusClick)
                {
                    Intent i = new Intent();

                    i.setType("image/*");

                    i.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(i,IMG_REQUEST);



                }

            }
        });


        return root;
    }

    public void getElementosVista()
    {
        nick = root.findViewById(R.id.etNickMiPerfil);

        nombre = root.findViewById(R.id.etNombreMiPerfil);

        apellido = root.findViewById(R.id.etApellidoMiPerfil);

        nacionalidad = root.findViewById(R.id.etNacionalidadMiPerfil);

        email = root.findViewById(R.id.etEmailMiPerfil);

        iv = root.findViewById(R.id.ivAvatarMiPerfil);

        btnEditar = root.findViewById(R.id.tvEditarPerfil);

        btnAceptar = root.findViewById(R.id.btnAceptarMiPerfil);

        btnCancelar = root.findViewById(R.id.btnCancelarMiPerfil);

        btnAceptar.setVisibility(root.INVISIBLE);

        btnCancelar.setVisibility(root.INVISIBLE);
    }

    public void setearElementosUsuario(Usuario usuario)
    {
        nick.setText(usuario.getNick());

        nombre.setText(usuario.getNombre());

        apellido.setText(usuario.getApellido());

        nacionalidad.setText(usuario.getNacionalidad());

        email.setText(usuario.getEmail());

        //--------- Imagen------

        urlAvatar = ap.getPATHRECURSOS() + usuario.getAvatar();

        Glide.with(getContext()).asBitmap().load(urlAvatar).diskCacheStrategy(DiskCacheStrategy.NONE).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                imgSeleccionada = resource;
                iv.setImageBitmap(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
      /*  Picasso.with(getContext()).load(urlAvatar).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
            {
                imgSeleccionada = bitmap;
                iv.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable)
            {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable)
            {

            }
        });

*/


    }

    public void perfilDelusuario()
    {
        btnEditar.setVisibility(root.VISIBLE);

        btnEditar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                activarEdit();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUEST && resultCode == getActivity().RESULT_OK && data != null)
        {
            Uri path = data.getData();

            try
            {
                imgSeleccionada = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path);

                iv.setImageBitmap(imgSeleccionada);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void activarEdit()
    {
        nick.setEnabled(true);

        nombre.setEnabled(true);

        apellido.setEnabled(true);

        nacionalidad.setEnabled(true);

        email.setEnabled(true);

        nick.setBackgroundResource(R.color.transparenteColor);

        nombre.setBackgroundResource(R.color.transparenteColor);

        apellido.setBackgroundResource(R.color.transparenteColor);

        nacionalidad.setBackgroundResource(R.color.transparenteColor);

        email.setBackgroundResource(R.color.transparenteColor);

        btnCancelar.setVisibility(root.VISIBLE);

        btnAceptar.setVisibility(root.VISIBLE);

        auxNick = nick.getText().toString();

        auxApellido = apellido.getText().toString();

        auxNombre = nombre.getText().toString();

        auxEmail = email.getText().toString();

        auxNacionalidad = nacionalidad.getText().toString();

        auxImagen = ((BitmapDrawable)iv.getDrawable()).getBitmap();

        ivStatusClick = true;

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                ivStatusClick = false;

                nick.setText(auxNick);

                nombre.setText(auxNombre);

                apellido.setText(auxApellido);

                nacionalidad.setText(auxNacionalidad);

                email.setText(auxEmail);

                iv.setImageBitmap(auxImagen);

                btnCancelar.setVisibility(root.INVISIBLE);

                btnAceptar.setVisibility(root.INVISIBLE);

                nick.setEnabled(false);

                nombre.setEnabled(false);

                apellido.setEnabled(false);

                nacionalidad.setEnabled(false);

                email.setEnabled(false);

                nick.setBackgroundResource(R.color.EditTextNOBG);

                apellido.setBackgroundResource(R.color.EditTextNOBG);

                email.setBackgroundResource(R.color.EditTextNOBG);

                nacionalidad.setBackgroundResource(R.color.EditTextNOBG);

                nombre.setBackgroundResource(R.color.EditTextNOBG);

            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                vm.imgto64(imgSeleccionada);
            }
        });

    }

}
