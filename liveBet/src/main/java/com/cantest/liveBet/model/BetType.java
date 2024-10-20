package com.cantest.liveBet.model;

public enum BetType {
	
    HOME_WIN 	(1),
    DRAW 		(0),
    AWAY_WIN 	(2);
    
    private final int value;
	
	BetType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public static BetType fromString(String betType) {
        switch (betType) {
            case "HOME_WIN":
                return HOME_WIN;
            case "DRAW":
                return DRAW;
            case "AWAY_WIN":
                return AWAY_WIN;
            default:
                throw new IllegalArgumentException("Unknown bet type: " + betType);
        }
    }
    
}