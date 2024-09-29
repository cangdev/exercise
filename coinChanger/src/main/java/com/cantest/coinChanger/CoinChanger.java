package com.cantest.coinChanger;

public class CoinChanger {
	
	private final static int quarter 	= 25;
	private final static int dime 		= 10;
	private final static int nickel 	= 5;
	private final static int penny 		= 1;
	
	public static void main(String[] args) {
		
		long s = System.currentTimeMillis();
		
		int coins[] = { quarter, dime, nickel, penny };
		int amountInCents = 100; // 1 dollar = 100 cents
		
		System.out.println(countCombinations(amountInCents,coins));
		System.out.println("Duration in ms:" + (System.currentTimeMillis() - s));
	}
	
	
	public static int countCombinations(int amount, int[] coins) {
		
		int[] combinations = new int[amount + 1];
		
		combinations[0] = 1;
		
		for (int coin : coins) {
			
			for (int i = coin; i <= amount; i++) {
				
				combinations[i] += combinations[i - coin];
				printAmount(combinations);
			}
		}
		
		return combinations[amount];
	}
	
	// Prints out the array.
	public static void printAmount(int[] arr) {
		for (int i = 0; i< arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
	}
}
