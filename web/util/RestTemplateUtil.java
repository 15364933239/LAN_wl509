package com.shengsheng.web.util;

import java.nio.charset.Charset;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author LiPeiTong
 * 2019年3月18日 下午4:46:39
 * <td>创建指定字符集的RestTemplate</td>
 * @param charset
 * @return
 */
public class RestTemplateUtil {
	
	 public static RestTemplate getInstance(String charset) {
	        RestTemplate restTemplate = new RestTemplate();
	        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName(charset)));
	        return restTemplate;
	    }
}
