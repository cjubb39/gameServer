package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import sharedResources.GameType;
import sharedResources.GameTypeWrap;
import javax.swing.JLabel;

/**
 * Allows user to select game to be played from GameType enum
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class GameChooser extends JDialog implements ActionListener {

	private JPanel contentPanel;
	private JButton okButton, cancelButton;
	private JComboBox comboBox;
	private GameTypeWrap choice;
	private Object indicator;
	private JLabel lblSelectFromThe;

	/**
	 * Create the GameChooser dialog.
	 */
	public GameChooser(GameTypeWrap choice, Object indicator) {
		this.contentPanel = new JPanel();
		this.choice = choice;
		this.indicator = indicator;

		setTitle("Choose a Game!");
		setBounds(100, 100, 420, 100);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			lblSelectFromThe = new JLabel("Select:");
			contentPanel.add(lblSelectFromThe);
		}
		{
			comboBox = new JComboBox();
			comboBox.setModel(new DefaultComboBoxModel(GameType.values()));
			contentPanel.add(comboBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				this.okButton = new JButton("OK");
				this.okButton.addActionListener(this);
				buttonPane.add(this.okButton);
				getRootPane().setDefaultButton(this.okButton);
			}
			{
				this.cancelButton = new JButton("Cancel");
				this.cancelButton.addActionListener(this);
				buttonPane.add(this.cancelButton);
			}
		}

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource().equals(this.okButton)) {
			// choice GameTypeWrap sent to value of comboBox on "ok"
			this.choice.setValue((GameType) this.comboBox.getSelectedItem());

		} else if (arg0.getSource().equals(this.cancelButton)) {
			System.exit(0);
		}

		// lets indicator object know game has been selected
		synchronized (this.indicator) {
			this.indicator.notifyAll();
		}

		this.setVisible(false);

	}

}