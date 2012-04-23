package tom.conn.internetSms;

import java.io.IOException;

public interface MessageSender {

	String SendMessage(String captchaValue, String captchaKey, String prefix,
			String messageText, String number) throws IOException;

}
