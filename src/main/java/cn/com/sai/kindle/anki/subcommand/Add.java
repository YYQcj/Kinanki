package cn.com.sai.kindle.anki.subcommand;

import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

import static cn.com.sai.kindle.anki.tool.PropertyUtil.*;

/**
 * @author cj
 * @date 2022/7/20 23:38
 * @description
 */
@Command(name = "add")
public class Add implements Callable<Integer> {
    @Option(names = {"-b", "--book"},
            description = "指定生词本书名", required = false)
    private String bookTitle;

    @Option(names = {"-pp", "--propertypath"},
            description = "配置文件路径", required = false)
    private String propertyPath;

    @Override
    public Integer call() {
        if (propertyPath != null) {
            changePropertyPath(propertyPath);
        }
        if (bookTitle != null) {
            addBookProperty(bookTitle);
        }
        return 0;
    }
}
