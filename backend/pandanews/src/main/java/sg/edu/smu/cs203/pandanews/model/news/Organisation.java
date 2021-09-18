package sg.edu.smu.cs203.pandanews.model.news;

public class Organisation {
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
