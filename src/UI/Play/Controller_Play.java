package UI.Play;

import Game.BotClass;
import Game.PlayerClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_Play {
    //------------------------------------------------------------------------------------------------------------------
    /*
     */
    //------------------------------------------------------------------------------------------------------------------
    // data fields

    // variables
    private int randDirect;
    private int Round = 0, positionPlayerCards_X = 0, positionPlayerCards_Y = 0;
    private boolean playerPickupBot = false;
    private int[][][] cards = new int[4][13][10];
    private String strResult;
    public boolean result;

    // pane
    public AnchorPane MainPane;

    // class
    private PlayerClass playerClass = new PlayerClass();
    private BotClass botClass = new BotClass(playerClass.getCardsRand());

    // color
    private ColorAdjust colorAdjust = new ColorAdjust();

    // button
    public Button Btn_Swap, Btn_Rand;
    public Button Btn_Place;
    public Button Btn_EndTurn;

    // add image
    public ImageView Arrow;
    ImageView[][] backCards = new ImageView[3][13];

    public ImageView Spade_1, Spade_2, Spade_3, Spade_4, Spade_5, Spade_6, Spade_7, Spade_8, Spade_9, Spade_10, Spade_11, Spade_12, Spade_13;
    public ImageView Heart_1, Heart_2, Heart_3, Heart_4, Heart_5, Heart_6, Heart_7, Heart_8, Heart_9, Heart_10, Heart_11, Heart_12, Heart_13;
    public ImageView Diamond_1, Diamond_2, Diamond_3, Diamond_4, Diamond_5, Diamond_6, Diamond_7, Diamond_8, Diamond_9, Diamond_10, Diamond_11, Diamond_12, Diamond_13;
    public ImageView Club_1, Club_2, Club_3, Club_4, Club_5, Club_6, Club_7, Club_8, Club_9, Club_10, Club_11, Club_12, Club_13;

    // Label (Controller_End)
    public Label labelPlay_Result = new Label();

    //------------------------------------------------------------------------------------------------------------------
    // method

    private ImageView[][] imgArraysMethod() {                                                                           // imgArrays
        return new ImageView[][]{
                {Spade_1, Spade_2, Spade_3, Spade_4, Spade_5, Spade_6, Spade_7, Spade_8, Spade_9, Spade_10, Spade_11, Spade_12, Spade_13},
                {Heart_1, Heart_2, Heart_3, Heart_4, Heart_5, Heart_6, Heart_7, Heart_8, Heart_9, Heart_10, Heart_11, Heart_12, Heart_13},
                {Diamond_1, Diamond_2, Diamond_3, Diamond_4, Diamond_5, Diamond_6, Diamond_7, Diamond_8, Diamond_9, Diamond_10, Diamond_11, Diamond_12, Diamond_13},
                {Club_1, Club_2, Club_3, Club_4, Club_5, Club_6, Club_7, Club_8, Club_9, Club_10, Club_11, Club_12, Club_13}
        };
    }

    public int randomDirection() {                                                                                      // random direction
        return (int) Math.round(Math.random());
    }

    private void backCards() {
        Image image = new Image("Image/Backcard.png");                                                                // load image

        for (int i = 0; i < backCards.length; i++) {
            for (int j = 0; j < backCards[i].length; j++) {
                backCards[i][j] = new ImageView(image);

                backCards[i][j].setFitWidth(105);
                backCards[i][j].setFitHeight(150);

                backCards[i][j].setLayoutX(j * 20);
                backCards[i][j].setLayoutY(i * 150);

                backCards[i][j].setId(i + "_" + j);

                backCards[i][j].addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    try {
                        BackCardsClick(mouseEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });     // action when click on image

                MainPane.getChildren().add(backCards[i][j]);        // set image on pane
                MainPane.getStylesheets().add("UI/Play/StylePlay.css");
//                backCards[i][j].getStyleClass().add("imageCards");

                backCards[i][j].getStyleClass().add("image-view");
                backCards[i][j].setDisable(true);
            }
        }
    }

    private void cardsSetDisable(boolean bool) {
        int[][][] tempCardCheck = playerClass.getCardsChecked();
        ImageView[][] imageViews = imgArraysMethod();
        for (int i = 0; i < tempCardCheck.length; i++) {
            for (int j = 0; j < tempCardCheck[i].length; j++) {
                if (tempCardCheck[i][j][1] <= 1)
                    imageViews[i][j].setDisable(bool);

            }
        }
    }

    private void backCardsSetDisable(int bot, boolean bool) {
        for (int i = 0; i < backCards[bot].length; i++) {
            backCards[bot][i].setDisable(bool);
        }
    }

    private int[][][] checkCardsPlaceOnStage(int[][][] temp) {                                                          // check cards on stage (Reserve for error)
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                if (temp[i][j][1] == 3 && cards[i][j][1] != 3)
                    cards[i][j][1] = 3;
                else if (temp[i][j][1] != 3)
                    cards[i][j][1] = temp[i][j][1];

                cards[i][j][0] = temp[i][j][0];
            }
        }

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                if (cards[i][j][1] == 3)
                    temp[i][j][1] = 3;
            }
        }

        return temp;
    }

    public void playerCardsPosition(int[][][] tempCards) {                                                              // set position player cards
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
                    } else {
                        colorAdjust.setBrightness(0);
                        CardImgArrays[i][j].setEffect(colorAdjust);
                    }
                }
            }
        }
    }

    public void checkWin() throws IOException {
        // check player win or lose
        if (playerClass.amountCards() == 0 && (botClass.getAmountCard()[0] != 0 || botClass.getAmountCard()[1] != 1 || botClass.getAmountCard()[2] != 2)) {
            result = true;
            strResult = "You Win!";

        } else {
            result = false;
            strResult = "You Lose!";
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // Button: Back cards click & action

    public void BackCardsClick(MouseEvent mouseEvent) throws IOException {


        if (randDirect == 0 && playerPickupBot == false) {
            cardsSetDisable(false); //?????????player
            playerPickupBot = true;


            int[] tempPos = new int[2];
            if (botClass.getAmountCard()[0] > 0) {
                System.out.println("?????????????????????!" + randDirect);
                tempPos = botClass.playerPickCard(0);

                if (botClass.getAmountCard()[0] == 1) {
                    Btn_EndTurn.setDisable(false);
                    backCards[0][0].setVisible(false);
                    backCards[0][0].setDisable(true);
                    backCardsSetDisable(2, false);
                }
            }
            else if (botClass.getAmountCard()[0] == 0 && botClass.getAmountCard()[2] > 0) {
                checkWin();
                backCardsSetDisable(2, false);
                System.out.println("?????????????????????!" + 2);

                playerPickupBot = true;
                tempPos = botClass.playerPickCard(2);

                if (botClass.getAmountCard()[2] == 1) {
                    Btn_EndTurn.setDisable(false);
                    backCards[2][0].setVisible(false);
                    backCards[2][0].setDisable(true);
                    backCardsSetDisable(1, false);
                }
            }
            else if (botClass.getAmountCard()[0] == 0 && botClass.getAmountCard()[2] == 0 && botClass.getAmountCard()[1] > 0) {
                checkWin();
                backCardsSetDisable(1, false);
                System.out.println("?????????????????????!" + 1);

                playerPickupBot = true;
                tempPos = botClass.playerPickCard(1);

                if (botClass.getAmountCard()[1] == 1) {
                    Btn_EndTurn.setDisable(false);
                    backCards[1][0].setVisible(false);
                    backCards[1][0].setDisable(true);
                }
            }
            else
                checkWin();

            System.out.println(">> " + tempPos[0] + " " + tempPos[1]);

            ImageView[][] imageViews = imgArraysMethod();
            int[][][] tempPlayer = playerClass.getCardsChecked();

            // print test
            for (int i = 0; i < tempPlayer.length; i++) {
                for (int j = 0; j < tempPlayer[i].length; j++) {
                    System.out.print(tempPlayer[i][j][1] + " ");
                }
                System.out.println();
            }

            // show cards in player hands
            for (int i = 0; i < imageViews.length; i++) {
                for (int j = 0; j < imageViews[i].length; j++) {
                    if (i == tempPos[0] && j == tempPos[1]) {
                        tempPlayer[i][j][0] = 0;                            // bot to player
                        imageViews[i][j].visibleProperty().setValue(true);
                        imageViews[i][j].setLayoutX(160);
                        imageViews[i][j].setLayoutY(650);
                        imageViews[i][j].setDisable(false);
                        imageViews[i][j].toBack();
                    }
                }

                System.out.println("BACKCARDS CLICK!! BOT1");
                playerClass.setCardsChecked(checkCardsPlaceOnStage(tempPlayer));
//                playerClass.setCardsChecked(tempPlayer);
                playerClass.checkDuplicate();
            }

//            if (botClass.getAmountCard()[0] > 1 && botClass.getAmountCard()[1] > 1 && botClass.getAmountCard()[2] > 1) {
                Btn_EndTurn.setDisable(false);
                backCards[randDirect][12 - botClass.getAmountCard()[randDirect]].setVisible(false);
                botClass.setCardPlace_DEL(randDirect);
//            }

        } else if (randDirect == 1 && playerPickupBot == false) {
            System.out.println("?????????????????????!" + randDirect);
            cardsSetDisable(false);

            playerPickupBot = true;
//            int[] tempPos = botClass.playerPickCard(randDirect);

            int[] tempPos = new int[2];
            if (botClass.getAmountCard()[1] > 0) {
                System.out.println("?????????????????????!" + randDirect);
                tempPos = botClass.playerPickCard(1);

                if (botClass.getAmountCard()[1] == 1) {
                    Btn_EndTurn.setDisable(false);
                    backCards[1][0].setVisible(false);
                    backCards[1][0].setDisable(true);
                }
            }
            else if (botClass.getAmountCard()[1] == 0 && botClass.getAmountCard()[2] > 0) {
                checkWin();
                backCardsSetDisable(2, false);
                System.out.println("?????????????????????!" + 2);

                playerPickupBot = true;
                tempPos = botClass.playerPickCard(2);

                if (botClass.getAmountCard()[2] == 1) {
                    Btn_EndTurn.setDisable(false);
                    backCards[2][0].setVisible(false);
                    backCards[2][0].setDisable(true);
                }
            }
            else if (botClass.getAmountCard()[1] == 0 && botClass.getAmountCard()[2] == 0 && botClass.getAmountCard()[0] > 0) {
                checkWin();
                backCardsSetDisable(0, false);
                System.out.println("?????????????????????!" + 1);

                playerPickupBot = true;
                tempPos = botClass.playerPickCard(0);

                if (botClass.getAmountCard()[0] == 1) {
                    Btn_EndTurn.setDisable(false);
                    backCards[0][0].setVisible(false);
                    backCards[0][0].setDisable(true);
                }
            }
            else
                checkWin();

            System.out.println(">> " + tempPos[0] + " " + tempPos[1]);

            // show cards in player hands
            ImageView[][] imageViews = imgArraysMethod();
            int[][][] tempPlayer = playerClass.getCardsChecked();
            for (int i = 0; i < imageViews.length; i++) {

                for (int j = 0; j < imageViews[i].length; j++) {
                    if (i == tempPos[0] && j == tempPos[1]) {
                        tempPlayer[i][j][0] = 0;
                        imageViews[i][j].visibleProperty().setValue(true);
                        imageViews[i][j].setLayoutX(160);
                        imageViews[i][j].setLayoutY(650);
                        imageViews[i][j].setDisable(false);
                        imageViews[i][j].toBack();
                    }

                }

                System.out.println("BACKCARDS CLICK!! BOT1");
                playerClass.setCardsChecked(checkCardsPlaceOnStage(tempPlayer));
//                playerClass.setCardsChecked(tempPlayer);
                playerClass.checkDuplicate();
            }

            Btn_EndTurn.setDisable(false);
            backCards[randDirect][12 - botClass.getAmountCard()[randDirect]].setVisible(false);
            botClass.setCardPlace_DEL(randDirect);
        } else {
            System.out.println("??????????????????!" + randDirect);

            if (botClass.getAmountCard()[0] == 1) {
                Btn_EndTurn.setDisable(false);
                backCards[0][0].setVisible(false);
                backCards[0][0].setDisable(true);
            }

            if (botClass.getAmountCard()[1] == 1) {
                Btn_EndTurn.setDisable(false);
                backCards[1][0].setVisible(false);
                backCards[1][0].setDisable(true);
            }

            if (botClass.getAmountCard()[2] == 1) {
                Btn_EndTurn.setDisable(false);
                backCards[2][0].setVisible(false);
                backCards[2][0].setDisable(true);
            }
        }


        colorAdjust.setBrightness(0);
        for (int i = 0; i < imgArraysMethod().length; i++) {
            for (int j = 0; j < imgArraysMethod().length; j++) {
                imgArraysMethod()[i][j].setEffect(colorAdjust);
            }
        }
        System.out.println("Amount: " + botClass.getAmountCard()[0] + " " + botClass.getAmountCard()[1] + " " + botClass.getAmountCard()[2] + " ");

    }

    //------------------------------------------------------------------------------------------------------------------
    // Button: Random

    public void BtnRand(MouseEvent mouseEvent) {
        // greyed out button XD
        Btn_Rand.setDisable(true);
        Btn_EndTurn.setDisable(false);

        // image to arrays
        ImageView[][] CardImgArrays = imgArraysMethod();
//        colorAdjust.setBrightness(-0.2);
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

//                    if (playerClass.getCardsChecked()[i][j][1] != 1)                                                    // check duplicate
//                        CardImgArrays[i][j].setEffect(colorAdjust);
                } else {
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
                } else if (i == 1 && j < backCards[i].length - 1) {
                    backCards[i][j].setRotate(90);
                    backCards[i][j].setLayoutY(400 - (20 * j));
                    backCards[i][j].setLayoutX(-50);
                } else if (i == 2) {
                    backCards[i][j].setRotate(180);
                    backCards[i][j].setLayoutY(-100);
                    backCards[i][j].setLayoutX(200 + (j * 20));
                }
            }
        }

        // random direction
        randDirect = randomDirection();
        randDirect = 0;
        if (randDirect == 1) {
            Arrow.setScaleX(-1);
        }
        System.out.println(randDirect);
    }


    //------------------------------------------------------------------------------------------------------------------
    // Button: Swap

    public void BtnSwap(MouseEvent mouseEvent) throws IOException {
        Btn_Rand.setDisable(false);
        Btn_Swap.setDisable(true);
        checkWin();
    }

    //------------------------------------------------------------------------------------------------------------------
    // Button: Place Cards

    public void BtnPlace(MouseEvent mouseEvent) throws IOException {
        int[][][] temp = playerClass.getCardsChecked();

        int numCards = 0;
        for (int i = 0; i < playerClass.getCardsChecked().length; i++) {
            for (int j = 0; j < playerClass.getCardsChecked().length; j++) {
                if (playerClass.getCardsChecked()[i][j][0] == 0 && playerClass.getCardsChecked()[i][j][1] <= 1)
                    numCards++;
            }
        }

        System.out.println("Place:" + numCards + ">>"+ playerClass.amountCards() +  " " + botClass.getAmountCard()[0] + " " + botClass.getAmountCard()[1] + " " + botClass.getAmountCard()[2]);
        if (numCards == 0 && (botClass.getAmountCard()[0] > 0 || botClass.getAmountCard()[1] > 0 || botClass.getAmountCard()[2] > 0)) {
            Group group = new Group();
//            Label label = new Label(strResult);
//            label.setLayoutX(200);
//            label.setLayoutY(200);
//            group.getChildren().add(label);

            // create stage
            Parent rootEnd = FXMLLoader.load(getClass().getResource("../End/End_Win.fxml"));
            group.getChildren().add(rootEnd);
            Scene rootPlayScene = new Scene(group);

            // stage
            Stage window = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();

            // display
            window.setScene(rootPlayScene);
            window.show();
        }
        else if (numCards > 0 && (botClass.getAmountCard()[0] == 0 && botClass.getAmountCard()[1] == 0 && botClass.getAmountCard()[2] == 0)) {
//            Group group = new Group();
////            Label label = new Label(strResult);
////            label.setLayoutX(200);
////            label.setLayoutY(200);
////            group.getChildren().add(label);
//
//            // create stage
//            Parent rootEnd = FXMLLoader.load(getClass().getResource("../End/End_Lose.fxml"));
//            group.getChildren().add(rootEnd);
//            Scene rootPlayScene = new Scene(group);
//
//            // stage
//            Stage window = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
//
//            // display
//            window.setScene(rootPlayScene);
//            window.show();
        }

        // check place cards
        if (playerClass.getCardsPickupID_split()[0][1].equals(playerClass.getCardsPickupID_split()[1][1])) {
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
        } else {
            for (int count = 0; count < 2; ) {                                                                           // check for down 2 cards
                for (int i = playerClass.getImgArraysID().length - 1; i >= 0; i--) {
                    for (int j = 0; j < playerClass.getImgArraysID()[i].length; j++) {
                        if (count < 2 && playerClass.getCardsPickupID()[count].equals(playerClass.getImgArraysID()[i][j]) && playerClass.getCardsChecked()[i][j][1] != 3) {
                            double tempPos = imgArraysMethod()[i][j].getLayoutY();
                            imgArraysMethod()[i][j].setLayoutY(650);
//                            temp[i][j][1] = 1; //????????????????????????????????????????????????????????????????????????????????????1??????

                            count++;
                        }

                        if (playerClass.getCardsChecked()[i][j][1] == 2) {
                            imgArraysMethod()[i][j].setLayoutY(650);
                        }
                    }
                }
            }
            System.out.println("PLACE CARDS SET!! (else)");
            playerClass.setCardsChecked(checkCardsPlaceOnStage(temp));
            playerClass.setCardsPickupID_count(0);
        }

        playerClass.checkDuplicate();
        positionPlayerCards_Y++;
        positionPlayerCards_X = 0;
        playerClass.setCardsPickupID_FILLNULL();
        Btn_Place.setDisable(true);
        Btn_EndTurn.setDisable(false);
//        playerClass.setCardsChecked(temp);
    }

    //------------------------------------------------------------------------------------------------------------------
    // Button: Pick Up Cards

    public void PickupCard(MouseEvent mouseEvent) {
        int[][][] temp = checkCardsPlaceOnStage(playerClass.getCardsChecked());
        double tempPos = mouseEvent.getPickResult().getIntersectedNode().getLayoutY();                                  // get old position
        System.out.println("\n-----");

        int[] pos = playerClass.checkIndex(mouseEvent.getPickResult().getIntersectedNode().getId());

        if (temp[pos[0]][pos[1]][1] == 1 && playerClass.getCardsPickupID_count() < 2)                                   // check dupilcate
        {
            // set position: UP
            System.out.println("Up\t");
            mouseEvent.getPickResult().getIntersectedNode().setLayoutY(620);
            temp[pos[0]][pos[1]][1] = 2;

            // SET id pick up cards
            playerClass.setCardsPickupID(mouseEvent.getPickResult().getIntersectedNode().getId());

            Btn_EndTurn.setDisable(true);
        } else if (temp[pos[0]][pos[1]][1] == 2) {
            // set position: Down
            System.out.println("Down\t");
            mouseEvent.getPickResult().getIntersectedNode().setLayoutY(650);
            temp[pos[0]][pos[1]][1] = 1;

            // DELETE id pick up cards
            playerClass.setCardsPickupID_NULL();

            Btn_EndTurn.setDisable(false);
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

    public void BtnEndTurn(MouseEvent mouseEvent) throws IOException {
        checkWin();

        System.out.println("Endturn:" + playerClass.amountCards() + " " + botClass.getAmountCard()[0] + " " + botClass.getAmountCard()[1] + " " + botClass.getAmountCard()[2]);
        if (playerClass.amountCards() == 0 && (botClass.getAmountCard()[0] > 0 || botClass.getAmountCard()[1] > 0 || botClass.getAmountCard()[2] > 0)) {
            Group group = new Group();
//            Label label = new Label(strResult);
//            label.setLayoutX(200);
//            label.setLayoutY(200);
//            group.getChildren().add(label);

            // create stage
            Parent rootEnd = FXMLLoader.load(getClass().getResource("../End/End_Win.fxml"));
            group.getChildren().add(rootEnd);
            Scene rootPlayScene = new Scene(group);

            // stage
            Stage window = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();

            // display
            window.setScene(rootPlayScene);
            window.show();
        }
        else if (playerClass.amountCards() > 0 && (botClass.getAmountCard()[0] == 0 && botClass.getAmountCard()[1] == 0 && botClass.getAmountCard()[2] == 0)) {
            Group group = new Group();
//            Label label = new Label(strResult);
//            label.setLayoutX(200);
//            label.setLayoutY(200);
//            group.getChildren().add(label);

            // create stage
            Parent rootEnd = FXMLLoader.load(getClass().getResource("../End/End_Lose.fxml"));
            group.getChildren().add(rootEnd);
            Scene rootPlayScene = new Scene(group);

            // stage
            Stage window = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();

            // display
            window.setScene(rootPlayScene);
            window.show();
        }

        if (randDirect == 0) {
            if (botClass.getAmountCard()[0] == 0)
                backCardsSetDisable(2 , false);
            if (botClass.getAmountCard()[2] == 0)
                backCardsSetDisable(1, false);
            if (botClass.getAmountCard()[1] == 0)
                checkWin();
        }
        else  {
            if (botClass.getAmountCard()[1] == 0)
                backCardsSetDisable(2 , false);
            if (botClass.getAmountCard()[2] == 0)
                backCardsSetDisable(0, false);
            if (botClass.getAmountCard()[0] == 0)
                checkWin();
        }

        // set position cards to low
        int[][][] tempCards = playerClass.getCardsChecked();
        for (int i = 0; i < playerClass.getCardsChecked().length; i++) {
            for (int j = 0; j < playerClass.getCardsChecked()[i].length; j++) {
                if (tempCards[i][j][1] == 2) {
                    tempCards[i][j][1] = 0;
                    imgArraysMethod()[i][j].setLayoutY(650);
                }
            }
        }

        playerPickupBot = false;
        Round++;
        botClass.checkCardOnHand();
        Btn_EndTurn.setDisable(true);
        backCardsSetDisable(randDirect, false);

        // greyed out when end turn
        colorAdjust.setBrightness(-0.2);
        for (int i = 0; i < playerClass.getCardsChecked().length; i++) {
            for (int j = 0; j < playerClass.getCardsChecked()[i].length; j++) {
                if (playerClass.getCardsChecked()[i][j][0] == 0 && playerClass.getCardsChecked()[i][j][1] <= 1)
                    imgArraysMethod()[i][j].setEffect(colorAdjust);
//                    imgArraysMethod()[i][j].setDisable(true);
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

        // bot pickup card from player
        if (Round > 1) {
            //botClass.botPickCardPlayer();
            System.out.println(">> Round: " + Round);
            int[][][] temp = playerClass.getCardsChecked();
            int numCardsOnPlayerHand = (int) Math.round(Math.random() * (playerClass.amountCards() - 1) + 1);
            int finalI = 0, finalJ = 0;
            for (int i = 0, tempCount = 0; i < temp.length; i++) {
                for (int j = 0; j < temp[i].length; j++) {
                    if (temp[i][j][1] <= 1 && temp[i][j][0] == 0)
                        tempCount++;
//                        finalI = i;
//                        finalJ = j;
//                    } else if (temp[i][j][1] <= 1 && temp[i][j][0] == 0)
//                        tempCount++;

                    if (tempCount == numCardsOnPlayerHand) {
                        tempCount++;
                        temp[i][j][0] = (randDirect == 0) ? 1 : 0;
                        imageView[i][j].setVisible(false);
                        System.out.println("bot" + temp[i][j][0] + " pick from player: " + i + "|" + j);
                        finalI = i;
                        finalJ = j;
                        playerClass.setCardsChecked(temp);
                    }

                }

            }
            if (randDirect == 0 && botClass.getAmountCard()[1] == 0) {
                temp[finalI][finalJ][0] = 2;
                if (botClass.getAmountCard()[2] == 0)
                    temp[finalI][finalJ][0] = 0;
            } else if (randDirect == 1 && botClass.getAmountCard()[0] == 0) {
                temp[finalI][finalJ][0] = 2;
                if (botClass.getAmountCard()[2] == 0)
                    temp[finalI][finalJ][0] = 1;

            }
            botClass.botPickCardPlayer(temp[finalI][finalJ][0], finalI, finalJ);
            botClass.botPickCardbot(randDirect);
        }

        playerClass.checkDuplicate();


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
//        for (int i = 0; i < backCards.length; i++) {
//            for (int j = 0; j < botClass.getCardPlace()[i]; j++) {
//                if (cardBotPlaceCount < 2 && botClass.getCardPlace()[i] <=13) {
////                    System.out.println(">> " + cardBotPlaceCount);
//                    cardBotPlaceCount++;
//                    backCards[i][j].visibleProperty().setValue(false);
//                } else if (cardBotPlaceCount >= 2 && botClass.getCardPlace()[i] <= 13) {
//                    cardBotPlaceCount = 0;
//                    j--;
//                }
//            }
//        }

//        for (int i = 0; i < backCards.length; i++) {
//            for (int j = 0; j < botClass.getAmountCard()[i]; j++) {
//                if (cardBotPlaceCount < 2 && botClass.getAmountCard()[i] <=13) {
////                    System.out.println(">> " + cardBotPlaceCount);
//                    cardBotPlaceCount++;
//                    backCards[i][j].visibleProperty().setValue(false);
//                } else if (cardBotPlaceCount >= 2 && botClass.getAmountCard()[i] <= 13) {
//                    cardBotPlaceCount = 0;
//                    j--;
//                }
//            }
//        }

        for (int i = 0; i < backCards.length; i++) {
            for (int j = 0; j < backCards[i].length; j++) {
                backCards[i][j].setVisible(false);
            }
        }
        int bot0 = 0, bot1 = 0, bot2 = 0;
        for (int j = backCards[0].length - 1; j >= 0; j--) {
            // bot0 Backcards
            if (botClass.getAmountCard()[0] > bot0) {
                bot0++;
                System.out.println("0 >> j: " + j + "\tbot0: " + botClass.getAmountCard()[0] + "print: " + bot0);
                backCards[0][j].setVisible(true);
            }

            // bot1 Backcards
            if (botClass.getAmountCard()[1] > bot1) {
                bot1++;
                System.out.println("1 >> j: " + j + "\tbot1: " + botClass.getAmountCard()[1] + "print: " + bot1);
                backCards[1][j].setVisible(true);
            }

            if (botClass.getAmountCard()[2] > bot2) {
                bot2++;
                System.out.println("2 >> j: " + j + "\tbot2: " + botClass.getAmountCard()[2] + "print: " + bot2);
                backCards[2][j].setVisible(true);
            }
        }
        System.out.println("BotCardsHand: " + bot0 + " " + bot1 + " " + bot2);
        System.out.println("AmountCards: " + botClass.getAmountCard()[0] + " " + botClass.getAmountCard()[1] + " " + botClass.getAmountCard()[2]);

        cardsSetDisable(true);
        System.out.println("ENDTURN SET!!");
        playerClass.setCardsChecked(checkCardsPlaceOnStage(tempCards));

        playerCardsPosition(tempCards);
    }
    //------------------------------------------------------------------------------------------------------------------
}