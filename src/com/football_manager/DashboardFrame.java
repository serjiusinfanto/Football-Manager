package com.football_manager;

import com.components.*;
import com.components.menu.MyMenuBar;
import com.football_manager.matches_frame.FinishedMatchesFrame;
import com.football_manager.matches_frame.UpcomingMatchesFrame;
import com.football_manager.table_frame.LeagueStandingsFrame;
import com.football_manager.table_frame.TopScorersFrame;
import com.models.Manager;
import com.player_transfer.ViewTransfersRequestsTypeDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DashboardFrame extends JFrame implements ActionListener {

    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();

    ArrayList<JLabel> managerDataLabels;
    ArrayList<MyButton> managerOptions;

    MyButton standingsButton;
    MyButton topScorersButton;
    MyButton fixturesButton;
    MyButton resultsButton;
    MyButton playersButton;
    MyButton transferRequestsButton;

    MyMenuBar menuBar;

    Manager manager;

    public DashboardFrame(Manager manager) { // Manager should come from login
        this.manager = manager;
        menuBar = new MyMenuBar(this, manager);

        // Left Panel -> Details
        managerDataLabels = new ArrayList<>();
        managerDataLabels.add(new MyDataLabel(String.valueOf(manager.getManagerID()), "/icons/color/icon_id.png", "Manager ID"));
        managerDataLabels.add(new MyDataLabel(manager.getName(), "/icons/color/icon_name.png", "Name"));
        managerDataLabels.add(new MyDataLabel(manager.getCountry(), "/icons/color/icon_country.png", "Country"));
        managerDataLabels.add(new MyDataLabel(String.valueOf(manager.getAge()), "/icons/color/icon_age.png", "Age"));


        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(myColor.getBackgroundColor());
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints leftGBC = new GridBagConstraints();

        for (int i = 0; i < managerDataLabels.size(); i++) {
            leftGBC.gridx = 0;
            leftGBC.gridy = i;
            leftPanel.add(managerDataLabels.get(i), leftGBC);
        }

        // Right Panel -> Standings and Top Scorers
        // Standings, Top Scorers, Results, Fixtures, Players

        standingsButton = new MyButton("Standings", "/icons/black/icon_standings.png");
        topScorersButton = new MyButton("Top Scorers", "/icons/black/icon_goals_scored.png");
        fixturesButton = new MyButton("Fixtures", "/icons/black/icon_matches_played.png");
        resultsButton = new MyButton("Results", "/icons/black/icon_results.png");
        playersButton = new MyButton("Players", "/icons/black/icon_position.png");
        transferRequestsButton = new MyButton("Transfer Requests", "/icons/black/icon_transfer.png");

        managerOptions = new ArrayList<>() {{
            add(standingsButton);
            add(topScorersButton);
            add(fixturesButton);
            add(resultsButton);
            add(playersButton);
            add(transferRequestsButton);
        }};

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(myColor.getBackgroundColor());
        GridLayout gridLayout = new GridLayout(3, 2); //using gridlayout -> 3 row,2 columns
        gridLayout.setHgap(50);// horizontal gap between buttons
        gridLayout.setVgap(50);// vertical gap between buttons
        rightPanel.setLayout(gridLayout);

        // add add managerOptions to the panel
        for (MyButton myButton : managerOptions) {
            myButton.addActionListener(this); // add action listener
            rightPanel.add(myButton);
        }

        //        Wrapper
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new GridLayout(1, 2));
        wrapper.add(leftPanel);
        wrapper.add(rightPanel);
        wrapper.setBorder(new EmptyBorder(100, 100, 100, 100));
        wrapper.setBackground(myColor.getBackgroundColor());


//        Frame
        this.setJMenuBar(menuBar);
        this.setTitle("Dashboard");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.add(wrapper);
        this.setIconImage(new MyImage().getLogo());
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Button Listeners
        if (e.getSource() == standingsButton) {
            new LeagueStandingsFrame(manager);
        } else if (e.getSource() == topScorersButton) {
            new TopScorersFrame(manager);
        } else if (e.getSource() == fixturesButton) {
            new UpcomingMatchesFrame(manager);
        } else if (e.getSource() == resultsButton) {
            new FinishedMatchesFrame(manager);
        } else if (e.getSource() == playersButton) {
            new PlayersFrame(manager);
        } else if (e.getSource() == transferRequestsButton) {
            new ViewTransfersRequestsTypeDialog(manager);
        }
        this.dispose();


    }

}


