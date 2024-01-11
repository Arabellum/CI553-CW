package clients.customer;

import catalogue.Basket;
import catalogue.BetterBasket;
import clients.Picture;
import middle.MiddleFactory;
import middle.StockReader;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Customer view.
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */

public class CustomerView implements Observer
{
  class Name                              // Names of buttons
  {
    public static final String CHECK  = "Check";
    public static final String CLEAR  = "Clear";
  }
  
  Font font = new Font("Arial", Font.PLAIN, 16);
  private static final int H = 600;       // Height of window pixels
  private static final int W = 800;       // Width  of window pixels

  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBtCheck = new JButton( Name.CHECK );
  private final JButton     theBtClear = new JButton( Name.CLEAR );

  private Picture thePicture = new Picture(80,80);
  private StockReader theStock   = null;
  private CustomerController cont= null;

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen 
   * @param y     y-cordinate of position of window on screen  
   */
  
  public CustomerView( RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                             // 
    {      
      theStock  = mf.makeStockReader();             // Database Access
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );

    Font font = new Font("Arial", Font.PLAIN, 16);

    theBtCheck.setBounds( 16, 25+60*0, 80, 40 );    // Check button
    theBtCheck.addActionListener(                   // Call back code
      e -> cont.doCheck( theInput.getText() ) );
    cp.add( theBtCheck );                           //  Add to canvas

    theBtClear.setBounds( 16, 25+60*1, 80, 40 );    // Clear button
    theBtClear.addActionListener(                   // Call back code
      e -> cont.doClear() );
    cp.add( theBtClear );                           //  Add to canvas

    theAction.setBounds( 110, 50, 270, 20 );       // Message area
    theAction.setText( "" );                        //  Blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 110, 25+60*0, 270, 40 );         // Product no area
    theInput.setText(""); 							// Blank
    theInput.setFont( font );
    cp.add( theInput );                             //  Add to canvas
    
    theSP.setBounds( 110, 25+60*1, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( font );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea

    thePicture.setBounds( 16, 25+60*2, 80, 80 );   // Picture area
    cp.add( thePicture );                           //  Add to canvas
    thePicture.clear();
    
    rootWindow.setVisible( true );                  // Make visible);
    theInput.requestFocus();    					// Focus is here
    
    theBtClear.setBackground(Color.BLACK);
    theBtClear.setForeground(Color.YELLOW);
    theBtCheck.setBackground(Color.BLACK);
    theBtCheck.setForeground(Color.YELLOW);
    theInput.setBackground(Color.BLACK);
    theInput.setForeground(Color.YELLOW);
    theOutput.setBackground(Color.BLACK);
    theOutput.setForeground(Color.YELLOW);
  }

   /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */

  public void setController( CustomerController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
   
  public void update( Observable modelC, Object arg )
  {
    CustomerModel model  = (CustomerModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    
    //Changing "image" to "productImage" to avoid conflicts
    ImageIcon productImage = model.getPicture();  // Image of product
    if ( productImage == null )
    {
      thePicture.clear();                  // Clear picture
    } else {
      thePicture.set( productImage );             // Display picture
    }
    theOutput.setText( model.getBasket().getDetails() );
    theInput.requestFocus();               // Focus is here
    
 // Again, using "invokeLater" to thread-safe the swing operations.
    SwingUtilities.invokeLater(() -> {
        theAction.setText(message);
        ImageIcon image = model.getPicture();
        if (image == null) {
            thePicture.clear();
        } else {
            thePicture.set(image);
        }
        theOutput.setText(model.getBasket().getDetails());
        theInput.requestFocus();
       
    });

  }

}