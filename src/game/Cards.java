package game;

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

    // getter setter
    public int[][] getCardPlay() {
        return cardPlay;
    }

    // method
    public void random() {
        // random
        for (int i = 0; i < card.length; i++) {
            card[i] = (int) Math.round((Math.random() * 51));
            if (num[card[i]] == 0) {
                num[card[i]]++;
                System.out.print(card[i] + " ");
            }
            else
                i--;
        }

        // id to arrays position
        int[][] cardPlay = new int[4][13];
        for (int i = 0; i < card.length; i++) {
            cardPlay[(card[i]/13)][card[i]%13] = i%4;
        }

        // test print
        for (int i = 0; i < cardPlay.length; i++) {
            for (int j = 0; j < cardPlay[i].length; j++) {
                if(cardPlay[i][j]==0)
                {
                    System.out.println("Suit:"+i+" Num:"+j+" Player:0");
                }
                else if(cardPlay[i][j]==1)
                {
                    System.out.println("Suit:"+i+" Num:"+j+" Player:1");
                }
                else if(cardPlay[i][j]==2)
                {
                    System.out.println("Suit:"+i+" Num:"+j+" Player:2");
                }
                else if(cardPlay[i][j]==3)
                {
                    System.out.println("Suit:"+i+" Num:"+j+" Player:3");
                }
//                System.out.print(cardPlay[i][j] + " ");
            }
        }
    }
}
