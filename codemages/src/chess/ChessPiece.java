package chess;

import boardgame.Piece;
import boardgame.Position;
import boardgame.Board;

public abstract class ChessPiece extends Piece {
	private Color color;

	protected ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().getPiece(position);
		return piece != null && piece.getColor() != this.color;
	}
}
