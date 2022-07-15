package cn.com.sai.kindle.anki;

import cn.com.sai.kindle.anki.data.Vocab;
import cn.com.sai.kindle.anki.data.VocabDBConnector;
import cn.com.sai.kindle.anki.tool.TXT;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

/**
 * @author cj
 * @date 2022/6/18 03:22
 * @description
 */
@Command(name = "kindle生词本转换anki牌组工具", mixinStandardHelpOptions = true, version = "1.0.0")
public class KindleWordsToAnkiApplication implements Callable<Integer> {
    @Option(names = {"-p", "--path"}, description = "kindle 生词本路径", defaultValue = "", required = true)
    private String vocabDbPath;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new KindleWordsToAnkiApplication()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        Properties properties = getProperties();
        String books = properties.getProperty("books");
        if (vocabDbPath.isBlank()) {
            vocabDbPath = searchDbPath();
        }
        VocabDBConnector vocabDBConnector = new VocabDBConnector(vocabDbPath, books);
        Map<String, List<Vocab>> vocabMap = vocabDBConnector.getVocabList();
        for (String title : vocabMap.keySet()) {
            TXT.writeToTxtFile(title, vocabMap.get(title));
        }

        // 先做，然后再优化
        // connect to vocab.db
            // 连接数据库，执行sql命令
            // 将查询结果映射成实体类，查询词典，添加释义，然后按照书名，转化成字符串列表
            // 最后变成一个个txt文件
        // fetch out words with their original sentence by book name
        // add meaning
        // generate .txt file
        return 0;
    }

    private Properties getProperties() {
        InputStream in = KindleWordsToAnkiApplication.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties = null;
        try {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private String searchDbPath() {
        String path = "/Volumes/Kindle/system/vocabulary/vocab.db";
        File file = new File(path);
        if (file.isFile()) {
            return path;
        }
        throw new RuntimeException("no db is found!");
    }
}
