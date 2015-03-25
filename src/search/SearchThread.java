package search;
import java.io.File;

public class SearchThread extends Thread {
    String word;
    boolean register;
    SearchFile sf;
    boolean FindNothing=true;

    public SearchThread(SearchFile sf, String word, boolean register) {
	this.word = word;
	this.register = register;
	this.sf = sf;
    }

    public void run() {
	File f = new File("C:/");
	while (!this.isInterrupted()) {
	    search(f);
	}
	if (FindNothing) {
	    sf.ta.append("Nothing was find!");
	}
    }

    public void search(File f) {
	String s = "";
	File[] mas = f.listFiles();
	if (mas != null) {
	    for (File tmp : mas) {
		if (!tmp.isHidden() && SearchFile.isSearching) {
		    if (tmp.isFile()) {
			sf.inputCatalog.setText(f.getPath());
			if (register) {
			    s = tmp.getName().toLowerCase();
			}
			else {
			    s = tmp.getName();
			}
			if (s.matches(word)) {
			    sf.ta.append(tmp.getPath() + "\n");
			    FindNothing = false;
			}
		    }
		    else {
			search(tmp);
		    }
		}
	    }
	}
    }

}
