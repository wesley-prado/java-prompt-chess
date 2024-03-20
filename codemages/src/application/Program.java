package application;

import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPosition;

public class Program {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();

		while (!chessMatch.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch);

				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPiece(sc);

				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);

				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPiece(sc);

				chessMatch.performChessMove(source, target);

				if (chessMatch.getPromoted() != null) {
					System.out.print("Enter piece for promotion (B/N/R/Q): ");
					String type = sc.nextLine();
					chessMatch.replacePromotedPiece(type);
				}
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}

		UI.clearScreen();
		UI.printMatch(chessMatch);
	}
}
