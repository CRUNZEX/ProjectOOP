package Game;

import javax.swing.text.html.ImageView;

public class ImageClass {
    // data fields
    public static ImageView[][] imgCards;

    // constructor
    public ImageClass() {

    }
    public ImageClass(javafx.scene.image.ImageView[][] imgArraysMethod) {
        this.imgCards = imgCards;
    }

    // getter
    public static ImageView[][] getImgCards() {
        return imgCards;
    }

    // setter
    public static void setImgCards(ImageView[][] imgCards) {
        ImageClass.imgCards = imgCards;
    }

}
