package chess;

import java.util.List;

import boardgame.Board;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public final class ChessPieceFactory {
	private ChessPieceFactory() {
	}

	public static ChessPiece createPiece(PieceType type, ChessMatch chessMatch,
			Color color) {
		Board board = chessMatch.getBoard();

		switch (type) {
			case ROOK:
				return new Rook(board, color);
			case KNIGHT:
				return new Knight(board, color);
			case BISHOP:
				return new Bishop(board, color);
			case QUEEN:
				return new Queen(board, color);
			case KING:
				return new King(board, color, chessMatch);
			case PAWN:
				return new Pawn(board, color, chessMatch);
			default:
				throw new IllegalArgumentException(
						"Invalid piece type: " + type);
		}
	}

	public static List<ChessPiece> createFirstRowPieces(ChessMatch chessMatch,
			Color color) {
		return List.of(
				createPiece(PieceType.ROOK, chessMatch, color),
				createPiece(PieceType.KNIGHT, chessMatch, color),
				createPiece(PieceType.BISHOP, chessMatch, color),
				createPiece(PieceType.QUEEN, chessMatch, color),
				createPiece(PieceType.KING, chessMatch, color),
				createPiece(PieceType.BISHOP, chessMatch, color),
				createPiece(PieceType.KNIGHT, chessMatch, color),
				createPiece(PieceType.ROOK, chessMatch, color));
	}

	public enum PieceType {
		ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN
	}
}
