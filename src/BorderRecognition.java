import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class BorderRecognition {
	
	static final int COUNTER_TRESHOLD_COLOR=0;
	static final int COUNTER_TRESHOLD_ELIMINATE=10;
	static final int ENLARGEMENT=0;
	static final int ELIMINATE_RADIUS=2;
	
	static boolean[][] borders;
	static int imageWidth;
	static int imageHeight;
	
	public static boolean[][] getBorders(BufferedImage img){
		borders=new boolean[img.getWidth()][img.getHeight()];
		imageWidth=img.getWidth();
		imageHeight=img.getHeight();
		getBorderPixels(img);
		eliminate();
		if(ENLARGEMENT>0)
			enlarge();
		return borders;
	}
	
	public static void getBorderPixels(BufferedImage img){
		for(int x=1;x<imageWidth-1;x++){
			for(int y=1;y<imageHeight-1;y++){
				int counter=0;
				for(int dx=-1;dx<2;dx++){
					for(int dy=-1;dy<2;dy++){
						if(ImageProcesing.isColorChangeImportant(img.getRGB(x, y), img.getRGB(x+dx, y+dy)))
							counter++;
					}
				}
				borders[x][y]=counter>COUNTER_TRESHOLD_COLOR;
			}
		}
	}
	
	public static void eliminate(){
		boolean[][] source=new boolean[imageWidth][imageHeight];
		for(int x=0;x<imageWidth;x++){
			for(int y=0;y<imageHeight;y++){
				source[x][y]=borders[x][y];
			}
		}
		
		for(int x=0;x<imageWidth;x++){
			for(int y=0;y<imageHeight;y++){
				if(x<ELIMINATE_RADIUS||y<ELIMINATE_RADIUS||x>imageWidth-1-ELIMINATE_RADIUS||y>imageHeight-1-ELIMINATE_RADIUS)
					borders[x][y]=true;
				else if(source[x][y]){
						int counter=0;
						for(int dx=-1*ELIMINATE_RADIUS;dx<=ELIMINATE_RADIUS;dx++){
							for(int dy=-1*ELIMINATE_RADIUS;dy<=ELIMINATE_RADIUS;dy++){
								if(source[x+dx][y+dy])
									counter++;
							}
						}
						borders[x][y]=counter>COUNTER_TRESHOLD_ELIMINATE;
					}
				}
			}
	}
	
	public static void enlarge(){
		boolean[][] source=new boolean[imageWidth][imageHeight];
		for(int x=0;x<imageWidth;x++){
			for(int y=0;y<imageHeight;y++){
				source[x][y]=borders[x][y];
			}
		}
		
		for(int x=0;x<imageWidth;x++){
			for(int y=0;y<imageHeight;y++){
				if(source[x][y]){
					for(int dx=-1*ENLARGEMENT;dx<=ENLARGEMENT;dx++){
						for(int dy=-1*ENLARGEMENT;dy<=ENLARGEMENT;dy++){
							if(x+dx>=0&&y+dy>=0&&x+dx<imageWidth&&y+dy<imageHeight){
								borders[x+dx][y+dy]=true;
							}
						}
					}
				}
			}
		}
	}
	
	public static void draw(Graphics graphics, int drawWidth, int drawHeight){
		graphics.setColor(Color.WHITE);
		for(int x=1;x<imageWidth-1;x++){
			for(int y=1;y<imageHeight-1;y++){
				if(borders[x][y]){
					graphics.drawRect(x*drawWidth/imageWidth, y*drawHeight/imageHeight, 1, 1);
				}
			}
		}
	}
	
	public static BufferedImage draw(BufferedImage img){
		BufferedImage ret=ImageProcesing.copy(img);
		for(int x=1;x<imageWidth-1;x++){
			for(int y=1;y<imageHeight-1;y++){
				if(borders[x][y]){
					ret.setRGB(x, y, ImageProcesing.WHITE);
				}
			}
		}
		return ret;
	}
	
}
