package cn.com.sai.kindle.anki;

import cn.com.sai.kindle.anki.data.Vocab;
import cn.com.sai.kindle.anki.data.VocabDBConnector;
import cn.com.sai.kindle.anki.subcommand.Add;
import cn.com.sai.kindle.anki.subcommand.Show;
import cn.com.sai.kindle.anki.tool.TXT;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

import static cn.com.sai.kindle.anki.global.AppConst.BOOK;
import static cn.com.sai.kindle.anki.tool.PropertyUtil.getProperties;

/**
 * @author cj
 * @date 2022/6/18 03:22
 * @description
 */
@Command(name = "kindle生词本转换anki牌组工具", subcommands = {Add.class, Show.class}, mixinStandardHelpOptions = true, version = "Kinanki-1.0.0")
public class KindleWordsToAnkiApplication implements Callable<Integer> {
    @Option(names = {"-p", "--path"},
            description = "kindle 生词本路径", defaultValue = "/Volumes/Kindle/system/vocabulary/vocab.db", required = true)
    private String vocabDbPath;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new KindleWordsToAnkiApplication()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        Properties properties = getProperties();
        String books = properties.getProperty(BOOK);
        VocabDBConnector vocabDBConnector = new VocabDBConnector(vocabDbPath, books);
        Map<String, List<Vocab>> vocabMap = vocabDBConnector.getVocabList();
        for (String title : vocabMap.keySet()) {
            TXT.writeToTxtFile(title, vocabMap.get(title));
        }
        return 0;
    }
}
