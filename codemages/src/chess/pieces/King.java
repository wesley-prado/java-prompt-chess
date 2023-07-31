package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	public King(Board chessboard, Color color) {
		super(chessboard, color);
	}

	@Override
	public String toString() {
		return "K";
	}
}
