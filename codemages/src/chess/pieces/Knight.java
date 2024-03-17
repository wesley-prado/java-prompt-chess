package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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

	@Override
	public boolean[][] possibleMoves() {
		Board board = getBoard();
		boolean[][] matrix = new boolean[board.getRows()][board.getColumns()];

		upLeftLMovement(matrix);
		upRightLMovement(matrix);
		downLeftLMovement(matrix);
		downRightLMovement(matrix);
		leftUpLMovement(matrix);
		leftDownLMovement(matrix);
		rightUpLMovement(matrix);
		rightDownLMovement(matrix);

		return matrix;
	}

	private void upLeftLMovement(boolean[][] matrix) {
		int row = this.position.getRow() - 2;
		int column = this.position.getColumn() - 1;
		Position p = new Position(row, column);

		checkPosition(matrix, p);
		checkCapturablePiece(matrix, p);
	}

	private void upRightLMovement(boolean[][] matrix) {
		int row = this.position.getRow() - 2;
		int column = this.position.getColumn() + 1;
		Position p = new Position(row, column);

		checkPosition(matrix, p);
		checkCapturablePiece(matrix, p);
	}

	private void downLeftLMovement(boolean[][] matrix) {
		int row = this.position.getRow() + 2;
		int column = this.position.getColumn() - 1;
		Position p = new Position(row, column);

		checkPosition(matrix, p);
		checkCapturablePiece(matrix, p);
	}

	private void downRightLMovement(boolean[][] matrix) {
		int row = this.position.getRow() + 2;
		int column = this.position.getColumn() + 1;
		Position p = new Position(row, column);

		checkPosition(matrix, p);
		checkCapturablePiece(matrix, p);
	}

	private void leftUpLMovement(boolean[][] matrix) {
		int row = this.position.getRow() - 1;
		int column = this.position.getColumn() - 2;
		Position p = new Position(row, column);

		checkPosition(matrix, p);
		checkCapturablePiece(matrix, p);
	}

	private void leftDownLMovement(boolean[][] matrix) {
		int row = this.position.getRow() + 1;
		int column = this.position.getColumn() - 2;
		Position p = new Position(row, column);

		checkPosition(matrix, p);
		checkCapturablePiece(matrix, p);
	}

	private void rightUpLMovement(boolean[][] matrix) {
		int row = this.position.getRow() - 1;
		int column = this.position.getColumn() + 2;
		Position p = new Position(row, column);

		checkPosition(matrix, p);
		checkCapturablePiece(matrix, p);
	}

	private void rightDownLMovement(boolean[][] matrix) {
		int row = this.position.getRow() + 1;
		int column = this.position.getColumn() + 2;
		Position p = new Position(row, column);

		checkPosition(matrix, p);
		checkCapturablePiece(matrix, p);
	}

	private void checkPosition(boolean[][] matrix, Position position) {
		if (getBoard().isPositionValid(position) && !getBoard().isThereAPiece(position)) {
			matrix[position.getRow()][position.getColumn()] = true;
		}
	}

	private void checkCapturablePiece(boolean[][] matrix, Position position) {
		if (getBoard().isPositionValid(position) && isThereOpponentPiece(position)) {
			matrix[position.getRow()][position.getColumn()] = true;
		}
	}
}
