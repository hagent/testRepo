package tom.conn.internetSms;

import java.io.IOException;
import java.text.MessageFormat;

import tom.conn.internetSms.Megafon.MegafonCaptchaLoader;
import tom.conn.internetSms.Megafon.MegafonCaptchaParams;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Pair;

public class CaptchaLoaderTask extends AsyncTask<String, Integer, Pair<Bitmap,String>> {
	private final int dialogId;
	private final InternetSmsActivity activity;
	private final MobileOperators operator;

	public CaptchaLoaderTask(
			InternetSmsActivity activity, 
			int loadDialogId, 
			PhoneNumber phoneNumber,
			MobileOperators operator) {
		this.activity = activity;
		dialogId = loadDialogId;
		this.operator = operator;
	}

	@Override
	protected Pair<Bitmap,String> doInBackground(String... urls) {
		publishProgress(1);
		
		try {			
			CaptchaLoader captchaLoader = MobileOperatorsProvider.getCaptchaLoader(operator);
			CaptchaParams captchaParams =  captchaLoader.getCaptchaParams();
			
			String captchaKey = captchaParams.getKey();
			Bitmap captchaBitmap = new ImageLoadService().Get(captchaParams.getUrl());
			
			return new Pair<Bitmap,String>(captchaBitmap, captchaKey);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress) {
		this.activity.showDialog(dialogId);
	}
	
	@Override
	protected void onPostExecute(Pair<Bitmap,String> bitmapKeyPair) {
		this.activity.dismissDialog(dialogId);
		this.activity.setCaptchaBitmap(bitmapKeyPair.first);  
		this.activity.setCaptchaKey(bitmapKeyPair.second);  
	}
}

