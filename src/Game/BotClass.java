package Game;

public class BotClass extends PlayerClass {
    //------------------------------------------------------------------------------------------------------------------
                                                                                                                      /*
    checkCardMatch-> ไว้เช็คจำนวนไพ่ ;-; ;-; ;-; ;-; ;-; ;-; ;-; ;-;
                                                                                                                      */
    //------------------------------------------------------------------------------------------------------------------
    // data fields

    private int[][][] bot = new int[3][4][13]; // [botหมายแรก][ดอกไพ่][หมายเลขไพ่] = 0(ไม่มีไพ่) 1(มีไพ่) 2(ลงแล้ว)
    private int[][] cardBotCheck = new int[13][3]; // [หมายเลขไพ่][botหมายเลข] = จำนวนไพ่
    private int[] cardPlace = new int[3];


    //------------------------------------------------------------------------------------------------------------------
    // constructor

    public BotClass() {
        for (int i = 0; i < getCardsRand().length; i++) {
            //int countBot0 = 0, countBot1 = 0, countBot2 = 0;
            for (int j = 0; j < getCardsRand()[i].length; j++) {
//                bot[getCardsRand()[i][j] - 1][i][j] = 1;
//                cardBotCheck[j][getCardsRand()[i][j] - 1]++;

                //cardboard->0
                if (getCardsRand()[i][j] == 1) {
                    bot[0][i][j] = 1;
                    //countBot0++;
                    cardBotCheck[j][0]++;
                } else if (getCardsRand()[i][j] == 2) {
                    bot[1][i][j] = 1;
                    //countBot1++;
                    cardBotCheck[j][1]++;
                } else if (getCardsRand()[i][j] == 3) {
                    bot[2][i][j] = 1;
                    //countBot2++;
                    cardBotCheck[j][2]++;
                }
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // getter setter
    public int[] getCardPlace() {
        return cardPlace;
    }

    public void setBot(int[][][] bot) { this.bot = bot; }

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
                    System.out.println("bot " + botNum + " " + j + " "+ i);
                    Checktwo++;
                    bot[botNum][j][i] = 2;
                    if (Checktwo == 2) {
                        cardBotCheck[i][botNum] -= 2;
                    }
                    
                    cardPlace[botNum]++;
                }
            }
        }
    }
}
