package com.pula.star.utils;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
public class AsyncImageLoader {
	private HashMap<String, SoftReference<Bitmap>> bitmapCache;
	public AsyncImageLoader(){
		bitmapCache = new HashMap<String, SoftReference<Bitmap>>();
	}

	public Bitmap loadImage(final String path,final ImageCallBack callback){
		if(bitmapCache.containsKey(path)){
			SoftReference<Bitmap> ref = bitmapCache.get(path);
			Bitmap bitmap = ref.get();
			if(bitmap !=null){
				return bitmap;
			}else{
				bitmapCache.remove(path);
			}
		}
		final Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				callback.imageLoaded(path, (Bitmap)msg.obj);
			}
		};
		new Thread(){
			@Override
			public void run() {
				try {
					InputStream stream =null ;
					stream = HttpTools.getInputStream(path);
					Bitmap bitmap = BitmapFactory.decodeStream(stream);					//Bitmap bitmap =  BitmapFactory.decodeStream(stream);  
					bitmapCache.put(path, new SoftReference<Bitmap>(bitmap));
					Message msg =handler.obtainMessage();
					msg.what=0;
					msg.obj=bitmap;
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

		return null;
	}

	public interface ImageCallBack{
		void imageLoaded(String path,Bitmap bitmap);
	}
}
