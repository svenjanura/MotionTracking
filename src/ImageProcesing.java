import java.awt.image.BufferedImage;


public class ImageProcesing {
	
	public static final int COLOR_TRESHOLD=15;
	public static int RED=16711680;
	public static int BLUE=255;
	public static int GREEN=65280;
	public static int WHITE=16777215;
	
	public static int[][] ImageToArray(BufferedImage image)
	{
		int[][]ret=new int[image.getWidth()][image.getHeight()];
		for(int x=0;x<image.getWidth();x++)
		{
			for(int y=0;y<image.getHeight();y++)
			{
				ret[x][y]=image.getRGB(x, y);
			}
		}
		return ret;
	}
	
	public static BufferedImage ArrayToImage(int[][] array, int width, int height)
	{
		BufferedImage img=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		for(int x=0;x<width;x++)
		{
			for(int y=0;y<height;y++)
			{
				img.setRGB(x, y, array[x][y]);
			}
		}
		return img;
	}
	
	public static BufferedImage copy(BufferedImage img){
		return ArrayToImage(ImageToArray(img),img.getWidth(),img.getHeight());
	}
	
	public static boolean isColorChangeImportant(int rgb1, int rgb2){
		int[] color1=getColorsFromRGB(rgb1);
		int[] color2=getColorsFromRGB(rgb2);
		if(Math.abs(color1[0]-color2[0])>COLOR_TRESHOLD||Math.abs(color1[1]-color2[1])>COLOR_TRESHOLD||Math.abs(color1[2]-color2[2])>COLOR_TRESHOLD)
			return true;
		return false;
	}
	
	public static int[] getColorsFromRGB(int rgb)
	{
		int[] ret=new int[3];
		ret[2]=rgb%256;
		rgb/=256;
		ret[1]=rgb%256;
		ret[0]=rgb/256;
		return ret;
	}
	
}
