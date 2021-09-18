package sg.edu.smu.cs203.pandanews.model.news;

public class Image {
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
