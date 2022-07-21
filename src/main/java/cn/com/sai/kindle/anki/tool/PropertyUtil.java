package cn.com.sai.kindle.anki.tool;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static cn.com.sai.kindle.anki.global.AppConst.*;

/**
 * @author cj
 * @date 2022/7/20 23:42
 * @description
 */
public class PropertyUtil {
    public static Properties getProperties() {
        InputStream in = null;
        try {
            in = new FileInputStream(CONFIG_FILE);
        } catch (FileNotFoundException e) {
            File file = new File(CONFIG_FILE);
            try {
                if (file.createNewFile()) {
                    System.out.println(String.format("create new %s", CONFIG_FILE));
                }
                in = new FileInputStream(CONFIG_FILE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        Properties properties = null;
        try {
            properties = new Properties();
            properties.load(in);
            if (properties.isEmpty()) {
                properties.setProperty(BOOK, "");
                properties.setProperty(PATH, "");
                FileOutputStream out = new FileOutputStream(CONFIG_FILE);
                properties.store(out, "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void changePropertyPath(String propertyPath) {
//        Properties properties = getProperties();
//        String filePath = properties.getProperty(PATH) + CONFIG_FILE;
//        File file = new File(filePath);
//        properties.setProperty(PATH, propertyPath);
//        filePath = properties.getProperty(PATH) + CONFIG_FILE;
//        try {
//            FileOutputStream out = new FileOutputStream(CONFIG_FILE);
//            properties.store(out, "");
//            file.delete();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void addBookProperty(String bookTitle) {
        Properties properties = getProperties();
        String bookTitles = properties.getProperty(BOOK);
        List<String> bookList = new ArrayList<>(Arrays.asList(bookTitles.split(";")));
        if (bookList.size() == 1 && bookList.get(0).equalsIgnoreCase("")) {
            bookList = new ArrayList<>();
        }
        if (bookList.contains(bookTitle)) {
            return;
        }
        bookList.add(bookTitle);
        bookTitles = StringUtils.join(bookList, ";");
        properties.setProperty(BOOK, bookTitles);
        try {
            properties.store(new FileOutputStream(CONFIG_FILE), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
