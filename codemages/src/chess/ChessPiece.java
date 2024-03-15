package chess;

import boardgame.Piece;
import boardgame.Position;
import boardgame.Board;

public abstract class ChessPiece extends Piece {
	private Color color;
	private int moveCount;

	protected ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void incrementMoveCount() {
		moveCount++;
	}

	public void decrementMoveCount() {
		moveCount--;
	}

	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(this.position);
	}

	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().getPiece(position);
		return piece != null && piece.getColor() != this.color;
	}
}
