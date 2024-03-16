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
		Position p = new Position(0, 0);

		int row = this.position.getRow() - 1;
		int column = this.position.getColumn() - 1;
		p.setValues(row, column);

		while (this.getBoard().isPositionValid(p) && !this.getBoard().isThereAPiece(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
			p.setValues(--row, --column);
		}

		checkCapturablePiece(matrix, p);
	}

	private void checkUpRightSquares(boolean[][] matrix) {
		Position p = new Position(0, 0);

		int row = this.position.getRow() - 1;
		int column = this.position.getColumn() + 1;
		p.setValues(row, column);

		while (this.getBoard().isPositionValid(p) && !this.getBoard().isThereAPiece(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
			p.setValues(--row, ++column);
		}

		checkCapturablePiece(matrix, p);
	}

	private void checkDownLeftSquares(boolean[][] matrix) {
		Position p = new Position(0, 0);

		int row = this.position.getRow() + 1;
		int column = this.position.getColumn() - 1;
		p.setValues(row, column);

		while (this.getBoard().isPositionValid(p) && !this.getBoard().isThereAPiece(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
			p.setValues(++row, --column);
		}

		checkCapturablePiece(matrix, p);
	}

	private void checkDownRightSquares(boolean[][] matrix) {
		Position p = new Position(0, 0);

		int row = this.position.getRow() + 1;
		int column = this.position.getColumn() + 1;
		p.setValues(row, column);

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
