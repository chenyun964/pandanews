package sg.edu.smu.cs203.pandanews.entities;

import java.util.ArrayList;

public class newsListDAO {
    private String _type;
    private String webSearchUrl;
    private ArrayList<newsDAO> value;

    public newsListDAO(String _type, String webSearchUrl, ArrayList<newsDAO> value) {
        this._type = _type;
        this.webSearchUrl = webSearchUrl;
        this.value = value;
    }

    public newsListDAO() {
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

    public ArrayList<newsDAO> getValue() {
        return value;
    }

    public void setValue(ArrayList<newsDAO> value) {
        this.value = value;
    }
}

class newsDAO {
    private String name;

    private String url;

    private Image image;

    private String description;

    private ArrayList<Organisation> provider;

    private String datePublished;

    public newsDAO(String name, String url, Image image, String description, ArrayList<Organisation> provider, String datePublished) {
        this.name = name;
        this.url = url;
        this.image = image;
        this.description = description;
        this.provider = provider;
        this.datePublished = datePublished;
    }

    public newsDAO() {
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


class Image {
    private Thumbnail thumbnail;

    private boolean isLicensed;

    public Image() {
    }

    public Image(Thumbnail thumbnail, boolean isLicensed) {
        this.thumbnail = thumbnail;
        this.isLicensed = isLicensed;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isLicensed() {
        return isLicensed;
    }

    public void setLicensed(boolean licensed) {
        isLicensed = licensed;
    }
}

class Thumbnail {
    private String contentUrl;

    private int width;

    private int height;

    public Thumbnail() {
    }

    public Thumbnail(String contentUrl, int width, int height) {
        this.contentUrl = contentUrl;
        this.width = width;
        this.height = height;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

class Organisation {
    private String _type;

    private String name;

    private Image image;

    public Organisation() {
    }

    public Organisation(String _type, String name, Image image) {
        this._type = _type;
        this.name = name;
        this.image = image;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}

