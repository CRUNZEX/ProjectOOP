package Game;

public class BotClass extends PlayerClass {
    private int bot[][][] = new int[3][4][13];
    private int cardBotCheck[][] = new int[13][3]; // [number][botหมายแรก]

    public BotClass() {
        for (int i = 0; i < getCardsRand().length; i++) {
            //int countBot0 = 0, countBot1 = 0, countBot2 = 0;
            for (int j = 0; j < getCardsRand()[i].length; j++) {
            //cardboard->0
                if (getCardsRand()[i][j] == 1) {
                    bot[0][i][j] = 0;
                    //countBot0++;
                    cardBotCheck[j][0] ++;
                } else if (getCardsRand()[i][j] == 2) {
                    bot[1][i][j] = 0;
                    //countBot1++;
                    cardBotCheck[j][1] ++;
                } else if (getCardsRand()[i][j] == 3) {
                    bot[2][i][j] = 0;
                    //countBot2++;
                    cardBotCheck[j][2] ++;
                }
            }
        }
    }

    public void checkCardMatch() {
        for (int i = 0; i < bot.length; i++) {
            for (int j = 0; j < cardBotCheck.length; j++) {
                while (cardBotCheck[j][i] >= 2) {
                    System.out.println("Bot" + i + " Place:" + j);
                    cardBotCheck[j][i] -= 2;

                }
            }
        }


    }
}
