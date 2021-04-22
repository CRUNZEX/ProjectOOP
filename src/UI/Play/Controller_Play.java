package UI.Play;

import Game.BotClass;
import Game.PlayerClass;
import UI.Menu.Controller_Menu;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;

public class Controller_Play extends Controller_Menu {
    //------------------------------------------------------------------------------------------------------------------
    /*

    */
    //------------------------------------------------------------------------------------------------------------------
    // data fields
    private int randDirect;
    public AnchorPane MainPane;
    public ImageView Arrow;

    private BotClass botClass = new BotClass();
    private PlayerClass playerClass = new PlayerClass();
    ImageView[][] backCards = new ImageView[3][13];

    // color
    private ColorAdjust colorAdjust = new ColorAdjust();

    // button
    public Button Btn_Swap, Btn_Rand;
    public Button Btn_Place;
    public Button Btn_EndTurn;

    // add image
    public ImageView Spade_1, Spade_2, Spade_3, Spade_4, Spade_5, Spade_6, Spade_7, Spade_8, Spade_9, Spade_10, Spade_11, Spade_12, Spade_13;
    public ImageView Heart_1, Heart_2, Heart_3, Heart_4, Heart_5, Heart_6, Heart_7, Heart_8, Heart_9, Heart_10, Heart_11, Heart_12, Heart_13;
    public ImageView Diamond_1, Diamond_2, Diamond_3, Diamond_4, Diamond_5, Diamond_6, Diamond_7, Diamond_8, Diamond_9, Diamond_10, Diamond_11, Diamond_12, Diamond_13;
    public ImageView Club_1, Club_2, Club_3, Club_4, Club_5, Club_6, Club_7, Club_8, Club_9, Club_10, Club_11, Club_12, Club_13;
    public ImageView BackSide;

    //------------------------------------------------------------------------------------------------------------------
    // method

    private ImageView[][] imgArraysMethod() {                                   // imgArrays
        return new ImageView[][]{
                {Spade_1, Spade_2, Spade_3, Spade_4, Spade_5, Spade_6, Spade_7, Spade_8, Spade_9, Spade_10, Spade_11, Spade_12, Spade_13},
                {Heart_1, Heart_2, Heart_3, Heart_4, Heart_5, Heart_6, Heart_7, Heart_8, Heart_9, Heart_10, Heart_11, Heart_12, Heart_13},
                {Diamond_1, Diamond_2, Diamond_3, Diamond_4, Diamond_5, Diamond_6, Diamond_7, Diamond_8, Diamond_9, Diamond_10, Diamond_11, Diamond_12, Diamond_13},
                {Club_1, Club_2, Club_3, Club_4, Club_5, Club_6, Club_7, Club_8, Club_9, Club_10, Club_11, Club_12, Club_13}
        };
    }

    public int randomDirection() {
        return (int) Math.round(Math.random());
    }

    private void backCards() {
        // load image
        Image image = new Image("Image/black_joker.png");

        for (int i = 0; i < backCards.length; i++) {
            for (int j = 0; j < backCards[i].length; j++) {
                backCards[i][j] = new ImageView(image);

                backCards[i][j].setFitWidth(105);
                backCards[i][j].setFitHeight(150);

                backCards[i][j].setLayoutX(j*20);
                backCards[i][j].setLayoutY(i*150);

                int finalI = i;
                backCards[i][j].addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> BackCardsClick(finalI));
                MainPane.getChildren().add(backCards[i][j]);
            }
        }
    }

    private void interrupt(long time) {
        long end = 0;
        while (end < time) {
            end++;
        }
        System.out.println("interrupt");
    }

    //------------------------------------------------------------------------------------------------------------------
    // Button: Random

    public void BackCardsClick(int i) {
        if (randDirect == 0 && i == 0) {
            System.out.println("หยิบได้!" + i);
        }
        else if (randDirect == 1 && i == 1) {
            System.out.println("หยิบได้!" + i);
        }
        else
            System.out.println("ไม่ให้!" + i);
    }

    public void BtnRand(MouseEvent mouseEvent) {
        // greyed out button
        Btn_Rand.setDisable(true);
        Btn_EndTurn.setDisable(false);

        // image to arrays
        ImageView[][] CardImgArrays = imgArraysMethod();
        colorAdjust.setBrightness(-0.2);
        playerClass.checkDuplicate();

        backCards();

        // random
        for (int i = 0, round = 0; i < playerClass.getCardsRand().length; i++) {
            for (int j = 0; j < playerClass.getCardsRand()[i].length; j++) {
                if (playerClass.getCardsRand()[i][j] == 0) {
                    CardImgArrays[i][j].setLayoutX((round * 40) + 200);                                                 // set new position
                    CardImgArrays[i][j].setLayoutY(650);
                    round++;

                    if (playerClass.getCardsChecked()[i][j][1] != 1)                                                    // check duplicate
                        CardImgArrays[i][j].setEffect(colorAdjust);
                }
                else {
                    CardImgArrays[i][j].visibleProperty().setValue(false);
                    CardImgArrays[i][j].setDisable(true);
                }
            }
        }

        // set position bot cards
        for (int i = 0; i < backCards.length; i++) {
            for (int j = 0; j < backCards[i].length; j++) {
                if (i == 0) {
                    backCards[i][j].setRotate(-90);
                    backCards[i][j].setLayoutY(400 - (20 * j));
                    backCards[i][j].setLayoutX(1150);
                }
                else if (i == 1) {
                    backCards[i][j].setRotate(90);
                    backCards[i][j].setLayoutY(400 - (20 * j));
                    backCards[i][j].setLayoutX(-50);
                }
                else if (i == 2) {
                    backCards[i][j].setRotate(180);
                    backCards[i][j].setLayoutY(-100);
                    backCards[i][j].setLayoutX(200 + (j * 20));
                }
            }
        }

        // random direction
        randDirect = randomDirection();
        if (randDirect == 1) {
            Arrow.setScaleX(-1);
        }
        System.out.println(randDirect);
    }

    //------------------------------------------------------------------------------------------------------------------
    // Button: Swap

    public void BtnSwap(MouseEvent mouseEvent) {
        Btn_Rand.setDisable(false);
        Btn_Swap.setDisable(true);
    }

    //------------------------------------------------------------------------------------------------------------------
    // Button: Place Cards

    public void BtnPlace(MouseEvent mouseEvent) {
        int[][][] temp = playerClass.getCardsChecked();

        // print test
        System.out.println("\n----------");
        for (int i = 0; i < playerClass.getCardsPickupID_split().length; i++) {
            for (int j = 0; j < playerClass.getCardsPickupID_split()[i].length; j++) {
                System.out.print(playerClass.getCardsPickupID_split()[i][j]);
            }
            System.out.print(" ");
        }

        System.out.println();
        for (int i = 0; i < playerClass.getCardsChecked().length; i++) {
            for (int j = 0; j < playerClass.getCardsChecked()[i].length; j++) {
                System.out.print(playerClass.getCardsChecked()[i][j][1] + " ");
            }
        }

        System.out.println();

        // check place cards
        if (playerClass.getCardsPickupID_split()[0][1].equals(playerClass.getCardsPickupID_split()[1][1])) {
            for (int i = playerClass.getImgArraysID().length - 1, count = 0; i >= 0; i--) {
                for (int j = 0; j < playerClass.getImgArraysID()[i].length; j++) {
                    if (count < 2 && playerClass.getCardsPickupID()[count].equals(playerClass.getImgArraysID()[i][j])) {
                        System.out.println(playerClass.getCardsPickupID()[count]);
                        imgArraysMethod()[i][j].setLayoutY(200);
                        temp[i][j][1] = 3;
                        count++;
                    }
                }
            }
            playerClass.setCardsChecked(temp);
            playerClass.setCardsPickupID_count(0);
        }
        else {
            for (int count = 0; count < 2;) {                                                                           // check for down 2 cards
                for (int i = playerClass.getImgArraysID().length - 1; i >= 0; i--) {
                    for (int j = 0; j < playerClass.getImgArraysID()[i].length; j++) {
                        if (count < 2 && playerClass.getCardsPickupID()[count].equals(playerClass.getImgArraysID()[i][j])) {
                            double tempPos = imgArraysMethod()[i][j].getLayoutY();
                            imgArraysMethod()[i][j].setLayoutY(tempPos + 30);
                            temp[i][j][1] = 1;
                            count++;
                        }
                    }
                }
            }
            playerClass.setCardsChecked(temp);
            playerClass.setCardsPickupID_count(0);
        }

        playerClass.setCardsPickupID_FILLNULL();
        Btn_Place.setDisable(true);
        playerClass.setCardsChecked(temp);
    }

    //------------------------------------------------------------------------------------------------------------------
    // Button: Pick Up Cards

    public void PickupCard(MouseEvent mouseEvent) {
        int[][][] temp = playerClass.getCardsChecked();
        double tempPos = mouseEvent.getPickResult().getIntersectedNode().getLayoutY();                                  // get old position
        System.out.println("\n-----");

        int[] pos = playerClass.checkIndex(mouseEvent.getPickResult().getIntersectedNode().getId());

        if (temp[pos[0]][pos[1]][1] == 1 && playerClass.getCardsPickupID_count() < 2)                                   // check dupilcate
        {
            // set position: UP
            System.out.print("Up\t");
            mouseEvent.getPickResult().getIntersectedNode().setLayoutY(tempPos - 30);
            temp[pos[0]][pos[1]][1] = 2;

            // SET id pick up cards
            playerClass.setCardsPickupID(mouseEvent.getPickResult().getIntersectedNode().getId());
        }
        else if (temp[pos[0]][pos[1]][1] == 2) {
            // set position: Down
            System.out.print("Down\t");
            mouseEvent.getPickResult().getIntersectedNode().setLayoutY(tempPos + 30);
            temp[pos[0]][pos[1]][1] = 1;

            // DELETE id pick up cards
            playerClass.setCardsPickupID_NULL();
        }
        if (playerClass.getCardsPickupID_count() == 2)                                                                  // disable
            Btn_Place.setDisable(false);
        else
            Btn_Place.setDisable(true);

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                System.out.print(temp[i][j][1] + " ");
            }
        }

        playerClass.setCardsChecked(temp);
    }

    //------------------------------------------------------------------------------------------------------------------
    // Button: End Turn

    public void BtnEndTurn(MouseEvent mouseEvent) throws InterruptedException {
        Btn_EndTurn.setDisable(true);
        // greyed out when end turn
        colorAdjust.setBrightness(-0.2);
        for (int i = 0; i < playerClass.getCardsChecked().length; i++) {
            for (int j = 0; j < playerClass.getCardsChecked()[i].length; j++) {
                if (playerClass.getCardsChecked()[i][j][0] == 0 && playerClass.getCardsChecked()[i][j][1] <= 1)
                    imgArraysMethod()[i][j].setEffect(colorAdjust);
            }
        }
        for(int i = 0;i <3;i++){
            botClass.checkCardMatch(i);
        }

        int cardBotPlaceCount = 0;

        // set null back cards
        for (int i = 0; i < backCards.length; i++) {
            for (int j = 0; j < botClass.getCardPlace()[i]; j++) {
                if (cardBotPlaceCount < 2) {
//                    System.out.println(">> " + cardBotPlaceCount);
                    cardBotPlaceCount++;
                    backCards[i][j].visibleProperty().setValue(false);
                }
                else if (cardBotPlaceCount >= 2) {
//                    System.out.println("interrupt Loop");
//                    interrupt(1000000);
                    cardBotPlaceCount = 0;
                    j--;
                }


            }
        }

        // print count bot place
        for (int i = 0; i < botClass.getCardPlace().length; i++) {
            System.out.println("bot" + i + ":" + botClass.getCardPlace()[i]);
        }

        ImageView[][] imageView = imgArraysMethod();
        for (int i = 0, tempRound = 0; i < botClass.getBot().length; i++) {     // i bot ค่าย
            for (int j = 0; j < botClass.getBot()[i].length; j++) {             // j suit
                for (int k = 0; k < botClass.getBot()[i][j].length; k++) {      // k num
                    if (botClass.getBot()[i][j][k] == 2) {
                        System.out.println(i + " " + j + " " + k);
                        imageView[j][k].visibleProperty().setValue(true);
                        if (tempRound == 0) {
                            imageView[j][k].setLayoutX(300);
                            imageView[j][k].setLayoutY(300);
                            tempRound++;
                        }
                        else if (tempRound == 1) {
                            imageView[j][k].setLayoutX(350);
                            imageView[j][k].setLayoutY(300);
                            tempRound--;
                        }
                    }
                }
            }
        }
    }


    //------------------------------------------------------------------------------------------------------------------
}
