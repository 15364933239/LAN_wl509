package com.shengsheng.web.util;


import java.io.IOException;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import com.shengsheng.checkon.entity.Device;

import net.sf.json.JSONObject;

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
     * 定时计算。每隔1分钟执行一次
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws InterruptedException 
     */    
	@Scheduled(cron="0 */3 * * * ?") 
    public void show() throws InterruptedException, IOException, SAXException{  
		
		System.out.println("当前时间="+System.currentTimeMillis());
       
		List<Device> deviceList = MyTest2.getOnLineDevices("123456");
		
		for(Device device:deviceList){
			JSONObject object = JSONObject.fromObject(device);
			RestTemplate restTemplate = RestTemplateUtil.getInstance("utf-8");
			String url = "http://localhost:9999/wl509/interfaceTest/Demo";
			String result = restTemplate.postForObject(url, object, String.class);
			System.out.println(result);
	    }
    }  
}
