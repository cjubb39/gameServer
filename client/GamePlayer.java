package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

/**
 * Frame allows user to interact with server to play selected game
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class GamePlayer extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JPanel sendPanel;
	private JScrollPane responsePanel;
	private JTextArea txtServerOutput;
	private JTextField txtResponseArea;
	private JButton btnSubmit;
	private PrintWriter output;

	/**
	 * Create the frame.
	 */
	public GamePlayer(PrintWriter out) {
		this.output = out;

		setTitle("Chae's Amazing Game Server!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(this.contentPane);

		this.txtServerOutput = new JTextArea();
		this.txtServerOutput.setLineWrap(true);
		this.txtServerOutput.setEditable(false);

		this.responsePanel = new JScrollPane(this.txtServerOutput,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		responsePanel.setAutoscrolls(true);

		// make sure scrollbar auto scrolls
		DefaultCaret caret = (DefaultCaret) this.txtServerOutput.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		this.contentPane.add(responsePanel, BorderLayout.CENTER);

		this.sendPanel = new JPanel();
		this.contentPane.add(this.sendPanel, BorderLayout.SOUTH);
		this.sendPanel.setLayout(new BorderLayout(0, 0));

		this.txtResponseArea = new JTextField();
		this.sendPanel.add(this.txtResponseArea);
		this.txtResponseArea.setColumns(10);

		this.btnSubmit = new JButton("Submit");
		this.btnSubmit.addActionListener(this);
		this.sendPanel.add(btnSubmit, BorderLayout.EAST);

		this.setVisible(true);

		// submit on enter key press
		this.getRootPane().setDefaultButton(btnSubmit);
		this.txtResponseArea.setCaretPosition(0);
	}

	/**
	 * Simple accessor for JTextArea where server messages printed
	 * 
	 * @return JTextArea where server messages are printed
	 */
	public JTextArea getServerResponseArea() {
		return this.txtServerOutput;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// on button press, send text in responseArea and clear that area
		if (arg0.getSource().equals(this.btnSubmit)) {
			this.output.println(this.txtResponseArea.getText());
			this.txtResponseArea.setText("");
		}
	}

}