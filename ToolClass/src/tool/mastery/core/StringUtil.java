package tool.mastery.core;

public class StringUtil {
	
	
	public static boolean StringIsNull(String str) {
		if(str == null || str.equals("")) {
			return true;
		}
		return false;
	}
	
	public static boolean isExistSpace(String str) {
		char[] lineArray = str.toCharArray();
		for(int i = 0 ; i < lineArray.length ; i ++) {
			String c = String.valueOf(lineArray[i]);
			if(!c.matches("[a-zA-Z_]+")) {
				return true;
			}
		}
		return false;
	}
	
	/** 
	* @Title: changeArrayStringToString 
	* @Description: 将数组转换成String类型字符串 
	* @param @param arrs
	* @param @return   
	* @return String    返回类型 
	* @throws 
	*/ 
	public static String changeArrayStringToString(String[] arrs) {
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0 ; i < arrs.length ; i ++) {
			sb.append(arrs[i] + ",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("]");
		return sb.toString();
	}
	
	/** 
	* @Title: deleteSpace 
	* @Description: 去除大标题中的空格来进行比较 
	* @param @param text
	* @param @return   
	* @return String    返回类型 
	* @throws 
	*/ 
	public static String deleteSpace(String text) {
		StringBuilder sb = new StringBuilder();
		char[] chs = text.toCharArray();
		for(char c : chs) {
			String s = String.valueOf(c);
			if(!s.equals(" ")) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
