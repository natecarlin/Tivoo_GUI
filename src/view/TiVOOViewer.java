package view;

import html_output.SummaryPage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


import process.TivooSystem;

public class TiVOOViewer extends JFrame{

	private String myTitle;
	private ArrayList<File> myFiles;
	private ArrayList<String> myLinks;
	private TivooSystem myTVS;
	private JTextField myMessage;
	private JEditorPane pane;
	
	protected static JFileChooser ourChooser = 
        new JFileChooser(System.getProperties().getProperty("user.dir"));
	
	public TiVOOViewer (String title){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel=new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(makeMessage(), BorderLayout.SOUTH);
		
		setTitle(title);
        myTitle = title;
        makeMenus();
        pack();
        
        setSize(400,400);
        setVisible(true);
	}
	
	public void setModel(TivooSystem TVS){
		myTVS=TVS;
		// to do: let myTVS to set Viewer to this.		
	}
	
	protected JPanel makeMessage() {
        JPanel p = new JPanel(new BorderLayout());
        myMessage = new JTextField(30);
        p.setBorder(BorderFactory.createTitledBorder("message"));
        p.add(myMessage, BorderLayout.CENTER);
        return p;
    }
	
	// Return input area where ENTER evaluates expression.
	/*
    protected JTextField makeInput ()
    {
        JTextField result = new JTextField();
        result.setBorder(BorderFactory.createLoweredBevelBorder());
        result.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed (ActionEvent evt)
                {
                    try
                    {
                        myDisplay.setIcon(myModel.evaluate(myInput.getText(), mySize).toIcon());
                    }
                    catch (ParserException e)
                    {
                        JOptionPane.showMessageDialog(Frame.this,
                                                      e.getMessage(),
                                                      "Input Error",
                                                      JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        return result;
    }
	
	
	*/
	
	
	protected void makeMenus() {
        JMenuBar bar = new JMenuBar();
        bar.add(makeFileMenu());
        //bar.add(makeOptionsMenu());
        setJMenuBar(bar);
    }
	
	private JMenu makeFileMenu(){
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new AbstractAction("Load File") {
            public void actionPerformed(ActionEvent ev) {
                doRead();
            }
        });
		
	    JMenu filterMenu=new JMenu("SelectFilter");
	    
	    
		fileMenu.add(filterMenu);
		
		filterMenu.add(new AbstractAction("Find Keyword") {
            public void actionPerformed(ActionEvent ev) {
                doKeywordProcess();
            }			
        });
		filterMenu.add(new AbstractAction("Find at Time") {
            public void actionPerformed(ActionEvent ev) {
                doTimeProcess();
            }			
        });
		filterMenu.add(new AbstractAction("Find between Time") {
            public void actionPerformed(ActionEvent ev) {
                doPeriodProcess();
            }			
        });
		
		fileMenu.add(new AbstractAction("Preview Webpage") {
            public void actionPerformed(ActionEvent ev) {
                try {
					doPreview();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

			
        });
		
		// more options like parse, filter or webpage preview
		
		
		fileMenu.add(new AbstractAction("Quit") {
            public void actionPerformed(ActionEvent ev) {
                System.exit(0);
            }
        });
		
		return fileMenu;
	}
	
	
	protected void doPeriodProcess() {
		// TODO Auto-generated method stub
		
	}

	protected void doTimeProcess() {
		// TODO Auto-generated method stub
		
	}

	private void doPreview() throws IOException{
			
		pane = new JEditorPane();/////////////
        getContentPane().setLayout(new BorderLayout());////////////
        getContentPane().add(pane, BorderLayout.CENTER);////////////
        pane.setEditable(false);
        pane.setPreferredSize(new Dimension(800,600));
        pane.addHyperlinkListener(new LinkFollower());
        pack();
        pane.setPage(myTVS.outputHtmlPage(new SummaryPage(null)));//////////what does the return string of outputHtmlPage mean?
        setVisible(true);
		//myTVS.
		
		// TODO Auto-generated method stub	
			}
	
	private class LinkFollower implements HyperlinkListener
    {
        public void hyperlinkUpdate (HyperlinkEvent evt)
        {
            if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
            {
                // user clicked a link, load it and show it
                try
                {
                    pane.setPage((evt.getURL().toString()));
                }
                catch (Exception e)
                {
                    String s = evt.getURL().toString();
                    JOptionPane.showMessageDialog(TiVOOViewer.this,
                            "loading problem for " + s + " " + e,
                            "Load Problem", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
	
	private void doKeywordProcess() {
		
		JTextField input = new JTextField();//////////////
		//input.
		
		myTVS.setMyEvents(myTVS.getEventCalendar().filterByName("meet"));
		
				// TODO Auto-generated method stub
				
			}
	

	private ArrayList<File> doRead(){
		
		int retval=ourChooser.showOpenDialog(null);
		if(retval!=JFileChooser.APPROVE_OPTION){
			return null;
		}
		File[] files=ourChooser.getSelectedFiles();
		myFiles=new ArrayList<File>();
			for(File f: files){
				myFiles.add(f);
			}
	    for(File f: myFiles){
	    	myTVS.loadCal(f.getPath());//////////
	    }
		
		//do something here

		ArrayList<File> list=new ArrayList<File>();
		list.addAll(myFiles);
		myFiles=null;
		return list;
	
	}
	
	
	public static void main(String[] args){
		
		
		
		TiVOOViewer tvv=new TiVOOViewer("TiVOO");
		
		tvv.setModel(new TivooSystem());
		
		
	}
	
}
