package sg.edu.smu.cs203.pandanews.model.news;

public class Thumbnail {
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
