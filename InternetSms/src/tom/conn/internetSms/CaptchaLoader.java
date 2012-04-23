package tom.conn.internetSms;

import java.io.IOException;

public interface CaptchaLoader {

	CaptchaParams getCaptchaParams()  throws IOException;

}
