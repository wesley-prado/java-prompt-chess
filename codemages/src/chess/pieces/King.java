package chess.pieces;

import java.util.List;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	private ChessMatch chessMatch;

	public King(Board chessboard, Color color, ChessMatch chessMatch) {
		super(chessboard, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		Board board = getBoard();
		boolean[][] possibleMoves = new boolean[board.getRows()][board.getColumns()];

		int row = this.position.getRow();
		int column = this.position.getColumn();

		checkKingStandardMoves(board, possibleMoves, List.of(
				new Position(row - 1, column), // top
				new Position(row + 1, column), // bottom
				new Position(row, column - 1), // left
				new Position(row, column + 1), // right
				new Position(row - 1, column - 1), // top left
				new Position(row - 1, column + 1), // top right
				new Position(row + 1, column - 1), // bottom left
				new Position(row + 1, column + 1) // bottom right
		));

		if (this.getMoveCount() == 0) {
			checkKingCastleMove(board, possibleMoves);
		}

		return possibleMoves;
	}

	private void checkKingStandardMoves(Board board, boolean[][] possibleMoves,
			List<Position> positions) {
		for (Position position : positions) {
			if (checkValidMove(board, position, getColor())) {
				possibleMoves[position.getRow()][position.getColumn()] = true;
			}
		}
	}

	private void checkKingCastleMove(Board board, boolean[][] possibleMoves) {
		int row = this.position.getRow();
		int column = this.position.getColumn();
		int kingsideRookColumn = column + 3;
		int queensideRookColumn = column - 4;

		Position p = new Position(row, kingsideRookColumn);
		boolean isKingsideRookPosValid = checkRookCastling(p);
		boolean isKingsidePathClear = true;

		Position pathPos = new Position(row, column + 1);
		while (pathPos.getColumn() < kingsideRookColumn) {
			isKingsidePathClear = isKingsidePathClear && !board.isThereAPiece(pathPos);
			pathPos.setColumn(pathPos.getColumn() + 1);
		}

		if (isKingsideRookPosValid && isKingsidePathClear) {
			possibleMoves[row][column + 2] = true;
		}

		p.setColumn(queensideRookColumn);
		boolean isQueensideRookPosValid = checkRookCastling(p);
		boolean isQueensidePathClear = true;

		pathPos.setColumn(column - 1);
		while (pathPos.getColumn() > queensideRookColumn) {
			isQueensidePathClear = isQueensidePathClear && !board.isThereAPiece(pathPos);
			pathPos.setColumn(pathPos.getColumn() - 1);
		}

		if (isQueensideRookPosValid && isQueensidePathClear) {
			possibleMoves[row][column - 2] = true;
		}
	}

	private boolean checkValidMove(Board board, Position position, Color color) {
		boolean isPositionValid = board.isPositionValid(position);

		if (!isPositionValid)
			return false;

		ChessPiece piece = (ChessPiece) board.getPiece(position);

		return piece == null || piece.getColor() != color;
	}

	private boolean checkRookCastling(Position position) {
		ChessPiece p = (ChessPiece) this.getBoard().getPiece(position);
		return p instanceof Rook && p.getColor() == this.getColor() && p.getMoveCount() == 0;
	}
}
