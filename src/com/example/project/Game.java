package com.example.project;

import java.util.Random;

public class Game {

	private char mBoard[];
	private final static int BOARD_SIZE = 9;

	public static final char PLAYER_USER = 'X';
	public static final char PLAYER_ANDROID = 'O';

	public Random mRandom;

	
	public static int getBoardSize() {
		return BOARD_SIZE;
	}

	public Game() {
		mBoard = new char[BOARD_SIZE];

		for (int i = 0; i < BOARD_SIZE; i++)
			mBoard[i] = ' ';

		mRandom = new Random();

	}

	public void clearBoard() {

		for (int i = 0; i < BOARD_SIZE; i++) {
			mBoard[i] = ' ';
		}
	}

	public void setMove(char player, int location) {

		mBoard[location] = player;

	}

	public int getAndroidMove() {

		int move;

		for (int i = 0; i < BOARD_SIZE; i++) {
			if (mBoard[i] != PLAYER_USER && mBoard[i] != PLAYER_ANDROID) {
				char current = mBoard[i];
				mBoard[i] = PLAYER_ANDROID;
				if (isWon() == 3) {
					setMove(PLAYER_ANDROID, i);
					return i;
				} else {
					mBoard[i] = current;
				}
			}

		}
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (mBoard[i] != PLAYER_USER && mBoard[i] != PLAYER_ANDROID) {
				char current = mBoard[i];
				mBoard[i] = PLAYER_USER;
				if (isWon() == 2) {
					setMove(PLAYER_ANDROID, i);
					return i;
				} else {
					mBoard[i] = current;
				}
			}
		}
		do {
			move = mRandom.nextInt(getBoardSize());
		} while (mBoard[move] == PLAYER_USER || mBoard[move] == PLAYER_ANDROID);
		setMove(PLAYER_ANDROID, move);
		return move;
	}

	public int isWon() {

		for (int i = 0; i <= 6; i += 3) {
			if (mBoard[i] == PLAYER_USER && mBoard[i + 1] == PLAYER_USER
					&& mBoard[i + 2] == PLAYER_USER)
				return 2;
			if (mBoard[i] == PLAYER_ANDROID && mBoard[i + 1] == PLAYER_ANDROID
					&& mBoard[i + 2] == PLAYER_ANDROID)
				return 3;

		}
		for (int i = 0; i <= 2; i++) {
			if (mBoard[i] == PLAYER_USER && mBoard[i + 3] == PLAYER_USER
					&& mBoard[i + 6] == PLAYER_USER)
				return 2;
			if (mBoard[i] == PLAYER_ANDROID && mBoard[i + 3] == PLAYER_ANDROID
					&& mBoard[i + 6] == PLAYER_ANDROID)
				return 3;

		}
		if ((mBoard[0] == PLAYER_USER && mBoard[4] == PLAYER_USER && mBoard[8] == PLAYER_USER)
				|| mBoard[2] == PLAYER_USER
				&& mBoard[4] == PLAYER_USER
				&& mBoard[6] == PLAYER_USER)
			return 2;
		if ((mBoard[0] == PLAYER_ANDROID && mBoard[4] == PLAYER_ANDROID && mBoard[8] == PLAYER_ANDROID)
				|| mBoard[2] == PLAYER_ANDROID
				&& mBoard[4] == PLAYER_ANDROID
				&& mBoard[6] == PLAYER_ANDROID)
			return 3;
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (mBoard[i] != PLAYER_USER && mBoard[i] != PLAYER_ANDROID)
				return 0;
		}
		return 1;
	}
}
