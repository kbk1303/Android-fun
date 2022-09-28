package com.example.variousjavaandandroidfun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Callback for long timed calls like REST calls */

        new Thread(() -> {
            ExecutorService executor = Executors.newFixedThreadPool(10);
            Future<String> future = executor.submit(() -> {
                /* replace Thread.sleep with the long hauling method call. */
                Thread.sleep(10000l);
                return "Hello world";
            });
            while(true) {
                if (future.isDone() && !future.isCancelled()) {
                    try {
                        /* only required for the Toast.. */
                        MainActivity.this.runOnUiThread(() -> {
                            try {
                                Toast.makeText(MainActivity.this, "From future: " + future.get(), Toast.LENGTH_LONG).show();
                            } catch (ExecutionException | InterruptedException e) { //future.get()
                                e.printStackTrace();
                            }
                        });
                        System.err.println("future: "+future.get());
                        break;
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
            executor.shutdown();
        }).start();




    }
}