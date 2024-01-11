package clients.collection;

import middle.MiddleFactory;
import middle.OrderProcessing;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Customer view.
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */

public class CollectView implements Observer
{
 private static final String COLLECT = "Collect";
 Font font = new Font("Arial", Font.PLAIN, 16);
  private static final int H = 600;       // Height of window pixels
  private static final int W = 800;       // Width  of window pixels

  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBtCollect= new JButton( COLLECT );
 
  private OrderProcessing   theOrder = null;
  private CollectController cont     = null;

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen 
   * @param y     y-cordinate of position of window on screen  
   */
  public CollectView(  RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                           // 
    {      
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );


    theBtCollect.setBounds( 16, 25+60*0, 80, 40 );  // Check Button
    theBtCollect.addActionListener(                 // Call back code
      e -> cont.doCollect( theInput.getText()) );
    cp.add( theBtCollect );                         //  Add to canvas

    theAction.setBounds( 110, 0 , 270, 20 );       // Message area
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 110, 25+60*0, 270, 40 );         // Input Area
    theInput.setText("");                           // Blank
    theInput.setFont( font ); 
    cp.add( theInput );                             //  Add to canvas

    theSP.setBounds( 110, 25+60*1, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( font );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
    theInput.requestFocus();                        // Focus is here
    
    theBtCollect.setBackground(Color.BLACK);
    theBtCollect.setForeground(Color.YELLOW);
    theAction.setBackground(Color.BLACK);
    theAction.setForeground(Color.BLACK);
    theInput.setBackground(Color.BLACK);
    theInput.setForeground(Color.YELLOW);
    theOutput.setBackground(Color.BLACK);
    theOutput.setForeground(Color.YELLOW);
  }  
  
  public void setController( CollectController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
  @Override 
  public void update( Observable modelC, Object arg )
  {
    CollectModel model  = (CollectModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    
    theOutput.setText( model.getResponce() );
    theInput.requestFocus();               // Focus is here
  }

}
