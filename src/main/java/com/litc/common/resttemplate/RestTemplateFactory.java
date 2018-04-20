package com.litc.common.resttemplate;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.litc.common.util.StringUtil;
import com.litc.common.util.Constant;

public class RestTemplateFactory {

	private static final Logger logger;
	private static RestTemplate restTemplate;

	/**
	 * get方式
	 * 
	 * @param methodName
	 *            方法名称
	 * @param classz
	 *            返回参数类型
	 * @return 传入的参数类型
	 */
	public static <T> T getForObj(String methodName, Class<T> classz) {
		return getForObjByUrl(Constant.URL + "/" + methodName, classz);
	}

	/**
	 * get方式
	 * 
	 * @param Url
	 *            接口地址
	 * @param classz
	 *            返回参数类型
	 * @return 传入的参数类型
	 */
	public static <T> T getForObjByUrl(String Url, Class<T> classz) {
		long time1 = System.currentTimeMillis();
		T obj = null;
		try {
			obj = classz.newInstance();
			obj = restTemplate.getForObject(Url, classz);
		} catch (Exception e) {
			logger.error("创建对象失败" + classz, e);
			e.printStackTrace();
		}
		long time2 = System.currentTimeMillis();
		logger.info("调用接口" + Url + "消耗时间" + (time2 - time1) + "ms");
		return obj;
	}

	/**
	 * get方式 方法自动拼接map成传入参数
	 * 
	 * @param methodName方法名称
	 * @param classz
	 *            返回参数类型
	 * @param paramMap
	 *            传入参数
	 * @return 传入的参数类型
	 */
	public static <T> T getForObj(String methodName, Class<T> classz,
			Map<String, ?> paramMap) {
		return getForObjByUrl(Constant.URL + "/" + methodName, classz, paramMap);
	}

	/**
	 * get方式 方法自动拼接map成传入参数
	 * 
	 * @param methodName方法名称
	 * @param classz
	 *            返回参数类型
	 * @param paramMap
	 *            传入参数
	 * @return 传入的参数类型
	 */
	public static <T> T getForObjByUrl(String Url, Class<T> classz,
			Map<String, ?> paramMap) {
		long time1 = System.currentTimeMillis();
		if (paramMap != null) {
			T obj = null;
			try {
				String paramStr = StringUtil.getParamStr(paramMap);
				obj = classz.newInstance();
				obj = restTemplate.getForObject(Url + paramStr, classz);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			long time2 = System.currentTimeMillis();
			logger.info("调用接口" + Url + "消耗时间" + (time2 - time1) + "ms");
			return obj;
		} else {
			return getForObjByUrl(Url, classz);
		}
	}

	/**
	 * post方式 方法自动拼接map成传入参数
	 * 
	 * @param method
	 *            接口地址
	 * @param classz
	 *            返回参数类型
	 * @param paramMap
	 *            传入参数
	 * @param paraProMap传入Map
	 * @return 传入的参数类型
	 */
	public static <T> T postForObj(String method, Class<T> classz,
			Map<String, ?> paramMap, Map<String, ?> map) {

		return postForObjByUrl(Constant.URL + "/" + method, classz, paramMap,
				map);
	}

	/**
	 * post方式 方法自动拼接map成传入参数
	 * 
	 * @param Url
	 *            接口地址
	 * @param classz
	 *            返回参数类型
	 * @param paramMap
	 *            传入参数
	 * @param paraProMap传入Map
	 * @return 传入的参数类型
	 */
	public static <T> T postForObjByUrl(String Url, Class<T> classz,
			Map<String, ?> paramMap, Map<String, ?> map) {
		long time1 = System.currentTimeMillis();
		T obj = null;
		try {
			String paramStr = StringUtil.getParamStr(paramMap);
			obj = classz.newInstance();
			obj = restTemplate.postForObject(Url + paramStr, map, classz);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		long time2 = System.currentTimeMillis();
		logger.info("调用接口" + Url + "消耗时间" + (time2 - time1) + "ms");
		return obj;
	}

	/**
	 * post方式 方法自动拼接map成传入参数
	 * 
	 * @param Url
	 *            接口地址
	 * @param classz
	 *            返回参数类型
	 * @param paramMap
	 *            传入参数
	 * @param paraProMap传入Map
	 * @return 传入的参数类型
	 */
	public static <T> T postForObj(String method, Class<T> classz,
			Map<String, ?> paramMap) {

		return postForObjByUrl(Constant.URL + "/" + method, classz, paramMap);
	}

	/**
	 * post方式 方法自动拼接map成传入参数
	 * 
	 * @param Url
	 *            接口地址
	 * @param classz
	 *            返回参数类型
	 * @param paramMap
	 *            传入参数
	 * @param paraProMap传入Map
	 * @return 传入的参数类型
	 */
	public static <T> T postForObjByUrl(String Url, Class<T> classz,
			Map<String, ?> paramMap) {
		long time1 = System.currentTimeMillis();
		T obj = null;
		try {
			String paramStr = StringUtil.getParamStr(paramMap);
			obj = classz.newInstance();
			obj = restTemplate.postForObject(Url + paramStr, null, classz);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		long time2 = System.currentTimeMillis();
		logger.info("调用接口" + Url + "消耗时间" + (time2 - time1) + "ms");
		return obj;
	}

	
	/**
	 * json参数发送请求
	 * @param url
	 * @param json
	 * @return
	 */
	public static Object postForObject(String url, String json) {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType
				.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());

		HttpEntity<String> httpEntity = new HttpEntity<String>(json,
				headers);
		return restTemplate.postForObject(url, httpEntity, String.class);
	}
	
	/**
	 * Object参数发送请求
	 * @param method
	 * @param map
	 * @return
	 */
	public static <T> String postForObject(String method, Object t) {
		return restTemplate.postForObject(Constant.URL +"/"+method, t, String.class);
	}
	
	static {
		logger = LoggerFactory.getLogger(RestTemplateFactory.class);
		restTemplate = new RestTemplate();
		logger.debug("");
	}

}
