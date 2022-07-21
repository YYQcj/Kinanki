package cn.com.sai.kindle.anki.subcommand;

import picocli.CommandLine.Command;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Callable;

import static cn.com.sai.kindle.anki.tool.PropertyUtil.getProperties;

/**
 * @author cj
 * @date 2022/7/20 23:39
 * @description
 */
@Command(name = "show")
public class Show implements Callable<Integer> {
    @Override
    public Integer call() {
        Properties properties = getProperties();
        String books = properties.getProperty("books");
        System.out.println(Arrays.toString(books.split(";")));
        return 0;
    }
}
