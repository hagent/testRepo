package tom.conn.internetSms;

public class CaptchaParams {
	
	String key;
	String url;
	
	public CaptchaParams(String key, String url){
		this.key = key;
		this.url = url;		
	}

	public String getKey() {
		return key;
	}

	public String getUrl() {
		return url;
	}

}
