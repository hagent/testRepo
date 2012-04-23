package tom.conn.internetSms.Beeline;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import tom.conn.internetSms.CaptchaLoader;
import tom.conn.internetSms.CaptchaParams;

public class BeelineCaptchaLoader implements CaptchaLoader {

	public CaptchaParams getCaptchaParams() throws IOException {
		Document doc = Jsoup.connect("https://www.beeline.ru/sms/index.wbp").get();
		
		Elements elems = doc.select("img[name=mimg]");
		if(elems.size() == 0 || elems.size() > 1){
			Log.e("tom", "Не удалось найти кептчу картинку на странице билайна");
//			throw new Exception("Не удалось найти кептчу картинку на странице билайна");
		}
			
		Element elem = elems.first();
		
		return new CaptchaParams("beeline key", "https://www.beeline.ru/" + elem.attr("src"));
	}

}
