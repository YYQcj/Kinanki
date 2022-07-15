package cn.com.sai.kindle.anki.tool;

import cn.com.sai.kindle.anki.data.Vocab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author cj
 * @date 2022/6/18 17:54
 * @description
 */
public class TXT {
    public static void writeToTxtFile(String title, List<Vocab> vocabList) {
        String path = title + ".txt";
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //如果没有文件就创建
//        if (!file.isFile()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            file.createNewFile()
//        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Vocab vocab : vocabList){
            try {
                writer.write(vocab + "\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
