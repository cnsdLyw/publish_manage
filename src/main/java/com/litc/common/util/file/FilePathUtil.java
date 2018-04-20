package com.litc.common.util.file;

import java.util.ArrayList;
import java.util.List;


/**
 * 文件路径帮助类
 * @author lu.j
 * 
 */
public class FilePathUtil
{

  public FilePathUtil()
  {

  }

  /**
   * 获取文件的后缀名
   * 
   * @param filename
   * @return
   */
  public static String getSuffix(String filename)
  {
    return filename.substring(filename.lastIndexOf('.') + 1);
  }

  public static String getPrefix(String filename)
  {
    String str = getFileName(filename);
    return str.substring(0, filename.lastIndexOf('.'));
  }

  /**
   * 给一个字符串返回一个结尾带"\"的文件路径地址。
   * 
   * @param path
   * @return
   */
  public static String getPath(String path)
  {
    if (path == null)
    {
      return null;
    }
    if (path.endsWith("/") || path.endsWith("\\"))
    {
      return path;
    }
    else
    {
      return path + "\\";
    }

  }

  /**
   * 将父目录和子目录组成一个文件目录
   * 
   * @param parentPath
   * @param childPath
   * @return
   */
  public static String getPath(String parentPath, String childPath)
  {

    if (isEmpty(parentPath))
    {
      return null;
    }
    StringBuffer buffer = new StringBuffer();
    buffer.append(getPath(parentPath));
    if (getPath(childPath) != null)
    {
      buffer.append(getPath(childPath));
    }

    return buffer.toString();
  }

  /**
   * 将文件目录和文件名合成一个完整的文件地址。
   * 
   * @param parentPath
   * @param fileName
   * @return
   */
  public static String getFullName(String parentPath, String fileName)
  {
    // System.out.println("in FullName");
    if (isEmpty(parentPath) || isEmpty(fileName))
    {
      return null;
    }
    StringBuffer buffer = new StringBuffer();
    buffer.append(getPath(parentPath)).append(fileName);
    String str = buffer.toString();
    // System.out.println(str);
    return str;
  }

  /**
   * 通过一个文件路径获得一个文件名
   * 
   * @param path
   * @return
   */
  public static String getFileName(String path)
  {
    if (path != null)
    {
      String[] tmp = path.split("[/|\\\\]");
      return tmp[tmp.length - 1];

    }
    else
    {
      return "";
    }
  }

  /**
   * 获取文件夹内某一类的文件的文件名。此类文件形如：<br>
   * 001.jpg<br>
   * 002.jpg<br>
   * .....<br>
   * 
   * @param currentpage
   *          当前显示的页码。
   * 
   * @param prefix
   *          文件的前缀。如上面的"00"
   * @param suffix
   *          文件后缀名称。如上面的"jpg"
   * @param num
   *          文件名不带后缀的显示位数 如上面例子就是"3"
   * @return
   */
  public static String pageCounttoFileName(int currentpage, String prefix,
      String suffix, int num)
  {
    String currentpageStr = "" + currentpage;
    String tempFileName = prefix + currentpageStr;
    int numSize = tempFileName.length();
    String fileName = tempFileName.substring(numSize - num, numSize) + "."
        + suffix;
    return fileName;
  }

  /**
   * 获取某文件全名的路径名
   * 
   * @param path
   * @return
   */
  public static String getFilePath(String path)
  {
    return path.substring(0, path.lastIndexOf("/"));
  }

  private static boolean isEmpty(String str)
  {
    if (str == null || "".equals(str))
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  /**
   * 去除文件名不允许的字符 <>/\|:"*?
   * 
   * @param fileName
   * @return
   */
  public static String filterFileNotAllowedChar(String fileName)
  {
    // <>/\|:"*?
    if (fileName == null || fileName.equals(""))
    {
      return "fileName";
    }

    StringBuffer sb = new StringBuffer();
    int length = fileName.length();

    for (int k = 0; k < length; k++)
    {
      char c = fileName.charAt(k);
      switch (c)
      {
      case '<':
        break;
      case '>':
        break;
      case '/':
        break;
      case '\\':
        break;
      case '|':
        break;
      case ':':
        break;
      case '\"':
        break;
      case '*':
        break;
      case '?':
        break;
      default:
        sb.append(c);
      }
    }

    String f = sb.toString();
    if (f.equals(""))
    {
      f = "fileName";
    }
    return f;
  }

  /**
   * 
   * 拆分设备路径，利用第一个逗号，区分设备名和相对路径 如：spiltDevicePath("ftp_hanpt,a/b/oh,my song.jpg")
   * 
   * @param devicePath
   *          　设备名和文件路径
   * @return
   */
  public static List<String> spiltDevicePath(String devicePath)
  {
    List<String> ls = new ArrayList<String>();
    if (devicePath == null)
      return ls;

    int index = devicePath.indexOf(",");

    if (index > 0)
    {
      String deviceName = devicePath.substring(0, index);
      String path = devicePath.substring(index + 1);

      ls.add(deviceName);
      ls.add(path);
    }
    return ls;
  }
}
