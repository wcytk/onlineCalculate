package com.www.calculate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/*
 * 直接使用可以在文件末尾写入新的文本
 */

public class FileOutput {
//	public static void main(String[] args) {
//		FileOutput fo = new FileOutput();
//		fo.output("abc");
//	}

    public FileOutput() {
        try {
            File file = new File("../result.txt");

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void output(String str) {
        try {
            File file = new File("../files/result.txt");
            FileWriter fw = new FileWriter(file, true);
            fw.write(str + "\r\n");
            fw.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void newOutput(String str) {
        try {
            File file = new File("../files/result.txt");
            FileWriter fw = new FileWriter(file);
            fw.write(str + "\r\n");
            fw.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
