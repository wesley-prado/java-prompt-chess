package boardgame;

public abstract class Piece {
	private Board board;
	protected Position position = null;

	protected Piece(Board board) {
		this.board = board;
	}

	protected Board getBoard() {
		return board;
	}

	public abstract boolean[][] possibleMoves();

	public boolean possibleMove(Position position) {
		return this.possibleMoves()[position.getRow()][position.getColumn()];
	}

	public boolean isThereAnyPossibleMove() {
		boolean[][] matrix = possibleMoves();

		for (boolean[] columns : matrix) {
			for (boolean isMovable : columns) {
				if (isMovable)
					return true;
			}
		}

		return false;
	}
}
