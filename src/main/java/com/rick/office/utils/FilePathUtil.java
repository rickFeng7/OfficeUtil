package com.rick.office.utils;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Rick
 * @date 2019/10/30
 * @description 文件路劲获取
 */
public class FilePathUtil {
    /**
     * 设置一个全局动态数组，来存放文件路径,主要遍历文件夹，包含所有子文件夹、文件的情况时，用到递归，所以要这样设置
     */
    private static ArrayList<String> dirAllStrArr = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        File dirFile = new File("D:\\Develop\\TestData\\officeUtil\\source");
        System.out.println("无子文件夹路径:" + dir(dirFile));
        dirAll(dirFile);
        System.out.println("有子文件夹路径:" + dirAllStrArr);
    }

    /**
     * 这里是仅仅查询当前路径下的所有文件夹、文件并且存放其路径到文件数组
     * 由于遇到文件夹不查询其包含所有子文件夹、文件，因此没必要用到递归
     */
    public static ArrayList<String> dir(File dirFile) {
        ArrayList<String> dirStrArr = new ArrayList<String>();

        if (dirFile.exists()) {
            // 直接取出利用listFiles()把当前路径下的所有文件夹、文件存放到一个文件数组
            File[] files = dirFile.listFiles();
            assert files != null;
            for (File file : files) {
                // 如果传递过来的参数dirFile是以文件分隔符，也就是/或者\结尾，则如此构造
                if (dirFile.getPath().endsWith(File.separator)) {
                    dirStrArr.add(dirFile.getPath() + file.getName());
                } else {
                    // 否则，如果没有文件分隔符，则补上一个文件分隔符，再加上文件名，才是路径
                    dirStrArr.add(dirFile.getPath() + File.separator
                            + file.getName());
                }
            }
        }
        return dirStrArr;
    }

    /**
     * 文件夹里还有文件夹情况
     */
    private static void dirAll(File dirFile) {
        if (dirFile.exists()) {
            File[] files = dirFile.listFiles();
            assert files != null;
            for (File file : files) {
                // 如果遇到文件夹则递归调用。
                if (file.isDirectory()) {
                    // 递归调用
                    dirAll(file);
                } else {
                    // 如果遇到文件夹则放入数组
                    if (dirFile.getPath().endsWith(File.separator)) {
                        dirAllStrArr.add(dirFile.getPath() + file.getName());
                    } else {
                        dirAllStrArr.add(dirFile.getPath() + File.separator
                                + file.getName());
                    }
                }
            }
        }
    }
}
