package com.luxlunaris.cincia.ide;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import javax.swing.*;

import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.luxlunaris.cincia.frontend.ast.interfaces.Constant;
import com.luxlunaris.cincia.frontend.ast.interfaces.Token;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keyword;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operator;
import com.luxlunaris.cincia.frontend.ast.tokens.punctuation.Punctuation;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.preprocessor.Preprocessor;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;



public class BestMain extends JFrame{

	private JTextPane textPane;

	public BestMain(){

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);            
		EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));
		textPane = new JTextPane();                
		textPane.setBorder(eb);
		textPane.setMargin(new Insets(5, 5, 5, 5));		
		textPane.setCharacterAttributes(getStyle(Color.RED), false);
		add(textPane);
		setPreferredSize(new Dimension(600, 600));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);   
		setTitle("filename.txt");


		KeyListener kl = new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {

				// SPECIAL KEYBINDINGS

				// Ctrl+Enter
				if(arg0.getKeyCode() == 10 && arg0.getModifiersEx()==128 ) {
					JOptionPane.showConfirmDialog(null, "control+enter!");
				}

				// Ctrl+L
				if(arg0.getKeyCode() == 76 && arg0.getModifiersEx()==128 ) {
					JOptionPane.showConfirmDialog(null, "control+L!");
				}

				//Ctrl+S
				if(arg0.getKeyCode() == 83 && arg0.getModifiersEx()==128 ) {
					JOptionPane.showConfirmDialog(null, "control+S!");
				}

				//Ctrl+Z
				if(arg0.getKeyCode() == 90 && arg0.getModifiersEx()==128 ) {
					JOptionPane.showConfirmDialog(null, "control+Z!");
				}

				//Ctrl+Y
				if(arg0.getKeyCode() == 89 && arg0.getModifiersEx()==128 ) {
					JOptionPane.showConfirmDialog(null, "control+Y!");
				}

				//Ctrl+I
				if(arg0.getKeyCode() == 73 && arg0.getModifiersEx()==128 ) {
					JOptionPane.showConfirmDialog(null, "control+I!");
				}

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

		};

		textPane.addKeyListener(kl);



		textPane.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {

				StyledDocument doc =  (StyledDocument)arg0.getDocument();

				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						reColorizeDocument(doc);
						testRun(doc);
					}
				});

			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {

			}
		});

	}
	
	
	public Color tokenToColor(Token token) {
		
		if(token instanceof Constant) {
			return Color.ORANGE;
		}else if(token instanceof Punctuation) {
			return Color.BLACK;
		}else if(token instanceof Identifier) {
			return Color.BLUE;
		}else if(token instanceof Operator) {
			return Color.MAGENTA;
		}else if(token instanceof Keyword) {
			return Color.RED;
		}
		
		return Color.BLACK;
	}


	public AttributeSet getStyle(Color c) {

		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
		aset = sc.addAttribute(aset, StyleConstants.FontSize, 50);
		//		aset =sc.addAttribute(aset, StyleConstants.Underline, true);
		return aset;
	}


	public void reColorizeDocument(StyledDocument doc) {

		String text = "";

		try {
			text = doc.getText(0, doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}


		CharStream cStream = new CharStream(text);
		TokenStream tokenStream = new TokenStream(cStream);
		tokenStream.next();
		int start = 0;
		int end = cStream.getPos();

//		System.out.println("tokens:");
		
		while(!tokenStream.isEnd()) {
			
			
			//TODO: deal with comments (maybe turn comments into Tokens)
//			System.out.println(tokenStream.peek()+" from: "+start+" to: "+end);
			doc.setCharacterAttributes(start, end, getStyle(tokenToColor(tokenStream.peek())), true);
			
			start=end;
			tokenStream.next(); //TODO: throw and catch exception with faulty row:col nums
			end = cStream.getPos();
			
		}

	}
	
	public void testRun(StyledDocument doc) {
		
	}


	public static void main(String[] args){

		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new BestMain();
			}
		});
	}
}