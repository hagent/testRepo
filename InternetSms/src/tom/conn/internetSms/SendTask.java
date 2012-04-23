package tom.conn.internetSms;

import java.io.IOException;

import tom.conn.internetSms.Megafon.MegafonSender;
import android.os.AsyncTask;

public class SendTask extends AsyncTask<String, Integer, String> {
	private final int dialogId;
	private final InternetSmsActivity activity;
	private String captchaValue;
	private String captchaKey;
	private final MobileOperators operator;
	private final PhoneNumber phoneNumber;
	private final String messageText;

	public SendTask(
			InternetSmsActivity activity, 
			int loadDialogId, 
			String captchaValue, 
			String captchaKey, 
			MobileOperators operator,
			PhoneNumber phoneNumber,
			String messageText) {
		
		this.activity = activity;
		dialogId = loadDialogId;
		this.captchaValue = captchaValue;
		this.captchaKey = captchaKey;
		this.operator = operator;
		this.phoneNumber = phoneNumber;
		this.messageText = messageText;
	}

	@Override
	protected String doInBackground(String... urls) {
		publishProgress(1);
		
		try {
			MessageSender sender = MobileOperatorsProvider.getMessageSender(operator);

			sender.SendMessage(captchaValue, 
					captchaKey, 
					phoneNumber.getPrefix(), 
					messageText, 
					phoneNumber.getNumber());
			return "sms sended";
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress) {
		this.activity.showDialog(dialogId);
	}
	
	@Override
	protected void onPostExecute(String result) {
		this.activity.dismissDialog(dialogId);
	}
}
