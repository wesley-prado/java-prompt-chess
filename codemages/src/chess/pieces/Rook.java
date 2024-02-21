package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {
	public Rook(Board chessboard, Color color) {
		super(chessboard, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		Board board = getBoard();
		boolean[][] matrix = new boolean[board.getRows()][board.getColumns()];

		checkUpSquares(matrix);
		checkDownSquares(matrix);
		checkLeftSquares(matrix);
		checkRightSquares(matrix);

		return matrix;
	}

	private void checkUpSquares(boolean[][] matrix) {
		Position p = new Position(0, 0);

		int row = this.position.getRow() - 1;
		p.setValues(row, this.position.getColumn());

		while (this.getBoard().isPositionValid(p) && !this.getBoard().isThereAPiece(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
			p.setRow(--row);
		}

		checkCapturablePiece(matrix, p);
	}

	private void checkDownSquares(boolean[][] matrix) {
		Position p = new Position(0, 0);

		int row = this.position.getRow() + 1;
		p.setValues(row, this.position.getColumn());

		while (this.getBoard().isPositionValid(p) && !this.getBoard().isThereAPiece(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
			p.setRow(++row);
		}

		checkCapturablePiece(matrix, p);
	}

	private void checkLeftSquares(boolean[][] matrix) {
		Position p = new Position(0, 0);

		int column = this.position.getColumn() - 1;
		p.setValues(this.position.getRow(), column);

		while (this.getBoard().isPositionValid(p) && !this.getBoard().isThereAPiece(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
			p.setColumn(--column);
		}

		checkCapturablePiece(matrix, p);
	}

	private void checkRightSquares(boolean[][] matrix) {
		Position p = new Position(0, 0);

		int column = this.position.getColumn() + 1;
		p.setValues(this.position.getRow(), column);

		while (this.getBoard().isPositionValid(p) && !this.getBoard().isThereAPiece(p)) {
			matrix[p.getRow()][p.getColumn()] = true;
			p.setColumn(++column);
		}

		checkCapturablePiece(matrix, p);
	}

	private void checkCapturablePiece(boolean[][] matrix, Position position) {
		if (getBoard().isPositionValid(position) && isThereOpponentPiece(position)) {
			matrix[position.getRow()][position.getColumn()] = true;
		}
	}
}
