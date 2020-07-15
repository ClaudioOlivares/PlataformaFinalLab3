package ui.miperfil;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import models.CheckPerfilView;
import models.EditPerfilView;
import models.Proyecto;
import models.Usuario;
import request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sharedprefs.Sharedprefs;

public class MiPerfilViewModel extends ViewModel {

    Sharedprefs sp;

    private MutableLiveData<Usuario> usuarioMutableLiveData;

    private MutableLiveData<String> stringMutableLiveData;

    private MutableLiveData<String> respuestaMutableLiveData;

    private MutableLiveData<String> imageMutableLiveData;

    public MiPerfilViewModel()
    {

    }

    public LiveData<Usuario> getUsuarioMutableLiveData()
    {
        if(usuarioMutableLiveData == null)
        {
           usuarioMutableLiveData = new MutableLiveData<>();
        }
        return usuarioMutableLiveData;
    }

    public LiveData<String> getStringMutableLiveData()
    {
        if(stringMutableLiveData == null)
        {
            stringMutableLiveData = new MutableLiveData<>();
        }

        return  stringMutableLiveData;
    }

    public LiveData<String> getRespuestaMutableLiveData()
    {
        if(respuestaMutableLiveData == null)
        {
            respuestaMutableLiveData = new MutableLiveData<>();
        }

        return  respuestaMutableLiveData;
    }

    public LiveData<String> getImageMutableLiveData()
    {
        if(imageMutableLiveData == null)
        {
            imageMutableLiveData = new MutableLiveData<>();
        }

        return  imageMutableLiveData;
    }

    public void traerusuarioLogeado(final Context ctx)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        final Call<Usuario> res = ApiClient.getMyApiClient().traerUsuarioLogeado(token);

        res.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response)
            {
                if(response.isSuccessful())
                {
                    usuarioMutableLiveData.setValue(response.body());
                }
                else
                {
                    try
                    {
                        Toast.makeText(ctx, response.errorBody().string(), Toast.LENGTH_SHORT).show();

                    }
                    catch (IOException e)
                    {
                        Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t)
            {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void checkearperfil(final Context ctx,String email)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        CheckPerfilView cv = new CheckPerfilView(email);

        Call<String> res = ApiClient.getMyApiClient().checkearPerfil(cv,token);

        res.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if(response.isSuccessful())
                {
                   stringMutableLiveData.postValue(response.body());
                }
                else
                {
                    try
                    {
                        Toast.makeText(ctx, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                    catch (IOException e)
                    {
                        Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void actualizarPerfil(EditText nick, EditText nombre, EditText apellido, EditText email, EditText nacionalidad,String imgBytes,final Context ctx)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        Usuario u = new Usuario(0,nick.getText().toString(),nombre.getText().toString(),
                apellido.getText().toString(),email.getText().toString(),nacionalidad.getText().toString(),"clave",imgBytes);

        Call<Usuario> res = ApiClient.getMyApiClient().actualizarPerfil(u, token);

        res.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response)
            {
                if(response.isSuccessful())
                {
                    respuestaMutableLiveData.setValue("Datos Actualizados");
                }
                else
                {
                    try
                    {
                        Toast.makeText(ctx, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e)
                    {
                        Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();;
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t)
            {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void imgto64(Bitmap bmp)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);

        byte[] imgBytes = byteArrayOutputStream.toByteArray();

        imageMutableLiveData.postValue(Base64.encodeToString(imgBytes,Base64.DEFAULT));
    }
}