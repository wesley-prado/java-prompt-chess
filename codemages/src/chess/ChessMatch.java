package chess;

import java.util.List;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPieceFactory.PieceType;

public class ChessMatch {
	private Board board;

	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] matrix = new ChessPiece[board.getRows()][board
				.getColumns()];

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++)
				matrix[i][j] = (ChessPiece) board.getPiece(i, j);
		}

		return matrix;
	}

	private void initialSetup() {
		placeBlackPieces();
		placeWhitePieces();
	}

	private void placeBlackPieces() {
		int startRow = 0;
		placePieces(startRow,
				ChessPieceFactory.createFirstRowPieces(board, Color.BLACK));
		placePawns(++startRow, Color.BLACK);
	}

	private void placeWhitePieces() {
		int startRow = 7;
		placePieces(startRow,
				ChessPieceFactory.createFirstRowPieces(board, Color.WHITE));
		placePawns(--startRow, Color.WHITE);
	}

	private void placePieces(int startRow,
			List<ChessPiece> pieces) {
		for (int i = 0; i < pieces.size(); i++) {
			board.placePiece(pieces.get(i), new Position(startRow, i));
		}
	}

	private void placePawns(int startRow, Color color) {
		for (int i = 0; i < board.getColumns(); i++) {
			board.placePiece(
					ChessPieceFactory.createPiece(PieceType.PAWN, board, color),
					new Position(startRow, i));
		}
	}
}
