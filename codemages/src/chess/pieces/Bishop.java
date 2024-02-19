package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {
	public Bishop(Board chessboard, Color color) {
		super(chessboard, color);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		Board board = getBoard();
		boolean[][] matrix = new boolean[board.getRows()][board.getColumns()];

		return matrix;
	}
}
