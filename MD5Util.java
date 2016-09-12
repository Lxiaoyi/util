
import java.security.MessageDigest;

public class MD5Util {
	
	private static char  hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * md5加密
	 * @param message
	 * @return
	 */
	public static String digest(String message) {
		if(message == null || "".equals(message)){
			return "";
		}
		try {
			byte[] strTemp = message.getBytes("UTF-8");
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * md5加密
	 * @param message
	 * @param isUpperCase
	 * @return
	 */
	public static String digest(String message, boolean isUpperCase){
		return isUpperCase ? digest(message).toUpperCase() : digest(message);
	}
}
