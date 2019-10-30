package com.rick.office.utils;

import com.rick.office.vo.ExcelVO;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rick
 * @date 2019/10/30
 * @description word工具类
 */
public class WordUtil {
    public static void main(String[] args) {
        try {
            File dirFile = new File("D:\\Develop\\TestData\\officeUtil\\source");
            ArrayList<String> dir = FilePathUtil.dir(dirFile);
            ArrayList<ExcelVO> excels = new ArrayList<ExcelVO>();
            for (String filePath : dir) {
                ExcelVO excelVO = new ExcelVO();
                String sentence = readWord(filePath);
                String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.lastIndexOf("."));
                System.out.println("文件名:" + fileName);
                excelVO.setFirstRow(fileName);
                System.out.println("句子:" + sentence);
                excelVO.setSecondRow(sentence);
                excels.add(excelVO);
            }
            ExcelUtil.data2Excel(excels);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取段落
     */
    private static String readWord(String path) {
        String buffer = "";
        try {
            if (path.endsWith(".doc")) {
                InputStream is = new FileInputStream(new File(path));
                WordExtractor ex = new WordExtractor(is);
                String[] buffers = ex.getParagraphText();
                for (String s : buffers) {
                    if (s.contains("初始委托资产")) {
                        buffer = s;
                        break;
                    }
                }
                ex.close();
            } else if (path.endsWith(".docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(path);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                List<XWPFParagraph> paragraphs = ((XWPFDocument) extractor.getDocument()).getParagraphs();
                for (XWPFParagraph s : paragraphs) {
                    if (s.getText().contains("初始委托资产")) {
                        buffer = s.getText();
                        break;
                    }
                }
                extractor.close();
            } else {
                System.out.println("此文件不是word文件！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 取句子
     */
    private static String getSentence(String sentence) {
        int a = sentence.indexOf("初始委托资产");
        String newSentence = sentence.substring(a, a + 50);
        int b = newSentence.indexOf("委托", newSentence.indexOf("委托") + 1);
        return newSentence.substring(0, b - 1);
    }
}
