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

	public static ChessPiece createPiece(PieceType type, Board board,
			Color color) {
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
				return new King(board, color);
			case PAWN:
				return new Pawn(board, color);
			default:
				throw new IllegalArgumentException(
						"Invalid piece type: " + type);
		}
	}

	public static List<ChessPiece> createFirstRowPieces(Board board,
			Color color) {
		return List.of(
				createPiece(PieceType.ROOK, board, color),
				createPiece(PieceType.KNIGHT, board, color),
				createPiece(PieceType.BISHOP, board, color),
				createPiece(PieceType.QUEEN, board, color),
				createPiece(PieceType.KING, board, color),
				createPiece(PieceType.BISHOP, board, color),
				createPiece(PieceType.KNIGHT, board, color),
				createPiece(PieceType.ROOK, board, color));
	}

	public enum PieceType {
		ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN
	}
}
