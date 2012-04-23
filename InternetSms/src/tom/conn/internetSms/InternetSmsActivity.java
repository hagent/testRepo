package tom.conn.internetSms;


import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InternetSmsActivity extends Activity {
	private static final int loadDialogId = 14;
	private static final int sendDialogId = 15;
	private static final int PICK_CONTACT = 16;
	
	ImageView imageView;
	TextView messageText;
	TextView captchaValue;
	Button loadButton;
	Button contactButton;
	Button sendButton;
	

	private String captchaKey;
	private TextView phoneNumberTextView;
	private PhoneNumber currentPhone;
	private MobileOperators currentOperator;

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        messageText = (TextView) findViewById(R.id.messageText);
        phoneNumberTextView = (TextView) findViewById(R.id.phoneNumber);
        captchaValue = (TextView) findViewById(R.id.captchaValue);
        loadButton = (Button) findViewById(R.id.loadCaptchaButton);
        sendButton = (Button) findViewById(R.id.sendButton);
        contactButton = (Button) findViewById(R.id.contactsButton);
        imageView = (ImageView) findViewById(R.id.capthchaImage);
        
        loadButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		onLoadCaptchaButtonClicked();
        	}
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
					onSendButtonClicked();
            }
        });
        contactButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				onContactClicked();
				
			}
		});
        
    }
    
	protected void onContactClicked() {
		Intent intent = new Intent(Intent.ACTION_PICK,  ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
		
		startActivityForResult(intent, PICK_CONTACT);
	}

	protected ProgressDialog onCreateDialog(int dialogId){
		if(dialogId == loadDialogId){
			ProgressDialog progress = new ProgressDialog(this);
			progress.setMessage("Loading...");
	        return progress;
		}
		
		return null;
	}
	
	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);

		switch (reqCode) {
		case (PICK_CONTACT):
			if (resultCode == Activity.RESULT_OK) {
				Uri contactData = data.getData();
				Cursor c = managedQuery(contactData, null, null, null, null);
				if (c.moveToFirst()) {
					String number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					phoneNumberTextView.setText(number);
				}
			}
			break;
		}
	}
	
	private void onLoadCaptchaButtonClicked() {
		String numberString = phoneNumberTextView.getText().toString();
		currentPhone = new PhoneNumber(numberString);
		
		ArrayList<MobileOperators> opertors = 
				MobileOperatorsProvider.getOperators(currentPhone.getPrefix());
		
		if(opertors.size() == 0){
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Ошибка");
			alertDialog.setMessage("Извините оператор не поддерживается");
			alertDialog.show();
			return;
		}		
		
		if(opertors.size() > 1){
			//TODO: реализовать выбор оператора пользователем
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Ошибка");
			alertDialog.setMessage("Номер поддерживается несколькими операторами");
			alertDialog.show();
			return;
		}
		
		currentOperator = opertors.get(0);
		new CaptchaLoaderTask(this, loadDialogId, currentPhone, currentOperator).execute();
	}	
	
	private void onSendButtonClicked() {
		new SendTask(this, 
				sendDialogId, 
				captchaValue.getText().toString(), 
				captchaKey,
				currentOperator,
				currentPhone,
				messageText.getText().toString()).execute();
	}

	public void setCaptchaBitmap(Bitmap captchaBitmap) {
		imageView.setImageBitmap(captchaBitmap);		
	}

	public void setCaptchaKey(String captchaKey) {
		this.captchaKey = captchaKey;
	}
}