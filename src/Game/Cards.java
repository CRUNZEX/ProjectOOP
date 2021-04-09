package Game;

public class Cards {
    // data fields
    private int[] card;
    private int[] num;
    private int[][] cardPlay;

    // constructor
    public Cards() {
        card = new int[52];
        num = new int[52];
        cardPlay = new int[4][13];
    }

    // getter
    public int[][] getCardPlay() {
        return cardPlay;
    }

    // method
    public int[][] random() {
        // random
        System.out.print("Swap ID Cards>> ");
        for (int i = 0; i < card.length; i++) {
            card[i] = (int) Math.round((Math.random() * 51));
            if (num[card[i]] == 0) {
                num[card[i]]++;
                System.out.print(card[i] + " ");
            }
            else
                i--;
        }
        System.out.println();

        // id to arrays position
        for (int i = 0; i < card.length; i++) {
            cardPlay[(card[i]/13)][card[i]%13] = i%4;
        }

        // test print
//        for (int i = 0; i < cardPlay.length; i++) {
//            for (int j = 0; j < cardPlay[i].length; j++) {
//                System.out.println("Suit:" + i + "\tNum:" + j + "\tPlayer:" + cardPlay[i][j]);
//            }
//        }

        return cardPlay;
    }

    public boolean checkCard(int numOnCard) {
        int temp = 0;
        for (int j = 0; j < cardPlay[numOnCard].length; j++) {
            if (cardPlay[numOnCard][j] == 0)
                temp++;
        }

        if (temp >= 2)
            return true;
        else
            return false;
    }
}
