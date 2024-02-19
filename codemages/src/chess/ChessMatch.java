package chess;

import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.ChessPieceFactory.PieceType;

public class ChessMatch {
	private Board board;

	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
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

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();

		validateSourcePosition(source);

		Piece capturedPiece = makeMove(source, target);

		return (ChessPiece) capturedPiece;
	}

	private void validateSourcePosition(Position position) {
		if (!board.isThereAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}

		if (!board.getPiece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no available move for the chosen piece");
		}
	}

	private Piece makeMove(Position source, Position target) {
		Piece piece = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(piece, target);

		return capturedPiece;
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
