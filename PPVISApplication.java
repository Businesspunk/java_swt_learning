import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class PPVISApplication {

	protected Shell shell;
	private Text text;
	private Combo combo;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PPVISApplication window = new PPVISApplication();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	protected void showErrorMessage()
	{
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.CANCEL );
        
        messageBox.setText("Error");
        messageBox.setMessage("Can't add value to ComboBox");
        messageBox.open();
	}
	
	protected void addToCombo( String val ) {
		if(combo.indexOf(val) == -1) {
			combo.add(val, 0);
		}else {
			showErrorMessage();
		}
		
	}
	
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("PPVIS - 1. First Part");
		
		combo = new Combo(shell, SWT.NONE);
		combo.setBounds(120, 44, 196, 38);
		
			
		text = new Text(shell, SWT.BORDER);
		text.setBounds(120, 125, 196, 29);
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				addToCombo( text.getText() );
			}
		});
		btnNewButton.setText("Добавить");
		btnNewButton.setBounds(181, 184, 93, 29);
	}
}
