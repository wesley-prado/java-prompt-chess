package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {
	public Queen(Board chessboard, Color color) {
		super(chessboard, color);
	}

	@Override
	public String toString() {
		return "Q";
	}
}