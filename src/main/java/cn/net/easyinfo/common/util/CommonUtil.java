package cn.net.easyinfo.common.util;

import cn.net.easyinfo.entity.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@Slf4j
public class CommonUtil {
	/**
	 * 获得request
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	/**
	 * 获得value
	 * @return
	 */
	public static String getValue(String key){
		return (String)getRequest().getParameter(key);
	}
	/**
	 * 获得session
	 * @return
	 */
	public static HttpSession getSession(){
		return getRequest().getSession();
	}
	/**
	 * 获得项目路径
	 * @return
	 */
	public static String getRealPath(){
		return getSession().getServletContext().getRealPath("/");
	}
	/**
	 * 向request中添加数据
	 * @param name
	 * @param obj
	 */
	public static void setRequestAttribute(String name,Object obj){
		getRequest().setAttribute(name, obj);
	}
	/**
	 * 获得session中属性
	 * @param name
	 * @return
	 */
	public static Object getSessionAttribute(String name){
		Object obj = getSession().getAttribute(name);
		return obj;
	}
	/**
	 * 得到用户信息
	 * @return
	 */
	public static LoginUser getUser(){
		if(getRequest().getParameter("token")==null)
			return (LoginUser) getRequest().getSession().getAttribute(ConfigInfo.SESSION_USER);
		else
			return ConfigInfo.sToken.get(getRequest().getParameter("token"));
	}
	/**
	 * 得到所有传递的参数以Map的形式保存
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParameterMap() {
		Map<String, String[]> map = (Map<String, String[]>) getRequest().getParameterMap();
		System.out.println(map);
		Map<String, Object> result = new HashMap<String, Object>();
		Set<Entry<String, String[]>> set = map.entrySet();
		StringBuffer params = new StringBuffer("params: ");
		for (Entry<String, String[]> entry : set) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			String value = StringUtils.join(values, "-");
			result.put(key, value);
			params.append(key + "=" + value + " ");
		}
		return result;
	}
	/**
	 * 得到所有传递的参数以Object的形式保存
	 */
	@SuppressWarnings("unchecked")
	public static Object getParameterObject(Class<?> cls) {
		Object entity = null;
		try {
			entity = Class.forName(cls.getName()).newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		Map<String, String[]> map = (Map<String, String[]>) getRequest().getParameterMap();
		Set<Entry<String, String[]>> set = map.entrySet();
		StringBuffer params = new StringBuffer();
		StringBuffer noParams = new StringBuffer();
		for (Entry<String, String[]> entry : set) {
			String key = entry.getKey();
			if(key.contains("inputEl")) continue;
//			log.debug(key);
			String[] values = entry.getValue();
			String value = StringUtils.join(values, "-");
//			log.debug(value);
			if(!value.equals("undefined")){
				try {
					log.debug(StringUtil.toFirstUpperCase(key));
					Class<?> type = cls.getMethod("get" + StringUtil.toFirstUpperCase(key)).getReturnType();
					Method method = cls.getMethod("set"	+ StringUtil.toFirstUpperCase(key), type);
//					log.debug(value+"----"+type);
					method.invoke(entity, type.cast(StringUtil.castString(value,type)));
					params.append(key + "=" + value + " ");
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					noParams.append(key + "=" + value + " ");
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		log.debug("params: " + params.toString());
		if (noParams.toString().length() > 0) {
			log.warn("no params: " + noParams.toString());
		}
		return entity;
	}
	
}
