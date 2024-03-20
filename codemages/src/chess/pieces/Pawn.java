package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	private ChessMatch chessMatch;

	public Pawn(Board chessboard, Color color, ChessMatch chessMatch) {
		super(chessboard, color);
		this.chessMatch = chessMatch;
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

			checkEnPassantMovement(board, matrix);

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

		checkEnPassantMovement(board, matrix);

		return matrix;
	}

	private boolean isForwardMovementValid(Board board, Position position) {
		return board.isPositionValid(position) && !board.isThereAPiece(position);
	}

	private boolean isDiagonalMovementValid(Board board, Position position) {
		return board.isPositionValid(position) && board.isThereAPiece(position)
				&& ((ChessPiece) board.getPiece(position)).getColor() != this.getColor();
	}

	private void checkEnPassantMovement(Board board, boolean[][] matrix) {
		Position left = new Position(this.position.getRow(), this.position.getColumn() - 1);
		Position right = new Position(this.position.getRow(), this.position.getColumn() + 1);
		int squareCount = this.getColor() == Color.WHITE ? -1 : 1;

		if (this.position.getRow() == 3 || this.position.getRow() == 4) {

			if (board.isPositionValid(left) && board.isThereAPiece(left)
					&& board.getPiece(left) == this.chessMatch.getEnPassantVulnerable()) {
				matrix[left.getRow() + squareCount][left.getColumn()] = true;
			}

			if (board.isPositionValid(right) && board.isThereAPiece(right)
					&& board.getPiece(right) == this.chessMatch.getEnPassantVulnerable()) {
				matrix[right.getRow() + squareCount][right.getColumn()] = true;
			}
		}
	}
}
