package model.agents;

import java.awt.Point;

public class PointPositif implements Cloneable {
    
	private Point p = new Point();
    
    public PointPositif(Point p) {
        this.p.x = (int) Math.abs(p.x);
        this.p.y = (int) Math.abs(p.y);
    }
	
	public int getX() {
		return p.x;
	}
	
	public int getY() {
		return p.y;
	}
	
	public boolean setX(int x) {
		this.p.x = Math.abs(x);
		return true;
	}
	
	public boolean setY(int y) {
		this.p.y = Math.abs(y);
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d)", this.p.x, this.p.y);
	}
	
	public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        return o;
    }
}

