package com.example.fauricio.inventario_2_0;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by fauricio on 21/05/18.
 */

public class ImageDownloadTask extends AsyncTask<String,Void,Bitmap> {
    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            Bitmap resultado = null;
            URL url = new URL(urls[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            resultado = BitmapFactory.decodeStream(inputStream);

            return resultado;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
/*
    ImageDownloadTask downloadTask = new ImageDownloadTask();

        try {
                Bitmap result = downloadTask.execute("https://firebasestorage.googleapis.com/v0/b/inventario-4d73a.appspot.com/o/ggg?alt=media&token=49d0fda1-dbb4-4189-9ac3-84f40c21d047").get();
                imageView.setImageBitmap(result);
                //Log.i("hola",result);
                //Toast.makeText(this,result,Toast.LENGTH_SHORT);
                } catch (InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(this,"Error al descargar contenido",Toast.LENGTH_SHORT);
                } catch (ExecutionException e) {
                e.printStackTrace();
                Toast.makeText(this,"Error al descargar contenido",Toast.LENGTH_SHORT);
                }
*/