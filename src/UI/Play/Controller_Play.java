package UI.Play;

import Game.Cards;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Controller_Play {
    // data fields
    private Cards cardsClass = new Cards();
    private int[][] Cards;
    private ColorAdjust colorAdjust = new ColorAdjust();
    private MotionBlur motionBlur = new MotionBlur();

    // button
    public Button Btn_Swap, Btn_Rand;

    // add image
    // Spade
    public ImageView Spade_1, Spade_2, Spade_3, Spade_4, Spade_5, Spade_6, Spade_7, Spade_8, Spade_9, Spade_10, Spade_11, Spade_12, Spade_13;
    // Heart
    public ImageView Heart_1, Heart_2, Heart_3, Heart_4, Heart_5, Heart_6, Heart_7, Heart_8, Heart_9, Heart_10, Heart_11, Heart_12, Heart_13;
    // Diamond
    public ImageView Diamond_1, Diamond_2, Diamond_3, Diamond_4, Diamond_5, Diamond_6, Diamond_7, Diamond_8, Diamond_9, Diamond_10, Diamond_11, Diamond_12, Diamond_13;
    // Club
    public ImageView Club_1, Club_2, Club_3, Club_4, Club_5, Club_6, Club_7, Club_8, Club_9, Club_10, Club_11, Club_12, Club_13;

    //------------------------------------------------------------------------------------------------------------------
    // method
    private ImageView[][] imgArrays() {     // img
        ImageView[][] CardArrays = new ImageView[][]{
                {Spade_1, Spade_2, Spade_3, Spade_4, Spade_5, Spade_6, Spade_7, Spade_8, Spade_9, Spade_10, Spade_11, Spade_12, Spade_13},
                {Heart_1, Heart_2, Heart_3, Heart_4, Heart_5, Heart_6, Heart_7, Heart_8, Heart_9, Heart_10, Heart_11, Heart_12, Heart_13},
                {Diamond_1, Diamond_2, Diamond_3, Diamond_4, Diamond_5, Diamond_6, Diamond_7, Diamond_8, Diamond_9, Diamond_10, Diamond_11, Diamond_12, Diamond_13},
                {Club_1, Club_2, Club_3, Club_4, Club_5, Club_6, Club_7, Club_8, Club_9, Club_10, Club_11, Club_12, Club_13}
        };
        return CardArrays;
    }

    private int[][][] checkDuplicate() {    // check
        int[][][] temp = new int[4][13][1];
        int[] count = new int[13];

        for (int i = 0; i < Cards.length; i++) {        // check count of num
            for (int j = 0; j < Cards[i].length; j++) {
                if (Cards[i][j] == 0)
                    count[j]++;
            }
        }

        for (int i = 0; i < temp.length; i++) {     // assign 1 if duplicate
            for (int j = 0; j < temp[i].length; j++) {
                if (count[j] > 1)
                    temp[i][j][0] = 1;
                else
                    temp[i][j][0] = 0;
                if (i == 0)
                    System.out.println(temp[i][j][0]);
            }
        }

        return temp;
    }

    //------------------------------------------------------------------------------------------------------------------
    // Button
    public void BtnRand(MouseEvent mouseEvent) {
        Btn_Rand.setDisable(true);      // greyed out button

        // image to arrays
        ImageView[][] CardArrays = imgArrays();
        int[][][] checkDup = checkDuplicate();
        colorAdjust.setBrightness(-0.2);

        // random
        for (int i = 0, round = 0; i < Cards.length; i++) {
            for (int j = 0; j < Cards[i].length; j++) {
                // set new position
                if (Cards[i][j] == 0) {
                    CardArrays[i][j].setLayoutX((round * 40) + 200);    // set position
                    CardArrays[i][j].setLayoutY(650);
                    round++;

                    if (checkDup[i][j][0] != 1) {       // check duplicate
                        CardArrays[i][j].setEffect(colorAdjust);
                    }
                }
                else
                    CardArrays[i][j].setImage(null);
            }
        }
    }

    public void BtnSwap(MouseEvent mouseEvent) {
        Btn_Rand.setDisable(false);
        Btn_Swap.setDisable(true);
        Cards = cardsClass.random();
    }

    public void test1(MouseEvent mouseEvent) {
        // image to arrays
        System.out.println("wowwww");
    }
}
