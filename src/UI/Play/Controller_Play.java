package UI.Play;

import Game.Cards;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;

public class Controller_Play {
    //------------------------------------------------------------------------------------------------------------------
    // === stage cards ===
    // Cards: position to name of cards
    //      0(Player) 1(Bot1) 2(Bot2) 3(Bot3)
    // CardsChecked:
    //    - [][][0]
    //      0 not duplicate -> gray out in first turn
    //      1 duplicate -> light in first turn
    //      2 upper cards -> after click this cards upper
    //      3 place button -> place cards on stage

    // === image ===
    // imgArraysMethod: pack image to arrays

    //------------------------------------------------------------------------------------------------------------------
    // data fields
    private Cards cardsClass = new Cards();
    private int[][] Cards;
    private int[][][] CardsChecked;
    private int countPickup = 0;
    //  - pick up
    private String[] cardIdPickup = new String[2];
    private String[][] cardIdPickupSplit = new String[2][2];
    //  - color
    private ColorAdjust colorAdjust = new ColorAdjust();
    private MotionBlur motionBlur = new MotionBlur();

    // button
    public Button Btn_Swap, Btn_Rand;
    public Button Btn_Place;

    // add image
    //   - Spade
    public ImageView Spade_1, Spade_2, Spade_3, Spade_4, Spade_5, Spade_6, Spade_7, Spade_8, Spade_9, Spade_10, Spade_11, Spade_12, Spade_13;
    //   - Heart
    public ImageView Heart_1, Heart_2, Heart_3, Heart_4, Heart_5, Heart_6, Heart_7, Heart_8, Heart_9, Heart_10, Heart_11, Heart_12, Heart_13;
    //   - Diamond
    public ImageView Diamond_1, Diamond_2, Diamond_3, Diamond_4, Diamond_5, Diamond_6, Diamond_7, Diamond_8, Diamond_9, Diamond_10, Diamond_11, Diamond_12, Diamond_13;
    //   - Club
    public ImageView Club_1, Club_2, Club_3, Club_4, Club_5, Club_6, Club_7, Club_8, Club_9, Club_10, Club_11, Club_12, Club_13;

    //------------------------------------------------------------------------------------------------------------------
    // method
    private ImageView[][] imgArraysMethod() {     // img
        return new ImageView[][]{
                {Spade_1, Spade_2, Spade_3, Spade_4, Spade_5, Spade_6, Spade_7, Spade_8, Spade_9, Spade_10, Spade_11, Spade_12, Spade_13},
                {Heart_1, Heart_2, Heart_3, Heart_4, Heart_5, Heart_6, Heart_7, Heart_8, Heart_9, Heart_10, Heart_11, Heart_12, Heart_13},
                {Diamond_1, Diamond_2, Diamond_3, Diamond_4, Diamond_5, Diamond_6, Diamond_7, Diamond_8, Diamond_9, Diamond_10, Diamond_11, Diamond_12, Diamond_13},
                {Club_1, Club_2, Club_3, Club_4, Club_5, Club_6, Club_7, Club_8, Club_9, Club_10, Club_11, Club_12, Club_13}
        };
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
                    temp[i][j][0] = 1;      // duplicate
                else
                    temp[i][j][0] = 0;
                if (i == 0)
                    System.out.println(temp[i][j][0]);
            }
        }

        return temp;
    }

    // getter setter
    public int[][][] getCardsChecked() {
        return CardsChecked;
    }

    public void setCardsChecked(int[][][] cardsChecked) {
        CardsChecked = cardsChecked;
    }

    //------------------------------------------------------------------------------------------------------------------
    // Button
    public void BtnRand(MouseEvent mouseEvent) {
        Btn_Rand.setDisable(true);      // greyed out button

        // image to arrays
        ImageView[][] CardArrays = imgArraysMethod();
        setCardsChecked(checkDuplicate());
        int[][][] checkDup = getCardsChecked();
        colorAdjust.setBrightness(-0.2);//ผมหมายถึงตอนแรกที่มันเด้งอะ ผมว่าอันนั้นมันก้โอเค ไอ้ตอนที่ผมบอกอันนั้นหมายถึงขาวดำของไพ่ตอนจบตา คุณก้ไปแก้ปุ่มผมเลยงงไปหมดละ ใช่

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
                else {
                    CardArrays[i][j].setImage(null);
                    CardArrays[i][j].setDisable(true);
                }
            }
        }
    }

    public void BtnSwap(MouseEvent mouseEvent) {
        Btn_Rand.setDisable(false);
        Btn_Swap.setDisable(true);
        Cards = cardsClass.random();
    }

    public void BtnPlace(MouseEvent mouseEvent) {
        int[][][] temp = getCardsChecked();
        ImageView[][] CardArrays = imgArraysMethod();
        int countOfBrightCard = 0; //มันต้องเซ้ตเปน0หรอ แบบนั้นถ้าผมกดมันก้เปน0อะดิ
        Arrays.sort(cardIdPickup);


        System.out.println("----------");
        for (int i = 0; i < cardIdPickupSplit.length; i++) {    // print test
            for (int j = 0; j < cardIdPickupSplit[i].length; j++) {
                System.out.println(cardIdPickupSplit[i][j]);
            }
        }

        if (cardIdPickupSplit[0][1].equals(cardIdPickupSplit[1][1])) {
//            System.out.println(">>id: " + cardIdPickupSplit[0][1] + " " + cardIdPickupSplit[1][1]);

            for (int i = imgArraysMethod().length - 1, count = 0; i >= 0; i--) {
                for (int j = 0; j < imgArraysMethod()[i].length; j++) {
//                    System.out.println(">>count: " + count);
                    if (count < 2 && cardIdPickup[count].equals(imgArraysMethod()[i][j].getId())) {
                        System.out.println(cardIdPickup[count]);
                        imgArraysMethod()[i][j].setLayoutY(200);
                        temp[i][j][0] = 3;
                        count++;
                    }
                }
            }

            countPickup = 0;
        }
        else {
            for (int count = 0; count < 2;) {   // check for down 2 cards
                for (int i = imgArraysMethod().length - 1; i >= 0; i--) {
                    for (int j = 0; j < imgArraysMethod()[i].length; j++) {
//                        System.out.println(">>count: " + count);
                        if (count < 2 && cardIdPickup[count].equals(imgArraysMethod()[i][j].getId())) {
                            double tempPos = imgArraysMethod()[i][j].getLayoutY();
                            System.out.println(cardIdPickup[count]);
                            imgArraysMethod()[i][j].setLayoutY(tempPos + 30);
                            temp[i][j][0] = 1;
                            count++;
                        }
                    }
                }
            }

            countPickup = 0;
        }

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                System.out.print(temp[i][j][0] + " ");
                if (temp[i][j][0] == 1)
                    countOfBrightCard++;
            }
        }
        System.out.println("count: " + countOfBrightCard);

        if (countOfBrightCard <= 1) { //ไอ้ตอนลงอยู่ไหนอะ คุณมีแต่บวกเพิ่มอะ ไม่มีลด ไอ้placeปะ
            colorAdjust.setBrightness(-0.2);
            for (int i = 0; i < temp.length; i++) {
                for (int j = 0; j < temp[i].length; j++) {
                    CardArrays[i][j].setEffect(colorAdjust);
                }
            }
        }

        Arrays.fill(cardIdPickup, null);
        Arrays.fill(cardIdPickupSplit, null);
        Btn_Place.setDisable(true);
        setCardsChecked(temp);//
    }

    public void PickupCard(MouseEvent mouseEvent) {
        int[][][] temp = getCardsChecked();
        double tempPos = mouseEvent.getPickResult().getIntersectedNode().getLayoutY();      // get old position
        System.out.println("-----");

        int posX = 0, posY = 0;
        for (int i = 0; i < imgArraysMethod().length; i++) {
            for (int j = 0; j < imgArraysMethod()[i].length; j++) {
                if (imgArraysMethod()[i][j].getId().equals(mouseEvent.getPickResult().getIntersectedNode().getId())) {       // map id to position in arrays
//                    System.out.println(imgArraysMethod()[i][j].getId());
                    posX = i;
                    posY = j;
                }
            }
        }

        if (temp[posX][posY][0] == 1 && countPickup < 2)   // check dupilcate
        {
            // set position: UP
            System.out.println("Up");
            mouseEvent.getPickResult().getIntersectedNode().setLayoutY(tempPos - 30);
            temp[posX][posY][0] = 2;

            // SET id pick up cards
            cardIdPickup[countPickup] = mouseEvent.getPickResult().getIntersectedNode().getId();
            cardIdPickupSplit[countPickup] = mouseEvent.getPickResult().getIntersectedNode().getId().split("_", -1);
            countPickup++;
        }
        else if (temp[posX][posY][0] == 2) {
            // set position: Down
            System.out.println("Down");
            mouseEvent.getPickResult().getIntersectedNode().setLayoutY(tempPos + 30);
            temp[posX][posY][0] = 1;

            // DELETE id pick up cards
            countPickup--;
            cardIdPickup[countPickup] = null;
            cardIdPickupSplit[countPickup] = null;
        }

//        System.out.println(cardIdPickupSplit[0][1] + " " + cardIdPickupSplit[1][1]); ใช่
        if (countPickup == 2)   // disable
            Btn_Place.setDisable(false);
        else
            Btn_Place.setDisable(true);

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                System.out.print(temp[i][j][0] + " ");
            }
        }

        setCardsChecked(temp);
    }
}
