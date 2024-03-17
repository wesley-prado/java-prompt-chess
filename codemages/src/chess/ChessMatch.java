package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.ChessPieceFactory.PieceType;
import chess.pieces.King;

public class ChessMatch {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private List<ChessPiece> capturedPieces = new ArrayList<>();
	private boolean check;
	private boolean checkMate;

	public ChessMatch() {
		turn = 1;
		currentPlayer = Color.WHITE;
		board = new Board(8, 8);
		initialSetup();
	}

	public int getTurn() {
		return this.turn;
	}

	public boolean getCheck() {
		return this.check;
	}

	public boolean getCheckMate() {
		return this.checkMate;
	}

	public Color getCurrentPlayer() {
		return this.currentPlayer;
	}

	public List<ChessPiece> getCapturedPieces() {
		return capturedPieces;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] matrix = new ChessPiece[board.getRows()][board
				.getColumns()];

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++)
				matrix[i][j] = (ChessPiece) board.getPiece(i, j);
		}

		return matrix;
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);

		return board.getPiece(position).possibleMoves();
	}

	public void performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();

		validateSourcePosition(source);
		validateTargetPosition(source, target);

		Piece capturedPiece = makeMove(source, target);

		if (isInCheck(this.currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}

		this.check = isInCheck(opponent(this.currentPlayer));

		if (this.isCheckMate(opponent(this.currentPlayer))) {
			this.checkMate = true;
		} else {
			nextTurn();
		}
	}

	private void validateSourcePosition(Position source) {
		if (!board.isThereAPiece(source)) {
			throw new ChessException("There is no piece on source position");
		}

		ChessPiece piece = (ChessPiece) board.getPiece(source);

		if (!piece.isThereAnyPossibleMove()) {
			throw new ChessException("There is no available move for the chosen piece");
		}

		if (piece.getColor() != this.currentPlayer) {
			throw new ChessException("The chosen piece is not yours");
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.getPiece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}

	private void nextTurn() {
		this.turn++;
		this.currentPlayer = opponent(this.currentPlayer);
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece piece = (ChessPiece) board.removePiece(source);
		piece.incrementMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(piece, target);

		if (capturedPiece != null) {
			capturedPieces.add((ChessPiece) capturedPiece);
		}

		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece movedPiece = (ChessPiece) board.removePiece(target);
		movedPiece.decrementMoveCount();
		board.placePiece(movedPiece, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			this.capturedPieces.remove(capturedPiece);
		}
	}

	private Color opponent(Color color) {
		return color == Color.WHITE ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece king(Color color) {
		ChessPiece king = (ChessPiece) this.board.getPiecesOnBoard().values().stream()
				.filter(piece -> ((ChessPiece) piece).getColor() == color && piece instanceof King)
				.findFirst()
				.orElse(null);

		if (king == null) {
			throw new IllegalStateException("There is no " + color + " king on the board");
		}

		return king;
	}

	private boolean isInCheck(Color color) {
		Color opponentColor = opponent(color);
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = this.board.getPiecesOnBoard().values().stream()
				.filter(piece -> ((ChessPiece) piece).getColor() == opponentColor)
				.collect(Collectors.toList());

		for (Piece p : opponentPieces) {
			boolean[][] possibleMoves = p.possibleMoves();
			if (possibleMoves[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}

		return false;
	}

	private boolean isCheckMate(Color color) {
		if (!this.isInCheck(color)) {
			return false;
		}

		List<Piece> pieces = this.board.getPiecesOnBoard().values().stream()
				.filter(piece -> ((ChessPiece) piece).getColor() == color)
				.collect(Collectors.toList());

		for (Piece p : pieces) {
			boolean[][] possibleMoves = p.possibleMoves();

			if (hasEscapeMoves(p, possibleMoves, color)) {
				return false;
			}
		}

		return true;
	}

	private boolean hasEscapeMoves(Piece piece, boolean[][] possibleMoves, Color color) {
		for (int row = 0; row < this.board.getRows(); row++) {
			for (int column = 0; column < this.board.getColumns(); column++) {
				if (possibleMoves[row][column]) {
					Position source = ((ChessPiece) piece).getChessPosition().toPosition();
					Position target = new Position(row, column);
					Piece capturedPiece = makeMove(source, target);
					boolean isInCheck = this.isInCheck(color);
					this.undoMove(source, target, capturedPiece);

					if (!isInCheck) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private void initialSetup() {
		placeBlackPieces();
		placeWhitePieces();
	}

	private void placeBlackPieces() {
		placePieces(Color.BLACK,
				ChessPieceFactory.createFirstRowPieces(board, Color.BLACK));
		placePawns(Color.BLACK);
	}

	private void placeWhitePieces() {
		placePieces(Color.WHITE,
				ChessPieceFactory.createFirstRowPieces(board, Color.WHITE));
		placePawns(Color.WHITE);
	}

	private void placePieces(Color color, List<ChessPiece> pieces) {
		int row = color == Color.BLACK ? 0 : 7;

		for (int column = 0; column < pieces.size(); column++) {
			board.placePiece(pieces.get(column), new Position(row, column));
		}
	}

	private void placePawns(Color color) {
		int row = color == Color.BLACK ? 1 : 6;

		for (int column = 0; column < board.getColumns(); column++) {
			board.placePiece(
					ChessPieceFactory.createPiece(PieceType.PAWN, board, color),
					new Position(row, column));
		}
	}
}
