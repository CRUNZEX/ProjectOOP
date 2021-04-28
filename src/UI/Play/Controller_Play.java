package UI.Play;

import Game.BotClass;
import Game.PlayerClass;
import UI.Menu.Controller_Menu;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Controller_Play {
    //------------------------------------------------------------------------------------------------------------------
    /*

    */
    //------------------------------------------------------------------------------------------------------------------
    // data fields
    private int randDirect;
    private int Round = 0, positionPlayerCards_X = 0, positionPlayerCards_Y = 0;
    private int[][] cardsPlace = new int[4][13];
    public AnchorPane MainPane;
    public ImageView Arrow;

    private PlayerClass playerClass = new PlayerClass();
    private BotClass botClass = new BotClass(playerClass.getCardsRand());
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
        Image image = new Image("Image/Backcard.png");

        for (int i = 0; i < backCards.length; i++) {
            for (int j = 0; j < backCards[i].length; j++) {
                backCards[i][j] = new ImageView(image);

                backCards[i][j].setFitWidth(105);
                backCards[i][j].setFitHeight(150);

                backCards[i][j].setLayoutX(j*20);
                backCards[i][j].setLayoutY(i*150);

                //backCards[i][j].addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> BackCardsClick());
//                backCards[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> BackCardsClick(mouseEvent));
                backCards[i][j].setOnMouseClicked(mouseEvent -> BackCardsClick(mouseEvent));
                MainPane.getChildren().add(backCards[i][j]);
                backCards[i][j].setDisable(true);
            }
        }
    }
    private void backCardsSetDisable(boolean bool) {
        for (int i = 0; i < backCards.length; i++) {
            for (int j = 0; j < backCards[i].length; j++) {
                backCards[i][j].setDisable(bool);
            }
        }
    }

    private int[][][] checkCardsPlaceOnStage(int[][][] temp) {
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                if (temp[i][j][1] == 3)
                    cardsPlace[i][j] = 3;
            }
        }

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                if (cardsPlace[i][j] == 3)
                    temp[i][j][1] = 3;
            }
        }

        return temp;
    }

    //------------------------------------------------------------------------------------------------------------------
    // Button: Random

    public void BackCardsClick(MouseEvent mouseEvent) {
        if (randDirect == 0) {
            System.out.println("หยิบได้!" + randDirect);

            int[] tempPos = botClass.playerPickCard(randDirect);
            System.out.println(">> " + tempPos[0] + " " + tempPos[1]);

            ImageView[][] imageViews = imgArraysMethod();
            int[][][] tempPlayer = playerClass.getCardsChecked();//เห็นมะ ตามมา

            for (int i = 0; i < imageViews.length; i++) {
                for (int j = 0; j < imageViews[i].length; j++) {
                    if (i == tempPos[0] && j == tempPos[1]) {
                        tempPlayer[i][j][0] = 0;        // bot to player
                        imageViews[i][j].visibleProperty().setValue(true);
                        imageViews[i][j].setLayoutX(120);
                        imageViews[i][j].setLayoutY(650);
                        imageViews[i][j].setDisable(false);
                        imageViews[i][j].toBack();
                    }
                }


                playerClass.setCardsChecked(checkCardsPlaceOnStage(tempPlayer));
                playerClass.checkDuplicate();
            }

            Btn_EndTurn.setDisable(false);
        }
        else if (randDirect == 1) {
            System.out.println("หยิบได้!" + randDirect);

            int[] tempPos = botClass.playerPickCard(randDirect);
            System.out.println(">> " + tempPos[0] + " " + tempPos[1]);//?ไม่ต้องมีหรอ อันนบนอะ

            ImageView[][] imageViews = imgArraysMethod();

            int[][][] tempPlayer = playerClass.getCardsChecked();
            for (int i = 0; i < imageViews.length; i++) {

                for (int j = 0; j < imageViews[i].length; j++) {
                    if (i == tempPos[0] && j == tempPos[1]) {
                        tempPlayer[i][j][0] = 0;
                        imageViews[i][j].visibleProperty().setValue(true);
                        imageViews[i][j].setLayoutX(120);
                        imageViews[i][j].setLayoutY(650);
                        imageViews[i][j].setDisable(false);
                        imageViews[i][j].toBack();
                    }
                }


                playerClass.setCardsChecked(checkCardsPlaceOnStage(tempPlayer));
                playerClass.checkDuplicate();
            }

            Btn_EndTurn.setDisable(false);
        }
        else
            System.out.println("ไม่ให้!" + randDirect);

    }
    public void BtnRand(MouseEvent mouseEvent) {
        // greyed out button
        Btn_Rand.setDisable(true);
        Btn_EndTurn.setDisable(false);

        // image to arrays
        ImageView[][] CardImgArrays = imgArraysMethod();
        colorAdjust.setBrightness(-0.2);
        playerClass.checkDuplicate();

        backCards();        // load image & set position

        // random
//        playerCardsPosition();
        for (int i = 0, round = 0; i < playerClass.cards().length; i++) {
            for (int j = 0; j < playerClass.cards()[i].length; j++) {
                if (playerClass.cards()[i][j] == 0) {
                    CardImgArrays[i][j].setLayoutX((round * 40) + 200);                                                 // set new position
                    CardImgArrays[i][j].setLayoutY(650);
                    round++;

                    if (playerClass.getCardsChecked()[i][j][1] != 1)                                                    // check duplicate
                        CardImgArrays[i][j].setEffect(colorAdjust);
                }
                else {
                    CardImgArrays[i][j].setVisible(false);
//                    CardImgArrays[i][j].visibleProperty().setValue(false);
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

    public void playerCardsPosition(int[][][] tempCards) {
        ImageView[][] CardImgArrays = imgArraysMethod();
        for (int i = 0, round = 0; i < tempCards.length; i++) {
            for (int j = 0; j < tempCards[i].length; j++) {
                if (tempCards[i][j][0] == 0 && tempCards[i][j][1] <= 1) {
                    CardImgArrays[i][j].setLayoutX((round * 40) + 200);                                                 // set new position
                    CardImgArrays[i][j].setLayoutY(650);
                    CardImgArrays[i][j].toFront();
//                    System.out.println("round: " + round);
                    round++;

                    if (tempCards[i][j][1] != 1) {      // check duplicate
                        colorAdjust.setBrightness(-0.2);
                        CardImgArrays[i][j].setEffect(colorAdjust);
                    }
                    else {
                        colorAdjust.setBrightness(0);
                        CardImgArrays[i][j].setEffect(colorAdjust);
                    }
                }
//                else {
//                    CardImgArrays[i][j].setVisible(false);
////                    CardImgArrays[i][j].visibleProperty().setValue(false);
//                    CardImgArrays[i][j].setDisable(true);
//                }
            }
        }

//        System.out.print("Player: ");
//        for (int i = 0; i < playerClass.getCardsChecked().length; i++) {
//            for (int j = 0; j < playerClass.getCardsChecked()[i].length; j++) {
//                if (playerClass.getCardsChecked()[i][j][1] == 3)
//                System.out.print(i + "|" + j + " ");
//            }
//        }
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
//        System.out.println("\n----------");
//        for (int i = 0; i < playerClass.getCardsPickupID_split().length; i++) {
//            for (int j = 0; j < playerClass.getCardsPickupID_split()[i].length; j++) {
//                System.out.print(playerClass.getCardsPickupID_split()[i][j]);
//            }
//            System.out.print(" ");
//        }
//
//        System.out.println();
//        for (int i = 0; i < playerClass.getCardsChecked().length; i++) {
//            for (int j = 0; j < playerClass.getCardsChecked()[i].length; j++) {
//                System.out.print(playerClass.getCardsChecked()[i][j][1] + " ");
//            }
//        }

//        System.out.println();

        // check place cards
        if (playerClass.getCardsPickupID_split()[0][1].equals(playerClass.getCardsPickupID_split()[1][1]) ) {
            for (int i = playerClass.getImgArraysID().length - 1, count = 0; i >= 0; i--) {
                for (int j = 0; j < playerClass.getImgArraysID()[i].length; j++) {
                    if (count < 2 && playerClass.getCardsPickupID()[count].equals(playerClass.getImgArraysID()[i][j])) {
//                        System.out.print(playerClass.getCardsPickupID()[count]);
                        imgArraysMethod()[i][j].setLayoutX(100 + (positionPlayerCards_X * 50));
                        imgArraysMethod()[i][j].setLayoutY(200 + (positionPlayerCards_Y * 20));
                        imgArraysMethod()[i][j].toFront();
                        positionPlayerCards_X++;
                        temp[i][j][1] = 3;
                        count++;
                    }
                }
            }
            System.out.println("PLACE CARDS SET!! (if)");
            playerClass.setCardsChecked(checkCardsPlaceOnStage(temp));
            playerClass.setCardsPickupID_count(0);
        }
        else {
            for (int count = 0; count < 2;) {                                                                           // check for down 2 cards
                for (int i = playerClass.getImgArraysID().length - 1; i >= 0; i--) {
                    for (int j = 0; j < playerClass.getImgArraysID()[i].length; j++) {
                        if (count < 2 && playerClass.getCardsPickupID()[count].equals(playerClass.getImgArraysID()[i][j]) && playerClass.getCardsChecked()[i][j][1] != 3) {
                            double tempPos = imgArraysMethod()[i][j].getLayoutY();
                            imgArraysMethod()[i][j].setLayoutY(tempPos + 30);
                            temp[i][j][1] = 1; //ตามมาอยู่มะดูว่ามันยังเซตเปน1มะ

                            count++;
                        }
                    }
                }
            }
            System.out.println("PLACE CARDS SET!! (else)");
            playerClass.setCardsChecked(checkCardsPlaceOnStage(temp));
            playerClass.setCardsPickupID_count(0);
        }

        positionPlayerCards_Y++;
        positionPlayerCards_X = 0;
        playerClass.setCardsPickupID_FILLNULL();
        Btn_Place.setDisable(true);
//        playerClass.setCardsChecked(temp);
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
            System.out.println("Up\t");
            mouseEvent.getPickResult().getIntersectedNode().setLayoutY(tempPos - 30);
            temp[pos[0]][pos[1]][1] = 2;

            // SET id pick up cards
            playerClass.setCardsPickupID(mouseEvent.getPickResult().getIntersectedNode().getId());
        }
        else if (temp[pos[0]][pos[1]][1] == 2) {
            // set position: Down
            System.out.println("Down\t");
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

        System.out.println("PICKUP CARDS SET!!");
        playerClass.setCardsChecked(checkCardsPlaceOnStage(temp));
    }

    //------------------------------------------------------------------------------------------------------------------
    // Button: End Turn

    public void BtnEndTurn(MouseEvent mouseEvent) throws InterruptedException {


        Round++;
        botClass.checkCardOnHand();
        Btn_EndTurn.setDisable(false);
        backCardsSetDisable(false);
        // greyed out when end turn
        colorAdjust.setBrightness(-0.2);
        for (int i = 0; i < playerClass.getCardsChecked().length; i++) {
            for (int j = 0; j < playerClass.getCardsChecked()[i].length; j++) {
                if (playerClass.getCardsChecked()[i][j][0] == 0 && playerClass.getCardsChecked()[i][j][1] <= 1)
                    imgArraysMethod()[i][j].setEffect(colorAdjust);
            }
        }

        //for(int i = 0;i <3;i++){
        //    botClass.checkCardMatch(i);
        //}

        int cardBotPlaceCount = 0;



        // print count bot place
        for (int i = 0; i < botClass.getCardPlace().length; i++) {
            System.out.println("bot" + i + ":" + botClass.getCardPlace()[i]);
        }

        playerClass.checkCard();
        System.out.println();

        ImageView[][] imageView = imgArraysMethod();
        int[][][] tempCards = playerClass.getCardsChecked();
        if(Round > 1){
            //botClass.botPickCardPlayer();
            botClass.botPickCardbot(randDirect);
        }
        for (int i = 0, tempRound = 0, tempPosi = 0; i < botClass.getBot().length; i++) {     // i bot
            botClass.checkCardMatch(i);
            System.out.println("bot" + i);
            for (int j = 0; j < 13; j++) {             // j num
                for (int k = 0; k < 4; k++) {      // k suit
                    if (botClass.getBot()[i][k][j] == 2) {

                        tempCards[k][j][1] = 3;         // set value place cards on stage
                        System.out.println(i + " " + j + " " + k);
                        // set position cards place on stage
                        imageView[k][j].visibleProperty().setValue(true);
                        if (tempRound == 0) {
                            imageView[k][j].setLayoutX(300);
                            imageView[k][j].setLayoutY(100 + (tempPosi * 20));
                            imageView[k][j].toFront();
                            tempRound++;
                        } else if (tempRound == 1) {
                            imageView[k][j].setLayoutX(350);
                            imageView[k][j].setLayoutY(100 + (tempPosi * 20));
                            imageView[k][j].toFront();
                            tempRound--;
                            tempPosi++;
                        }
                    }
                }
            }
        }

        // set null back cards
        for (int i = 0; i < backCards.length; i++) {
            for (int j = 0; j < botClass.getCardPlace()[i]; j++) {
                if (cardBotPlaceCount < 2) {
//                    System.out.println(">> " + cardBotPlaceCount);
                    cardBotPlaceCount++;
                    backCards[i][j].visibleProperty().setValue(false);
                }
                else if (cardBotPlaceCount >= 2) {

                    cardBotPlaceCount = 0;
                    j--;
                }


            }
        }

        System.out.println("ENDTURN SET!!");
        playerClass.setCardsChecked(checkCardsPlaceOnStage(tempCards));

        playerCardsPosition(tempCards);

//        for (int i = 0; i < playerClass.getCardsChecked().length; i++) {
//            for (int j = 0; j < playerClass.getCardsChecked()[i].length; j++) {
//                System.out.print(playerClass.getCardsChecked()[i][j][1] + " ");
//            }
//            System.out.println();
//        }
//        ImageView[][] imageView = imgArraysMethod();
//        long start = System.currentTimeMillis();
//        for (int i = 0, tempRound = 0; i < botClass.getBot().length; i++) {     // i bot ค่าย
//            if (start + 1000 <= System.currentTimeMillis()) {
//                System.out.println(start);
//                for (int j = 0; j < 13; j++) {             // j num
//                    for (int k = 0; k < 4; k++) {      // k suit
//                        if (botClass.getBot()[i][k][j] == 2) {
//                            System.out.println(i + " " + j + " " + k);
//                            imageView[k][j].visibleProperty().setValue(true);
//                            if (tempRound == 0) {
//                                imageView[k][j].setLayoutX(300);
//                                imageView[k][j].setLayoutY(300);
//                                imageView[k][j].toFront();
//                                tempRound++;
//                            } else if (tempRound == 1) {
//                                imageView[k][j].setLayoutX(350);
//                                imageView[k][j].setLayoutY(300);
//                                imageView[k][j].toFront();
//                                tempRound--;
//                            }
//                        }
//                    }
//                }
//                start = System.currentTimeMillis();
//            }
//            else
//                i--;
//        }
    }

    //------------------------------------------------------------------------------------------------------------------
}
