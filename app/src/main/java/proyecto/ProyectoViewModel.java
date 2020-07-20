package proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import models.CheckPerfilView;
import models.ImagenProyecto;
import models.Proyecto;
import models.ProyectoMasImagenesView;
import request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sharedprefs.Sharedprefs;

public class ProyectoViewModel extends ViewModel
{
    Sharedprefs sp;

    private MutableLiveData<List<ImagenProyecto>> proyectoMutableLiveData;

    private MutableLiveData<String> checkeoMutableLiveData;

    private MutableLiveData<Proyecto> proyectoactualizadoMutableLiveData;


    public ProyectoViewModel()
    {

    }

    public LiveData<List<ImagenProyecto>> getProyectoMutableLiveData()
    {
        if(proyectoMutableLiveData == null)
        {
            proyectoMutableLiveData = new MutableLiveData<>();
        }
        return proyectoMutableLiveData;
    }

    public LiveData<String> getCheckeoMutableLiveData()
    {
        if(checkeoMutableLiveData == null)
        {
            checkeoMutableLiveData = new MutableLiveData<>();
        }
        return checkeoMutableLiveData;
    }

    public LiveData<Proyecto> getProyectoactualizadoMutableLiveData()
    {
        if(proyectoactualizadoMutableLiveData == null)
        {
            proyectoactualizadoMutableLiveData = new MutableLiveData<>();
        }
        return proyectoactualizadoMutableLiveData;
    }



    public void traerProyecto(final Context ctx, int id)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        final Call<List<ImagenProyecto>> res = ApiClient.getMyApiClient().TraerProyectoImagenes(token,id);

        res.enqueue(new Callback <List<ImagenProyecto>>()
        {
            @Override
            public void onResponse(Call<List<ImagenProyecto>> call, Response<List<ImagenProyecto>> response)
            {
                if(response.isSuccessful())
                {
                    if(response.body().size() != 0) 
                    {
                        proyectoMutableLiveData.setValue(response.body());
                    }
                    else
                    {
                        Toast.makeText(ctx, "El proyecto no contiene elementos", Toast.LENGTH_SHORT).show();
                    }
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
            public void onFailure(Call<List<ImagenProyecto>> call, Throwable t)
            {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void checkearSiEsProyectoDelUsuario(final Context ctx, int id)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        Call<String> res = ApiClient.getMyApiClient().checkearproyecto(token,id);

        res.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if(response.isSuccessful())
                {
                    checkeoMutableLiveData.postValue(response.body());
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
                Toast.makeText(ctx, "", Toast.LENGTH_SHORT).show();
            }
        });
    }


     public void actualizarProyecto(int idProyecto, EditText titulo, EditText genero,EditText status, EditText plataforma,
      String portada64, String videotrailer64, int idUser, EditText textoResumen, EditText textoCompleto,
     String videoCorto64, List<String> imgsUrls64, final Context ctx)
     {
         String token = "Bearer " + sp.leerToken(ctx);

         List<ImagenProyecto> lista = new ArrayList<>();

         ProyectoMasImagenesView p = new ProyectoMasImagenesView();

         p.setIdProyecto(idProyecto);

         p.setTitulo(titulo.getText().toString());

         p.setGenero(genero.getText().toString());

         p.setStatus(status.getText().toString());

         p.setPlataforma(plataforma.getText().toString());

         p.setPortada(portada64);

         p.setVideo(videoCorto64);

         p.setIdUser(idUser);

         p.setTextoResumen(textoResumen.getText().toString());

         p.setTextoCompleto(textoCompleto.getText().toString());

         p.setVideoTrailer(videotrailer64);

         p.setFechaCreacion(null);

         for (String url:imgsUrls64)
         {
              ImagenProyecto i = new ImagenProyecto();

              i.setUrl(url);

              lista.add(i);
         }
         p.setImagenes(lista);

         Call<Proyecto> res = ApiClient.getMyApiClient().actualizarProyecto(p,token);

         res.enqueue(new Callback<Proyecto>() {
             @Override
             public void onResponse(Call<Proyecto> call, Response<Proyecto> response)
             {
                 if(response.isSuccessful())
                 {
                     proyectoactualizadoMutableLiveData.postValue(response.body());
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
             public void onFailure(Call<Proyecto> call, Throwable t)
             {
                 Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();

             }
         });

     }

    public String imgto64(Bitmap bmp)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);

        byte[] imgBytes = byteArrayOutputStream.toByteArray();

        return (Base64.encodeToString(imgBytes,Base64.DEFAULT));
    }



    public String videoto64(Uri uri, Context ctx)
    {
        InputStream inputStream = null;

        try
        {
            inputStream = ctx.getContentResolver().openInputStream(uri);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        int bufferSize = 50 * 1024 * 1024;

        byte[] buffer = new byte[bufferSize];

        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        int len = 0;

        try
        {
            while ((len = inputStream.read(buffer)) != -1)
            {
                byteBuffer.write(buffer, 0, len);
            }
        }
        catch (IOException e)
        {
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();;
        }

        String video64 = Base64.encodeToString(byteBuffer.toByteArray(), Base64.DEFAULT);

        return  video64;
    }

}
