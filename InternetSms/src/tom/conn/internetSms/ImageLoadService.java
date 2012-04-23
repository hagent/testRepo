package tom.conn.internetSms;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageLoadService {
	public Bitmap  Get(String url) throws IOException{
		URL myFileUrl = new URL(url);
        HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
        conn.setDoInput(true);
        conn.connect();
        InputStream is = conn.getInputStream();
        return BitmapFactory.decodeStream(is);
	}
}
