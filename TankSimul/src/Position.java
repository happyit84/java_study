
public class Position {
	public final static double MAX_X = 1000.0f;
	public final static double MAX_Y = 1000.0f;
	public final static double MAX_DISTANCE = Math.sqrt(MAX_X*MAX_X + MAX_Y*MAX_Y);
	private double x;
	private double y;
	
	public Position(){
		reset();
	}
	
	public void reset(){
		x = Math.random()*MAX_X;
		y = Math.random()*MAX_Y;
	}
	
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double distanceFrom(Position pos){
		double diffX = x-pos.getX();
		double diffY = y-pos.getY();
		return Math.sqrt(diffX*diffX + diffY*diffY);
	}
}
