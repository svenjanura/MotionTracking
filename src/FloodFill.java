import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class FloodFill {
	
	static int[][] shapes;
	static int width;
	static int height;
	static ArrayList<Integer> colors;
	static int shapesTotal;
	
	public static int[][] getShapes(boolean[][] borders)
	{
		initShapes(borders);
		int faceNr=1;
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				if(shapes[x][y]==0){
					floodFill(x,y,faceNr);
					faceNr++;
				}
			}
		}
		shapesTotal=faceNr-1;
		return shapes;
	}
	
	public static void initShapes(boolean[][] borders){
		height=borders[0].length;
		width=borders.length;
		shapes=new int[width][height];
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				if(borders[x][y]){
					shapes[x][y]=-1;
				}
				else{
					shapes[x][y]=0;
				}
			}
		}
	}
	
	public static void floodFill(int x, int y, int faceNr){
		if(shapes[x][y]!=0)
			return;
		ArrayList<Point> query=new ArrayList<Point>();
		query.add(new Point(x,y));
		while(query.size()>0)
		{
			Point currentPoint=query.get(0);
			int w=currentPoint.x;
			int e=currentPoint.x;
			int n=currentPoint.y;
			
			while(w>=0&&shapes[w][n]==0){
				w--;
			}
			w++;
			while(e<width&&shapes[e][n]==0){
				e++;
			}
			e--;
			for(int currentX=w;currentX<=e;currentX++){
				shapes[currentX][n]=faceNr;
				if(n>0&&shapes[currentX][n-1]==0)
					query.add(new Point(currentX,n-1));
				if(n<height-1&&shapes[currentX][n+1]==0){
					query.add(new Point(currentX,n+1));
				}
			}
			query.remove(currentPoint);
		}
	}
	
	public static BufferedImage draw(BufferedImage img){
		BufferedImage result=ImageProcesing.copy(img);
		if(colors==null)
			colors=new ArrayList<Integer>();
		while(colors.size()<shapesTotal)
			colors.add((int)(Math.random()*ImageProcesing.WHITE));
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				if(shapes[x][y]==-1){
					result.setRGB(x, y, ImageProcesing.WHITE);
				}
				if(shapes[x][y]>0){
					result.setRGB(x,y,colors.get(shapes[x][y]-1));
				}
			}
		}
		return result;
	}
	
}
