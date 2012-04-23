package tom.conn.internetSms;

public class PhoneNumber {
	private String prefix;
	private String number;
	private String originalPhoneNumber;
	
	public PhoneNumber(String fullPhoneNumber){
		originalPhoneNumber = fullPhoneNumber;
		
		String workPhoneNumber = fullPhoneNumber;
		
		workPhoneNumber = workPhoneNumber.replace("-", "");
		if(workPhoneNumber.charAt(0) == '+')
			workPhoneNumber = workPhoneNumber.replaceFirst("+", "");
		
		//TODO: получить код города
		if(workPhoneNumber.length() == 7)
			workPhoneNumber = "7495" + workPhoneNumber;
		
		if(workPhoneNumber.charAt(0) == '8')
			workPhoneNumber = workPhoneNumber.replaceFirst("8", "7");
		
		prefix =  workPhoneNumber.substring(0, 4);
		number = workPhoneNumber.substring(4, workPhoneNumber.length());
	}

	public String getPrefix() {
		return prefix;
	}

	public String getNumber() {
		return number;
	}

	public String getOriginalPhoneNumber() {
		return originalPhoneNumber;
	}
}
