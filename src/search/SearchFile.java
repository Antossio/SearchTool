package search;
import java.awt.Color;
import java.awt.Container;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class SearchFile extends JFrame implements ActionListener {
    Container cnt;
    JLabel word = new JLabel("Search for:");
    JLabel catalog = new JLabel("Catalog:");
    JButton search = new JButton("Search");
    JTextField inputWord = new JTextField();
    JTextField inputCatalog = new JTextField();
    JCheckBox register = new JCheckBox("Case sensitive");
    TextArea ta = new TextArea();
    Timer tm = new Timer(1000, this);
    JLabel[] m = new JLabel[60];
    static int j = 0;
    static boolean isSearching = false;
    String file = "/statusBar.gif";

    public static void main(String[] args) {
	new SearchFile();
    }

    public SearchFile() {
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setBounds(10, 10, 500, 500);
	cnt = getContentPane();
	setVisible(true);
	cnt.setLayout(null);
	word.setBounds(50, 50, 65, 20);
	cnt.add(word);
	inputWord.setBounds(125, 50, 100, 20);
	cnt.add(inputWord);
	catalog.setBounds(50, 75, 50, 20);
	cnt.add(catalog);
	inputCatalog.setBounds(125, 75, 225, 20);
	cnt.add(inputCatalog);
	search.setBounds(155, 100, 100, 20);
	cnt.add(search);
	register.setBounds(225, 50, 150, 20);
	cnt.add(register);
	search.addActionListener(this);
	ta.setBounds(50, 125, 300, 300);
	cnt.add(ta);
	int x = 5;
	int start = 50;
	for (int i = 0; i < m.length; i++) {
	    m[i] = new JLabel();
	    m[i].setIcon(new ImageIcon(getClass().getResource(file)));;
	    m[i].setBounds(start, 425, x, 10);
	    m[i].setVisible(false);
	    cnt.add(m[i]);
	    start = start + x;
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	Object scr = e.getSource();
	if(scr == search) {
	    SearchThread sthrd = new SearchThread(this, convert(inputWord.getText()), register.isSelected());
	    if(isSearching == false) {
		isSearching = true;
		tm.start();
		sthrd.start();
		search.setText("Stop");
	    }
	    else {
		isSearching = false;
		tm.stop();
		search.setText("Search");
		sthrd.interrupt();
	    }
	}
	else if (scr == tm) {
	    m[j].setVisible(true);
	    repaint();
	    j++;
	    if (j == m.length) {
		for (int i = 1; i < m.length; i++) {
		    m[i].setVisible(false);
		}
		j = 1;				
	    }
	}
    }

    String convert(String s) {
	if (s.indexOf('*') == -1) {
	    return s;
	}
	StringBuffer source = new StringBuffer(s);
	int dotIndex = source.lastIndexOf(".");
	source.insert(dotIndex, "\\");
	int index = source.indexOf("*");
	source.insert(index, ".");
	return source.toString();
    }
}
