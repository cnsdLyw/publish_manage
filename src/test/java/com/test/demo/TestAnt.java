package com.test.demo;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestAnt {
	public static void main(String[] args) {
		//初始化一个httpclient
        HttpClient client = new DefaultHttpClient();
        //我们要爬取的一个地址，这里可以从数据库中抽取数据，然后利用循环，可以爬取一个URL队列
        String url="https://item.jd.com/12215146.html";
        //抓取的数据
        //List<JdModel> bookdatas=URLFecter.URLParser(client, url);
        try {
			URLParser(client, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HttpResponse getRawHtml(HttpClient client, String personalUrl) {
        //获取响应文件，即html，采用get方法获取响应数据
        HttpGet getMethod = new HttpGet(personalUrl);
        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,HttpStatus.SC_OK, "OK");
        try {
            //执行get方法
            response = client.execute(getMethod);
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            // getMethod.abort();
        }
        return response;
    }
	
	
	 /*public static List<JdModel> getData (String html) throws Exception{
	        //获取的数据，存放在集合中
	        List<JdModel> data = new ArrayList<JdModel>();
	        //采用Jsoup解析
	        Document doc = Jsoup.parse(html);
	        //获取html标签中的内容
	        Elements elements=doc.select("ul[class=gl-warp clearfix]").select("li[class=gl-item]");
	        for (Element ele:elements) {
	            String bookID=ele.attr("data-sku");
	            String bookPrice=ele.select("div[class=p-price]").select("strong").select("i").text();
	            String bookName=ele.select("div[class=p-name]").select("em").text();
	            //创建一个对象，这里可以看出，使用Model的优势，直接进行封装
	            JdModel jdModel=new JdModel();
	            //对象的值
	            jdModel.setBookID(bookID);
	            jdModel.setBookName(bookName);
	            jdModel.setBookPrice(bookPrice);
	            //将每一个对象的值，保存到List集合中                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	            data.add(jdModel);
	        }
	        //返回数据
	        return data;
	    }*/
	
	public static void URLParser (HttpClient client, String url) throws Exception {
        HttpResponse response = getRawHtml(client, url);      
        //获取响应状态码
        int StatusCode = response.getStatusLine().getStatusCode();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
        System.out.println("StatusCode  "+StatusCode);
        if(StatusCode==200){
        	 String entity = EntityUtils.toString (response.getEntity(),"utf-8");   
        	 //System.out.println("entity ---- "+entity);
             Document doc = Jsoup.parse(entity);
             //获取html标签中的内容
             Elements elements=doc.select("div[class=mc]").select("div[class=p-parameter]").select("ul[class=p-parameter-list]");
             for (Element ele:elements) {
            	 Elements elements1 = ele.select("li");
            	 for (Element ele1:elements1) {
            		 String str[] = ele1.text().split("：");
            		 System.out.println(str[0]+"       "+str[1]);
            	 }
             }
        }
    }
}
