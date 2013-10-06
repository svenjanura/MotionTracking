import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;


public class MotionTrackingMain {
	
	//Constants
	static final int FRAME_WIDTH=700;
	static final int FRAME_HEIGHT=700;
	static final int IMAGE_WIDTH_DRAW=500;
	
	//Global Variables
	static JFrame frame;
	static Graphics graphicsOverall;
	static Graphics graphicsImage;
	static Webcam webcam;
	static int imageHeightDraw;
	static int imageHeight;
	static int imageWidth;
	
	public static void main(String[] args) {
		init();
		BufferedImage lastImage=webcam.getImage();
		while(true)
			update(lastImage);
	}
	
	public static void update(BufferedImage lastImage) {
		BufferedImage currentImage=webcam.getImage();
		boolean[][] shapeBorders=BorderRecognition.getBorders(currentImage);
		int[][] shapes=FloodFill.getShapes(shapeBorders);
		BufferedImage changedImg=BorderRecognition.draw(currentImage);
		graphicsImage.drawImage(changedImg,0,0,IMAGE_WIDTH_DRAW,imageHeightDraw,frame);
		lastImage=ImageProcesing.copy(currentImage);
	}
	
	public static void init() {
		frame=new JFrame("Motion Tracking");
		frame.setVisible(true);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		webcam=Webcam.getWebcams().get(1);
		webcam.open();
		BufferedImage initImage=webcam.getImage();
		imageWidth=initImage.getWidth();
		imageHeight=initImage.getHeight();
		imageHeightDraw=imageHeight*IMAGE_WIDTH_DRAW/imageWidth;
		
		graphicsOverall=frame.getGraphics();
		graphicsImage=graphicsOverall.create(100, 100, IMAGE_WIDTH_DRAW, imageHeightDraw);
	}

}
