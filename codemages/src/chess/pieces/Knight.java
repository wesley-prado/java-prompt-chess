package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {
	public Knight(Board chessboard, Color color) {
		super(chessboard, color);
	}

	@Override
	public String toString() {
		return "N";
	}
}
