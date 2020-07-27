package jep;

import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Class AnswerPanel the panel that displays the answer to a given question
 * object
 *
 * @author Phoenix
 * @version 3/2/2020
 */
public class AnswerPanel extends QAPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1741857724532193636L;

	/**
	 * Constructor for objects of AnswerPanel, initializes everything
	 */
	public AnswerPanel() {
		super();
		backButton.addActionListener(new Listener("QuestionListPanel"));
	}

	@Override
	public void setQuestion(Question q) {
		super.setQuestion(q);
		editArea.setText(q.getAnswer());
		content.setText(q.getAnswer());
	}

	@Override
	public void setEdit(boolean e) {
		super.setEdit(e);
		if (edit) {
			GridBagConstraints c = new GridBagConstraints();
			JButton back = new JButton("Save & Back to Question");
			back.addActionListener(new Listener("QuestionPanel"));
			setUpSaveButton(back);

			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 3;
			c.weightx = 1.0;
			c.weighty = 1.0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.LINE_END;
			c.insets = new Insets(0, 30, 0, 30);
			add(back, c);
		}
	}

	public void changeAnswer() {
		if (edit) {
			doEdit();
		}
	}

}
