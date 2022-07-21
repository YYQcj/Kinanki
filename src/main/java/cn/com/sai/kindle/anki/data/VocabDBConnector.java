package cn.com.sai.kindle.anki.data;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cj
 * @date 2022/6/18 16:41
 * @description
 */
public class VocabDBConnector {
    private Connection connection;
    private Statement stmt;
    private String[] booksArray;
    public VocabDBConnector(String path, String books) {
        try {
            File file = new File(path);
            if (!file.isFile()) {
                throw new RuntimeException("no db is found!");
            }
            String url = "jdbc:sqlite:" + path;
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            stmt = connection.createStatement();
            System.out.println("Opened database successfully");
            System.out.println("Connected to the database");
            DatabaseMetaData dm = connection.getMetaData();
            System.out.println("Driver name: " + dm.getDriverName());
            System.out.println("Driver version: " + dm.getDriverVersion());
            System.out.println("Product name: " + dm.getDatabaseProductName());
            System.out.println("Product version: " + dm.getDatabaseProductVersion());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("Fail to open %s", path));
        }
        booksArray = books.split(";");
    }


    public Map<String, List<Vocab>> getVocabList() {
        Map<String, List<Vocab>> titleToWords = new HashMap<>();
        String sql = getSQL();
        System.out.println(sql);
        try {
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                Vocab vocab = new Vocab();
                String title = resultSet.getString("title");
                if (!titleToWords.containsKey(title)) {
                    titleToWords.put(title, new ArrayList<>());
                }
                String word = resultSet.getString("word");
                String usage = resultSet.getString("usage");
                usage = usage.replace(word, "<b><u>" + word + "</u></b>");
                vocab.setWord(word);
                vocab.setStem(resultSet.getString("stem"));
                vocab.setTitle(title);
                vocab.setAuthors(resultSet.getString("authors"));
                vocab.setUsage(usage);
                titleToWords.get(title).add(vocab);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return titleToWords;
    }

    private String getSQL() {
        String sql = "SELECT\n" +
                "              ws.id,\n" +
                "              ws.word,\n" +
                "              ws.stem,\n" +
                "              ws.lang,\n" +
                "              datetime(ws.timestamp * 0.001, 'unixepoch', 'localtime') added_tm,\n" +
                "              lus.usage,\n" +
                "              bi.title,\n" +
                "              bi.authors,\n" +
                "              ws.CATEGORY\n" +
                "            FROM words AS ws LEFT JOIN lookups AS lus ON ws.id = lus.word_key\n" +
                "              LEFT JOIN book_info AS bi ON lus.book_key = bi.id";
        if (booksArray.length == 0) {
            return sql;
        }
        sql += getWHERE();
        return sql;
    }

    private String getWHERE() {
        System.out.println(String.join(",", booksArray));
        String wherePart = " WHERE";
        for (int i = 0; i < booksArray.length; i += 1) {
            if (booksArray.length == 1) {
                wherePart += " bi.title = '" + booksArray[i] + "'";
                continue;
            }
            if (i < booksArray.length - 1) {
                wherePart += " bi.title = '" + booksArray[i] + "'" + " OR";
            } else {
                wherePart += " bi.title = '" + booksArray[i] + "'";
            }
        }
        return wherePart;
    }
}
