/*
 *  TablePanel.java
 *  David Töyrä (dv15dta)
 *  2019-01-30
 */
package view;

import controller.TableListener;
import model.RadioProgram;
import model.TableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

/**
 * Jpanel that holds the tables.
 * Uses an abstract table model from the model package that
 * holds all the data.
 */
public class TablePanel extends JPanel
{
    private String[] columnNames = {"Program", "Start", "End", "Channel"};
    private RadioGUI gui;
    private JTable episodeTable;
    private JScrollPane tableScroll;

    /**
     * Constructor for the table panel.
     * @param gui JFrame that holds all the GUI
     */
    public TablePanel(RadioGUI gui)
    {
        this.gui = gui;
        this.setLayout(new BorderLayout());
        tableScroll = new JScrollPane();
        tableScroll.createHorizontalScrollBar();
        tableScroll.createVerticalScrollBar();
    }

    /**
     * Creates a visual JTable unto a scrollpane and adds it to the panel.
     * The JTable is made of the abstract table model that holds all
     * the program data.
     * @param programList list of programs.
     */
    public void createTable(ArrayList<RadioProgram> programList)
    {

        episodeTable = new JTable(new TableModel(programList));
        //set column names
        episodeTable.getColumnModel().getColumn(0).setHeaderValue("Program");
        episodeTable.getColumnModel().getColumn(1).setHeaderValue("Start");
        episodeTable.getColumnModel().getColumn(2).setHeaderValue("End");
        episodeTable.getColumnModel().getColumn(3).setHeaderValue("Channel");

        //Set colour of rows to blue if the program has already been.
        episodeTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value,
                                                           boolean isSelected,
                                                           boolean hasFocus,
                                                           int row,
                                                           int col)
            {


                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                LocalDateTime startTime = (LocalDateTime) table.getModel().getValueAt(row, 1);
                if (startTime.isBefore(LocalDateTime.now()))
                {
                    setBackground(Color.BLUE);
                    setForeground(Color.WHITE);
                } else
                    {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
                value = startTime.format(ISO_LOCAL_TIME);
                return this;
            }
        });

        //Add selection listener to the table
        episodeTable.getSelectionModel().addListSelectionListener
                (new TableListener(episodeTable,gui, programList));
        tableScroll.getViewport().add(episodeTable);
        this.add(tableScroll,BorderLayout.CENTER);
        tableScroll.setVisible(true);
    }

    /**
     * Get the JTable
     * @return JTable
     */
    public JTable getEpisodeTable() {
        return episodeTable;
    }

}
