package com.example.nazarbogdan.mobile;

import android.content.Context;
import android.content.SharedPreferences;

public class PersistantStorage {
    static final String STORAGE_NAME = "Userr";

    private static SharedPreferences settings = null;
    private static SharedPreferences.Editor editor = null;
    private static Context context = null;

    public static void init( Context cntxt ){
        context = cntxt;
    }

    private static void init(){
        settings = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public static void addProperty( String name, String surname, String email, String phone ){
        if( settings == null ){
            init();
        }
        editor.putString( "name", name );
        editor.putString( "surname", surname );
        editor.putString( "email", email );
        editor.putString( "phone", phone );
        editor.commit();
    }

    public static String getProperty( String name ){
        if( settings == null ){
            init();
        }
        return settings.getString( name, null );
    }
}
}
