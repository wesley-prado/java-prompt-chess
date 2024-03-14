package chess.pieces;

import java.util.List;

import boardgame.Board;
import boardgame.Position;
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

	@Override
	public boolean[][] possibleMoves() {
		Board board = getBoard();

		int row = this.position.getRow();
		int column = this.position.getColumn();

		List<Position> positions = List.of(
				new Position(row - 1, column), // top
				new Position(row + 1, column), // bottom
				new Position(row, column - 1), // left
				new Position(row, column + 1), // right
				new Position(row - 1, column - 1), // top left
				new Position(row - 1, column + 1), // top right
				new Position(row + 1, column - 1), // bottom left
				new Position(row + 1, column + 1)); // bottom right

		return checkKingMoves(board, positions);
	}

	private boolean[][] checkKingMoves(Board board, List<Position> positions) {
		boolean[][] possibleMoves = new boolean[board.getRows()][board.getColumns()];

		for (Position position : positions) {
			if (checkValidMove(board, position, getColor())) {
				possibleMoves[position.getRow()][position.getColumn()] = true;
			}
		}

		return possibleMoves;
	}

	private boolean checkValidMove(Board board, Position position, Color color) {
		boolean isPositionValid = board.isPositionValid(position);

		if (!isPositionValid)
			return false;

		ChessPiece piece = (ChessPiece) board.getPiece(position);

		return piece == null || piece.getColor() != color;
	}
}
