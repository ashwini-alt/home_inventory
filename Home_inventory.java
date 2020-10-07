package Home_inventory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.*;
import java.util.Date;

import com.toedter.calendar.*;
import java.beans.*;
import Home_inventory.PhotoPanel;
import Home_inventory.InventoryDocument;

import java.beans.*;
import Home_inventory.InventoryItem;
import java.io.*;

public class Home_inventory extends JFrame {
	JToolBar inventory_toolbar = new JToolBar();
	JButton new_button = new JButton(new ImageIcon("C:\\Users\\shankar\\eclipse-workspace\\Home_inventory\\src\\new2.png"));
	JButton delete_button = new JButton(new ImageIcon("C:\\Users\\shankar\\eclipse-workspace\\Home_inventory\\src\\delete2.png"));
	JButton save_button = new JButton(new ImageIcon("C:\\Users\\shankar\\eclipse-workspace\\Home_inventory\\src\\save2.png"));
	JButton previous_button = new JButton(new ImageIcon("C:\\Users\\shankar\\eclipse-workspace\\Home_inventory\\src\\previous2.png"));
	JButton next_button = new JButton(new ImageIcon("C:\\Users\\shankar\\eclipse-workspace\\Home_inventory\\src\\next2.png"));
	JButton print_button = new JButton(new ImageIcon("C:\\Users\\shankar\\eclipse-workspace\\Home_inventory\\src\\print2.png"));
	JButton exit_button = new JButton(new ImageIcon("C:\\Users\\shankar\\eclipse-workspace\\Home_inventory\\src\\exit.png"));
	JLabel itemLabel = new JLabel();
	JTextField itemTextField = new JTextField();
	JLabel locationLabel = new JLabel();
	JComboBox locationComboBox = new JComboBox();
	JCheckBox markedCheckBox = new JCheckBox();
	JLabel serialLabel = new JLabel();
	JTextField serialTextField = new JTextField();
	JLabel priceLabel = new JLabel();
	JTextField priceTextField = new JTextField();
	JLabel dateLabel = new JLabel();
	JDateChooser dateDateChooser = new JDateChooser();
	JLabel storeLabel = new JLabel();
	JTextField storeTextField = new JTextField();
	JLabel noteLabel = new JLabel();
	JTextField noteTextField = new JTextField();
	JLabel photoLabel = new JLabel();
	static JTextArea photoTextArea = new JTextArea();
	JButton photoButton = new JButton();
	JPanel searchPanel = new JPanel();
	JButton[] searchButton = new JButton[26];
	PhotoPanel photoPanel=new PhotoPanel();
	JFrame frame=new JFrame();
	
	static final int maximumEntries = 300;
	static int numberEntries;
	static InventoryItem[] myInventory = new InventoryItem[maximumEntries];
	int currentEntry;
	static final int entriesPerPage = 2;
	static int lastPage;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
     new Home_inventory().show();
	}
	
	
	Home_inventory(){
		
		
	setTitle("Home inventory Management");
	setResizable(true);
	addWindowListener(new WindowAdapter()
	{
	public void windowClosing(WindowEvent evt)
	{

		exitForm(evt);
	}

	private void exitForm(WindowEvent evt) {
		if (JOptionPane.showConfirmDialog(null, "Any unsaved changes will be lost.\nAre you sure you want to exit?", "Exit Program", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
				return;
				// write entries back to file
				try
				{
				PrintWriter outputFile = new PrintWriter(new BufferedWriter(new FileWriter("inventory.txt")));
				outputFile.println(numberEntries);
				if (numberEntries != 0)
				{
				for (int i = 0; i < numberEntries; i++)
				{
				outputFile.println(myInventory[i].description);
				outputFile.println(myInventory[i].location);
				outputFile.println(myInventory[i].serialNumber);
				outputFile.println(myInventory[i].marked);
				outputFile.println(myInventory[i].purchasePrice);
				outputFile.println(myInventory[i].purchaseDate);
				outputFile.println(myInventory[i].purchaseLocation);
				outputFile.println(myInventory[i].note);
				outputFile.println(myInventory[i].photoFile);
				}
				}
				// write combo box entries
				outputFile.println(locationComboBox.getItemCount());
				if (locationComboBox.getItemCount() != 0)
				{
				for (int i = 0; i < locationComboBox.getItemCount(); i++)
				outputFile.println(locationComboBox.getItemAt(i));
				}
				outputFile.close();
				}
			
				catch (Exception ex)
				{
				}
				System.exit(0);
				}

		
	
	});
	//pack();
	//Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
	//setBounds((int) (0.5 * (screenSize.width - getWidth())), (int) (0.5 * (screenSize.height- getHeight())), getWidth(), getHeight());
	setLayout(new FlowLayout());
	setSize(800,1000);
	getContentPane().setLayout(new GridBagLayout());
	GridBagConstraints gc;
	inventory_toolbar.setFloatable(false);
	inventory_toolbar.setBackground(Color.BLUE);
	inventory_toolbar.setOrientation(SwingConstants.VERTICAL);
	gc = new GridBagConstraints();
	gc.gridx = 0;
	gc.gridy = 0;
	gc.gridheight = 9;
	gc.fill = GridBagConstraints.VERTICAL;
	getContentPane().add(inventory_toolbar, gc);
	
	
	
	inventory_toolbar.addSeparator();
	
	Dimension bsize = new Dimension(60, 80);
	new_button.setText("New");
	sizeButton(new_button,bsize);
	new_button.setToolTipText("Add New Item");
	new_button.setHorizontalTextPosition(SwingConstants.CENTER);
	new_button.setVerticalTextPosition(SwingConstants.BOTTOM);
	inventory_toolbar.add(new_button);
	new_button.addActionListener(new ActionListener()
	{
	public void actionPerformed(ActionEvent e)
	{
	newButtonActionPerformed(e);
	}

	private void newButtonActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		checkSave();
		blankValues();
	}
	});
	sizeButton(delete_button,bsize);
	delete_button.setText("Delete");
	delete_button.setToolTipText("Delete Current item");
	delete_button.setHorizontalTextPosition(SwingConstants.CENTER);
	delete_button.setVerticalTextPosition(SwingConstants.BOTTOM);
	inventory_toolbar.add(delete_button);
	delete_button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			delete_button_actionperformed(e);
		}

		private void delete_button_actionperformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?","Delete Inventory Item", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
					return;
					deleteEntry(currentEntry);
					if (numberEntries == 0)
					{
					currentEntry = 0;
					blankValues();
					}
					else
					{
					currentEntry--;
					if (currentEntry == 0)
					currentEntry = 1;
					showEntry(currentEntry);
					}
		}
	});
	
	
	sizeButton(save_button,bsize);
	save_button.setText("Save");
	save_button.setToolTipText("Save Current item");
	save_button.setHorizontalTextPosition(SwingConstants.CENTER);
	save_button.setVerticalTextPosition(SwingConstants.BOTTOM);
	inventory_toolbar.add(save_button); 
	save_button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			save_button_actionperformed(e);
		}

		private void save_button_actionperformed(ActionEvent e) {
			// TODO Auto-generated method stub
			itemTextField.setText(itemTextField.getText().trim());
			if (itemTextField.getText().equals(""))
			{
			JOptionPane.showConfirmDialog(null, "Must have item description.", "Error",JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			itemTextField.requestFocus();
			return;
			}
			if (new_button.isEnabled())
			{
			// delete edit entry then resave
			deleteEntry(currentEntry);
			}
			// capitalize first letter
			String s = itemTextField.getText();
			itemTextField.setText(s.substring(0, 1).toUpperCase() + s.substring(1));
			numberEntries++;
			// determine new current entry location based on description
			currentEntry = 1;
			if (numberEntries != 1)
			{
			do
			{
			if
			(itemTextField.getText().compareTo(myInventory[currentEntry - 1].description) < 0)
			break;
			currentEntry++;
			}
			while (currentEntry < numberEntries);
			}
			// move all entries below new value down one position unless at end
			if (currentEntry != numberEntries)
			{
			for (int i = numberEntries; i >= currentEntry + 1; i--)
			{
			myInventory[i - 1] = myInventory[i - 2];
			
			myInventory[i - 2] = new InventoryItem();
			}
			}
			myInventory[currentEntry - 1] = new InventoryItem();
			myInventory[currentEntry - 1].description = itemTextField.getText();
			myInventory[currentEntry - 1].location =
			locationComboBox.getSelectedItem().toString();
			myInventory[currentEntry - 1].marked = markedCheckBox.isSelected();
			myInventory[currentEntry - 1].serialNumber = serialTextField.getText();
			myInventory[currentEntry - 1].purchasePrice = priceTextField.getText();
			myInventory[currentEntry - 1].purchaseDate =
			dateToString(dateDateChooser.getDate());
			myInventory[currentEntry - 1].purchaseLocation = storeTextField.getText();
			myInventory[currentEntry - 1].photoFile = photoTextArea.getText();
			myInventory[currentEntry - 1].note = noteTextField.getText();
			showEntry(currentEntry);
			if (numberEntries < maximumEntries)
			new_button.setEnabled(true);
			else
			new_button.setEnabled(false);
			delete_button.setEnabled(true);
			print_button.setEnabled(true);
		}
	});
	inventory_toolbar.addSeparator();
	sizeButton(previous_button,bsize);
	previous_button.setText("Previous");
	previous_button.setToolTipText("Display previous item");
	previous_button.setHorizontalTextPosition(SwingConstants.CENTER);
	previous_button.setVerticalTextPosition(SwingConstants.BOTTOM);
	inventory_toolbar.add(previous_button); 
	previous_button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			previous_button_actionperformed(e);
		}

		private void previous_button_actionperformed(ActionEvent e) {
			// TODO Auto-generated method stub
			checkSave();
			currentEntry--;
			showEntry(currentEntry);
		}
	});
	next_button.setText("Next");
	sizeButton(next_button,bsize);
	next_button.setToolTipText("Display Next Item");
	next_button.setHorizontalTextPosition(SwingConstants.CENTER);
	next_button.setVerticalTextPosition(SwingConstants.BOTTOM);
	inventory_toolbar.add(next_button);
	next_button.addActionListener(new ActionListener()
	{
	public void actionPerformed(ActionEvent e)	
	{
		nextButtonActionPerformed(e);
		}

	private void nextButtonActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		checkSave();
		currentEntry++;
		showEntry(currentEntry);
	}
		});
		inventory_toolbar.addSeparator();
		print_button.setText("Print");
		sizeButton(print_button,bsize);
		print_button.setToolTipText("Print Inventory List");
		print_button.setHorizontalTextPosition(SwingConstants.CENTER);
		print_button.setVerticalTextPosition(SwingConstants.BOTTOM);
		inventory_toolbar.add(print_button);
		print_button.addActionListener(new ActionListener()
		{
		public void actionPerformed(ActionEvent e)
		{
		printButtonActionPerformed(e);
		}

		private void printButtonActionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			lastPage = (int) (1 + (numberEntries - 1) / entriesPerPage);
			PrinterJob inventoryPrinterJob = PrinterJob.getPrinterJob();
			inventoryPrinterJob.setPrintable(new InventoryDocument());
			if (inventoryPrinterJob.printDialog())
			{
			try
			{
			inventoryPrinterJob.print();
			}
			catch (PrinterException ex)
			{
			JOptionPane.showConfirmDialog(null, ex.getMessage(), "Print Error",JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}
			}

		}
		});
		exit_button.setText("Exit");
		sizeButton(exit_button,bsize);
		exit_button.setHorizontalTextPosition(SwingConstants.CENTER);
		exit_button.setVerticalTextPosition(SwingConstants.BOTTOM);
		exit_button.setToolTipText("Exit Program");
		inventory_toolbar.add(exit_button);
		exit_button.addActionListener(new ActionListener()
		{
		public void actionPerformed(ActionEvent e)
		{
		exitButtonActionPerformed(e);
		}

		private void exitButtonActionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);

		}
		});
		itemLabel.setText("Inventory Item");
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		gc.insets = new Insets(10, 10, 0, 10);
		gc.anchor = GridBagConstraints.EAST;
		getContentPane().add(itemLabel, gc);
		
		itemTextField.setPreferredSize(new Dimension(400, 25));
		gc = new GridBagConstraints();
		gc.gridx = 2;
		gc.gridy = 0;
		gc.gridwidth = 5;
		gc.insets = new Insets(10, 0, 0, 10);
		gc.anchor = GridBagConstraints.WEST;
		getContentPane().add(itemTextField, gc);
		itemTextField.addActionListener(new ActionListener ()
		{
		public void actionPerformed(ActionEvent e)
		{
		itemTextFieldActionPerformed(e);
		}

		private void itemTextFieldActionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			locationComboBox.requestFocus();
		}
		});
		
		locationLabel.setText("Location");
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 1;
		gc.insets = new Insets(10, 10, 0, 10);
		gc.anchor = GridBagConstraints.EAST;
		getContentPane().add(locationLabel, gc);
		
		
		locationComboBox.setPreferredSize(new Dimension(270, 25));
		locationComboBox.setFont(new Font("Arial", Font.PLAIN, 12));	
		locationComboBox.setEditable(true);
		locationComboBox.setBackground(Color.WHITE);
		gc = new GridBagConstraints();
		gc.gridx = 2;
		gc.gridy = 1;
		gc.gridwidth = 3;
		gc.insets = new Insets(10, 0, 0, 10);
		gc.anchor = GridBagConstraints.WEST;
		getContentPane().add(locationComboBox, gc);
		locationComboBox.addActionListener(new ActionListener ()
		{
		public void actionPerformed(ActionEvent e)
		{
		locationComboBoxActionPerformed(e);
		}

		private void locationComboBoxActionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (locationComboBox.getItemCount() != 0)
			{
			for (int i = 0; i < locationComboBox.getItemCount(); i++)
			{
			if (locationComboBox.getSelectedItem().toString().equals(locationComboBox.getItemAt(i).toString()))
			{
				serialTextField.requestFocus();
				return;
				}
				}
				}
				// If not found, add to list box
				locationComboBox.addItem(locationComboBox.getSelectedItem());
				serialTextField.requestFocus();
		}
		});

		
		markedCheckBox.setText("Marked?");
		gc = new GridBagConstraints();
		gc.gridx = 5;
		gc.gridy = 1;
		gc.insets = new Insets(10, 10, 0, 0);
		gc.anchor = GridBagConstraints.WEST;
		getContentPane().add(markedCheckBox, gc);
		
		serialLabel.setText("Serial Number");
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 2;
		gc.insets = new Insets(10, 10, 0, 10);
		gc.anchor = GridBagConstraints.EAST;
		getContentPane().add(serialLabel, gc);
		
		serialTextField.setPreferredSize(new Dimension(270, 25));
		gc = new GridBagConstraints();
		gc.gridx = 2;
		gc.gridy = 2;
		gc.gridwidth = 3;
		gc.insets = new Insets(10, 0, 0, 10);
		gc.anchor = GridBagConstraints.WEST;
		getContentPane().add(serialTextField, gc);
		serialTextField.addActionListener(new ActionListener ()
		{
		public void actionPerformed(ActionEvent e)
		{
		serialTextFieldActionPerformed(e);
		}

		private void serialTextFieldActionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			priceTextField.requestFocus();
		}
		});
		
		priceLabel.setText("Purchase Price");
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 3;
		gc.insets = new Insets(10, 10, 0, 10);
		gc.anchor = GridBagConstraints.EAST;
		getContentPane().add(priceLabel, gc);
		
		priceTextField.setPreferredSize(new Dimension(160, 25));
		gc = new GridBagConstraints();
		gc.gridx = 2;
		gc.gridy = 3;
		gc.gridwidth = 2;
		 gc.insets = new Insets(10, 0, 0, 10);
		gc.anchor = GridBagConstraints.WEST;
		getContentPane().add(priceTextField, gc);
		priceTextField.addActionListener(new ActionListener ()
		{
		public void actionPerformed(ActionEvent e)
		{
		priceTextFieldActionPerformed(e);
		}

		private void priceTextFieldActionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			dateDateChooser.requestFocus();
		}
		});
		dateLabel.setText("Date Purchased");
		gc = new GridBagConstraints();
		gc.gridx = 4;
		gc.gridy = 3;
		gc.insets = new Insets(10, 10, 0, 0);
		gc.anchor = GridBagConstraints.WEST;
		getContentPane().add(dateLabel, gc);
		
		dateDateChooser.setPreferredSize(new Dimension(120, 25));
		gc = new GridBagConstraints();
		gc.gridx = 5;
		gc.gridy = 3;
		gc.gridwidth = 2;
		gc.insets = new Insets(10, 0, 0, 10);
		gc.anchor = GridBagConstraints.WEST;
		getContentPane().add(dateDateChooser, gc);
		dateDateChooser.addPropertyChangeListener(new PropertyChangeListener()
		{
		public void propertyChange(PropertyChangeEvent e)
		{
		dateDateChooserPropertyChange(e);
		}

		private void dateDateChooserPropertyChange(PropertyChangeEvent e) {
			// TODO Auto-generated method stub
			storeTextField.requestFocus();
		}
		});
		
		storeLabel.setText("Store/Website");
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 4;
		gc.insets = new Insets(10, 10, 0, 10);
		gc.anchor = GridBagConstraints.EAST;
		getContentPane().add(storeLabel, gc);
		
		storeTextField.setPreferredSize(new Dimension(400, 25));
		gc = new GridBagConstraints();
		gc.gridx = 2;
		gc.gridy = 4;
		gc.gridwidth = 5;
		gc.insets = new Insets(10, 0, 0, 10);
		gc.anchor = GridBagConstraints.WEST;
		getContentPane().add(storeTextField, gc);
		storeTextField.addActionListener(new ActionListener ()
		{
		public void actionPerformed(ActionEvent e)
		{
		storeTextFieldActionPerformed(e);
		}

		private void storeTextFieldActionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			noteTextField.requestFocus();
		}
		});
		
		noteLabel.setText("Note");
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 5;
		gc.insets = new Insets(10, 10, 0, 10);
		gc.anchor = GridBagConstraints.EAST;
		getContentPane().add(noteLabel, gc);
		
		noteTextField.setPreferredSize(new Dimension(400, 25));
		gc = new GridBagConstraints();
		gc.gridx = 2;
		gc.gridy = 5;
		gc.gridwidth = 5;
		gc.insets = new Insets(10, 0, 0, 10);
		gc.anchor = GridBagConstraints.WEST;
		getContentPane().add(noteTextField, gc);
		noteTextField.addActionListener(new ActionListener ()
		{
		public void actionPerformed(ActionEvent e)
		{
		noteTextFieldActionPerformed(e);
		}

		private void noteTextFieldActionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			photoButton.requestFocus();
		}
		});
		
		photoLabel.setText("Photo");
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 6;
		gc.insets = new Insets(10, 10, 0, 10);
		gc.anchor = GridBagConstraints.EAST;
		getContentPane().add(photoLabel, gc);
		
		photoTextArea.setPreferredSize(new Dimension(350, 35));
		photoTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
		photoTextArea.setEditable(false);
		photoTextArea.setLineWrap(true);
		photoTextArea.setWrapStyleWord(true);
		photoTextArea.setBackground(new Color(255, 255, 192));
		photoTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		gc = new GridBagConstraints();
		gc.gridx = 2;
		gc.gridy = 6;
		gc.gridwidth = 4;
		gc.insets = new Insets(10, 0, 0, 10);
		gc.anchor = GridBagConstraints.WEST;
		getContentPane().add(photoTextArea,gc);
		
		photoButton.setText("...");
		gc = new GridBagConstraints();
		gc.gridx = 6;
		gc.gridy = 6;
		gc.insets = new Insets(10, 0, 0, 10);
		gc.anchor = GridBagConstraints.WEST;
		getContentPane().add(photoButton, gc);
		photoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				photoButtonActionperformed(e);
			}

			private void photoButtonActionperformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser openChooser = new JFileChooser();
				openChooser.setDialogType(JFileChooser.OPEN_DIALOG);
				openChooser.setDialogTitle("Open Photo File");
			 openChooser.addChoosableFileFilter(new FileNameExtensionFilter("new2.jpg"));
				int status=openChooser.showOpenDialog(frame);
				if (status == JFileChooser.APPROVE_OPTION)
				showPhoto(openChooser.getSelectedFile().toString());
			}
		});
		
		searchPanel.setPreferredSize(new Dimension(240, 160));
		searchPanel.setBorder(BorderFactory.createTitledBorder("Item Search"));
		searchPanel.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 7;
		gc.gridwidth = 3;
		gc.insets = new Insets(10, 0, 10, 0);
		gc.anchor = GridBagConstraints.CENTER;
		getContentPane().add(searchPanel, gc);
		
		int x=0,y=0;
		for(int i=0;i<26;i++) {
			searchButton[i]=new JButton();
			searchButton[i].setText(String.valueOf((char) (65 + i)));
			searchButton[i].setFont(new Font("Arial",Font.BOLD,12));
            searchButton[i].setMargin(new Insets(-10,-10,-10,-10));
            sizeButton(searchButton[i],new Dimension(37,27));
            searchButton[i].setBackground(Color.cyan);
            gc = new GridBagConstraints();
            gc.gridx = x;
            gc.gridy = y;
            searchPanel.add(searchButton[i], gc);
            
            searchButton[i].addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent a) {
            		searchButtonactionperformed(a);
            	}

				private void searchButtonactionperformed(ActionEvent a) {
					// TODO Auto-generated method stub
					int i;
					if (numberEntries == 0)
					return;
					// search for item letter
					String letterClicked = a.getActionCommand();
					i = 0;
					do
					{
					if (myInventory[i].description.substring(0, 1).equals(letterClicked))
					{
					currentEntry = i + 1;
					showEntry(currentEntry);
					return;
					}
					i++;
					}
					while (i < numberEntries);
					JOptionPane.showConfirmDialog(null, "No " + letterClicked + " inventory items.",
					"None Found", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE);

				}
            });
            x++;
           if(x%6==0) {
        	   x=0;
        	   y++;
           }
		}
		photoPanel.setPreferredSize(new Dimension(240,160));
		gc=new GridBagConstraints();
		gc.gridx = 4;
		gc.gridy = 7;
		gc.gridwidth = 3;
		gc.insets = new Insets(10, 0, 10, 10);
		gc.anchor = GridBagConstraints.CENTER;
		getContentPane().add(photoPanel, gc);
		
		InventoryItem myItem;
		myItem = new InventoryItem();
		myItem.description = "This is my inventory item";
		
		int n;
		// open file for entries try
		try {
		BufferedReader inputFile = new BufferedReader(new FileReader("inventory.txt"));
		numberEntries =
		Integer.valueOf(inputFile.readLine()).intValue();
		if (numberEntries != 0)
		{
		for (int i = 0; i < numberEntries; i++)
		{
		myInventory[i] = new InventoryItem();
		myInventory[i].description = inputFile.readLine();
		myInventory[i].location = inputFile.readLine();
		myInventory[i].serialNumber = inputFile.readLine();
		myInventory[i].marked =
		Boolean.valueOf(inputFile.readLine()).booleanValue();
		myInventory[i].purchasePrice = inputFile.readLine();
		myInventory[i].purchaseDate = inputFile.readLine();
		myInventory[i].purchaseLocation = inputFile.readLine();
		myInventory[i].note = inputFile.readLine();
		myInventory[i].photoFile = inputFile.readLine();
		}
		}
		// read in combo box elements
		n = Integer.valueOf(inputFile.readLine()).intValue();
		if (n != 0)
		{
		for (int i = 0; i < n; i++)
		{
		locationComboBox.addItem(inputFile.readLine());
		}
		}
		inputFile.close();
		}
		catch (Exception ex)
		{
		numberEntries = 0;
		}
		if (numberEntries == 0)
		{
		new_button.setEnabled(false);
		delete_button.setEnabled(false);
		next_button.setEnabled(false);
		previous_button.setEnabled(false);
		print_button.setEnabled(false);
		}
	
}
	
private void sizeButton(JButton b, Dimension d) {
	
			b.setPreferredSize(d);
			b.setMinimumSize(d);
			b.setMaximumSize(d);
	
	}
private void showEntry(int j) {
	itemTextField.setText(myInventory[j - 1].description);
	locationComboBox.setSelectedItem(myInventory[j - 1].location);
	markedCheckBox.setSelected(myInventory[j - 1].marked);
	serialTextField.setText(myInventory[j - 1].serialNumber);
	priceTextField.setText(myInventory[j - 1].purchasePrice);
	dateDateChooser.setDate(stringToDate(myInventory[j - 1].purchaseDate));
	storeTextField.setText(myInventory[j - 1].purchaseLocation);
	noteTextField.setText(myInventory[j - 1].note);
	showPhoto(myInventory[j - 1].photoFile);
	next_button.setEnabled(true);
	previous_button.setEnabled(true);
	if (j == 1)
	previous_button.setEnabled(false);
	if (j == numberEntries)
	next_button.setEnabled(false);
	itemTextField.requestFocus();

}

private Date stringToDate(String s)
{
int m = Integer.valueOf(s.substring(0, 2)).intValue() - 1;
int d = Integer.valueOf(s.substring(3, 5)).intValue();
int y = Integer.valueOf(s.substring(6)).intValue() - 1900;
return(new Date(y, m, d));
}
private String dateToString(Date dd){
	String yString = String.valueOf(dd.getYear() + 1900);
	int m = dd.getMonth() + 1;
	String mString = new DecimalFormat("00").format(m);
	int d = dd.getDate();
	String dString = new DecimalFormat("00").format(d);
	return(mString + "/" + dString + "/" + yString);
	}
private void showPhoto(String photoFile)
{
if (!photoFile.equals(""))
{
try
{
photoTextArea.setText(photoFile);
}
catch (Exception ex)
{
photoTextArea.setText("");
}
}
else
{
photoTextArea.setText("");
}
photoPanel.repaint();
}

private void blankValues()
{
// blank input screen
new_button.setEnabled(false);
delete_button.setEnabled(false);
save_button.setEnabled(true);
previous_button.setEnabled(false);
next_button.setEnabled(false);
print_button.setEnabled(false);
itemTextField.setText("");
locationComboBox.setSelectedItem("");
markedCheckBox.setSelected(false);
serialTextField.setText("");
priceTextField.setText("");
dateDateChooser.setDate(new Date());
storeTextField.setText("");
noteTextField.setText("");
photoTextArea.setText("");
photoPanel.repaint();
itemTextField.requestFocus();
}
private void deleteEntry(int j)
{
// delete entry j
if (j != numberEntries)
{
// move all entries under j up one level
for (int i = j; i < numberEntries; i++)
{
myInventory[i - 1] = new InventoryItem();
myInventory[i - 1] = myInventory[i];
}
}
numberEntries--;
}
private void checkSave()
{
boolean edited = false;
if (!myInventory[currentEntry - 1].description.equals(itemTextField.getText()))
edited = true;
else if (!myInventory[currentEntry -1].location.equals(locationComboBox.getSelectedItem().toString()))
	edited = true;
	else if (myInventory[currentEntry - 1].marked != markedCheckBox.isSelected())
	edited = true;
	else if (!myInventory[currentEntry - 1].serialNumber.equals(serialTextField.getText()))
	edited = true;
	else if (!myInventory[currentEntry - 1].purchasePrice.equals(priceTextField.getText()))
	edited = true;
	else if (!myInventory[currentEntry -1].purchaseDate.equals(dateToString(dateDateChooser.getDate())))
	edited = true;
	else if (!myInventory[currentEntry -1].purchaseLocation.equals(storeTextField.getText()))
	edited = true;
	else if (!myInventory[currentEntry - 1].note.equals(noteTextField.getText()))
	edited = true;
	else if (!myInventory[currentEntry - 1].photoFile.equals(photoTextArea.getText()))
	edited = true;
	if (edited)
	{
	if (JOptionPane.showConfirmDialog(null, "You have edited this item. Do you want tosave the changes?", "Save Item", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
	save_button.doClick();
	}
	}

}


