package org.hari.devotion.extensions;

import com.venky.swf.db.extensions.ParticipantExtension;
import com.venky.swf.plugins.security.db.model.UserRole;
import org.hari.devotion.db.model.User;

import java.util.Arrays;
import java.util.List;

public class UserParticipantExtension extends ParticipantExtension<User> {
    static {
        registerExtension(new UserParticipantExtension());
    }
    @Override
    protected List<Long> getAllowedFieldValues(com.venky.swf.db.model.User user, User partiallyFilledModel, String fieldName) {
        if (fieldName.equalsIgnoreCase("SELF_USER_ID")){
            List<UserRole> roles = user.getRawRecord().getAsProxy(com.venky.swf.plugins.security.db.model.User.class).getUserRoles();
            boolean isSignup = false;
            for (UserRole role:roles){
                if (role.getRole().getName().equalsIgnoreCase("SIGNUP")){
                    isSignup = true;
                    break;
                }
            }
            if (!isSignup) {
                return Arrays.asList(user.getId());
            }
        }
        return null;
    }
}
