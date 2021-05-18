package com.football_manager.table_frame;

import com.components.MyColor;
import com.components.MyFont;
import com.components.MyImage;
import com.components.menu.MyMenuBar;
import com.components.menu.MyRefreshLabel;
import com.models.Manager;
import com.api.API;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.api.UpdateAPI.getLastUpdatedDateTime;

public class TableDataFrame extends JFrame {

    public MyColor myColor = new MyColor();
    public MyFont myFont = new MyFont();
    public JScrollPane scrollPane;
    API api = new API();

    JTable table;
    MyMenuBar menuBar;

    public Manager manager;

    public TableDataFrame(String heading, Manager manager, String lastUpdatedName) {

        this.manager = manager;
        menuBar = new MyMenuBar(this, manager);

        // Heading
        JLabel headingLabel = new JLabel();
        headingLabel.setText(heading);
        headingLabel.setForeground(myColor.getPrimaryColor());
        headingLabel.setFont(myFont.getFontMedium().deriveFont(30f));

        // Refresh Label
        MyRefreshLabel refreshLabel = new MyRefreshLabel();
        refreshLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                if (heading == "League Standings") {
                    api.getCurrentLeagueStanding();
                    new LeagueStandingsFrame(manager);
                } else {
                    api.getTopScorers();
                    new TopScorersFrame(manager);
                }
            }
        });

        // Last update time label
        JLabel lastUpdateLabel = new JLabel();
        lastUpdateLabel.setText(String.format("Last Updated at %s", getLastUpdatedDateTime(lastUpdatedName)));
        lastUpdateLabel.setForeground(myColor.getTextColor());
        lastUpdateLabel.setFont(myFont.getFontMedium().deriveFont(Font.ITALIC, 14f));

        //        Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new MyColor().getBackgroundColor()); //set background for panel
        topPanel.setLayout(new GridBagLayout());// setting gridlayout for panel

        GridBagConstraints headerGBC = new GridBagConstraints();
        headerGBC.insets = new Insets(0, 10, 0, 0);

        headerGBC.gridx = 0;
        headerGBC.gridy = 0;
        topPanel.add(headingLabel, headerGBC);

        headerGBC.gridx = 1;
        headerGBC.gridy = 0;
        topPanel.add(refreshLabel, headerGBC);

        headerGBC.gridx = 2;
        headerGBC.gridy = 0;
        topPanel.add(lastUpdateLabel, headerGBC);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1150, 400));// set dimension to jscrollpane

        //        Wrapper Panel
        JPanel wrapper = new JPanel(); // wrapper for all components in frame
        wrapper.setLayout(new GridBagLayout()); // set gridbaglayout
        wrapper.setBackground(new MyColor().getBackgroundColor());// set background color for wrapper

        GridBagConstraints wrapperGBC = new GridBagConstraints();
        wrapperGBC.insets = new Insets(50, 0, 50, 0);

        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 0;
        wrapper.add(topPanel, wrapperGBC);

        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 2;
        wrapperGBC.gridheight = 4;
        wrapper.add(scrollPane, wrapperGBC);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setJMenuBar(menuBar);
        this.setTitle(heading);
        this.setIconImage(new MyImage().getLogo());
        this.add(wrapper);
        this.setVisible(true);

    }

    public void fitColumnSizeToContent() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel(); // get table model from table
        TableColumnModel columnModel = table.getColumnModel(); // get column model from table
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            int width = 20;
            for (int j = 0; j < tableModel.getRowCount(); j++) {
                TableCellRenderer renderer1 = table.getCellRenderer(j, i);
                Component comp = table.prepareRenderer(renderer1, j, i);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300) {
                width = 300;
            }
            columnModel.getColumn(i).setPreferredWidth(width);
        }
    }
}
