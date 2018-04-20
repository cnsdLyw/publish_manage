package com.test.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.UUID;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class TestZip {
	public static void main(String[] args) {
		
		//压缩
		/*File folder = new File("C:/tttttttttt");
		File[] fileList = folder.listFiles();
		System.out.println("fileList  "+fileList.length+"  "+folder.getPath());
		String fileName = UUID.randomUUID().toString() + ".zip";
		String outFilePath = "C:/tttttttttt" + File.separator + fileName;
		File file = new File(outFilePath);
		FileOutputStream outStream;
		try {
			outStream = new FileOutputStream(file);
			ZipOutputStream toClient = new ZipOutputStream(outStream);
			toClient.setEncoding("gbk");
			try {
				ZipFileDownloadUtil.zipFile(folder.getPath().length(),fileList, toClient);// 打包压缩文件
			} catch (Exception e) {
				e.printStackTrace();
			}
			toClient.close();
			outStream.close();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
			//file.renameTo("aaaqasdas.jonix");
			//String zipName = "导出文件_" + sdf.format(new Date());
			//ZipFileDownloadUtil.downloadZip(file, response, zipName);// 下载文件
		} catch (Exception e1) {
			e1.printStackTrace();
		}*/
		
		//解压
		
		/*try {
			unZipFiles(new File("C:/tttttttttt/bbb/tttttttttt.jonix"),"C:/tttttttttt/bbb/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String str =UUID.randomUUID().toString() ;
		System.out.println("str "+str);	String str1 =UUID.randomUUID().toString() ;
		System.out.println("str "+str1);	String str2 =UUID.randomUUID().toString() ;
		System.out.println("str "+str2);	String str3 =UUID.randomUUID().toString() ;
		System.out.println("str "+str3);	String str4 =UUID.randomUUID().toString() ;
		System.out.println("str "+str4);	String str5 =UUID.randomUUID().toString() ;
		System.out.println("str "+str5);	String str6 =UUID.randomUUID().toString() ;
		System.out.println("str "+str6);	String str7 =UUID.randomUUID().toString() ;
		System.out.println("str "+str7);	String str8 =UUID.randomUUID().toString() ;
		System.out.println("str "+str8);	String str9 =UUID.randomUUID().toString() ;
		System.out.println("str "+str9);	String str10 =UUID.randomUUID().toString() ;
		System.out.println("str "+str10);	String str11 =UUID.randomUUID().toString() ;
		System.out.println("str "+str11);	String str12 =UUID.randomUUID().toString() ;
		System.out.println("str "+str12);	String str13 =UUID.randomUUID().toString() ;
		System.out.println("str "+str13);	String str14 =UUID.randomUUID().toString() ;
		System.out.println("str "+str14);	String str15 =UUID.randomUUID().toString() ;
		System.out.println("str "+str15);	String str16 =UUID.randomUUID().toString() ;
		System.out.println("str "+str16);
		
	}
	
    public static void unZipFiles(File zipFile,String descDir)throws IOException{  
        File pathFile = new File(descDir);  
        if(!pathFile.exists()){  
            pathFile.mkdirs();  
        }  
        ZipFile zip = new ZipFile(zipFile);  
        for(Enumeration entries = zip.getEntries();entries.hasMoreElements();){  
            ZipEntry entry = (ZipEntry)entries.nextElement();  
            String zipEntryName = entry.getName();  
            InputStream in = zip.getInputStream(entry);  
            String outPath = (descDir+zipEntryName).replaceAll("\\*", "/");;  
            //判断路径是否存在,不存在则创建文件路径  
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
            if(!file.exists()){  
                file.mkdirs();  
            }  
            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
            if(new File(outPath).isDirectory()){  
                continue;  
            }  
            //输出文件路径信息  
            System.out.println(outPath);  
              
            OutputStream out = new FileOutputStream(outPath);  
            byte[] buf1 = new byte[1024];  
            int len;  
            while((len=in.read(buf1))>0){  
                out.write(buf1,0,len);  
            }  
            in.close();  
            out.close();  
            }  
        System.out.println("******************解压完毕********************");  
    }  
}
