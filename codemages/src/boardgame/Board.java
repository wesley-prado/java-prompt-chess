package boardgame;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;

	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1)
			throw new BoardException(
					"Error creating board: there must be at least 1 row and 1 column");

		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece getPiece(int row, int column) {
		return pieces[row][column];
	}

	public Piece getPiece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
	}

	public void placePiece(Piece piece, int row, int column) {
		placePiece(piece, new Position(row, column));
	}

	public void placePiece(Piece piece, Position position) {
		if (isThereAPiece(position))
			throw new BoardException(
					"There is already a piece on position " + position);

		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

	public Piece removePiece(Position position) {
		if (!isThereAPiece(position)) {
			return null;
		}

		Piece piece = getPiece(position);
		piece.position = null;
		pieces[position.getRow()][position.getColumn()] = null;

		return piece;
	}

	public boolean isThereAPiece(int row, int column) {
		return isThereAPiece(new Position(row, column));
	}

	public boolean isThereAPiece(Position position) {
		if (!isPositionValid(position))
			throw new BoardException("Position not on the board: " + position);

		return getPiece(position) != null;
	}

	public boolean isPositionValid(int row, int column) {
		return isPositionValid(new Position(row, column));
	}

	public boolean isPositionValid(Position position) {
		return position.getRow() >= 0 && position.getRow() < rows
				&& position.getColumn() >= 0 && position.getColumn() < columns;
	}
}
