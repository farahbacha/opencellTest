package com.farah;

import java.util.Date;

import javax.servlet.ServletException;

public  class BaseException extends ServletException{
    //Each exception message will be held here
    private String message;
    private String flux;
    private String file_name;
    private String status;
    private String error_code;

    public BaseException(String msg, String flux,String file_name,String status,String error_code)
    {
    	super (msg);
        this.message = msg;
        this.flux= flux;
        this.file_name= file_name;
        this.status= status;
        this.error_code= error_code;
        
    }

    public BaseException(String msg)
    {
    	super (msg);
        
    }
    @Override
    public String toString() {
    	return this.message+"\n"+this.flux+"\n"+this.file_name+"\n"+this.status+"\n"+this.error_code;
    }
    //Message can be retrieved using this accessor method
    public String getMessage() {
        return message;
    }
}