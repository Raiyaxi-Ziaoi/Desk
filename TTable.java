import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TTable {
    private String horzSep, verticalSep, joinSep; // Seperators for table
    private String[] headers; // Headers for table
    
    private boolean rightAlign;
    
    private List<String[]> rows = new ArrayList<>();

    public TTable(String hSep, String vSep, String jSep, boolean showVert, boolean setRAli, String template) { // Constructor for table
    	setShowVerticalLines(showVert, vSep, jSep); // Sets vertical lines
    	setHorizontalSeperator(hSep); // Sets horizontal lines
    	setRightAlign(setRAli); // Sets right align
    	switch (template.toLowerCase()) { // Template
    		case "time":
    	        setHeaders("", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"); 
    	        addRow("09:00", "0", "1", "2", "3", "4", "5", "6");
    	        addRow("15:00", "0", "1", "2", "3", "4", "5", "6");
    	        addRow("19:00", "0", "1", "2", "3", "4", "5", "6");
    	        addRow("21:00", "0", "1", "2", "3", "4", "5", "6");
    			break;
    		case "list":
    	        addRow("1)", "Study", "Code", "Sleep");
    	        addRow("2)", "Write", "Read", "Sleep");
    	        addRow("3)", "Shop", "Go Out", "Sleep");
    	        addRow("4)", "Free", "Free", "Sleep");
    			break;
    		case "data":
    	        addRow("32", "D9", "5B", "1A");
    	        addRow("12", "42", "9D", "1B");
    	        addRow("3E", "DF", "5B", "D8");
    	        addRow("1A", "96", "9D", "C9");
    	        addRow("E4", "3F", "DE", "FE");
    	        addRow("A5", "62", "15", "FF");
    			break;
            default:
                break;
    	}
    }
    
    public TTable(String hSep, String vSep, String jSep, boolean showVert, boolean setRAli) {  // Templateless constructor
    	setShowVerticalLines(showVert, vSep, jSep);
    	setHorizontalSeperator(hSep);
    	setRightAlign(setRAli);
    }
    
    public TTable() { // General table
    	setShowVerticalLines(true, "|", "+");
    	setHorizontalSeperator("-");
    	setRightAlign(false);

    	setHeaders("", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"); 
    	addRow("09:00", "0", "1", "2", "3", "4", "5", "6");
    	addRow("15:00", "0", "1", "2", "3", "4", "5", "6");
    	addRow("19:00", "0", "1", "2", "3", "4", "5", "6");
    	addRow("21:00", "0", "1", "2", "3", "4", "5", "6");
    }
    
    public void setHeaders(String... headers) { // Set headers for table (If empty it will not show any header)
    	this.headers = headers; 
    }

    public void addRow(String... cells) {  // Adds a row to the table
    	rows.add(cells); 
    }
    
    public void print() { // Prints out table
        int[] maxWidths = headers != null ? Arrays.stream(headers).mapToInt(String::length).toArray() : null; // Sets header

        for (String[] cells : rows) { // Loops though cells
            if (maxWidths == null) maxWidths = new int[cells.length]; // Checks if headers and rows are the same size
            if (cells.length != maxWidths.length) throw new IllegalArgumentException("Error: Number of cells and headers must be the same.");
            for (int i = 0; i < cells.length; i++) maxWidths[i] = Math.max(maxWidths[i], cells[i].length());
        }

        if (headers != null) { // If there is a header
            printLine(maxWidths);
            printRow(headers, maxWidths);
            printLine(maxWidths);
        }
        for (String[] cells : rows) printRow(cells, maxWidths); // Prints all rows
        if (headers != null) printLine(maxWidths); // Prints end-cap
    }
    
    public void insert(int x, int y, String data) { // Insert data on rowX and rowY
    	rows.get(x)[y] = data;
    }
    
    public void insert(int x, String data) { // Insert data on headers
    	headers[x] = data;
    }
    
    
    // Private methods

    private void setRightAlign(boolean rightAlign) { // Sets right-align
    	this.rightAlign = rightAlign; 
    }
    
    private void setHorizontalSeperator(String sep) { // Sets horizontal seperator
    	horzSep = sep; 
    }

    private void setShowVerticalLines(boolean showVerticalLines, String vSep, String jSep) { // Show vertical lines?
    	verticalSep = showVerticalLines ? vSep : "";
    	joinSep = showVerticalLines ? jSep : " ";
    }

    private void printLine(int[] columnWidths) { // Prints a line
        for (int i = 0; i < columnWidths.length; i++) {
            String line = String.join("", Collections.nCopies(columnWidths[i] + verticalSep.length() + 1, horzSep)); // Formats row on table
            System.out.print(joinSep + line + (i == columnWidths.length - 1 ? joinSep : "")); // Prints out row
        }
        System.out.println();
    }

    private void printRow(String[] cells, int[] maxWidths) { // Prints a row
        for (int i = 0; i < cells.length; i++) { // Loops though cells in the row
            String s = cells[i];
            String verStrTemp = i == cells.length - 1 ? verticalSep : "";
            if (rightAlign) { // Formatted print based on right align
                System.out.printf("%s %" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
            } else {
                System.out.printf("%s %-" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
            }
        }
        System.out.println(); // New-line
    }
}