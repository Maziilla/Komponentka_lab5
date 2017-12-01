/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.beans.*;
import java.io.Serializable;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.text.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Mazilaa
 */
public class MyText extends JTextField implements Serializable {
    private int value = 0;
    private String OldText="0";
    private boolean isDec = true;
    
 
    public MyText() {
        super();        
        setText("0");
        this.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e){
                SaveOldValue();
                //System.out.println("check");
                checkText(e.getKeyChar());
            }
            @Override
            public void keyTyped(KeyEvent e){                
            }
            
            @Override
            public void keyReleased(KeyEvent e){
                checkText(e.getKeyChar());
            }
            }
        );
    }
    
    public int getValue(){
        return value;
    }
    public boolean getDec()
    {
        return isDec;
    } 
    public void setDec(boolean val){
        isDec = val;
        if(isDec)
            setText(Integer.toString(value));
        else
             setText(Integer.toHexString(value));
    }
    @Override
    public String getText() {
        return super.getText();
    }
    
    public void checkText(char ch)
    {
        String value = this.getText();
        if(value.length() == 0){
            this.setText("0");
            return;
        }
        
        int result = 0;
        try{
            //System.out.println("check");
            if(isDec)
                result = Integer.valueOf(value);
            else
                result = Integer.parseInt(value,16);
        }
        catch (NumberFormatException ex){
            this.setText(OldText);
            return;
        }         
        if(value.equals(OldText)){
            return;
        }
        
        if(result>255)
            if(isDec){
                value = "255";
            }
            else{
                value = "FF";
            }            
        if(result<0)
            if(isDec){
                value = "0";
            }            
        this.setText(value);
        
    }
    
    public void SaveOldValue(){
        int result;
        try{
            if(isDec)
                result = Integer.valueOf(getText());
            else
                result = Integer.parseInt(getText(),16);
        }
        catch(NumberFormatException ex){
            return;
        }
        OldText = getText();
    }
    @Override
    public void setText(String text){
        int result = 0;
        try{
            if(isDec){
                result = Integer.valueOf(text);
            }
            else{
                result = Integer.parseInt(text,16);
            }
            
        }
        catch (NumberFormatException ex){
            super.setText("0");            
        }
        if(result>255)
            if(isDec){
                text = "255";
            }
            else{
                text = "FF";
            }            
        if(result<0)            
            text = "0";              
        super.setText(text);
        if(isDec){
            this.value = Integer.valueOf(text);
        }
        else{
            this.value = Integer.parseInt(text,16);
        }
        repaint();
    }
}
