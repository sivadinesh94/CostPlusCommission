package com.costcommission.exception;

public class RecordNotFoundException extends  Exception{
    public  RecordNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
