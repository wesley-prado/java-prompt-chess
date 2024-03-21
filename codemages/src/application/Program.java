package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPosition;
import chess.constants.PromotionPiece;

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
					chessMatch.replacePromotedPiece(readPromotionPiece(sc));
				}
			}
			catch (InputMismatchException | ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}

		UI.clearScreen();
		UI.printMatch(chessMatch);
	}

	private static PromotionPiece readPromotionPiece(Scanner sc) {
		PromotionPiece promoPiece = null;

		while (promoPiece == null) {
			System.out.print("Enter piece for promotion (B/N/R/Q): ");

			try {
				promoPiece = PromotionPiece.valueOf(sc.nextLine().toUpperCase());
			}
			catch (Exception e) {
				promoPiece = null;
			}
		}

		return promoPiece;
	}
}
