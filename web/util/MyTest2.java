package com.shengsheng.web.util;


import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.xml.sax.SAXException;

import com.shengsheng.checkon.entity.Device;

/**
 * 
 * @author LiPeiTong
 * 2019年3月18日 下午8:38:08
 * <tr>获取在线设备</tr>
 */
public class MyTest2 {

	/**
	 * <tr>获取当前设备</tr>
	 * @param password
	 * @return List<Device>
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws SAXException
	 */
//	public static void main(String[] args) throws InterruptedException, IOException, SAXException {
//		getOnLineDevices("123456");
//	}
	public static List<Device> getOnLineDevices(String password) throws InterruptedException, IOException, SAXException {
		
		//使用System.setProperty方法设置对应的系统属性
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\lenovo\\Desktop\\wl509\\src\\main\\java\\com\\shengsheng\\web\\util\\geckodriver.exe");
		
		System.setProperty ( "webdriver.firefox.bin" , "D:\\software\\Firefox\\firefox.exe" );
		FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
	    firefoxOptions.setBinary(firefoxBinary);
	    FirefoxDriver driver =  new FirefoxDriver(firefoxOptions);
		
		//隐式等待
		driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
		
		//在当前浏览器窗口以GET方式加载一个新的页面 
		driver.get("http://192.168.1.1");
		//当使用ID找到lgPwd标签，并点击
		WebElement passwordElement =  driver.findElement(By.id("lgPwd"));
		passwordElement.click();
		//强制等待
		Thread.sleep(1000);
		//System.out.println(password.getTagName());
		passwordElement.clear();
		Thread.sleep(1000);
		passwordElement.sendKeys(password);
		Thread.sleep(1000);
	    driver.findElement(By.id("loginSub")).click();
	    Thread.sleep(1000);
	    driver.findElement(By.id("routerSetMbtn")).click();
	    Thread.sleep(1000);
	    driver.findElement(By.id("dhcpServer_rsMenu")).click();
	    Thread.sleep(1000);
	    WebElement  table = driver.findElement(By.id("dhcpLeaseTbl"));
	    List<WebElement> trList1 =table.findElements(By.xpath("//tbody/tr"));
	   	List<Device> deviceList = new ArrayList<>();
	   	
		if (trList1.size() < 10) {
			List<WebElement> trList = table.findElements(By.xpath("//tbody/tr[@style='display: table-row;']"));
			System.out.println("共有"+(trList1.size()-1)+"台设备在线");
			int i = 0;
			for (WebElement tr : trList) {
				i++;
				if(i==1) continue;
				System.out.println();
				List<WebElement> tdList = tr.findElements(By.tagName("td"));
				Device device = new Device();
				String name = "";
				name = tdList.get(0).getAttribute("title");
				System.out.println("一页的设备名字:"+name);
				device.setDname(name);

				String mac = "";
				mac = tdList.get(1).getAttribute("title");
				System.out.println("一页的马克地址："+mac);
				device.setMac(mac);
				
				deviceList.add(device);
			}
		}else {
			    WebElement pageListDiv = driver.findElement(By.id("dhcpLeaseTblpagIngList"));
			    System.out.println("多页输出=" + pageListDiv.toString());
			    List<WebElement> spanList = pageListDiv.findElements(By.xpath("//*[@class='ListSpan']"));
			    WebElement temp1 = pageListDiv.findElement(By.className("listSpanS"));
			    spanList.add(temp1);
			    System.out.println("总页数：" + spanList.size());
			    int i = 0;
				for (WebElement span : spanList) {
					i++;
					List<WebElement> trList = table.findElements(By.xpath("//tbody/tr[@style='display: table-row;']"));
					System.out.println();
					System.out.println("第"+i+"页共"+trList.size()+"台设备");
					for (WebElement tr : trList) {
						System.out.println();
						List<WebElement> tdList = tr.findElements(By.tagName("td"));
						Device device = new Device();
						String name = "";
						if (tdList.get(0) != null) {
							name = tdList.get(0).getAttribute("title");
							System.out.println("设备名："+name);
						}
						device.setDname(name);

						String mac = "";
						if (tdList.get(1) != null) {
							mac = tdList.get(1).getAttribute("title");
							System.out.println("设备马克："+mac);
						}
						device.setMac(mac);

						deviceList.add(device);
					}
					span.click();
					Thread.sleep(1000);
				}
		}
	    
	    driver.close();
	    return deviceList;
	}
	
	
}
