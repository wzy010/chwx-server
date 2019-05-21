package cn.net.easyinfo.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import sun.misc.BASE64Encoder;

public class StringUtil {
	public static void main(String[] args) {
//		String ss = "http://mp.weixin.qq.com/s?__biz=MzA4MDQzMzIxMQ==&mid=400449257&idx=1&sn=60d94353d1fa52438df564e5ee3c6baf&scene=1&srcid=1103MeT3DqldZHWi54hLw3Se&uin=NzI2NTYxODU%3D&key=04dce534b3b035ef817e7819a2729bdbb50ae486d7c4f0eec705d9b450f582523377f78f83616273a03689fa";
//		System.out.println(weixinUrlFormat(ss));
//		String m = "：但是，小伙伴们要注意早晚温差比较大喔";
//		System.out.println(m.replace("：", ""));
		String s = "第一次当制片人就好莱坞大片，老铁你咋那么厉害呢～来来来，周五电影院约起，带上火龙果宝宝";
		System.out.println("------------");
//		System.out.println(encode("https://weibo.com/3288875501/GmcnSf9gk?ref=home&rid=2_0_8_3080949495070340012_6_0&sudaref=weibo.com&type=comment"));
//		System.out.println(decode("https%3A%2F%2Fweibo.com%2F3288875501%2FGmcnSf9gk%3Fref%3Dhome%26rid%3D2_0_8_3080949495070340012_6_0%26sudaref%3Dweibo.com%26type%3Dcomment"));
		System.out.println(encode(s));
		System.out.println(decode("%E7%AC%AC%E4%B8%80%E6%AC%A1%E5%BD%93%E5%88%B6%E7%89%87%E4%BA%BA%E5%B0%B1%E5%A5%BD%E8%8E%B1%E5%9D%9E%E5%A4%A7%E7%89%87%EF%BC%8C%E8%80%81%E9%93%81%E4%BD%A0%E5%92%8B%E9%82%A3%E4%B9%88%E5%8E%89%E5%AE%B3%E5%91%A2%EF%BD%9E%E6%9D%A5%E6%9D%A5%E6%9D%A5%EF%BC%8C%E5%91%A8%E4%BA%94%E7%94%B5%E5%BD%B1%E9%99%A2%E7%BA%A6%E8%B5%B7%EF%BC%8C%E5%B8%A6%E4%B8%8A%E7%81%AB%E9%BE%99%E6%9E%9C%E5%AE%9D%E5%AE%9D"));
		System.out.println("------------");
		
	}
	/**
	 * 【异常处理】Incorrect string value: '\xF0\x90\x8D\x83...' for column... 
	 * Emoji表情字符过滤的Java实现
	 * 替换4个字节的utf-8编码
	 * @param msg
	 * @return
	 */
	public static String IncorrectString(String msg){
		if(msg!=null)
			msg = msg.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
//			msg = msg.replaceAll("[^\\u0000-\\uFFFF]", "");
		return msg;
	}
	public static String encode(String str){
		if(str==null) return str;
		try {
			str = URLEncoder.encode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	public static String decode(String str){
		if(str==null) return str;
		try {
			str = URLDecoder.decode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	public static int getStart(String start){
		int s = 0;
		try {
			s = Integer.parseInt(start);
		} catch (NumberFormatException e) {
			System.out.println("getStart java.lang.NumberFormatException");
		}
		return s;
	}
	public static int getLimit(String limit){
		int s = 15;
		try {
			s = Integer.parseInt(limit);
		} catch (NumberFormatException e) {
			System.out.println("getLimit java.lang.NumberFormatException");
		}
		return s;
	}
	/**
	 * @param str
	 * @return
	 */
	public static String formatStr(String str){
		if(str==null) return str;
		if(str != null) {
			try {
				str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	/**
	 * 字符串去掉重复 1,2,3,2,3,4,5,5
	 * @param str
	 * @return
	 */
	public static String deleteRepeat(String str){
		if(str==null) return str;
		String ag[] = str.split(",");
		Set<String> set = new HashSet<String>();
		for(int i=0;i<ag.length;i++){
			set.add(ag[i]);
		}
		StringBuffer sb = new StringBuffer();
		for(String r : set) {
			sb.append(r);
			sb.append(",");
		}
		return sb.toString().substring(0, sb.toString().length()-1);
	}
	/**
	 * 字符串首字母大写
	 * @param 字符串
	 * @return 首字母大写的字符串
	 */
	public static String toFirstUpperCase(String str) {
		if(str == null || str.length() < 1) {
			return "";
		}
		String start = str.substring(0,1).toUpperCase();
		String end = str.substring(1, str.length());
		return start + end;
	}
	/**
	 * 类型转换
	 */
	public static Object castString(String value, Class<?> cls) {
		String name = cls.getSimpleName();
		Object cast = value;
		if(name.equalsIgnoreCase("Integer")) {
			cast = Integer.parseInt(cast.toString().equals("")?"0":cast.toString());
		}
		if(name.equalsIgnoreCase("Long")) {
			cast = Long.parseLong(cast.toString());
		}
		if(name.equalsIgnoreCase("Short")) {
			cast = Short.parseShort(cast.toString());
		}
		if(name.equalsIgnoreCase("Float")) {
			cast = Float.parseFloat(cast.toString());
		}
		if(name.equalsIgnoreCase("Double")) {
			cast = Double.parseDouble(cast.toString());
		}
		if(name.equalsIgnoreCase("Boolean")) {
			cast = Boolean.parseBoolean(cast.toString());
		}
		return cast;
	}
	/**
	 * 过滤掉字符串中HTML标签
	 * @param input
	 * @return
	 */
	public static String filterHTMl(String input){
		if(input == null) return input;
		String str = input.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "").replaceAll("</[a-zA-Z]+[1-9]?>", "");
		str = str.replaceAll("&nbsp;", "");
		return str;
	}
	/**
	 * 微信url不匹配
	 * @param input
	 * @return
	 */
	public static String weixinUrlFormat(String url){
		if(url.contains("#wechat_redirect") && url.contains("weixin.qq")){
			return url;
		}else{
			int index = url.indexOf("scene");
			if(index>0){
				url = url.substring(0, index);
			}
			url += "#wechat_redirect";
		}
		return url;
	}
	public static String getImageStr(String imgFile) {
//      String imgFile = "e:/11.png";
      InputStream in = null;
      byte[] data = null;
      try {
          in = new FileInputStream(imgFile);
          data = new byte[in.available()];
          in.read(data);
          in.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
      BASE64Encoder encoder = new BASE64Encoder();
//      encoder.encode(data).replaceAll("\r\n", "")
      return encoder.encode(data);
  }
	

}
