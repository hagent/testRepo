package tom.conn.internetSms.Megafon;


import java.io.IOException;
import java.util.Date;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import tom.conn.internetSms.MessageSender;

public class MegafonSender implements MessageSender{
	public String SendMessage(
			String captchaValue, 
			String captchaKey, 
			String phonePrefix, 
			String message, 
			String phoneNumber ) throws IOException{
		Date date = new Date();
		
		Connection conn = Jsoup.connect("https://sendsms.megafon.ru/sms.action")
		.data("prefix", phonePrefix,
			"addr", phoneNumber,
			"message", message,
			"send_day", Integer.toString(date.getDay()),
			"send_month", Integer.toString(date.getMonth()),
			"send_hour", Integer.toString(date.getHours()),
			"send_minute", Integer.toString(date.getMinutes()),
			"send_year", Integer.toString(date.getYear()),
			"recaptcha_challenge_field", captchaKey,
			"recaptcha_response_field", captchaValue);
		
		Document doc = conn.post();
		
		return "";
	}
	
}
