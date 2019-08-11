package programming.blog.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Utils {
	
	private final String ALPHABET ="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final Random random = new SecureRandom();
    
    private Logger logger = LoggerFactory.getLogger(Utils.class);
    //generate userId
    public String generatedUserId(int length){

        return generatedRandomString(length);
    }
    //generate address id
    public String generateCommentId(int length) {
    	return generatedRandomString(length);
    }
    
    //generated post Id
    public String generatedPostId(String text) {
    	return generatedPostRandomString(text);
    }

    //random string generator
    private String generatedRandomString(int length){
        StringBuilder returnValue = new StringBuilder();
        for(int i=0;i<length;i++){
            returnValue.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);

    }
    private String generatedPostRandomString(String text){
    	text = text.toLowerCase().trim();
        StringBuilder returnValue = new StringBuilder();
        returnValue.append(text.replace(' ','-'));
        return new String(returnValue);

    }
    //validate email.
    public boolean validateEmail(String email) {
    	String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                "[a-zA-Z0-9_+&*-]+)*@" + 
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                "A-Z]{2,7}$";
    	Pattern pat = Pattern.compile(emailRegex);
    	if((email == null)) {
    		return false;
    		}
    	//logger.info("----------Valid email id-----------");
    	return pat.matcher(email.trim()).matches();
    	//return true;

    }


	

}
