package Game;

import java.util.Arrays;

public class PlayerClass {
    //------------------------------------------------------------------------------------------------------------------
                                                                                                                      /*
     === stage cards ===
     Cards: position to name of cards
        0(Player) 1(Bot1) 2(Bot2) 3(Bot3)

     CardsChecked:
        - [][][0] : Cards -> 0(Player) 1(Bot1) 2(Bot2) 3(Bot3)
        - [][][1]
            0 not duplicate -> gray out in first turn
            1 duplicate -> light in first turn
            2 upper cards -> after click this cards upper
            3 place button -> place cards on stage
                                                                                                                      */
    //------------------------------------------------------------------------------------------------------------------
    // data fields
    
    private CardsClass cardsClass;
    private int[][][] cardsChecked;                                         // main
    private int[][] cardsRand;                                              // random
    
    private String[][] imgArraysID;                                         // image id name
    
    private String[] cardsPickupID;                                         // cards id pickup
    private String[][] cardsPickupID_split;
    private int cardsPickupID_count;

    //------------------------------------------------------------------------------------------------------------------
    // constructor

    public PlayerClass() {
        // assign
        cardsClass = new CardsClass();
        cardsChecked = new int[4][13][10];
        imgArraysID = new String[][]{
                {"Spade_1", "Spade_2", "Spade_3", "Spade_4", "Spade_5", "Spade_6", "Spade_7", "Spade_8", "Spade_9", "Spade_10", "Spade_11", "Spade_12", "Spade_13"},
                {"Heart_1", "Heart_2", "Heart_3", "Heart_4", "Heart_5", "Heart_6", "Heart_7", "Heart_8", "Heart_9", "Heart_10", "Heart_11", "Heart_12", "Heart_13"},
                {"Diamond_1", "Diamond_2", "Diamond_3", "Diamond_4", "Diamond_5", "Diamond_6", "Diamond_7", "Diamond_8", "Diamond_9", "Diamond_10", "Diamond_11", "Diamond_12", "Diamond_13"},
                {"Club_1", "Club_2", "Club_3", "Club_4", "Club_5", "Club_6", "Club_7", "Club_8", "Club_9", "Club_10", "Club_11", "Club_12", "Club_13"}
        };

        cardsRand = cardsClass.random();
        
        cardsPickupID = new String[2];
        cardsPickupID_split = new String[2][2];
        cardsPickupID_count = 0;

        for (int i = 0; i < cardsRand.length; i++) {                                                                    // assign cards from random to main( cardChecked )
            for (int j = 0; j < cardsRand[i].length; j++) {
                cardsChecked[i][j][0] = cardsRand[i][j];
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // getter setter
        // -----

    public int[][] getCardsRand() {
        return cardsRand;
    }

    public int[][] cards() {
        return cardsRand;
    }

        // -----
    public void setCardsChecked(int[][][] cardsChecked) {
        System.out.println("SET แล้วนะCARDS CHECKED!!");
        System.out.println();
        this.cardsChecked = cardsChecked;
        int tempCount = 0;
        for (int i = 0; i < this.cardsChecked.length; i++) {
            for (int j = 0; j < this.cardsChecked[i].length; j++) {
                System.out.print(this.cardsChecked[i][j][1] + " ");
                if (this.cardsChecked[i][j][1] == 3)
                    tempCount++;
            }
            System.out.println();
        }

        System.out.println("CARDS PLACE: " + tempCount);
        System.out.println();
    }

    public int[][][] getCardsChecked() {
        return cardsChecked;
    }
    
        // -----
    public String[][] getImgArraysID() {
        return imgArraysID;
    }
    
        // -----
    public void setCardsPickupID(String cardsPickupID) {
        this.cardsPickupID[cardsPickupID_count] = cardsPickupID;
        setCardsPickupID_split(cardsPickupID);
        cardsPickupID_count++;
        
        if (cardsPickupID_count == 2)
            Arrays.sort(this.cardsPickupID);
    }

    public String[] getCardsPickupID() {
        return cardsPickupID;
    }
    
    public void setCardsPickupID_NULL() {
        cardsPickupID_count--;
        cardsPickupID[cardsPickupID_count] = null;
        cardsPickupID_split[cardsPickupID_count]= null;
    }
    
    public void setCardsPickupID_FILLNULL() {
        Arrays.fill(cardsPickupID, null);
        Arrays.fill(cardsPickupID_split, null);
    }
    
        // -----
    private void setCardsPickupID_split(String cardsPickupID) {
        this.cardsPickupID_split[cardsPickupID_count] = cardsPickupID.split("_", -1);
    }

    public String[][] getCardsPickupID_split() {
        return cardsPickupID_split;
    }

        // -----
    public void setCardsPickupID_count(int cardsPickupID_count) {
        this.cardsPickupID_count = cardsPickupID_count;
    }

    public int getCardsPickupID_count() {
        return cardsPickupID_count;
    }
        // -----
    //------------------------------------------------------------------------------------------------------------------
    // method

    public void checkCard() {
        int a= 0;
        for (int i = 0; i < cards().length; i++) {
            for (int j = 0; j < cards()[i].length; j++) {
                System.out.print(cards()[i][j] + " ");
                a++;
            }
        }
        System.out.println(">> " + a);
    }

    public void checkDuplicate() {
        int[] tempCount = new int[13];

        for (int i = 0; i < getCardsChecked().length; i++) {                                                            // check count of number on cards
            for (int j = 0; j < getCardsChecked()[i].length; j++) {
                if (getCardsChecked()[i][j][0] == 0)
                    tempCount[j]++;
            }
        }

        for (int i = 0; i < getCardsChecked().length; i++) {                                                            // assign 1 if duplicate
            for (int j = 0; j < getCardsChecked()[i].length; j++) {
                if (tempCount[j] > 1)
                    cardsChecked[i][j][1] = 1;                                                                          // duplicate
                else
                    cardsChecked[i][j][1] = 0;
            }
        }
    }
    
    public int[] checkIndex(String id) {                                                                                // check index between 2 id
        int[] tempPos = new int[2];

        for (int i = 0; i < getImgArraysID().length; i++) {
            for (int j = 0; j < getImgArraysID()[i].length; j++) {
                if (id.equals(getImgArraysID()[i][j])) {
                    tempPos[0] = i;
                    tempPos[1] = j;
                }
            }
        }
        
        return tempPos;
    }
    
    //------------------------------------------------------------------------------------------------------------------
}
