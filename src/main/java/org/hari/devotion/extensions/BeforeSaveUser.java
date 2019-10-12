package org.hari.devotion.extensions;

import com.venky.core.util.ObjectUtil;
import com.venky.swf.db.extensions.BeforeModelSaveExtension;
import com.venky.swf.db.model.reflection.ModelReflector;
import org.hari.devotion.db.model.User;

import java.util.regex.Pattern;

public class BeforeSaveUser extends BeforeModelSaveExtension<User> {
    static {
        registerExtension(new BeforeSaveUser());
    }
    @Override
    public void beforeSave(User user) {
        ModelReflector<User> reflector = ModelReflector.instance(getModelClass(this));
        String email = user.getEmail();
        if (ObjectUtil.isVoid(email)){
            user.setEmail(user.getName());
        }


        if (!reflector.isVoid(email) && user.getRawRecord().isFieldDirty("NAME")){
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

            Pattern pat = Pattern.compile(emailRegex);
            if (!pat.matcher(email).matches()){
                if (!user.getName().equalsIgnoreCase("root")){
                    throw new RuntimeException("Email is invalid!");
                }else {
                    user.setEmail(null);
                }
            }
        }
        String phoneNumber = user.getPhoneNumber();
        if (!reflector.isVoid(phoneNumber) && user.getRawRecord().isFieldDirty("PHONE_NUMBER")){
            int length = phoneNumber.length();
            if (length == 10){
                phoneNumber = ("+91"+phoneNumber);
            }else if (length == 12){
                phoneNumber = ("+"+phoneNumber);
            }
            if (phoneNumber.length() != 13){
                throw new RuntimeException("Phone number invalid e.g. +911234567890");
            }
            Pattern pattern = Pattern.compile("\\+[0-9]+");
            if (!pattern.matcher(phoneNumber).matches()){
                throw new RuntimeException("Phone number invalid e.g. +911234567890");
            }
        }


    }
}
