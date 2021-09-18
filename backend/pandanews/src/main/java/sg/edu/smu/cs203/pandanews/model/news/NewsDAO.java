package sg.edu.smu.cs203.pandanews.model.news;


import java.util.ArrayList;

public class NewsDAO {
    private String name;

    private String url;

    private Image image;

    private String description;

    private ArrayList<Organisation> provider;

    private String datePublished;

    public NewsDAO(String name, String url, Image image, String description, ArrayList<Organisation> provider, String datePublished) {
        this.name = name;
        this.url = url;
        this.image = image;
        this.description = description;
        this.provider = provider;
        this.datePublished = datePublished;
    }

    public NewsDAO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Organisation> getProvider() {
        return provider;
    }

    public void setProvider(ArrayList<Organisation> provider) {
        this.provider = provider;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }
}


