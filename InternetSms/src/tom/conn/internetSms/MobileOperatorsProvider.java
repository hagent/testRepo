package tom.conn.internetSms;

import java.util.ArrayList;

import tom.conn.internetSms.Beeline.BeelineCaptchaLoader;
import tom.conn.internetSms.Beeline.BeelineSender;
import tom.conn.internetSms.Megafon.MegafonCaptchaLoader;
import tom.conn.internetSms.Megafon.MegafonSender;

public class MobileOperatorsProvider {

	public static ArrayList<MobileOperators> getOperators(String prefix) {
		// TODO реализовать определение принадлежности префикса оператору
		return new ArrayList<MobileOperators>(){{
			add( MobileOperators.Beeline);
		}};
	}

	public static CaptchaLoader getCaptchaLoader(MobileOperators operator) throws Exception {
		if(operator == MobileOperators.Megafon)
			return new MegafonCaptchaLoader();
		
		if(operator == MobileOperators.Beeline)
			return new BeelineCaptchaLoader();
		
		throw new Exception("оператор " + operator + "не поддерживается");
	}

	public static MessageSender getMessageSender(MobileOperators operator) {
		if(operator == MobileOperators.Megafon)
			return new MegafonSender();
		
		if(operator == MobileOperators.Beeline)
			return new BeelineSender();
		
		return null;
	}

}

/*
<option value="7926" name="prefix_f">+7 926</option>
<option value="7925" name="prefix_f">+7 495(925*)</option>
<option value="7921" name="prefix_f">+7 812(921*)</option>
<option value="7931" name="prefix_f">+7 931</option>
<option value="7920" name="prefix_f">+7 920</option>
<option value="7922" name="prefix_f">+7 922</option>
<option value="7923" name="prefix_f">+7 923</option>
<option value="7924" name="prefix_f">+7 924</option>
<option value="7927" name="prefix_f">+7 927</option>
<option value="7928" name="prefix_f">+7 928</option>
<option value="7937" name="prefix_f">+7 937</option>
<option value="7929" name="prefix_f">+7 929</option>
<option value="7930" name="prefix_f">+7 930</option>
<option value="7932" name="prefix_f">+7 932</option>
<option value="7938" name="prefix_f">+7 938</option>
<option value="7933" name="prefix_f">+7 933</option>
*/
