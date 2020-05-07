import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

public class PPVISecondApplication {

	protected Shell shell;
	protected Display display;
	protected Shell[] shells;
	protected int state = 0;
	protected boolean isActive = true;
	protected Listener listenerStop;
	protected Listener listenerStart;

	public static void main(String[] args) {
		try {
			PPVISecondApplication window = new PPVISecondApplication();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void boot()
	{
		listenerStop = new Listener() {
	        public void handleEvent(Event e) {
	        	if((e.stateMask == SWT.COMMAND) && (e.keyCode == 115))
	            {
	        		System.out.println("Stop");
	        		isActive = false;
	            }
	        }
	    };
	    
	    listenerStart = new Listener() {
            public void handleEvent(Event e) {
            		
            	if((e.stateMask == SWT.COMMAND) && (e.keyCode == 114))
                {
            		isActive = true;
            		System.out.println("Start");
            		startAnimate();
                }
            }
        };
	   
	}
	public void open() {
		boot();
		
		display = Display.getDefault();
		
	
		display.addFilter(SWT.KeyDown, listenerStart);	
		display.addFilter(SWT.KeyDown, listenerStop);
		
		createContents();
		shell.open();
		shell.layout();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents() {
		shell = new Shell();
		
		shell.setSize(950, 600);
		shell.setText("PPVIS Second Application");
		
		for( int i = 1; i < 6; i++ ) {
			Button button = new Button(shell, SWT.NONE);
			button.setBounds(200, 50*i, 90, 30);
			button.setText("Button - " + i);
		}
		
		shells = new Shell[5];
		
		for(int i = 0; i < 5; i++) {
			shells[i] = new Shell();
			shells[i].setSize(300,300);
			shells[i].setText("Shell - " + i);
			Button button = new Button(shells[i], SWT.NONE);
			button.setBounds(100, 150, 90, 30);
			button.setText("Button");
		}
	}
	
	protected void showMiniShells()
	{
		for(int i = 0; i < 5; i++) {
			shells[i].open();
		}
	}
	
	protected void hideMiniShells() {
		for(int i = 0; i < 5; i++) {
			shells[i].setVisible(false);
		}
	}
	
	protected void hideMainShell()
	{
		shell.setVisible(false);
	}
	
	protected void showMainShell()
	{
		shell.setVisible(true);
	}
	
	protected void setStartPostitionForMini()
	{
		shells[0].setLocation(400,200);
		shells[1].setLocation(800,200);
		shells[2].setLocation(1200,200);
		shells[3].setLocation(500,600);
		shells[4].setLocation(1000,600);
	}
	
	protected void swap( Shell first, Shell second )
	{
		int oldX = first.getLocation().x, oldY = first.getLocation().y;
		first.setLocation( second.getLocation().x, second.getLocation().y );
		second.setLocation(oldX, oldY);
	}
	
	protected void swapShells( int firstIndex, int secondIndex )
	{
		Shell first = shells[firstIndex];
		swap( shells[firstIndex], shells[secondIndex] );
		shells[firstIndex] = shells[secondIndex];
		shells[secondIndex] = first;
	}
	
	protected void startAnimate() {
		display.timerExec(1000, new Runnable() {
    		public void run() {
    			System.out.println("Sheduler 1000");
    			
    			if(isActive) {
    				
    				switch ( state ) {
    					case 0:
    					case 1:
    						hideMainShell();
    						showMiniShells();
    						setStartPostitionForMini();
    						state = 2;
    						break;
    					case 2: // 1-2
    						swapShells(0, 1);
    						state = 3;
    						break;
    					case 3: // 2-3
    						swapShells(1, 2);
    						state = 4;
    						break;
    					case 4: // 3-4
    						swapShells(2, 3);
    						state = 5;
    						break;
    					case 5: // 4-5
    						swapShells(3, 4);
    						state = 6;
    						break;
    					case 6: // 5-1
    						swapShells(4, 0);
    						state = 7;
    						break;
    					case 7:
    						hideMiniShells();
    						showMainShell();
    						state = 0;
    						break;
    				}
    				
    				
        			display.timerExec(1000, this);
    			}
    		}
    	});
	}
}
