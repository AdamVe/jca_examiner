package com.adamve.jcaexaminer;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listJcaProviders();
    }

    public static void listServices(Provider provider) {
        Set<Provider.Service> services = provider.getServices();
        for (Provider.Service s : services) {
            String serviceInfo = " <" + s.getType() + "> <" + s.getAlgorithm() + "> " + s.getClassName();
            Log.d(TAG, serviceInfo);
        }
    }

    public static void logProvider(Provider provider) {
        String providerInfo = provider.getName() + "/" + provider.getInfo() + "/" + provider.getVersion();
        Log.d(TAG, providerInfo);
        listServices(provider);
    }

    public static void listJcaProviders() {
        Provider[] providers = Security.getProviders();

        Log.d(TAG, "Standard providers");
        for (Provider p : providers) {
            logProvider(p);
        }

        Security.removeProvider("BC");
        Security.addProvider(new BouncyCastleProvider());

        Log.d(TAG, "Replaced BC provider");
        Provider bcProvider = Security.getProvider("BC");
        logProvider(bcProvider);
    }
}