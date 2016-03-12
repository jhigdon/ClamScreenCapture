package clam.io;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

public class ScreenCapturedFrame implements Comparable<Object> {
	public int x = 0;
	public int y = 0;
	public byte[] data;
	public int index = 0;
	public long timestamp = System.currentTimeMillis();
	int xOffset = -0;
	int yOffset = 0;
	public ScreenCapturedFrame () {
		PointerInfo pi = MouseInfo.getPointerInfo();
		Point p = pi.getLocation ();
		x = p.x - xOffset;
		y = p.y - yOffset;
	}
	public int compareTo(Object o) {
		if (o==null) return 0;
		ScreenCapturedFrame c = (ScreenCapturedFrame) o;		
		if (c.timestamp < timestamp) {
			return 1;
		} else if (c.timestamp > timestamp) {
			return -1;
		}
		return 0;
	}
}