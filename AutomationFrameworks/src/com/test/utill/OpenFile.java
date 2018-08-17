package com.test.utill;
import java.io.IOException;

public class OpenFile {
    public static void open(String url){
        try {
            String[] cmd = new String[5];
            cmd[0] = "cmd";
            cmd[1] = "/c";
            cmd[2] = "start";
            cmd[3] = " ";
            cmd[4] = url;
            Runtime.getRuntime().exec(cmd);
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) {
        ExcelCells.getLastCellNum("e://abc.xlsx");
        ExcelFunction.getExcelCells("e://abc.xlsx");
        OpenFile.open("e://abc.xlsx");
    }*/
}