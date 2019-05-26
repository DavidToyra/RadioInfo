package controller;

import model.RadioProgram;
import view.RadioGUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.IOException;
import java.util.ArrayList;

public class TableListener implements ListSelectionListener
{
    private JTable table;
    private RadioGUI gui;
    private ArrayList<RadioProgram> programs;

    public TableListener(JTable table, RadioGUI gui, ArrayList<RadioProgram> programs)
    {
        this.table = table;
        this.gui = gui;
        this.programs = programs;
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {

        RadioProgram program = programs.get(table.getSelectedRow());
        try {
            gui.programOptionPane(program);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
