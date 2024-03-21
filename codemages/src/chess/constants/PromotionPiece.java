package chess.constants;

public enum PromotionPiece {
	R("ROOK"), N("KNIGHT"), B("BISHOP"), Q("QUEEN");

	String name;

	PromotionPiece(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
