package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	public Pawn(Board chessboard, Color color) {
		super(chessboard, color);
	}

	@Override
	public String toString() {
		return "P";
	}
}
