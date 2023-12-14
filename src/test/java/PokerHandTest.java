import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PokerHandTest {


    @Test
    public void testHighCardComparison() {
        PokerHand hand1 = new PokerHand("KS 2H 5C JD TD");
        PokerHand hand2 = new PokerHand("2C 3C AC 4C 5C");
        assertTrue(hand1.compareTo(hand2) < 0);
    }

    @Test
    public void testEqualHighCardComparison() {
        PokerHand hand1 = new PokerHand("KS 2H 5C JD TD");
        PokerHand hand2 = new PokerHand("KH 2C 5D JD TC");
        assertEquals(0, hand1.compareTo(hand2));
    }
    @Test
    public void testDifferentFlashComparison() {
        PokerHand hand1 = new PokerHand("KS 2S 5S JS TS");
        PokerHand hand2 = new PokerHand("AD 3D 6D QD 9D");
        assertTrue(hand1.compareTo(hand2) < 0);
    }
    @Test
    public void testEqualFlashComparison() {
        PokerHand hand1 = new PokerHand("KS 2S 5S JS TS");
        PokerHand hand2 = new PokerHand("KD 2D 5D JD TD");
        assertEquals(0, hand1.compareTo(hand2));
    }
    @Test
    public void testDifferentHighCardComparison() {
        PokerHand hand1 = new PokerHand("KS 2H 5C JD TD");
        PokerHand hand2 = new PokerHand("AS 3H 6C QD 9D");
        assertTrue(hand1.compareTo(hand2) < 0);
    }

    @Test
    public void testAceAsLowestCard() {
        PokerHand hand1 = new PokerHand("2S 3H 4C 5D AD");
        PokerHand hand2 = new PokerHand("AS 3H 6C QD 9D");
        assertTrue(hand1.compareTo(hand2) < 0);
    }
    /* ----------------------------------------- */
    @Test
    void isRoyalFlush() {
        PokerHand hand = new PokerHand("AC JC QC KC TC");
        assertEquals("Royal Flush",hand.rankHandString());
    }

    @Test
    void isStraightFlush() {
        PokerHand hand = new PokerHand("TC JC QC KC 9C");
        assertEquals("Straight Flush",hand.rankHandString());
    }

    @Test
    void isFourOfAKind() {
        PokerHand hand = new PokerHand("AC AS AD QC AH");
        assertEquals("Four of a Kind",hand.rankHandString());
    }

    @Test
    void isFullHouse() {
        PokerHand hand = new PokerHand("AC AS AD QC QH");
        assertEquals("Full House",hand.rankHandString());
    }

    @Test
    void isFlush() {
        PokerHand hand = new PokerHand("1C JC QC KC 9C");
        assertEquals("Flush",hand.rankHandString());
    }

    @Test
    void isStraight() {
        PokerHand hand = new PokerHand("AC QC JC TC KH");
        assertEquals("Straight",hand.rankHandString());
        PokerHand hand1 = new PokerHand("9C TC JC QC KH");
        assertEquals("Straight",hand1.rankHandString());
    }

    @Test
    void isThreeOfAKind() {
        PokerHand hand = new PokerHand("KC TC KC QC KH");
        assertEquals("Three of a Kind",hand.rankHandString());
    }

    @Test
    void isTwoPair() {
        PokerHand hand = new PokerHand("9C 9C JC KC KH");
        assertEquals("Two Pair",hand.rankHandString());
    }

    @Test
    void isOnePair() {
        PokerHand hand = new PokerHand("TC TC 5C 3C KH");
        assertEquals("One Pair",hand.rankHandString());
    }
}