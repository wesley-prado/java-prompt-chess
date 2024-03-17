package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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

		checkUpLeftSquares(matrix);
		checkUpRightSquares(matrix);
		checkDownLeftSquares(matrix);
		checkDownRightSquares(matrix);

		return matrix;
	}

	private void checkUpLeftSquares(boolean[][] matrix) {
		int row = this.position.getRow() - 1;
		int column = this.position.getColumn() - 1;
		Position p = new Position(row, column);

		while (this.getBoard().isPositionValid(p) && !this.getBoard().isThereAPiece(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
			p.setValues(--row, --column);
		}

		checkCapturablePiece(matrix, p);
	}

	private void checkUpRightSquares(boolean[][] matrix) {
		int row = this.position.getRow() - 1;
		int column = this.position.getColumn() + 1;
		Position p = new Position(row, column);

		while (this.getBoard().isPositionValid(p) && !this.getBoard().isThereAPiece(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
			p.setValues(--row, ++column);
		}

		checkCapturablePiece(matrix, p);
	}

	private void checkDownLeftSquares(boolean[][] matrix) {
		int row = this.position.getRow() + 1;
		int column = this.position.getColumn() - 1;
		Position p = new Position(row, column);

		while (this.getBoard().isPositionValid(p) && !this.getBoard().isThereAPiece(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
			p.setValues(++row, --column);
		}

		checkCapturablePiece(matrix, p);
	}

	private void checkDownRightSquares(boolean[][] matrix) {
		int row = this.position.getRow() + 1;
		int column = this.position.getColumn() + 1;
		Position p = new Position(row, column);

		while (this.getBoard().isPositionValid(p) && !this.getBoard().isThereAPiece(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
			p.setValues(++row, ++column);
		}

		checkCapturablePiece(matrix, p);
	}

	private void checkCapturablePiece(boolean[][] matrix, Position position) {
		if (getBoard().isPositionValid(position) && isThereOpponentPiece(position)) {
			matrix[position.getRow()][position.getColumn()] = true;
		}
	}
}
