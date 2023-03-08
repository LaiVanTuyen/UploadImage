package ra;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String id;
    private String name;
    private String image;
    private boolean status;
    private List<String> listImage= new ArrayList<>();

    public Product(String id, String name, String image, boolean status, List<String> listImage) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.status = status;
        this.listImage = listImage;
    }

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getListImage() {
        return listImage;
    }

    public void setListImage(List<String> listImage) {
        this.listImage = listImage;
    }
}
