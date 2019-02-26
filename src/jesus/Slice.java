package jesus;

public class Slice {
	private Point topLeft;
	private Point bottomRight;
	
	public Slice(Point topLeft, Point bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		
	}
	
	public Point getTopLeft() {
		return topLeft;
	}
	public void setTopLeft(Point topLeft) {
		this.topLeft = topLeft;
	}
	public Point getBottomRight() {
		return bottomRight;
	}
	public void setBottomRight(Point bottomRight) {
		this.bottomRight = bottomRight;
	}
	public Slice clone() {
		return new Slice(topLeft.clone(),bottomRight.clone());
	}
	public String toString() {
		return topLeft.toString() + " " + bottomRight.toString();
	}
}
