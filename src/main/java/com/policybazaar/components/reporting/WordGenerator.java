package com.policybazaar.components.reporting;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WordGenerator {

    public WordGenerator() {
    }

    public static String GenerateDoc(String TC_ID) throws Exception {
        File theDir = new File(".\\src\\main\\resources\\com\\policybazaar\\results\\doc\\");
        if (!theDir.exists()) {
            System.out.println("creating directory: .\\src\\main\\resources\\com\\policybazaar\\results\\doc\\");
            boolean result = false;

            try {
                theDir.mkdir();
                result = true;
            } catch (Exception var10) {
                var10.printStackTrace();
            }
            if (result) {
                System.out.println("DIR created");
            }
        }
        XWPFDocument document = new XWPFDocument();
        String timeStamp =(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(Calendar.getInstance().getTime());
        String timeStamp2 = (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(Calendar.getInstance().getTime());
        String filename = ".\\src\\main\\resources\\com\\policybazaar\\results\\doc\\" + TC_ID + "_"+ timeStamp2 + ".docx";
        FileOutputStream out = new FileOutputStream(new File(filename));
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun paragraphOneRunOne = paragraph.createRun();
        paragraphOneRunOne.setBold(true);
        paragraphOneRunOne.setFontSize(20);
        paragraphOneRunOne.setText(TC_ID);
        paragraphOneRunOne.addBreak();
        paragraphOneRunOne.addBreak();
        paragraphOneRunOne.addBreak();
        XWPFRun paragraphOneRun2 = paragraph.createRun();
        paragraphOneRun2.setBold(true);
        paragraphOneRun2.setItalic(true);
        paragraphOneRun2.setFontSize(15);
        paragraphOneRun2.setText("Execution Started:" + timeStamp);
        paragraphOneRun2.addBreak();
        document.write(out);
        out.flush();
        out.close();
        return filename;
    }
    public static void endTest(String fileID) throws Exception{
        String timeStamp = (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(Calendar.getInstance().getTime());
        FileInputStream fs = new FileInputStream(new File(fileID));
        XWPFDocument document = new XWPFDocument(fs);
        XWPFParagraph tmpParagraph = document.createParagraph();
        tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun tmpRun = tmpParagraph.createRun();
        tmpRun.setText("Test Case Status: " + ExtentTestManager.getTest().getRunStatus());
        tmpRun.setBold(true);
        tmpRun.setFontSize(20);
        tmpRun.setColor("000099");
        tmpRun.addBreak();
        XWPFRun paragraphOneRun2 = tmpParagraph.createRun();
        paragraphOneRun2.setBold(true);
        paragraphOneRun2.setItalic(true);
        paragraphOneRun2.setFontSize(15);
        paragraphOneRun2.setText("Execution Completed: " + timeStamp);
        FileOutputStream fos = new FileOutputStream(fileID);
        document.write(fos);
        fos.close();
    }
    public static void LogPass (String Msg, String fileID){
        try {
            FileInputStream fs = new FileInputStream(new File(fileID));
            XWPFDocument document = new XWPFDocument(fs);
            XWPFParagraph tmpParagraph = document.createParagraph();
            XWPFRun tmpRun = tmpParagraph.createRun();
            tmpRun.addBreak();
            tmpRun.setText(Msg);
            tmpRun.setBold(true);
            tmpRun.setFontSize(12);
            tmpRun.setColor("000099");
            FileOutputStream fos = new FileOutputStream(fileID);
            document.write(fos);
            fos.close();
        }catch (Throwable var7){
            var7.printStackTrace();
        }
    }
    public static void LogPass_Img(String Msg, String fileID, String img){
        try {
            FileInputStream fs = new FileInputStream(new File(fileID));
            XWPFDocument document = new XWPFDocument(fs);
            XWPFParagraph tmpParagraph = document.createParagraph();
            XWPFRun tmpRun = tmpParagraph.createRun();
            tmpRun.addBreak();
            tmpRun.setText(Msg);
            tmpRun.setBold(true);
            tmpRun.setFontSize(12);
            tmpRun.setColor("000099");
            FileOutputStream fos = new FileOutputStream(fileID);
            document.write(fos);
            fos.close();
            Log_Img(Msg, fileID, img);
        }catch (Throwable var8){
            var8.printStackTrace();
        }
    }

    public static void LogFail(String Msg, String fileID, String img){
        try {
            FileInputStream fs = new FileInputStream(new File(fileID));
            XWPFDocument document= new XWPFDocument(fs);
            XWPFParagraph tmpParagraph = document.createParagraph();
            XWPFRun tmpRun = tmpParagraph.createRun();
            tmpRun.addBreak();
            tmpRun.setText(Msg);
            tmpRun.setBold(true);
            tmpRun.setFontSize(12);
            tmpRun.setColor("FF0000");
            FileOutputStream fos = new FileOutputStream(fileID);
            document.write(fos);
            fos.close();
            Log_Img(Msg, fileID, img);
        }catch (Throwable var8){
            var8.printStackTrace();
        }
    }

    public static void Log_Img(String Msg, String fileID, String img){
        try {
            CustomXWPFDocument document = new CustomXWPFDocument(new FileInputStream(new File(fileID)));
            FileOutputStream fos = new FileOutputStream(new File(fileID));
            String blipId = document.addPictureData(new FileInputStream(new File(img)), 6);
            document.createPicture(blipId,document.getNextPicNameNumber(6),600,600);
            document.write(fos);
            fos.flush();
            fos.close();
        }catch (Throwable var6){}
    }

}
