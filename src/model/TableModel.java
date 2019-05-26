package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TableModel extends AbstractTableModel {

    private ArrayList<RadioProgram> programs;
    public TableModel(ArrayList<RadioProgram> programList)
    {
        this.programs = programList;

    }

    @Override
    public int getRowCount() {
        return programs.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch (columnIndex) {
            case 0 : return programs.get(rowIndex).getProgramName();
            case 1 : return programs.get(rowIndex).getStartTime();
            case 2 : return programs.get(rowIndex).getEndTime();
            case 3 : return programs.get(rowIndex).getChannelName();
        }


        return null;
    }
}
