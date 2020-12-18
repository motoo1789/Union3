package property;

import java.util.ArrayList;

public class ViolationPropertyContext {

    private ViolationProperty property;
    private ArrayList<String> ErrorViolation = new ArrayList<String>();
    private String patternname;
    private String errorcode;

    public ViolationPropertyContext(String patternname, String errorcode) {
       property = GetSendeeProperty.getInstance();
       this.patternname = patternname;
       this.errorcode = errorcode;
    }

    public ArrayList<String> getErrorViolation() {

    	for(int i = 0; i < 3; i++)
    	{
	    	String sendViolation;

	    	sendViolation = property.getViolation(this, patternname,errorcode);
	    	ErrorViolation.add(sendViolation);

	    }

    	return ErrorViolation;
    }


    void changeState(ViolationProperty property) {
    	this.property = property;
    }
}
