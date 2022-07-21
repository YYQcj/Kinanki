package cn.com.sai.kindle.anki.subcommand;

import picocli.CommandLine.*;

import java.util.concurrent.Callable;

import static cn.com.sai.kindle.anki.tool.PropertyUtil.addBookProperty;

/**
 * @author cj
 * @date 2022/7/21 20:45
 * @description
 */
//@Command(name = "config")
public class Config implements Callable<Integer> {
//    @Option(names = {"-pp", "--propertypath"},
//            description = "指定生词本书名", required = true)
//    private String property;

    @Override
    public Integer call() {
        return 0;
    }
}
