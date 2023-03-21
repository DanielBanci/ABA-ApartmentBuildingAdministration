package mainPanels;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import appInterface.AdminAccount;
import appInterface.ApartmentAccount;
import appInterface.Login;
import mainClasses.Apartment;
import mainClasses.User;

/**
The MenuBarPanel class is a JPanel that displays a menu bar with different options based on the user account type.
This class contains two private methods that create a menu bar for different account types (admin or apartment) and
displays the corresponding options. The class also loads an image and displays it at the top of the panel.
@author Ciprian Banci
@version 1.0
*/
public class MenuBarPanel extends JPanel {
	/**
	 * Constructor of the class.
	 * 
	 * @param frame the JFrame to which the menu bar will be added
	 * @param user the User object of the current user
	 */
	public MenuBarPanel(JFrame frame,User user) {
		super();  
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.setMinimumSize(new Dimension(250,300));
		this.setBackground(new Color(0,80,145,217));
		JMenuBar menuBar;
		
		if(frame instanceof ApartmentAccount)
			menuBar = makeMenuBarApartment((ApartmentAccount)frame,(Apartment)user);
		else
			menuBar = makeMenuBarAdmin((AdminAccount)frame);
		this.add(menuBar);
		//load the image
		
		Image image = null;
		ImageIcon imageIcon = null;
		try {
			image = (new ImageIcon(this.getClass().getResource("/city-icon.png"))).getImage();
			//Image modifiedImage = image.getScaledInstance(120,150,java.awt.Image.SCALE_SMOOTH);
			imageIcon = new ImageIcon(image);
		}catch(Exception e) {
			e.printStackTrace();
		}
						
		//display the login image
		JPanel pic = new JPanel();
		pic.setLayout(new GridLayout(1,1));
		JLabel imageLabel = new JLabel("");
		imageLabel.setIcon(imageIcon);
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		//imageLabel.setPreferredSize(new Dimension(10,130));
		pic.setPreferredSize(new Dimension(10,130));
		pic.setMaximumSize(new Dimension(250,130));
		pic.add(imageLabel);
		pic.setOpaque(false);
		this.add(pic,0);
		
		//empty panel
		JPanel emptyPanel = new JPanel();
		this.add(emptyPanel);
		emptyPanel.setOpaque(false);
		emptyPanel.setMaximumSize(new Dimension(200,500));
		
	}
	
	/**
	Creates a customized menu bar for an admin user account.
	@param frame the JFrame to which the menu bar will be added
	@param user the admin object of the current user
	@return a JMenuBar object with menu items specific to an admin user account
	*/
	private JMenuBar makeMenuBarAdmin(final AdminAccount frame) {
		/*
		 * creating menu for admin account
		 */
		
	    //creating a vertical menu bar
		final Color color = new Color(0,172,255,210);
	    JMenuBar menuBar = new JMenuBar();

	    menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
	    menuBar.setMaximumSize(new Dimension(150,500));
	    menuBar.setMinimumSize(new Dimension(150,200));

	    //creating JMenuItems
	    int size = 16;
	    final JMenuItem mIHome = new JMenuItem("Acasa");
	    mIHome.setBackground(color);
	    //mIHome.setOpaque(true);
	    mIHome.setHorizontalAlignment(SwingConstants.CENTER);
	    mIHome.setFont(new Font("Times New Roman",Font.PLAIN,size));
	    try {
	        Image img = ImageIO.read(getClass().getResource("/homeIcon.png"));
	        Image modifiedImage = img.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
	        mIHome.setIcon(new ImageIcon(modifiedImage));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    
	    final JMenuItem mINoticeBoard = new JMenuItem("Avizier");
	    mINoticeBoard.setBackground(color);
	    mINoticeBoard.setHorizontalAlignment(SwingConstants.CENTER);
	    mINoticeBoard.setFont(new Font("Times New Roman",Font.PLAIN,size));
	    try {
	        Image img = ImageIO.read(getClass().getResource("/icons8-bill-24.png"));
	        Image modifiedImage = img.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
	        mINoticeBoard.setIcon(new ImageIcon(modifiedImage));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    
	    final JMenuItem mIUpdate = new JMenuItem("Act. avizier");
	    mIUpdate.setBackground(color);
	    mIUpdate.setHorizontalAlignment(SwingConstants.CENTER);
	    mIUpdate.setFont(new Font("Times New Roman",Font.PLAIN,size));
	    try {
	        Image img = ImageIO.read(getClass().getResource("/refreshIcon.png"));
	        Image modifiedImage = img.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
	        mIUpdate.setIcon(new ImageIcon(modifiedImage));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    
	    final JMenuItem mIChat = new JMenuItem("Sesizari");
	    mIChat.setBackground(color);
	    mIChat.setHorizontalAlignment(SwingConstants.CENTER);
	    mIChat.setFont(new Font("Times New Roman",Font.PLAIN,size));
	    try {
	        Image img = ImageIO.read(getClass().getResource("/chatIcon.png"));
	        Image modifiedImage = img.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
	        mIChat.setIcon(new ImageIcon(modifiedImage));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    
	    JMenuItem emptyItem = new JMenuItem();
	    
	    final JMenuItem mILogOut = new JMenuItem("Deconectare");
	    mILogOut.setBackground(color);
	    mILogOut.setHorizontalAlignment(SwingConstants.CENTER);
	    mILogOut.setFont(new Font("Times New Roman",Font.PLAIN,size));
	    try {
	        Image img = ImageIO.read(getClass().getResource("/logOut.png"));
	        Image modifiedImage = img.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
	        mILogOut.setIcon(new ImageIcon(modifiedImage));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    //actions
	    final Color mouseAColor = new Color(141,213,255,100);
	    mIHome.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		frame.contentPane.remove(frame.noticeBoardPanel);
	    		frame.contentPane.remove(frame.chatPanel);
	    		frame.contentPane.remove(frame.updatePanel);
	    		frame.contentPane.remove(frame.homePanel);
	    		frame.contentPane.remove(frame.apInfoPanel);
	    		frame.contentPane.remove(frame.modDataAcc);
	    		frame.contentPane.remove(frame.adminCh);
	    		frame.pack();
	    		frame.repaint();
	    		frame.contentPane.add(frame.homePanel);
	    		frame.pack();
	    		frame.repaint();
	    	}
	    });
	    mIHome.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		mIHome.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		mIHome.setBackground(color);
	    		frame.repaint();
	    	}
	    });
	    mINoticeBoard.addActionListener(new ActionListener() {
	    	//show NoticeBoardPanel
	    	public void actionPerformed(ActionEvent e) {
	    		frame.contentPane.remove(frame.noticeBoardPanel);
	    		frame.contentPane.remove(frame.chatPanel);
	    		frame.contentPane.remove(frame.updatePanel);
	    		frame.contentPane.remove(frame.homePanel);
	    		frame.contentPane.remove(frame.apInfoPanel);
	    		frame.contentPane.remove(frame.modDataAcc);
	    		frame.contentPane.remove(frame.adminCh);
	    		frame.pack();
	    		frame.repaint();
	    		frame.contentPane.add(frame.noticeBoardPanel);
	    		//frame.pack();
	    		//frame.table.refreshTable(frame);
	    		frame.noticeBoardPanel.getTable().refreshTable(frame);
	    		frame.repaint();
	    		frame.pack();
	    	}
	    });
	    mINoticeBoard.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		mINoticeBoard.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		mINoticeBoard.setBackground(color);
	    		frame.repaint();
	    	}
	    });
	    
	    mIUpdate.addActionListener(new ActionListener() {
	    	//show updatePanel
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		frame.contentPane.remove(frame.chatPanel);
	    		frame.contentPane.remove(frame.noticeBoardPanel);
	    		frame.contentPane.remove(frame.updatePanel);
	    		frame.contentPane.remove(frame.homePanel);
	    		frame.contentPane.remove(frame.apInfoPanel);
	    		frame.contentPane.remove(frame.modDataAcc);
	    		frame.contentPane.remove(frame.adminCh);
	    		frame.pack();
	    		frame.repaint();
	    		frame.contentPane.add(frame.updatePanel);
	    		frame.pack();
	    	}
	    });
	    mIUpdate.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		mIUpdate.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		mIUpdate.setBackground(color);
	    		frame.repaint();
	    	}
	    });
	    
	    mIChat.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		frame.contentPane.remove(frame.noticeBoardPanel);
	    		frame.contentPane.remove(frame.chatPanel);
	    		frame.contentPane.remove(frame.updatePanel);
	    		frame.contentPane.remove(frame.homePanel);
	    		frame.contentPane.remove(frame.apInfoPanel);
	    		frame.contentPane.remove(frame.modDataAcc);
	    		frame.contentPane.remove(frame.adminCh);
	    		frame.repaint();
	    		frame.contentPane.add(frame.chatPanel);
	    		frame.repaint();
	    		frame.pack();
	    		
	    	}
	    });
	    mIChat.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		mIChat.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		mIChat.setBackground(color);
	    		frame.repaint();
	    	}
	    });

	    
	    mILogOut.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		frame.dispose();
	    		Login.lunch();
	    	}
	    });
	    mILogOut.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		mILogOut.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		mILogOut.setBackground(color);
	    		frame.repaint();
	    	}
	    });
	    
	    final JMenu menu = new JMenu("Mai multe");
	    menu.setBackground(color);
	    menu.setOpaque(true);
	    menu.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		menu.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		menu.setBackground(color);
	    		frame.repaint();
	    	}
	    });
	    try {
	        Image img = ImageIO.read(getClass().getResource("/more.png"));
	        Image modifiedImage = img.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
	        menu.setIcon(new ImageIcon(modifiedImage));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    menu.setHorizontalAlignment(SwingConstants.CENTER);
	    JMenuItem mIApInfo = new JMenuItem("Actualizare informatii apartamente");
	    JMenuItem mISettings = new JMenuItem("Setari cont");
	    JMenuItem mINewAdmin = new JMenuItem("Schimbare administrator");
	    mIApInfo.setHorizontalAlignment(SwingConstants.CENTER);
	    mISettings.setHorizontalAlignment(SwingConstants.CENTER);
	    mINewAdmin.setHorizontalAlignment(SwingConstants.CENTER);
	    menu.setMaximumSize(new Dimension(210,200));
	    menu.add(mIApInfo);
	    menu.add(mISettings);
	    menu.add(mINewAdmin);
	    
	    //actions for menu options
	    
	    mISettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.contentPane.remove(frame.noticeBoardPanel);
	    		frame.contentPane.remove(frame.chatPanel);
	    		frame.contentPane.remove(frame.updatePanel);
	    		frame.contentPane.remove(frame.homePanel);
	    		frame.contentPane.remove(frame.apInfoPanel);
	    		frame.contentPane.remove(frame.modDataAcc);
	    		frame.contentPane.remove(frame.adminCh);
	    		frame.repaint();
	    		frame.contentPane.add(frame.modDataAcc);
	    		frame.repaint();
	    		frame.pack();
				
			}
	    	
	    });
	    
	    mISettings.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		mISettings.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		mISettings.setBackground(Color.WHITE);
	    		frame.repaint();
	    	}
	    });
	    
	    mIApInfo.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		frame.contentPane.remove(frame.noticeBoardPanel);
	    		frame.contentPane.remove(frame.chatPanel);
	    		frame.contentPane.remove(frame.updatePanel);
	    		frame.contentPane.remove(frame.homePanel);
	    		frame.contentPane.remove(frame.apInfoPanel);
	    		frame.contentPane.remove(frame.modDataAcc);
	    		frame.contentPane.remove(frame.adminCh);
	    		frame.repaint();
	    		frame.contentPane.add(frame.apInfoPanel);
	    		frame.repaint();
	    		frame.pack();
	    	}
	    });
	    
	    mIApInfo.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		mIApInfo.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		mIApInfo.setBackground(Color.WHITE);
	    		frame.repaint();
	    	}
	    });
	    
	    mINewAdmin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.contentPane.remove(frame.noticeBoardPanel);
	    		frame.contentPane.remove(frame.chatPanel);
	    		frame.contentPane.remove(frame.updatePanel);
	    		frame.contentPane.remove(frame.homePanel);
	    		frame.contentPane.remove(frame.apInfoPanel);
	    		frame.contentPane.remove(frame.modDataAcc);
	    		frame.contentPane.remove(frame.adminCh);
	    		frame.repaint();
	    		frame.contentPane.add(frame.adminCh);
	    		frame.repaint();
	    		frame.pack();
				
			}
	    	
	    });
	    
	    mINewAdmin.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		mINewAdmin.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		mINewAdmin.setBackground(Color.WHITE);
	    		frame.repaint();
	    	}
	    });
	    
	    //addiding the items to menu bar
	    menuBar.add(mIHome);
	    menuBar.add(mINoticeBoard);
	    menuBar.add(mIUpdate);
	    menuBar.add(mIChat);
	    menuBar.add(menu);
	    menuBar.add(emptyItem);
	    menuBar.add(mILogOut);
	    
		emptyItem.setBackground(color);
	    menuBar.setPreferredSize(new Dimension(200,500));
	    menuBar.setMaximumSize(new Dimension(200,500));
	   // menuBar.setMinimumSize(new Dimension(200,600));
	    
		return menuBar;
	}
	
	/**
	Creates a customized menu bar for an apartment user account.
	@param frame the JFrame to which the menu bar will be added
	@param user the Apartment object of the current user
	@return a JMenuBar object with menu items specific to an apartment user account
	*/
	private JMenuBar makeMenuBarApartment(final ApartmentAccount frame, final Apartment apartment) {
		/*
		 * creating menu for apartment account
		 */
		
	    //creating a vertical menu bar
		JMenuBar menuBar = new JMenuBar();
	    menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
	    menuBar.setPreferredSize(new Dimension(200,500));
	    menuBar.setMaximumSize(new Dimension(200,500));

	    //creating JMenuItems
	    int size = 16;
	    final Color color = new Color(0,172,255,210);
	    final JMenuItem mIHome = new JMenuItem("Acasa");
	    mIHome.setBackground(color);
	    mIHome.setFont(new Font("Times New Roman",Font.PLAIN,size));
	    mIHome.setHorizontalAlignment(SwingConstants.RIGHT);
	    try {
	        Image img = ImageIO.read(getClass().getResource("/homeIcon.png"));
	        Image modifiedImage = img.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
	        mIHome.setIcon(new ImageIcon(modifiedImage));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    
	    final JMenuItem mINoticeBoard = new JMenuItem("Avizier");
	    mINoticeBoard.setBackground(color);
	    mINoticeBoard.setHorizontalAlignment(SwingConstants.RIGHT);
	    try {
	        Image img = ImageIO.read(getClass().getResource("/icons8-bill-24.png"));
	        Image modifiedImage = img.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
	        mINoticeBoard.setIcon(new ImageIcon(modifiedImage));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    mINoticeBoard.setFont(new Font("Times New Roman",Font.PLAIN,size));
	    final JMenuItem mIUpdate = new JMenuItem("Act. contoare");
	    mIUpdate.setBackground(color);
	    mIUpdate.setHorizontalAlignment(SwingConstants.RIGHT);
	    try {
	        Image img = ImageIO.read(getClass().getResource("/refreshIcon.png"));
	        Image modifiedImage = img.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
	        mIUpdate.setIcon(new ImageIcon(modifiedImage));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    
	    final JMenuItem mIChat = new JMenuItem("Sesizari");
	    mIChat.setBackground(color);
	    mIChat.setHorizontalAlignment(SwingConstants.RIGHT);
	    mIChat.setFont(new Font("Times New Roman",Font.PLAIN,size));
	    try {
	        Image img = ImageIO.read(getClass().getResource("/chatIcon.png"));
	        Image modifiedImage = img.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
	        mIChat.setIcon(new ImageIcon(modifiedImage));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    
	    JMenuItem emptyItem = new JMenuItem();
	    emptyItem.setBackground(color);
	    
	    final JMenuItem mILogOut = new JMenuItem("Deconectare");
	    mILogOut.setBackground(color);
	    mILogOut.setHorizontalAlignment(SwingConstants.RIGHT);
	    mILogOut.setFont(new Font("Times New Roman",Font.PLAIN,size));
	    try {
	        Image img = ImageIO.read(getClass().getResource("/logOut.png"));
	        Image modifiedImage = img.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
	        mILogOut.setIcon(new ImageIcon(modifiedImage));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    final JMenu menu = new JMenu("Mai multe");
	    menu.setPreferredSize(new Dimension(200,50));
	    menu.setMinimumSize(new Dimension(200,50));
	    menu.setBackground(color);
	    menu.setOpaque(true);
	    try {
	        Image img = ImageIO.read(getClass().getResource("/more.png"));
	        Image modifiedImage = img.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
	        menu.setIcon(new ImageIcon(modifiedImage));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    menu.setFont(new Font("Times New Roman",Font.PLAIN,size));
	    menu.setHorizontalAlignment(SwingConstants.CENTER);
	    JMenuItem mISettings = new JMenuItem("Schimba date cont");
	    mISettings.setHorizontalAlignment(SwingConstants.CENTER);
	    menu.setMaximumSize(new Dimension(210,200));
	    menu.add(mISettings);
	    
	    JMenuItem mIApData = new JMenuItem("Date apartament");
	    mIApData.setHorizontalAlignment(SwingConstants.CENTER);
	    menu.add(mIApData);

	    //menu items actions
	    final Color mouseAColor = new Color(141,213,255,100);
	    mIHome.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		frame.contentPane.remove(frame.noticeBoardPanel);
	    		frame.contentPane.remove(frame.chatPanel);
	    		frame.contentPane.remove(frame.updatePanel);
	    		frame.contentPane.remove(frame.homePanel);
	    		frame.contentPane.remove(frame.apInfoPanel);
	    		frame.contentPane.remove(frame.modDataAcc);
	    		frame.pack();
	    		frame.repaint();
	    		frame.contentPane.add(frame.homePanel);
	    		frame.pack();
	    		frame.repaint();
	    	}
	    	
	    });
	    mIHome.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		mIHome.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		mIHome.setBackground(color);
	    		frame.repaint();
	    	}
	    });
	    mINoticeBoard.addActionListener(new ActionListener() {
	    	//show NoticeBoardPanel
	    	public void actionPerformed(ActionEvent e) {
	    		frame.contentPane.remove(frame.noticeBoardPanel);
	    		frame.contentPane.remove(frame.chatPanel);
	    		frame.contentPane.remove(frame.updatePanel);
	    		frame.contentPane.remove(frame.homePanel);
	    		frame.contentPane.remove(frame.apInfoPanel);
	    		frame.contentPane.remove(frame.modDataAcc);
	    		frame.pack();
	    		frame.repaint();
	    		frame.contentPane.add(frame.noticeBoardPanel);
	    		//frame.table.refreshTable(frame);
	    		frame.noticeBoardPanel.getTable().refreshTable(frame);
				frame.pack();
	    	}
	    });
	    mINoticeBoard.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		mINoticeBoard.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		mINoticeBoard.setBackground(color);
	    		frame.repaint();
	    	}
	    });
	    mIUpdate.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		frame.contentPane.remove(frame.chatPanel);
	    		frame.contentPane.remove(frame.noticeBoardPanel);
	    		frame.contentPane.remove(frame.updatePanel);
	    		frame.contentPane.remove(frame.homePanel);
	    		frame.contentPane.remove(frame.apInfoPanel);
	    		frame.contentPane.remove(frame.modDataAcc);
	    		frame.pack();
	    		frame.repaint();
	    		frame.contentPane.add(frame.updatePanel);
	    		frame.pack();
	    	}
	    });
	    mIUpdate.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		mIUpdate.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		mIUpdate.setBackground(color);
	    		frame.repaint();
	    	}
	    });
	    mIChat.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		frame.contentPane.remove(frame.noticeBoardPanel);
	    		frame.contentPane.remove(frame.chatPanel);
	    		frame.contentPane.remove(frame.updatePanel);
	    		frame.contentPane.remove(frame.homePanel);
	    		frame.contentPane.remove(frame.apInfoPanel);
	    		frame.contentPane.remove(frame.modDataAcc);
	    		frame.repaint();
	    		frame.contentPane.add(frame.chatPanel);
	    		frame.repaint();
	    		frame.pack();
	    		
	    	}
	    });
	    mIChat.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		mIChat.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		mIChat.setBackground(color);
	    		frame.repaint();
	    	}
	    });
	    mILogOut.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		frame.dispose();
	    		Login.lunch();
	    	}
	    });
	    mILogOut.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		mILogOut.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		mILogOut.setBackground(color);
	    		frame.repaint();
	    	}
	    });
	    mISettings.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		frame.contentPane.remove(frame.noticeBoardPanel);
	    		frame.contentPane.remove(frame.chatPanel);
	    		frame.contentPane.remove(frame.updatePanel);
	    		frame.contentPane.remove(frame.homePanel);
	    		frame.contentPane.remove(frame.apInfoPanel);
	    		frame.contentPane.remove(frame.modDataAcc);
	    		frame.repaint();
	    		frame.contentPane.add(frame.modDataAcc);
	    		frame.repaint();
	    		frame.pack();
	    	}
	    });
	    menu.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		menu.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		menu.setBackground(color);
	    		frame.repaint();
	    	}
	    });
	    
	    
	    mIApData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.contentPane.remove(frame.noticeBoardPanel);
	    		frame.contentPane.remove(frame.chatPanel);
	    		frame.contentPane.remove(frame.updatePanel);
	    		frame.contentPane.remove(frame.homePanel);
	    		frame.contentPane.remove(frame.apInfoPanel);
	    		frame.contentPane.remove(frame.modDataAcc);
	    		frame.contentPane.add(frame.apInfoPanel);
	    		frame.repaint();
	    		frame.pack();
			}
	    	
	    });
	    mIApData.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		menu.setBackground(mouseAColor);
	    		frame.repaint();
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		menu.setBackground(color);
	    		frame.repaint();
	    	}
	    });

	    //addiding the items to menu bar
	    menuBar.add(mIHome);
	    menuBar.add(mINoticeBoard);
	    menuBar.add(mIUpdate);
	    menuBar.add(mIChat);
	    menuBar.add(menu);
		menuBar.add(emptyItem);
	    menuBar.add(mILogOut);
	    
	    emptyItem.setMinimumSize(new Dimension(100,150));
	    
	    menuBar.setPreferredSize(new Dimension(200,500));
	    menuBar.setMaximumSize(new Dimension(210,500));
	    
		return menuBar;
	}
}
