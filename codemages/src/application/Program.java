package application;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPosition;
import chess.ChessPiece;

public class Program {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
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

			ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
		}

	}
}
