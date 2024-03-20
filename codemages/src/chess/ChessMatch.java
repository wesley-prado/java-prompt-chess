package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.constants.*;
import chess.pieces.King;
import chess.pieces.Pawn;

public class ChessMatch {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private List<ChessPiece> capturedPieces = new ArrayList<>();
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;

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
		return this.capturedPieces;
	}

	public Board getBoard() {
		return this.board;
	}

	public ChessPiece getEnPassantVulnerable() {
		return this.enPassantVulnerable;
	}

	public ChessPiece getPromoted() {
		return this.promoted;
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

		ChessPiece movedPiece = (ChessPiece) board.getPiece(target);
		this.promoted = null;
		if (movedPiece instanceof Pawn) {
			int expectedFinalRow = movedPiece.getColor() == Color.WHITE ? 0 : 7;

			if (target.getRow() == expectedFinalRow) {
				this.promoted = movedPiece;
				this.promoted = replacePromotedPiece("Q");
			}
		}

		this.check = isInCheck(opponent(this.currentPlayer));

		if (this.isCheckMate(opponent(this.currentPlayer))) {
			this.checkMate = true;
		}
		else {
			nextTurn();
		}

		if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() + 2
				|| target.getRow() == source.getRow() - 2)) {
			this.enPassantVulnerable = movedPiece;
		}
		else {
			this.enPassantVulnerable = null;
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
		ChessPiece movedPiece = (ChessPiece) board.removePiece(source);
		movedPiece.incrementMoveCount();
		ChessPiece capturedPiece = (ChessPiece) board.removePiece(target);
		board.placePiece(movedPiece, target);

		// Castling kingside rook
		if (movedPiece instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceRookPos = new Position(source.getRow(), source.getColumn() + 3);
			Position targetRookPos = new Position(source.getRow(), source.getColumn() + 1);
			Piece rook = board.removePiece(sourceRookPos);
			board.placePiece(rook, targetRookPos);

			((ChessPiece) rook).incrementMoveCount();
		}
		// Castling queenside rook
		else if (movedPiece instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceRookPos = new Position(source.getRow(), source.getColumn() - 4);
			Position targetRookPos = new Position(source.getRow(), source.getColumn() - 1);
			Piece rook = board.removePiece(sourceRookPos);
			board.placePiece(rook, targetRookPos);

			((ChessPiece) rook).incrementMoveCount();
		}
		// En Passant
		else if (movedPiece instanceof Pawn && target.getColumn() != source.getColumn()
				&& capturedPiece == null) {
			int squareCount = movedPiece.getColor() == Color.WHITE ? 1 : -1;
			Position pawnPosition = new Position(target.getRow() + squareCount, target.getColumn());
			capturedPiece = (ChessPiece) board.removePiece(pawnPosition);
		}

		if (capturedPiece != null) {
			capturedPieces.add(capturedPiece);
		}

		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece movedPiece = (ChessPiece) board.removePiece(target);
		movedPiece.decrementMoveCount();
		board.placePiece(movedPiece, source);

		// Undo kingside rook castle
		if (movedPiece instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceRookPos = new Position(source.getRow(), source.getColumn() + 1);
			Position targetRookPos = new Position(source.getRow(), source.getColumn() + 3);
			Piece rook = board.removePiece(sourceRookPos);
			board.placePiece(rook, targetRookPos);

			((ChessPiece) rook).decrementMoveCount();
		}
		// Undo queenside rook castle
		else if (movedPiece instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceRookPos = new Position(source.getRow(), source.getColumn() - 1);
			Position targetRookPos = new Position(source.getRow(), source.getColumn() - 4);
			Piece rook = board.removePiece(sourceRookPos);
			board.placePiece(rook, targetRookPos);

			((ChessPiece) rook).decrementMoveCount();
		}
		// En Passant
		else if (movedPiece instanceof Pawn && target.getColumn() != source.getColumn()
				&& capturedPiece == this.enPassantVulnerable) {
			int row = movedPiece.getColor() == Color.WHITE ? 3 : 4;
			Position pawnPosition = new Position(row, target.getColumn());
			board.placePiece(capturedPiece, pawnPosition);
			this.capturedPieces.remove(capturedPiece);
		}
		else if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			this.capturedPieces.remove(capturedPiece);
		}
	}

	public ChessPiece replacePromotedPiece(String type) {
		if (this.promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted");
		}

		Position pos = this.promoted.getChessPosition().toPosition();
		this.board.removePiece(pos);

		PromotionPiece pPiece;
		switch (type) {
			case "Q":
				pPiece = PromotionPiece.QUEEN;
				break;
			case "B":
				pPiece = PromotionPiece.BISHOP;
				break;
			case "N":
				pPiece = PromotionPiece.KNIGHT;
				break;
			case "R":
				pPiece = PromotionPiece.ROOK;
				break;
			default:
				throw new IllegalArgumentException("Invalid promotion piece type");
		}

		ChessPiece promotedPiece = ChessPieceFactory.createPiece(pPiece, this,
				this.promoted.getColor());
		this.board.placePiece(promotedPiece, pos);

		return promotedPiece;
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
		placePieces(Color.WHITE);
		placePieces(Color.BLACK);
	}

	private void placePieces(Color color) {
		boolean isWhite = color == Color.WHITE;
		int row = isWhite ? 1 : 8;
		int pawnRow = isWhite ? row + 1 : row - 1;

		placePiece(ChessPieceFactory.createPiece(PieceType.ROOK, this, color),
				new ChessPosition('a', row));
		placePiece(ChessPieceFactory.createPiece(PieceType.KNIGHT, this, color),
				new ChessPosition('b', row));
		placePiece(ChessPieceFactory.createPiece(PieceType.BISHOP, this, color),
				new ChessPosition('c', row));
		placePiece(ChessPieceFactory.createPiece(PieceType.QUEEN, this, color),
				new ChessPosition('d', row));
		placePiece(ChessPieceFactory.createPiece(PieceType.KING, this, color),
				new ChessPosition('e', row));
		placePiece(ChessPieceFactory.createPiece(PieceType.BISHOP, this, color),
				new ChessPosition('f', row));
		placePiece(ChessPieceFactory.createPiece(PieceType.KNIGHT, this, color),
				new ChessPosition('g', row));
		placePiece(ChessPieceFactory.createPiece(PieceType.ROOK, this, color),
				new ChessPosition('h', row));

		placePiece(ChessPieceFactory.createPiece(PieceType.PAWN, this, color),
				new ChessPosition('a', pawnRow));
		placePiece(ChessPieceFactory.createPiece(PieceType.PAWN, this, color),
				new ChessPosition('b', pawnRow));
		placePiece(ChessPieceFactory.createPiece(PieceType.PAWN, this, color),
				new ChessPosition('c', pawnRow));
		placePiece(ChessPieceFactory.createPiece(PieceType.PAWN, this, color),
				new ChessPosition('d', pawnRow));
		placePiece(ChessPieceFactory.createPiece(PieceType.PAWN, this, color),
				new ChessPosition('e', pawnRow));
		placePiece(ChessPieceFactory.createPiece(PieceType.PAWN, this, color),
				new ChessPosition('f', pawnRow));
		placePiece(ChessPieceFactory.createPiece(PieceType.PAWN, this, color),
				new ChessPosition('g', pawnRow));
		placePiece(ChessPieceFactory.createPiece(PieceType.PAWN, this, color),
				new ChessPosition('h', pawnRow));
	}

	private void placePiece(Piece piece, ChessPosition chessPosition) {
		board.placePiece(piece, chessPosition.toPosition());
	}
}
