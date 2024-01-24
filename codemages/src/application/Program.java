package application;

import chess.ChessMatch;

public class Program {
	public static void main(String[] args) {
		ChessMatch chessMatch = new ChessMatch();

		while (true) {
			UI.clearScreen();
			UI.printBoard(chessMatch.getPieces());

			System.out.println();
			System.out.print("Source: ");
			ChessPosition source = UI.readChessPiece(sc);

			System.out.println();
			System.out.print("Target: ");
			ChessPosition target = UI.readChessPiece(sc);

			ChessPiece capturedPiece = chessMatch.performChessPiece(source, target);
		}

	}
}
