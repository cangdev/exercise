package coinChanger;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cantest.coinChanger.CoinChanger;

public class CoinChangerTest {

	private final CoinChanger coinChanger;

	public CoinChangerTest() {
		coinChanger = new CoinChanger();
	}

	@Test
	public void testCoinChange1() {
		int[] coins = {1, 5, 10, 25};
	    int  amount = 15;

	    assertEquals(6, coinChanger.countCombinations(amount, coins));
	}
	
	@Test
	public void testCoinChange2() {
		int[] coins = {1, 5, 10, 25};
		int  amount = 100;
		
		assertEquals(242, coinChanger.countCombinations(amount, coins));
	}
	
	@Test
	public void testCoinChange3() {
		int[] coins = {1, 5, 10, 25};
	    int  amount = 3000;

	    assertEquals(3681531, coinChanger.countCombinations(amount, coins));
	}
	
	@Test
	public void testCoinChange4() {
		int[] coins = {};
		int  amount = 100;
		
		assertEquals(0, coinChanger.countCombinations(amount, coins));
	}
	
	@Test
	public void testCoinChange5() {
		int[] coins = {1, 5, 10, 25};
		int  amount = 1;
		
		assertEquals(1, coinChanger.countCombinations(amount, coins));
	}
	
	@Test
	public void testCoinChange6() {
		int[] coins = {1, 5, 10, 25};
		int  amount = 0;
		
		assertEquals(1, coinChanger.countCombinations(amount, coins));
	}
	
	@Test
	public void testCoinChange7() {
		int[] coins = {25, 10, 5, 1};
		int  amount = 100;
		
		assertEquals(242, coinChanger.countCombinations(amount, coins));
	}
	
	@Test
	public void testCoinChange8() {
		int[] coins = {25, 10, 5, 1};
		int  amount = 15;
		
		assertEquals(6, coinChanger.countCombinations(amount, coins));
	}

}
