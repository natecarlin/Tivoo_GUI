package view;



import html_output.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import org.joda.time.DateTime;

import controller.TivooSystem;

@SuppressWarnings("serial")
public class TivooView extends JPanel{
	private JFileChooser myFileChooser;
	private JButton myPreviewButton;
	//private JButton myOutputButton;
	private JButton myLoadButton;
	private JButton mySortButton;
	private JButton myFilterButton;
	//private JTextField myXmlUrl;
	private JTextField myOutputDestination;
	private JTextField myYearField;
	private JTextField myMonthField;
	private JTextField myDayField;
	private JTextField myCategoryField;
	private JTextField myKeywordField;
	private JTextField mySearchYear1Field;
	private JTextField mySearchMonth1Field;
	private JTextField mySearchDay1Field;
	private JTextField mySearchHour1Field;
	private JTextField mySearchMin1Field;
	private JTextField mySearchYear2Field;
	private JTextField mySearchMonth2Field;
	private JTextField mySearchDay2Field;
	private JTextField mySearchHour2Field;
	private JTextField mySearchMin2Field;
	private JComboBox myOutputType;
	private JComboBox myFilterType;
	private JComboBox mySortType;
	private TivooSystem myTivooSystem;
	
	public TivooView(TivooSystem s){
		myTivooSystem = s;
		add(makeProcessGui(), BorderLayout.CENTER);
		add(makeOutputGui(), BorderLayout.EAST);
		add(makeFileChooser(), BorderLayout.WEST);
		
	}
	
	private JComponent makeFileChooser(){
		JPanel panel = new JPanel();
		myFileChooser = new JFileChooser();
		myFileChooser.setControlButtonsAreShown(false);
		panel.add(myFileChooser, BorderLayout.CENTER);
		panel.add(makeLoadGui(), BorderLayout.SOUTH);
		return panel;
	}
	
	private JComponent makeProcessGui(){
		JPanel panel = new JPanel();
		myFilterButton = new JButton("filter");
		myFilterButton.addActionListener(new FilterListener());
		mySortButton = new JButton("sort");
		mySortButton.addActionListener(new SortListener());
		mySearchYear1Field = new JTextField("start year", 6);
		mySearchMonth1Field = new JTextField("start month", 4);
		mySearchDay1Field = new JTextField("start day", 4);
		mySearchHour1Field = new JTextField("start hour", 4);
		mySearchMin1Field = new JTextField("start min", 4);
		mySearchYear2Field = new JTextField("end year", 6);
		mySearchMonth2Field = new JTextField("end month", 4);
		mySearchDay2Field = new JTextField("end day", 4);
		mySearchHour2Field = new JTextField("end hour", 4);
		mySearchMin2Field = new JTextField("end min", 4);
		myCategoryField = new JTextField("category", 10);
		myKeywordField = new JTextField("keyword", 10);
		String [] filtertypes = new String [4];
		filtertypes [0] = "keyword";
		filtertypes [1] = "remove keyword";
		filtertypes [2] = "time";
		filtertypes [3] = "between times";
		String[] sorttypes = new String [4];
		sorttypes [0] = "end time";
		sorttypes [1] = "start time";
		sorttypes [2] = "name";
		sorttypes [3] = "reverse";
		myFilterType = new JComboBox(filtertypes);
		myFilterType.addActionListener(new FilterTypeListener());
		mySortType = new JComboBox(sorttypes);
		panel.add(myFilterType, BorderLayout.EAST);
		panel.add(myFilterButton, BorderLayout.CENTER);
		panel.add(mySortType, BorderLayout.WEST);
		panel.add(mySortButton, BorderLayout.WEST);
		return panel;
	}
	
	private JComponent makeLoadGui(){
		JPanel panel = new JPanel(new BorderLayout());
		myLoadButton = new JButton ("load");
		myLoadButton.addActionListener(new LoadActionListener());
		//myXmlUrl = new JTextField("load url", 50);
		panel.add(myLoadButton, BorderLayout.WEST);
		//panel.add(myXmlUrl,BorderLayout.EAST);
		return panel;
	}
	
	private JComponent makeOutputGui(){
		JPanel panel = new JPanel(new BorderLayout());
		myYearField = new JTextField("year", 6);
		myMonthField = new JTextField("month", 4);
		myDayField = new JTextField("day", 4);
		myPreviewButton = new JButton("preview");
		//myOutputButton = new JButton("output");
		//myOutputButton.addActionListener(new OutputListener());
		myOutputDestination = new JTextField("output destination", 50);
		String [] pagetypes = new String[7];
		pagetypes[0] = "conflicting events";
		pagetypes[1] = "day calendar";
		pagetypes[2] = "detail";
		pagetypes[3] = "month calendar";
		pagetypes[4] = "sorted events";
		pagetypes[5] = "summary";
		pagetypes[6] = "week calendar";
		myOutputType = new JComboBox(pagetypes);
		myOutputType.addActionListener(new OutputSelectionListener());
		myPreviewButton.addActionListener(new PreviewListener());
		//panel.add(myOutputButton, BorderLayout.EAST);
		panel.add(myOutputType, BorderLayout.CENTER);
		panel.add(myOutputDestination, BorderLayout.WEST);
		panel.add(myPreviewButton, BorderLayout.EAST);
		return panel;
	}
	
	private class LoadActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			myTivooSystem.loadCal(myFileChooser.getSelectedFile().getPath());
			
		}
		
	}
	
	private class OutputSelectionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(myOutputType.getSelectedItem().equals("month calendar")){
				JFrame frame = new JFrame("time");
				JPanel panel = new JPanel();
		       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        // add our user interface components to Frame and show it
		        panel.add(myYearField);
		        panel.add(myMonthField);
		        panel.add(myDayField);
		        frame.getContentPane().add(panel);
		        frame.pack();
		        frame.setVisible(true);
			}
			if(myOutputType.getSelectedItem().equals("day calendar")){
				JFrame frame = new JFrame("time");
				JPanel panel = new JPanel();
		       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        // add our user interface components to Frame and show it
		        panel.add(myYearField);
		        panel.add(myMonthField);
		        panel.add(myDayField);
		        frame.getContentPane().add(panel);
		        frame.pack();
		        frame.setVisible(true);
			}
			if(myOutputType.getSelectedItem().equals("week calendar")){
				JFrame frame = new JFrame("time");
				JPanel panel = new JPanel();
		        panel.add(myYearField);
		        panel.add(myMonthField);
		        panel.add(myDayField);
		        frame.getContentPane().add(panel);
		        frame.pack();
		        frame.setVisible(true);
			}
		}
	}
	
	private class FilterListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(myFilterType.getSelectedItem().equals("keyword")){
				myTivooSystem.setMyEvents(myTivooSystem.getEventCalendar()
						.searchCalendar(myCategoryField.getText(), myKeywordField.getText()));
			}
			if(myFilterType.getSelectedItem().equals("remove keyword")){
				myTivooSystem.setMyEvents(myTivooSystem.getEventCalendar()
						.removeAllContaining(myCategoryField.getText(), myKeywordField.getText()));
			}
			if(myFilterType.getSelectedItem().equals("time")){
				DateTime time = new DateTime(Integer.parseInt(mySearchYear1Field.getText()), Integer.parseInt(mySearchMonth1Field.getText()),Integer.parseInt(mySearchDay1Field.getText()),
							Integer.parseInt(mySearchHour1Field.getText()), Integer.parseInt(mySearchMin1Field.getText()));
				myTivooSystem.setMyEvents(myTivooSystem.getEventCalendar()
						.eventsAtTime(time));
			}
			if(myFilterType.getSelectedItem().equals("between times")){
				DateTime time1 = new DateTime(Integer.parseInt(mySearchYear1Field.getText()), Integer.parseInt(mySearchMonth1Field.getText()),Integer.parseInt(mySearchDay1Field.getText()),
						Integer.parseInt(mySearchHour1Field.getText()), Integer.parseInt(mySearchMin1Field.getText()));
				DateTime time2 = new DateTime(Integer.parseInt(mySearchYear2Field.getText()), Integer.parseInt(mySearchMonth2Field.getText()),Integer.parseInt(mySearchDay2Field.getText()),
						Integer.parseInt(mySearchHour2Field.getText()), Integer.parseInt(mySearchMin2Field.getText()));
			myTivooSystem.setMyEvents(myTivooSystem.getEventCalendar()
					.eventsBetweenTimes(time1, time2));
			}
			
		}
	}
	
	private class SortListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(myFilterType.getSelectedItem().equals("end time")){
				myTivooSystem.setMyEvents(myTivooSystem.getEventCalendar().sortByEndTime());
			}
			if(myFilterType.getSelectedItem().equals("start time")){
				myTivooSystem.setMyEvents(myTivooSystem.getEventCalendar().sortByStartTime());
			}
			if(myFilterType.getSelectedItem().equals("name")){
				myTivooSystem.setMyEvents(myTivooSystem.getEventCalendar().sortByName());
			}
			if(myFilterType.getSelectedItem().equals("reverse")){
				myTivooSystem.setMyEvents(myTivooSystem.getEventCalendar().reverse());
			}
		}
	}
	
	private class FilterTypeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(myFilterType.getSelectedItem().equals("keyword")){
				JFrame frame = new JFrame("time");
				JPanel panel = new JPanel();
		        panel.add(myCategoryField);
		        panel.add(myKeywordField);
		        frame.getContentPane().add(panel);
		        frame.pack();
		        frame.setVisible(true);
			}
			if(myFilterType.getSelectedItem().equals("remove keyword")){
				JFrame frame = new JFrame("time");
				JPanel panel = new JPanel();
		        panel.add(myCategoryField);
		        panel.add(myKeywordField);
		        frame.getContentPane().add(panel);
		        frame.pack();
		        frame.setVisible(true);
			}
			if(myFilterType.getSelectedItem().equals("time")){
				JFrame frame = new JFrame("time");
				JPanel panel = new JPanel();
				panel.add(mySearchYear1Field);
			    panel.add(mySearchMonth1Field);
			    panel.add(mySearchDay1Field);
			    panel.add(mySearchHour1Field);
			    panel.add(mySearchMin1Field);
			    panel.add(mySearchYear2Field);
			    panel.add(mySearchMonth2Field);
			    panel.add(mySearchDay2Field);
			    panel.add(mySearchHour2Field);
			    panel.add(mySearchMin2Field);
		        frame.getContentPane().add(panel);
		        frame.pack();
		        frame.setVisible(true);
			}
			if(myFilterType.getSelectedItem().equals("between times")){
				JFrame frame = new JFrame("time");
				JPanel panel = new JPanel();
		        panel.add(mySearchYear1Field);
		        panel.add(mySearchMonth1Field);
		        panel.add(mySearchDay1Field);
		        panel.add(mySearchHour1Field);
		        panel.add(mySearchMin1Field);
		        frame.getContentPane().add(panel);
		        frame.pack();
		        frame.setVisible(true);
			}
		}
	}
	
	private class PreviewListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFrame frame = new JFrame("preview");
			JEditorPane pane = new JEditorPane();
			pane.setEditable(false);
			pane.setPreferredSize(new Dimension(800, 600));
			frame.getContentPane().add(pane);
			String destination = myOutputDestination.getText();
			if(myOutputType.getSelectedItem().equals("conflicting events")){
			myTivooSystem.outputHtmlPage(new ConflictingEventsPage(destination));
			try {
				pane.setPage("file:" + destination + "/TiVOOConflictingEventsPage.html");
			} catch (IOException e) {
				System.out.println("bad destination");
			}
			}
			if(myOutputType.getSelectedItem().equals( "day calendar")){
				DateTime time = new DateTime(Integer.parseInt(myYearField.getText()),Integer.parseInt(myMonthField.getText()),
						Integer.parseInt(myDayField.getText()),0,0);
				myTivooSystem.outputHtmlPage(new DayCalendarPage(destination, time));
				try {
					pane.setPage("file:" + destination + "/TiVOOdayCalendarPage.html");
				} catch (IOException e) {
					//
					System.out.println("bad destination");
				}
			}
			if(myOutputType.getSelectedItem().equals( "detail")){
				myTivooSystem.outputHtmlPage(new DetailPage(destination));
			}
			if(myOutputType.getSelectedItem().equals( "month calendar")){
				DateTime time = new DateTime(Integer.parseInt(myYearField.getText()),Integer.parseInt(myMonthField.getText()),
						Integer.parseInt(myDayField.getText()),0,0);
				myTivooSystem.outputHtmlPage(new MonthCalendarPage(destination, time));
				try {
					pane.setPage("file:" + destination + "/TiVOOmonthCalendarPage.html");
				} catch (IOException e) {
					System.out.println("bad destination");
				}
			}
			if(myOutputType.getSelectedItem().equals( "sorted events")){
				myTivooSystem.outputHtmlPage(new SortedEventsPage(destination));
				try {
					pane.setPage("file:" + destination + "/TiVOOSortedEventsPage.html");
				} catch (IOException e) {
					System.out.println("bad destination");
				}
			}
			if(myOutputType.getSelectedItem().equals( "summary")){
				myTivooSystem.outputHtmlPage(new SummaryPage(destination));
				try {
					pane.setPage("file:" + destination + "/TiVOOSummaryPage.html");
				} catch (IOException e) {
					System.out.println("bad destination");
				}
			}
			if(myOutputType.getSelectedItem().equals( "week calendar")){
				DateTime time = new DateTime(Integer.parseInt(myYearField.getText()),Integer.parseInt(myMonthField.getText()),
						Integer.parseInt(myDayField.getText()),0,0);
				myTivooSystem.outputHtmlPage(new WeekCalendarPage(destination, time));
				try {
					pane.setPage("file:" + destination + "/TiVOOweekCalendarPage.html");
				} catch (IOException e) {
					System.out.println("bad destination");
				}
			
		}
			frame.pack();
		frame.setVisible(true);
	}
}
	
}

