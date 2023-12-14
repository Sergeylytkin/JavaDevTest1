import java.util.*;
import java.util.stream.Collectors;

public class PokerHand implements Comparable{
    private String[] hand;
    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
    private static final String[] SUITS = {"S", "H", "D", "C"};
    private static final Map<Integer,String> map = new HashMap<>();
    {
        map.put(9,"Royal Flush");
        map.put(8,"Straight Flush");
        map.put(7,"Four of a Kind");
        map.put(6,"Full House");
        map.put(5,"Flush");
        map.put(4,"Straight");
        map.put(3,"Three of a Kind");
        map.put(2,"Two Pair");
        map.put(1,"One Pair");
        map.put(0,"High Card");
    }
    public PokerHand(String handStr) {
        this.hand = handStr.split(" ");
    }

    public String rankHandString() {
        return map.get(rankHand());
    }

    public int rankHand() {
        String[] ranks = Arrays.stream(hand).map(card -> card.substring(0, 1)).toArray(String[]::new);
        String[] suits = Arrays.stream(hand).map(card -> card.substring(1, 2)).toArray(String[]::new);

        if (isRoyalFlush(ranks, suits)) {
            return 9;
        } else if (isStraightFlush(ranks, suits)) {
            return 8;
        } else if (isFourOfAKind(ranks)) {
            return 7;
        } else if (isFullHouse(ranks)) {
            return 6;
        } else if (isFlush(suits)) {
            return 5;
        } else if (isStraight(ranks)) {
            return 4;
        } else if (isThreeOfAKind(ranks)) {
            return 3;
        } else if (isTwoPair(ranks)) {
            return 2;
        } else if (isOnePair(ranks)) {
            return 1;
        } else {
            return 0;
        }
    }

    public boolean isRoyalFlush(String[] ranks, String[] suits) {
        //проверка на наличие 5 лучших карт и карты можно расположить в точной последовательности по рангу
        return Arrays.asList(ranks).containsAll(Arrays.asList("T", "J", "Q", "K", "A")) && isStraightFlush(ranks, suits);
    }

    public boolean isStraightFlush(String[] ranks, String[] suits) {
        //если все карты единой масти и карты можно расположить в точной последовательности по рангу
        return isFlush(suits) && isStraight(ranks);
    }

    public boolean isFourOfAKind(String[] ranks) {
        //если карта одного ранга встречается 4 раза
        Map<String, Long> counts = Arrays.stream(ranks).collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        return counts.values().stream().anyMatch(count -> count == 4);
    }

    public boolean isFullHouse(String[] ranks) {
        //если карта одного ранга встречается 3 раза и если карта другого ранга встречается 2 раза
        Map<String, Long> counts = Arrays.stream(ranks).collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        return counts.values().stream().anyMatch(count -> count == 3) && counts.values().stream().anyMatch(count -> count == 2);
    }

    public boolean isFlush(String[] suits) {
        //если все карты единой масти
        return Arrays.stream(suits).distinct().count() == 1;
    }

    public boolean isStraight(String[] ranks) {
        //если карты можно расположить в точной последовательности по рангу
        String[] arrStr = rankSort(ranks);//сортировка по возрастанию
        for (int i = 0; i < arrStr.length - 1; i++) {
            if (!RANKS[Arrays.asList(RANKS).indexOf(arrStr[i]) + 1].equalsIgnoreCase(arrStr[i + 1])) {
                return false;
            }
        }
        return true;
    }

    public boolean isThreeOfAKind(String[] ranks) {
        //если карта одного ранга встречается 3 раза
        Map<String, Long> counts = Arrays.stream(ranks).collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        return counts.values().stream().anyMatch(count -> count == 3);
    }

    public boolean isTwoPair(String[] ranks) {
        //если пар карт с одинаковым рангом две штуки
        return Arrays.stream(ranks).filter(rank -> Arrays.asList(ranks).indexOf(rank) != Arrays.asList(ranks).lastIndexOf(rank)).distinct().count() == 2;
    }

    public boolean isOnePair(String[] ranks) {
        //если есть хотя бы две карты одного ранга (PS: при проверке без верхних if тут карт может быть и больше)
        //return Arrays.stream(ranks).anyMatch(rank -> Arrays.asList(ranks).indexOf(rank) != Arrays.asList(ranks).lastIndexOf(rank));
        //если карта одного ранга встречается 2 раза
        Map<String, Long> counts = Arrays.stream(ranks).collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        return counts.values().stream().anyMatch(count -> count == 2);
    }
    private static String[] rankSort(String[] ranks){
        //сортировка по возрастанию
        String[] sortArr = ranks;
        for (int _i = 0; _i < sortArr.length - 1; _i++) {
            for(int _j = 0; _j < sortArr.length - _i - 1; _j++) {
                if (Arrays.asList(RANKS).indexOf(sortArr[_j+1]) < Arrays.asList(RANKS).indexOf(sortArr[_j]) ) {
                    String swap = sortArr[_j];
                    sortArr[_j] = sortArr[_j + 1];
                    sortArr[_j + 1] = swap;
                }
            }
        }
        return sortArr;
    }
    @Override
    public int compareTo(Object compared) {
        if(this.rankHand()>((PokerHand)compared).rankHand()) {
            return 1;
        }else if(this.rankHand()<((PokerHand)compared).rankHand()){
            return -1;
        }else {
            return this.cardsCompare((PokerHand)compared);
        }
    }

    private int cardsCompare(PokerHand compared){
        String[] thisRank = Arrays.stream(this.hand).map(card -> card.substring(0, 1)).toArray(String[]::new);
        String[] compRank = Arrays.stream(compared.hand).map(card -> card.substring(0, 1)).toArray(String[]::new);
        thisRank = rankSort(thisRank);
        compRank = rankSort(compRank);
        for (int _i = thisRank.length-1; _i >=0 ; _i--) {
            if (Arrays.asList(RANKS).indexOf(thisRank[_i]) > Arrays.asList(RANKS).indexOf(compRank[_i]) ) {
                return 1;
            }if(Arrays.asList(RANKS).indexOf(thisRank[_i]) < Arrays.asList(RANKS).indexOf(compRank[_i]) ) {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        String result="";
        for (int _i = 0; _i < hand.length; _i++) {
            result+=hand[_i];
            if(_i<hand.length-1)
                result+=" ";
        }
        return result;
    }

    public static void main(String[] args) {
        ArrayList<PokerHand> hands = new ArrayList<PokerHand>();
        hands.add(new PokerHand("KS 2H 5C JD TD"));//High Card
        hands.add(new PokerHand("1C JC QC KC 9C"));//Flush
        hands.add(new PokerHand("9C JC QC KC TC"));//Straight Flush
        hands.add(new PokerHand("2C 3C AC 4C 5C"));//Flush
        hands.add(new PokerHand("3S 2H 5C 6D TD"));//High Card
        hands.add(new PokerHand("AC JC QC KC TC"));//Royal Flush

        Collections.sort(hands);
        for (int _i = 0; _i < hands.size(); _i++) {
            PokerHand pHand = hands.get(_i);
            System.out.println(pHand.toString()+" - "+pHand.rankHandString());
        }

    }


}
