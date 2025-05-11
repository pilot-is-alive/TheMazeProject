package main;

import java.util.Objects;

public class PixelCoordinate {
	final int x;
	final int y;
	
	public PixelCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PixelCoordinate other = (PixelCoordinate) obj;
		return x == other.x && y == other.y;
	}
}
