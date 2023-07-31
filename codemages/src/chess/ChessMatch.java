package chess;

import java.util.Arrays;
import java.util.List;

import boardgame.Board;
import boardgame.Position;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

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
		placePieces(startRow, createPieces(Color.BLACK));
		placePawns(++startRow, Color.BLACK);
	}

	private void placeWhitePieces() {
		int startRow = 7;
		placePieces(startRow, createPieces(Color.WHITE));
		placePawns(--startRow, Color.WHITE);
	}

	private List<ChessPiece> createPieces(Color color) {
		return Arrays.asList(
				new Rook(board, color),
				new Knight(board, color),
				new Bishop(board, color),
				new Queen(board, color),
				new King(board, color),
				new Bishop(board, color),
				new Knight(board, color),
				new Rook(board, color));
	}

	private void placePieces(int startRow,
			List<ChessPiece> pieces) {
		for (int i = 0; i < pieces.size(); i++) {
			board.placePiece(pieces.get(i), new Position(startRow, i));
		}
	}

	private void placePawns(int startRow, Color color) {
		for (int i = 0; i < board.getColumns(); i++) {
			board.placePiece(new Pawn(board, color),
					new Position(startRow, i));
		}
	}
}
