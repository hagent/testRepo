package tom.conn.internetSms.Megafon;


import java.io.IOException;
import java.text.MessageFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tom.conn.internetSms.CaptchaLoader;
import tom.conn.internetSms.CaptchaParams;


import com.google.gson.Gson;

public class MegafonCaptchaLoader implements CaptchaLoader {
	public MegafonCaptchaParams GetMegaphonCaptchaParams() throws IOException{
		Document doc = Jsoup.connect("https://sendsms.megafon.ru/").get();
		
		Elements el = doc.getElementsByAttributeValueContaining("src", 
				"//www.google.com/recaptcha/api/challenge?k=");
		String paramsUrl = el.first().attributes().get("src");
		
		Document paramsDoc = Jsoup.connect("http:" + paramsUrl).get();
		
		Element body = paramsDoc.getElementsByTag("body").first();
		
		String jsonParams =  "{" + body.html().split("[{}]")[1] + "}";
		System.out.println("params json:" + jsonParams);
		
		Gson gson = new Gson();
		MegafonCaptchaParams data = gson.fromJson(jsonParams, MegafonCaptchaParams.class);
		
		return data;
	}

	public CaptchaParams getCaptchaParams() throws IOException {
		MegafonCaptchaParams megafonCaptchaParams = GetMegaphonCaptchaParams();
		
		String captchaKey = megafonCaptchaParams.getChallenge();
		String captchaUrl = MessageFormat.format("{0}image?c={1}", 
				megafonCaptchaParams.getServer(), captchaKey);
		
		return new CaptchaParams(captchaKey, captchaUrl);
	}
	
}
