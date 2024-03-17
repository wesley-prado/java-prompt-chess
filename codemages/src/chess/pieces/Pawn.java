package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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

	@Override
	public boolean[][] possibleMoves() {
		Board board = getBoard();
		boolean[][] matrix = new boolean[board.getRows()][board.getColumns()];

		Position p = new Position(0, 0);
		int startRow = position.getRow();
		int startColumn = position.getColumn();

		if (getColor() == Color.WHITE) {
			p.setValues(startRow - 1, startColumn);
			if (isForwardMovementValid(board, p)) {
				matrix[p.getRow()][p.getColumn()] = true;
			}

			Position p2 = new Position(startRow - 2, startColumn);
			boolean isPathClear = matrix[p.getRow()][p.getColumn()];
			boolean isFirstMovement = getMoveCount() == 0;
			if (isFirstMovement && isPathClear && isForwardMovementValid(board, p2)) {
				matrix[p2.getRow()][p2.getColumn()] = true;
			}

			p.setValues(startRow - 1, startColumn - 1);
			if (isDiagonalMovementValid(board, p)) {
				matrix[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(startRow - 1, startColumn + 1);
			if (isDiagonalMovementValid(board, p)) {
				matrix[p.getRow()][p.getColumn()] = true;
			}

			return matrix;
		}

		p.setValues(startRow + 1, startColumn);
		if (isForwardMovementValid(board, p)) {
			matrix[p.getRow()][p.getColumn()] = true;
		}

		Position p2 = new Position(startRow + 2, startColumn);
		boolean isPathClear = matrix[p.getRow()][p.getColumn()];
		boolean isFirstMovement = getMoveCount() == 0;
		if (isFirstMovement && isPathClear && isForwardMovementValid(board, p2)) {
			matrix[p2.getRow()][p2.getColumn()] = true;
		}

		p.setValues(startRow + 1, startColumn - 1);
		if (isDiagonalMovementValid(board, p)) {
			matrix[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(startRow + 1, startColumn + 1);
		if (isDiagonalMovementValid(board, p)) {
			matrix[p.getRow()][p.getColumn()] = true;
		}

		return matrix;
	}

	private boolean isForwardMovementValid(Board board, Position position) {
		return board.isPositionValid(position) && !board.isThereAPiece(position);
	}

	private boolean isDiagonalMovementValid(Board board, Position position) {
		return board.isPositionValid(position) && board.isThereAPiece(position)
				&& ((ChessPiece) board.getPiece(position)).getColor() != this.getColor();
	}
}
