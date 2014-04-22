package crazycar.gui.actors;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import crazycar.gui.CrazycarGUI;
import crazycar.logic.data.Direction;

public enum CarBackground {
	north("car-north.gif"),south("car-south.gif"),west("car-west.gif"),east("car-east.gif");
	
	private CarBackground(String url) {	
		this.image = convert(read(CrazycarGUI.imagefolder() + url));
  }

	private final BufferedImage image;	
	
	private static BufferedImage read(String url){
	  try {
	  	System.out.println(new File(url).isFile());
	    return ImageIO.read(new File(url));
    } catch (IOException e) {
	    e.printStackTrace();
    }
	  return null;
	}
	
	public static BufferedImage convert(BufferedImage rawImage){
	   BufferedImage rgbImage = new BufferedImage(rawImage.getWidth(null), rawImage.getHeight(null), 2);
	    Graphics g = rgbImage.createGraphics();
	    // if rawImage contains alpha, consider filling the rgbImage with a default BG color here
	    g.drawImage(rawImage, 0, 0, null);
	    g.dispose();
	    rawImage.flush();
	    return rgbImage;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public static CarBackground imageOf(Direction d){
		if (d.equals(Direction.north)) {
			return north;
		} else if (d.equals(Direction.south)) {
			return south;
		} else if (d.equals(Direction.west)) {
			return west;
		} else if (d.equals(Direction.east)) {
			return east;
		}
		throw new UnsupportedOperationException("newCar:wrong direction " + d);
	}
	
}
