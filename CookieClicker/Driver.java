package game;

import java.awt.*; //Used for awt components
import java.awt.event.*; //Used for awt events

//Following imports are used for serialization
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*; //Used for swing components
import java.util.Timer; //Used for updating every second
import java.util.TimerTask; //Also used for updating every second

public class Driver 
{
	//Sends information about the Cookie object to a serialized file
	private static final String cookieFile = "cookie.ser";
	
	private Cookie cookie; //Cookie that is used for gameplay
	private JFrame frame; //Creates the frame for the game
	private JPanel headerPanel; //Header panel for the game
	private JPanel resetButtonPanel; //Panel for reset button
	private JPanel gameplayPanel; //Panel for the main gameplay
	private JPanel buildingIconPanel; //Panel for building icons
	private JPanel buildingPanel; //Panel for the buildings
	private JLabel headerLabel; //Basically the title
	private JLabel cookieLabel; //Used to display the cookie
	private JLabel numCookiesLabel; //Represents number of cookies in bank + CPS
	private JLabel buildingLabel; //Used to output any text related to buildings
	private JButton resetButton; //Used to reset the game
	private Timer updateNumCookies; //Updates the number of cookies consistently
	private ImageIcon cookieIcon; //ImageIcon that represents the Cookie
	
	public Driver()
	{
		frame = new JFrame(); //Construct the frame
		
		//Construct the Cookie object (for gameplay) from save data
		cookie = loadData();
	
		if(cookie == null)
		{
			cookie = new Cookie();
		}
		
		//Construct the header panel
		headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		headerPanel.setLayout(new GridLayout());
		headerPanel.setBackground(new Color(210, 105, 30)); //Chocolate color
		headerPanel.setPreferredSize(new Dimension(50, 250));
		
		//Construct the header label
		headerLabel = new JLabel("Cookie Clicker!", SwingConstants.RIGHT);
		headerLabel.setFont(new Font("Arial Black", Font.BOLD, 50));
		headerLabel.setForeground(new Color(255, 218, 185)); //Peach color
		
		//Construct button to reset the game
		resetButton = new JButton("Reset");
		resetButton.setPreferredSize(resetButton.getPreferredSize());
		resetButton.setBackground(Color.gray);
		resetButton.setForeground(Color.black);
		resetButton.addActionListener(e ->
		{
			cookie = new Cookie();
		});
		
		//Make a panel for the button
		resetButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		resetButtonPanel.setBackground(new Color(210, 105, 30));
		resetButtonPanel.setPreferredSize(new Dimension(10, 20));
		resetButtonPanel.add(resetButton);
		
		//Components to be added to the headerPanel
		headerPanel.add(headerLabel);
		headerPanel.add(resetButtonPanel);
		
		//Construct the main gameplay panel
		gameplayPanel = new JPanel(new FlowLayout());
	
		//Construct cookie icon
		cookieIcon = new ImageIcon("cookie.png"); 
		//Associate the cookie icon with a label 
		cookieLabel = new JLabel();
		cookieLabel.setIcon(cookieIcon);
		cookieLabel.setPreferredSize(
		new Dimension(cookieIcon.getIconWidth(), cookieIcon.getIconHeight()));
		cookieLabel.setVisible(true);
		cookieLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		/*
		 * Associate the cookie icon with a Mouse Listener
		 * that allows the user to add to the # of cookies through clicks
		 */
		cookieLabel.addMouseListener(new MouseAdapter() 
		{
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                cookie.incrementCookies(true);
            }
        });
		
		//Set the image of the frame to be the cookie
		frame.setIconImage(cookieIcon.getImage());
		
		//Label that stores the number of cookies
		numCookiesLabel = new JLabel();
		numCookiesLabel.setFont(new Font("Comfortaa", Font.BOLD, 25));
		numCookiesLabel.setForeground(new Color(210, 105, 30)); //Chocolate
		
		/*
		 * Allow functionality where cookies are consistently added
		 * based on the user's CPS (and the corresponding text is updated)
		 */
		updateNumCookies = new Timer();
		
		//TimerTask to perform functionality of Timer
		TimerTask updateNumCookiesTask = new TimerTask() 
		{
            @Override
            public void run() 
            {
            	cookie.incrementCookies(false); //Update the # of cookies
            	numCookiesLabel.setText //Update the text
            	("<html>" + (int) cookie.getNumCookies() + " Cookies" + 
            	"<br>" + "CPS: " + cookie.getCookiesPerSecond());
            }
        };
        
		updateNumCookies.scheduleAtFixedRate(updateNumCookiesTask, 0, 10);
		
		//Create a panel for the Building icons
		buildingIconPanel = new JPanel(new GridLayout(9, 1, 0, 0));
		
		//Add ImageIcons associated with the Building Buttons
		for(int i = 0; i < 7; i++)
		{
			JLabel icon = new JLabel();
			icon.setIcon(cookie.buildings[i].getIcon());
			icon.setPreferredSize(new Dimension(50, 50));
			buildingIconPanel.add(icon);
		}
		//Add a filler JLabel
		buildingIconPanel.add(new JLabel());
		
		/*
		 * Create a Panel for the 8 Buildings
		 */
		buildingPanel = new JPanel(new GridLayout(9, 1, 0, 0));
		
		//Initialize the building label text
		buildingLabel = new JLabel();
		buildingLabel.setFont(new Font("Comfortaa", Font.BOLD, 25));
		buildingLabel.setForeground(new Color(210, 105, 30)); //Chocolate
		
		//Add all the Building buttons to the buildingPanel
        for(int i = 0; i <= 7; i++) 
        {
        	//Initialize the building associated with the button
        	Building building = cookie.buildings[i];
        	//Create the buttons and set their icons
        	JButton buildingButton = new JButton(building + "");
        	if(cookie.buildings[i].getIcon() != null)
        	{
        		buildingButton.setIcon(new ImageIcon("cursor.png"));
        	}
        	buildingButton.setHorizontalTextPosition(JButton.RIGHT);
        	buildingButton.setVerticalTextPosition(JButton.BOTTOM);
        	/*
        	 * Allow the user to buy Buildings using the button
        	 */
        	buildingButton.addActionListener(action ->
        	{
        		try //Buy building if the user has enough money
        		{
        			cookie.buyBuilding(building);
        			buildingLabel.setText("You have successfully bought a " 
        			+ building.getType());
        			buildingButton.setText(building + "");
        		}
        		catch(IllegalArgumentException e)
        		{
        			buildingLabel.setText(e.getMessage());
        		}
        	});
        	//Add the buttons to the building panel
            buildingPanel.add(buildingButton);
        }
        
        //Add the building label to the building panel
        buildingPanel.add(buildingLabel);
		
		//Components to be added to the gameplay panel
		gameplayPanel.add(numCookiesLabel);
		gameplayPanel.add(cookieLabel, BorderLayout.SOUTH);
		gameplayPanel.add(buildingIconPanel);
		gameplayPanel.add(buildingPanel);
		
		//Components to be added to the frame
		frame.add(headerPanel, BorderLayout.NORTH);
		frame.add(gameplayPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close operation
		frame.setTitle("Class Manager"); //Title
		frame.pack(); //Pack all components
		frame.setVisible(true); //Make the frame visible
		
		/*
		 * Perform saving and timer stopping operations upon 
		 * closing the window
		 */
		frame.addWindowListener(new WindowAdapter() 
		{
            @Override
            public void windowClosing(WindowEvent e) 
            {
            	saveData(cookie); //Save data
                frame.dispose(); //Close window
            }
        });
		
	}
	
	//Run the program
	public static void main(String[] args)
	{
		new Driver();
	}
	
	/*
	 * The following is used to save the contents of the Cookie
	 */
	private static void saveData(Cookie cookie) 
	{
        try (ObjectOutputStream oos = new 
        ObjectOutputStream(new FileOutputStream(cookieFile))) 
        {
            oos.writeObject(cookie);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

	/*
	 * The following is used to reload the content of the Cookie
	 * when the user reopens the program
	 */
    private static Cookie loadData() 
    {
        try (ObjectInputStream ois = new
        ObjectInputStream(new FileInputStream(cookieFile))) 
        {
            return (Cookie) ois.readObject();
        } 
        catch(Exception e) 
        {
            return null; 
        }
    }
}