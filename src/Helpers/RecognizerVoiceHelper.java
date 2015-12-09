package Helpers;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Future;

import Data.Move;
import Logic.ChessGUI;

public class RecognizerVoiceHelper implements Runnable {
	public Recognizer recognizer;
	public Microphone microphone;
	public ConfigurationManager cm;
	private ChessGUI Chess;
	public RecognizerVoiceHelper(ChessGUI chess){
		try{
			Chess = chess;
			URL url;
            url = RecognizerVoiceHelper.class.getResource("helloworld.config.xml");
            System.out.println("Loading...");
            System.out.println(url);
            cm = new ConfigurationManager(url);
            System.out.println(cm);
            recognizer = (Recognizer) cm.lookup("recognizer");
    	    microphone = (Microphone) cm.lookup("microphone");
    	    /* allocate the resource necessary for the recognizer */
            recognizer.allocate();
		} catch (IOException e) {
            System.err.println("Problem when loading HelloWorld: " + e);
            e.printStackTrace();
        } catch (PropertyException e) {
            System.err.println("Problem configuring HelloWorld: " + e);
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.err.println("Problem creating HelloWorld: " + e);
            e.printStackTrace();
        }
	}
	@Override
    public void run(){
		if (microphone.startRecording()) {
			System.out.println
		    ("Say: (Good morning | Hello) " +
	                 "( Bhiksha | Evandro | Paul | Philip | Rita | Will )");
			while (true) {
			    System.out.println
				("Start speaking. Press Ctrl-C to quit.\n");
			    Result result = recognizer.recognize();
			    
			    if (result != null) {
		    		System.out.println("Resultado: "+  result.getBestFinalResultNoFiller());	    	 
			     	if(result.getBestFinalResultNoFiller() != ""){
			     		int col = convertToInt(result.getBestFinalResultNoFiller().charAt(0));
				     	int row = convertToInt(result.getBestFinalResultNoFiller().substring(2,result.getBestFinalResultNoFiller().length()));
			    		if(row >= 0 && row < 8 && col >= 0 && col < 7){
			    			if(Chess.getChessBoardButtons()[row][col].isEnabled())
				    			Chess.buttonPressed(row,col);
			    		}
			     	}
			     				
			    }else{
			    	System.out.println("I can't hear what you said.\n");
			    }
		}
	    }else {
			System.out.println("Cannot start microphone.");
			recognizer.deallocate();
			System.exit(1);
	    }
	}
	public int convertToInt(String letter){
		switch(letter){
			case "one": return 0; 
			case "two": return 1;
			case "three": return 2;
			case "four": return 3;
			case "five": return 4;
			case "six": return 5;
			case "seven": return 6;
			case "eigth": return 7;
			default: return 7;
		}
	}
    public int convertToInt(char letter){
		switch(Character.toUpperCase(letter)){
			case 'H': return 0; 
			case 'G': return 1;
			case 'F': return 2;
			case 'E': return 3;
			case 'D': return 4;
			case 'C': return 5;
			case 'B': return 6;
			case 'A': return 7;
			default: return 7;
		}
	}
	
}


