package jep;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * Write a description of class QuestionPanel here.
 *
 * @author Phoenix Changkachith
 * @version 3/2/2020
 * 
 */
public class QuestionPanel extends QAPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7476901316775349781L;
	/**
	 * JButton[] pointButtons an array of JButtons that allow the user to choose
	 * which team gets points
	 */
	private JButton[] pointButtons;
	/**
	 * boolean addedButton the boolean that is used to control the number of buttons
	 * that appear on the screen when the edit function is enabled
	 */
	private boolean addedButton;
	/**
	 * String[] order the array of strings that are appended to the buttons and are
	 * used as a makeshift-queue
	 */
	private final String[] order = new String[] { "First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh",
			"Eighth", "Ninth", "Tenth" };
	/**
	 * int orderIndex the int storing the index of the appended queue from order
	 */
	private int orderIndex;
	private JButton goToAnswer;

	/**
	 * Constructor for objects of class QuestionPanel
	 */
	public QuestionPanel() {
		super();
		backButton.addActionListener(new Listener("QuestionListPanel"));
		orderIndex = 0;
		addedButton = false;
		pointButtons = new JButton[numTeams];
		for (int i = 0; i < numTeams; i++) {
			pointButtons[i] = new JButton("Team " + (i + 1));
			pointButtons[i].addActionListener(new PointsListener(i + 1));
			pointButtons[i].setFont(new Font("New Times Roman", Font.BOLD, 20));
			pointButtons[i].setForeground(questionColor);
			pointButtons[i].setBackground(buttonBackColor);
			pointButtons[i].setMargin(new Insets(0, 10, 0, 10));
			pointButtons[i].setOpaque(true);
			footerHolder.add(pointButtons[i]);
		}

	}

	@Override
	public void setQuestion(Question q) {
		super.setQuestion(q);
		editArea.setText(q.getQuestion());
		content.setText(q.getQuestion());
	}
	@Override
	public void doEdit()
	{
		super.doEdit();
		currQues.setQuestion(editArea.getText());
	}
	/**
	 * Class PointsListener the listener that controls all functions related to
	 * giving and taking points
	 * 
	 * @author Phoenix
	 * @version 3/2/2020
	 */
	private class PointsListener implements ActionListener {
		/**
		 * int index the index of the team that this listener points to
		 */
		int index;
		/**
		 * boolean queued the boolean that controls whether or not a team has been
		 * queued to answer the question next
		 */
		boolean queued;

		/**
		 * PointsListener Constructor creates a points listener with basically default
		 * values
		 *
		 * @param i index of the team
		 */
		public PointsListener(int i) {
			index = i;
			queued = false;
		}

		/**
		 * Method setQueued changes the queue status
		 *
		 * @param q whether or not a team is queued
		 */
		public void setQueued(boolean q) {
			queued = q;
		}

		public void handleQueuedQuestion() {
			int points = 0;
			String input = null;
			boolean valueFound = false;
			do {
				try {
					String message = "Enter points to award/remove (use negative to remove)";
					if (currQues.isDailyDouble()) {
						message += "\n\n Points gained are doubled for daily doubles.";
					}
					input = JOptionPane.showInputDialog(message);
					points = Integer.parseInt(input);
					if (points <= currQues.getCurrentValue()) {
						valueFound = true;
					} else {
						JOptionPane.showMessageDialog(new JFrame(),
								"Please enter a number less than or equal to the available points");
					}
				} catch (NumberFormatException exception) {
					if (input == null) {
						break;
					} else {
						JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid number");
					}
				}
			} while (!valueFound);
			if (input == null) {
				return;
			}
			DefaultPanel.setScore(calculateScore(points), index - 1);
			if (points > 0)
				currQues.setCurrentValue(currQues.getCurrentValue() - points);
			money.setText("$" + currQues.getCurrentValue());
			int[] scores = DefaultPanel.getScores();
			for (int i = 0; i < numTeams; i++) {
				pointLabels[i].setText("Team " + (i + 1) + ": " + scores[i]);
			}
			if (currQues.getCurrentValue() == 0) {
				Driver.switchPanels("AnswerPanel");
				resetTeamButtons();
			}
		}

		public void handleFinalJeopardy() {
			JButton button = pointButtons[index - 1];
			String text = button.getText();
			if (text.contains("Correct")) {
				pointButtons[index - 1].setText("Team " + index + " Incorrect");
			} else {
				pointButtons[index - 1].setText("Team " + index + " Correct");
			}
			boolean allCorrected = true;
			for (int i = 0; i < pointButtons.length; i++) {
				if (!(pointButtons[i].getText().contains("Correct")
						|| pointButtons[i].getText().contains("Incorrect"))) {
					allCorrected = false;
				}
			}
			if (allCorrected && !addedButton) {
				addedButton = true;
				goToAnswer = new JButton("Go to answer");

				goToAnswer.setFont(new Font("New Times Roman", Font.BOLD, 24).deriveFont(fontAttributes));
				removeBackground(goToAnswer);
				goToAnswer.setForeground(questionColor);
				goToAnswer.setBackground(buttonBackColor);
				Dimension dim = new Dimension(Integer.MAX_VALUE, 50);
				goToAnswer.setPreferredSize(dim);
				goToAnswer.setMinimumSize(dim);
				goToAnswer.addActionListener(new FinalListener());
				GridBagConstraints c = new GridBagConstraints();

				c.gridx = 0;
				c.gridy = 4;
				c.gridwidth = 4;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 1.0;
				c.weighty = 1.0;
				add(goToAnswer, c);
			}

		}

		/**
		 * Method actionPerformed triggered whenever the team points buttons are called
		 * and will always handle distribution of points/ask for the points to be
		 * risked/edit the file
		 *
		 * @param e unused action event
		 */
		public void actionPerformed(ActionEvent e) {

			if (!currQues.isFinalJeopardy()) {
				if (queued) {
					handleQueuedQuestion();
				} else {
					pointButtons[index - 1].setText(pointButtons[index - 1].getText() + " " + order[orderIndex]);
					orderIndex++;
					queued = true;
				}
			} else {
				handleFinalJeopardy();
			}

		}

		/**
		 * Method calculateScore returns the new score
		 *
		 * @return new score
		 */
		private int calculateScore(int points) {
			int doubleController = (((Boolean) currQues.isDailyDouble()).compareTo(false) + 1);
			return findExistingScore() + points * doubleController;
		}

		/**
		 * Method findExistingScore returns the existing score of a team
		 *
		 * @return the existing score
		 */
		private int findExistingScore() {
			return DefaultPanel.getScore(index - 1);
		}

		/**
		 * Class FinalListener the listener used to handle final jeopardy functions
		 * 
		 * @author Phoenix
		 * @version 3/2/2020
		 */
		private class FinalListener implements ActionListener {
			/**
			 * Method actionPerformed triggered after the "bets" are locked in
			 *
			 * @param e unused actionevent
			 */
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < numTeams; i++) {
					DefaultPanel.setScore(calculateScore(i), i);
				}
				Driver.switchPanels("AnswerPanel");
			}

			/**
			 * Method calculateScore calculates the score of a team based on their bet
			 *
			 * @param i the team index of the team
			 * @return the team's new score
			 */
			private int calculateScore(int i) {
				int newValue = findExistingScore(i);
				if (pointButtons[i].getText().contains("Incorrect")) {
					newValue -= currQues.getFinalJeopardyValue(i);
				} else {
					newValue += currQues.getFinalJeopardyValue(i);
				}
				return newValue;
			}

			/**
			 * Method findExistingScore finds the existing score of a team
			 *
			 * @param i the index of the team
			 * @return the team's existing score
			 */
			private int findExistingScore(int i) {
				return DefaultPanel.getScore(i);
			}
		}
	}

	/**
	 * Method setEdit prepares the questionpanel to be edited if the user has chosen
	 * to go into edit mode
	 *
	 * @param e boolean deciding whether or not the question is to be edited
	 */
	@Override
	public void setEdit(boolean e) {
		super.setEdit(e);
		if (edit) {
			GridBagConstraints c = new GridBagConstraints();
			JButton next = new JButton("Save & Go to Answer");
			next.addActionListener(new Listener("AnswerPanel"));
			setUpSaveButton(next);

			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 3;
			c.weightx = 1.0;
			c.weighty = 1.0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.LINE_END;
			c.insets = new Insets(0, 30, 0, 30);
			add(next, c);

		}

	}

	public void resetTeamButtons() {
		orderIndex = 0;
		for (int i = 0; i < pointButtons.length; i++) {
			PointsListener targetListener = ((PointsListener) pointButtons[i].getActionListeners()[0]);
			targetListener.setQueued(false);
			pointButtons[i].setText(pointButtons[i].getText().substring(0, 6));
			if (addedButton) {
				remove(goToAnswer);
				addedButton = false;
			}

		}
	}
}
