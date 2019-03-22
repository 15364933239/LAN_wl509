package com.shengsheng.web.util;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import com.shengsheng.checkon.entity.Device;
import com.shengsheng.web.contorller.Test;

import net.sf.json.JSONArray;

/**
 * 
 * @author LiPeiTong
 * 2019年3月19日 上午8:50:50
 * <tr>设备集合发送</tr>
 * 	<td></td>
 */
@Component  
public class MyTaskAnnotation {
	
	 /**  
	 * <定时计算。每隔1分钟执行一次>
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ServletException 
     */    
	@Scheduled(cron="0 */3 * * * ?") 
    public void show() throws InterruptedException, IOException, SAXException, ServletException{  
		
		SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");//设置日期格式
       
		List<Device> deviceList = MyTest2.getOnLineDevices("123456");
		for (Device device : deviceList) {
			device.setTime(df.format(new Date()));
		}
		JSONArray json = JSONArray.fromObject(deviceList);
		Test.sendInf(json);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<JSONArray> entity = new HttpEntity<JSONArray>(json,headers);
		System.out.println(entity);
		RestTemplate restTemplate = RestTemplateUtil.getInstance("utf-8");
		String url = "http://localhost:9999/clsMnger/interfaceTest/Demo";
		ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		System.out.println(result);
		
    }  
}
