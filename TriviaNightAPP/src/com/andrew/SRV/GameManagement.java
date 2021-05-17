package com.andrew.SRV;

import java.util.Random;

public class GameManagement {
	// generates game code when game is hosted
	public static int generateGameCode() {
		Random rnd = new Random();
		int n = 10000 + rnd.nextInt(900000);
		return n;
	}
}
