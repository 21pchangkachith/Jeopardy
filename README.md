PROJECT TITLE: Jeopardy (revised)
DATE: 
	
	July 28, 2020
LATEST VERSION: 
	
	v3.1
AUTHOR: 
	
	Phoenix Changkachith

REQUIREMENTS TO RUN: 
	
	Java RTE. Download it at: 
	https://java.com/en/download/

HOW TO START:

	Execute the jar file by double clicking. If this does not work:
		1) Try executing start.bat
		2) Try executing start.sh


PURPOSE:

	This project was started with the intention of improving the system used with powerpoints.
	Some quality of life improvements that this project includes are:
		Ability to digitally back up one copy of a gameset that was played 
		Points counter.
		New UI.
		Customizable number of daily doubles.
		Baseline for new features (team names, leaderboards, etc.) 

HOW TO USE:

	Creating a new set:
		1) Play>Create new set 
		2) Enter name of desired set in yellow textbox
		3) Click "Create new set:" 
		4) Navigate to the folder you want to set to be created in
		5) Press "Open"
	Importing sets from powerpoint:
		1) Play>Create new set
		2) Click "Import set"
		3) Navigate to the powerpoint in the popup menu and select it.
		4) Press "Open"
	Editing a created gameset:
		1) Play>Select
		2) Navigate to the desired gameset and select it.
		3) Click "Open" in the popup window
		4) Click "Edit"
		5) Edit things (Final jeopardy is edited the same way as a regular question)
			a) To edit categories, click on the category textbox and type changes. Press enter when done to save changes.
			b) To edit questions, click on the money symbols, then click on the blue textbox and enter changes. Click "Save & Go to Answer" to save changes.
			c) To edit answers, click on the money symbols, then click "Save & Go to Answer." Enter changes the same way you would for questions, then click "Save & Back to Question."
	Playing a created gameset:
		1) Play>Select
		2) Navigate to the desired set and select it.
		3) Click "Open" in the popup window
		4) Click "Play"
		5) Play game
			a) Click money symbol to go to a question
				i) if the question is a daily double, enter the bet of the team which chose the money symbol, then proceed to ii)
				ii) Click Team numbers in the order that the teams are ready to answer the question.				
				iii) When a guess has been made, click the team number of the team who made the guess again.
				iv) From the popup window, enter the number of points to give or subtract from the team and press "OK". Repeat until all points have been distributed.
				v) Click the back button one finished.
			b) Click "Final Jeopardy" to go to the final jeopardy.
				i) Enter all team bets and click "Go to question" once finished.
				ii) If a team got the question correct, click their  team button once. If they got it incorrect, click it twice. (odd number of times or even number also works).
				iii) Once the validity of every answer has been confirmed, click "Go to answer"
	Playing a ".sav" gameset (saved progress gameset):
		1) Play>Select
		2) Navigate to the desired set and select it.
		3) Click "Open" in the popup window
		4) Since the file is a game in progress, no edits can be made. Click "Play."
		5) Refer to the instructions in "Playing a created gameset" to play the  game
	Changing settings:
		1) Main Menu>Settings
		2) Edit Settings
			i) For daily doubles, click "Change # Daily Doubles" and enter desired # of daily doubles (0-25)
			ii) To change the default path (make selecting files and creating files a little bit easier) Click "Change Default Path" and navigate to the desired folder. Click "Open" when done.
			iii) To change the number of teams, click "Change # teams. Enter the new value and click OK.
		3) Restart to apply changes.
