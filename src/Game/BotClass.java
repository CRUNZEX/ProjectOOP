package Game;

import java.util.ArrayList;

public class BotClass {
    //------------------------------------------------------------------------------------------------------------------
                                                                                                                      /*
    checkCardMatch-> ไว้เช็คจำนวนไพ่ ;-; ;-; ;-; ;-; ;-; ;-; ;-; ;-;
                                                                                                                      */
    //------------------------------------------------------------------------------------------------------------------
    // data fields

    private int[][][] bot = new int[3][4][13]; // [botหมายแรก][ดอกไพ่][หมายเลขไพ่] = 0(ไม่มีไพ่) 1(มีไพ่) 2(ลงแล้ว)
    private int[][] cardBotCheck = new int[13][3]; // [หมายเลขไพ่][botหมายเลข] = จำนวนไพ่
    private int[] cardPlace = new int[3]; //[botหมายเลข] = จำนวนไพ่ที่botแต่ละตัวลง
    private int[] amountCard = new int[3];// จำนวนไ่พ่บนมือbot

    //------------------------------------------------------------------------------------------------------------------
    // constructor

    public BotClass(int[][] cards) {
        for (int i = 0; i < cards.length; i++) {
            //int countBot0 = 0, countBot1 = 0, countBot2 = 0;
            for (int j = 0; j < cards[i].length; j++) {
//                bot[getCardsRand()[i][j] - 1][i][j] = 1;
//                cardBotCheck[j][getCardsRand()[i][j] - 1]++;

                //cardboard->0
                if (cards[i][j] == 1) {
                    bot[0][i][j] = 1;
                    //countBot0++;
                    cardBotCheck[j][0]++;
                    amountCard[0]++;
                } else if (cards[i][j] == 2) {
                    bot[1][i][j] = 1;
                    //countBot1++;
                    cardBotCheck[j][1]++;
                    amountCard[1]++;
                } else if (cards[i][j] == 3) {
                    bot[2][i][j] = 1;
                    //countBot2++;
                    cardBotCheck[j][2]++;
                    amountCard[2]++;
                }
            }
        }
        checkCard(cards);
    }

    //------------------------------------------------------------------------------------------------------------------
    // getter setter
    public int[] getCardPlace() {
        return cardPlace;
    }

    public void setBot(int[][][] bot) {
        this.bot = bot;
    }

    public int[][][] getBot() {
        return bot;
    }

    //------------------------------------------------------------------------------------------------------------------
    // method

    public void checkCardMatch(int botNum) {
        for (int i = 0; i < cardBotCheck.length; i++) { //Num
            int Checktwo = 0;
            for (int j = 0; j < bot[botNum].length; j++) { //Suit
                if (cardBotCheck[i][botNum] >= 2 && bot[botNum][j][i] == 1) {
//                    System.out.println("bot " + botNum + " " + j + " "+ i);
                    Checktwo++;
                    bot[botNum][j][i] = 2;
                    if (Checktwo == 2) {
                        cardBotCheck[i][botNum] -= 2;
                        amountCard[botNum] -= 2;
                    }
                    if (amountCard[botNum] == 1) {
                        System.out.println(botNum + "Win  XD");
                    }
                    cardPlace[botNum]++;
                }
            }
        }
    }

    public void checkCard(int[][] cards) {
        int a = 0;
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards[i].length; j++) {
                System.out.print(cards[i][j] + " ");
                a++;
            }
        }
        System.out.println(">> " + a);
    }

    public int[] playerPickCard(int botNum) {
        int[] cardPick = new int[2]; // Cardที่playerหยิบไป
        int numCardsOnBotHand = (int) Math.round(Math.random() * amountCard[botNum]);                                   // random num on bot handles
        System.out.println("numcardsBot: " + numCardsOnBotHand);
        for (int i = 0, tempCount = 0; i < 4; i++) { //Suit
            for (int j = 0; j < 13; j++) { //Num
                if (bot[botNum][i][j] == 1 && tempCount == 0) {
                    tempCount++;
                    cardPick[0] = i; //Suit
                    cardPick[1] = j; //Num
                } else if (bot[botNum][i][j] == 1)
                    tempCount++;


                if (tempCount == numCardsOnBotHand) {
                    cardPick[0] = i; //Suit
                    cardPick[1] = j; //Num
                    bot[botNum][i][j] = 0;
                    cardBotCheck[j][botNum]--;
                    amountCard[botNum]--;
                    tempCount++;

                    return cardPick;
                }

            }
        }
        return cardPick;
    }

    public void botPickCardPlayer(int botNum, int suit, int num) {
        bot[botNum][suit][num] = 1;
        cardBotCheck[num][botNum]++;
        amountCard[botNum]++;
    }

    public void botPickCardbot(int direction) {
        // random num on bot handles
        //System.out.println("numcardsBot : " + numCardsOnBotHand);
        for (int botNum = 0; botNum < 3; botNum++) {
            if (botNum != direction) {
                int numCardsOnBotHand = (int) Math.round(Math.random() * amountCard[botNum]);
                for (int i = 0, tempCount = 0; i < 4; i++) { //Suit
                    for (int j = 0; j < 13; j++) { //Num
                        if (bot[botNum][i][j] == 1 && tempCount == 0)
                            tempCount = 0;
                        else if (bot[botNum][i][j] == 1)
                            tempCount++;

                        if (tempCount == numCardsOnBotHand) {
                            bot[botNum][i][j] = 0;
                            cardBotCheck[j][botNum]--;
                            amountCard[botNum]--;
                            tempCount++;

                            if (direction == 0) {
                                int botNumpick = (botNum + 1) % 3;
                                bot[botNumpick][i][j] = 1;
                                cardBotCheck[j][botNumpick]++;
                                amountCard[botNumpick]++;
                                System.out.println("Bot" + botNumpick + " pickCard:" + i + "|" + j + " Frombot" + botNum);
                            } else if (direction == 1) {
                                int botNumpick = botNum - 1;
                                if (botNumpick == -1)
                                    botNumpick = 2;
                                bot[botNumpick][i][j] = 1;
                                cardBotCheck[j][botNumpick]++;
                                amountCard[botNumpick]++;
                                System.out.println("Bot" + botNumpick + " pickCard:" + i + "|" + j + " Frombot" + botNum);
                            }
                        }


                    }
                }
            }
        }
    }

    public void checkCardOnHand() {//checkcardบนมือบอทเฉยนะนพ
        for (int i = 0; i < bot.length; i++) { //bot
            System.out.print("Bot" + i + ": ");
            for (int j = 0; j < bot[i].length; j++) {//suit
                for (int k = 0; k < bot[i][j].length; k++) {//num
                    if (bot[i][j][k] == 1)
                        System.out.print(j + "|" + k + " ");

                }

            }
            System.out.println();
        }
    }
}