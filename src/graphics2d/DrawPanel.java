package graphics2d;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DrawPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JFrame f;
	
	private int windowWidth = 500;
	private int windowHeight = 500;
	
	private int offsetX = 20;
	private int offsetY = 20;
	
	private Graphics g;
	
	private int scale = 3;
	@Override
	public void paintComponent( Graphics g ) {
		super.paintComponent( g );
		this.g = g;
		
		drawCircle( 100, 110, 110, 8 );
	}
	
	public void drawCircle( double rad, double x, double y, double width ) {
		HashSet< String > tested = new HashSet< String >();
		ArrayList< int[] > nodes = new ArrayList< int[] >();
		int[] fc = new int[] { ( int ) ( x + rad ), ( int ) y };
		tested.add( fc[ 0 ] + ":" + fc[ 1 ] );
		nodes.add( fc );
		while ( !nodes.isEmpty() ) {
			int[] c = nodes.remove( 0 );
			double dist = onCircle( rad, c, x, y );
			if ( dist < width ) {
				int cval = ( int ) ( ( dist * 255 ) / width );
				Color color = new Color( cval, cval, cval );
				g.setColor( color );
				drawPoint( c[ 0 ], c[ 1 ] );
				for ( int px = -1; px < 2; px++ ) {
					for ( int py = -1; py < 2; py++ ) {
						if ( px != 0 || py != 0 ) {
							int[] nc = new int[] { c[ 0 ] + px, c[ 1 ] + py };
							if ( !tested.contains( nc[ 0 ] + ":" + nc[ 1 ] ) ) {
								tested.add( nc[ 0 ] + ":" + nc[ 1 ] );
								nodes.add( nc );
							}
						}
					}
				}
			}
		}
	}
	
	public double onCircle( double rad, int[] c, double x, double y ) {
		double dist = distance( c[ 0 ], c[ 1 ], x, y );
		return  Math.abs( rad - dist );
	}
	
	public double distance( double x1, double y1, double x2, double y2 ) {
		double xd = ( x1 - x2 );
		double yd = ( y1 - y2 );
		return Math.sqrt( ( xd * xd ) + ( yd * yd ) );
	}
	
	public void drawLine( double x1, double y1, double x2, double y2 ) {
		double s = Math.max( Math.abs( x1 - x2 ), Math.abs( y1 - y2 ) );
		for ( double i = 0; i <= s; i++ ) {
			drawPoint( x2 + ( x1 - x2 ) * ( i / s ), y2 + ( y1 - y2 ) * ( i / s ) );
		}
	}
	
	public static void main( String[] a ) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				new DrawPanel().createGUI();
			}
		} );
	}
	
	public void drawPoint( double x, double y ) {
		g.fillRect( offsetX + ( int ) x * scale, offsetY + ( int ) y * scale, scale, scale );
	}
	
	public void createGUI() {
		f = new JFrame( "Drawing Board" );
		f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		f.add( this );
		
		f.setSize( windowWidth, windowHeight );
		f.setVisible( true );
		f.setResizable( true );
		
		
	}

}
