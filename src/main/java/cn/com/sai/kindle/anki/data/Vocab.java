package cn.com.sai.kindle.anki.data;

/**
 * @author cj
 * @date 2022/6/18 17:06
 * @description
 */
public class Vocab {
    private String word;
    private String stem;
    private String usage;
    private String title;
    private String authors;
    private String meaning = "xxxxxx";

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return word + "\t" + stem + "\t" + usage + "<br> <div class=\"book\">" + "——《" + title + "(" + authors + ")" + "》" + "</div>" + "\t" + meaning;
    }
}
