package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import sharedResources.GameType;

/**
 * Dialogue to allow user to select play again options
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class GamePlayAgain extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JButton okButton, btnChangeGame, cancelButton;
	private Integer indicator;
	private GameType gt;

	/**
	 * Create the dialog.
	 */
	public GamePlayAgain(GameType gt) {
		this.gt = gt;
	}

	/**
	 * Set-up the dialogue
	 */
	public void init() {
		this.indicator = new Integer(42);

		setTitle("Game Over!");
		setBounds(100, 100, 420, 100);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblDoYouWant = new JLabel("Do you want to play "
					+ gt.toString() + " again?");
			contentPanel.add(lblDoYouWant);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			okButton = new JButton("Yes");
			okButton.setActionCommand("Confirm");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);

			btnChangeGame = new JButton("Change Game");
			buttonPane.add(btnChangeGame);

			cancelButton = new JButton("Exit");
			cancelButton.setActionCommand("Exit");
			buttonPane.add(cancelButton);

			okButton.addActionListener(this);
			btnChangeGame.addActionListener(this);
			cancelButton.addActionListener(this);
		}

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(false);
	}

	/**
	 * This method is called when Game Play Again dialogue is to be shown
	 * 
	 * @return Indicator code for play again action
	 */
	public int action() {
		this.setVisible(true);

		while (true) {
			// continues until indicator set to value return value by action
			// performed
			synchronized (this.indicator) {
				if (this.indicator < 10) {
					break;
				}
			}
		}

		System.out.println("out of loop");
		this.dispose();

		return this.indicator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		synchronized (this.indicator) {
			// set return codes
			if (arg0.getSource().equals(this.okButton)) {
				this.indicator = new Integer(1);
			} else if (arg0.getSource().equals(this.btnChangeGame)) {
				this.indicator = new Integer(2);
			} else if (arg0.getSource().equals(this.cancelButton)) {
				this.indicator = new Integer(0);
			}
		}
	}

}