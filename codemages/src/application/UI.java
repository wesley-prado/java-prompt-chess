package application;

import chess.ChessPiece;

public class UI {

	private UI() {
	}

	public static void printBoard(ChessPiece[][] pieces) {
		int boardLength = pieces.length;

		for (int i = 0; i < boardLength; i++) {
			System.out.print((boardLength - i) + " ");

			for (int j = 0; j < boardLength; j++)
				printPiece(pieces[i][j]);

			System.out.println();
		}

		System.out.println("  a b c d e f g h");
	}

	public static void printPiece(ChessPiece piece) {
		if (piece == null)
			System.out.print("-");

		else
			System.out.print(piece);

		System.out.print(" ");
	}
}
