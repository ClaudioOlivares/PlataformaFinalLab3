package sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedprefs {
    private static SharedPreferences sp;

    public static SharedPreferences conectar(Context ctx) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("datos", 0);
        }
        return sp;
    }

    public static String leerToken(Context ctx)
    {
        SharedPreferences sp = conectar(ctx);
        String token = sp.getString("token", null);
        return token;
    }

    public static void guardarToken(Context ctx, String token)
    {
        SharedPreferences sp = conectar(ctx);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token",token);
        editor.commit();

    }
}
