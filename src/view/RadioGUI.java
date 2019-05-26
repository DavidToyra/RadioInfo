/*
 *  RadioGUI.java
 *  David Töyrä (dv15dta)
 *  2019-01-30
 */
package view;

import model.Channel;
import model.RadioProgram;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Main gui class, creates a JFrame and attaches all other
 * components there.
 */
public class RadioGUI
{

   private RadioMenuBar menuBar;
   private ChannelListPanel channelPanel;
   private JFrame frame;
   private TablePanel tablePanel;
   public RadioGUI()
   {
      //Create frame
      frame = new JFrame("Radio info");
      frame.setPreferredSize( new Dimension(800,600));
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


      //Add menu bar
      menuBar = new RadioMenuBar();
      frame.setJMenuBar(menuBar);
      frame.setSize(600,400);
      System.out.println(SwingUtilities.isEventDispatchThread());
      //Add tabel panel
      tablePanel = new TablePanel(this);
      tablePanel.setPreferredSize(new Dimension(300,400));

      frame.pack();
      frame.setVisible(true);
      //channelPanel.repaint();
   }

   /**
    * Add the channel list panel unto the frame.
    * @param channels list of radio channels
    */
   public void addChannelPanel(ArrayList<Channel> channels)
   {
      channelPanel = new ChannelListPanel(channels);
      frame.add(channelPanel);
      channelPanel.repaint();
   }

   /**
    * Adds the table panel and hides the jlist panel.
    */
   public void showTables(ArrayList<RadioProgram> programList)
   {
      frame.add(tablePanel);
      channelPanel.setVisible(false);
      tablePanel.createTable(programList);
      frame.pack();
      tablePanel.setVisible(true);
      frame.repaint();
   }

   /**
    * All execptions are sent here and displayed on a joptionpane.
    */
   public void exceptionPane(String errorMessage)
   {

      JOptionPane.showMessageDialog(null, errorMessage);
   }

   /**
    * Extended program info on a option pane. Displays image
    * and description if available.
    * @param program radio program object
    * @throws IOException if image could not be loaded
    */
   public void programOptionPane(RadioProgram program) throws IOException {
      ImageIcon icon = null;
      if(!(program.getImage() == null))
      {
         URL url = new URL(program.getImage());
         BufferedImage image;
         image = ImageIO.read(url);
         Image scaledImage = image.getScaledInstance(250,250, Image.SCALE_SMOOTH);
         icon = new ImageIcon(scaledImage);
      }

      JOptionPane.showMessageDialog(null, new JLabel(program.getDescription(),
              icon, JLabel.LEFT), program.getProgramName(), JOptionPane.INFORMATION_MESSAGE);
   }

   /**
    * Shows the channel list panel and hides table panel.
    */
   public void showChannels()
   {

      channelPanel.makeChannelJList();
      channelPanel.setVisible(true);
      tablePanel.setVisible(false);
   }

   /**
    * Get the channel list panel
    * @return JPanel
    */
   public ChannelListPanel getChannelPanel() {
      return channelPanel;
   }


   /**
    * Get the table panel
    * @return JPanel
    */
   public TablePanel getTablePanel() {
      return tablePanel;
   }

   /**
    * Get the menubar
    * @return JMenubar
    */
   public RadioMenuBar getMenuBar() {
      return menuBar;
   }


}
