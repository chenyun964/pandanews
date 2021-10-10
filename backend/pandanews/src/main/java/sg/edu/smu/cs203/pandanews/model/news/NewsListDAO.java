package sg.edu.smu.cs203.pandanews.model.news;

import java.util.ArrayList;

public class NewsListDAO {
    private String _type;
    private String webSearchUrl;
    private ArrayList<NewsDAO> value;

    public NewsListDAO(String _type, String webSearchUrl, ArrayList<NewsDAO> value) {
        this._type = _type;
        this.webSearchUrl = webSearchUrl;
        this.value = value;
    }

    public NewsListDAO() {
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String getWebSearchUrl() {
        return webSearchUrl;
    }

    public void setWebSearchUrl(String webSearchUrl) {
        this.webSearchUrl = webSearchUrl;
    }

    public ArrayList<NewsDAO> getValue() {
        return value;
    }

    public void setValue(ArrayList<NewsDAO> value) {
        this.value = value;
    }

}


