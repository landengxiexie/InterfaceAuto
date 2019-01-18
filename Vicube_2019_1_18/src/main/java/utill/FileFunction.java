package utill;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("ALL")
public class FileFunction {
//    获取某个目录下所有文件、文件夹
    public ArrayList<String> getFilesPath(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//              LogFunction.logInfo("文     件：" + tempList[i]);
                files.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
//              LogFunction.logInfo("文件夹：" + tempList[i]);
            }
        }
        LogFunction.logInfo("文  件的数量为：" + files.size());
        LogFunction.logInfo("文件夹的数量为：" + (tempList.length - files.size()));

        return files;
    }

//    从文件路径中获取文件名的方法
    public String getFilesName(String path) {
        String fileName=null;
        ArrayList<String> files = this.getFilesPath(path);
        for (String file : files) {
            File f = new File(file.trim());
            fileName = f.getName();
            LogFunction.logInfo("文件名为：" + fileName);
        }
        return fileName;
    }
//
public String getNewistFilesName(String path) {
    String fileName=null;
    ArrayList<String> files = this.getFilesPath(path);
    BasicFileAttributes b=null;
//    for (String file : files) {
        File f = new File(files.get(0).trim());
        try {
             b=Files.readAttributes(Paths.get("D:\\SVN2\\自动化操作文档.docx"),BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    FileTime fileTime = b.lastModifiedTime();

//    LogFunction.logInfo( b.lastModifiedTime().toString());
        LogFunction.logInfo("文件名为：" + fileName);
//    }
    return fileName;
}
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }
    public static void main(String[] args) {
        FileFunction f = new FileFunction();
//        f.getFiles("G:\\BaiduNetdiskDownload");
        f.getNewistFilesName("G:\\BaiduNetdiskDownload");
    }
}
