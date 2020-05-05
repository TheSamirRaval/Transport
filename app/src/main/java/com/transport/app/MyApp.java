package com.transport.app;

import android.app.Application;


import com.google.android.gms.maps.MapsInitializer;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.transport.BuildConfig;
import com.transport.R;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;


/**
 * Created by SAM on 3/1/2020.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @NotNull
                @Override
                protected String createStackElementTag(@NotNull StackTraceElement element) {
                    return String.format("%s->%s", super.createStackElementTag(element), element.getLineNumber());
                }
            });
        }

        try {
            MapsInitializer.initialize(getApplicationContext());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
