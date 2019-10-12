package org.hari.devotion.extensions;

import com.venky.swf.db.Database;
import com.venky.swf.db.extensions.AfterModelCreateExtension;
import com.venky.swf.plugins.security.db.model.Role;
import com.venky.swf.plugins.security.db.model.UserRole;
import org.hari.devotion.db.model.User;

public class AfterCreateUser extends AfterModelCreateExtension<User> {
    static {
        registerExtension(new AfterCreateUser());
    }
    @Override
    public void afterCreate(User model) {
        UserRole role = Database.getTable(UserRole.class).newRecord();
        role.setUserId(model.getId());
        role.setRoleId(Role.getRole("USER").getId());
        role.save();
    }
}
