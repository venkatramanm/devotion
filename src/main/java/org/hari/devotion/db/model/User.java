package org.hari.devotion.db.model;

import com.venky.core.util.Bucket;
import com.venky.swf.db.annotations.column.COLUMN_DEF;
import com.venky.swf.db.annotations.column.COLUMN_NAME;
import com.venky.swf.db.annotations.column.IS_NULLABLE;
import com.venky.swf.db.annotations.column.defaulting.StandardDefault;
import com.venky.swf.db.annotations.column.pm.PARTICIPANT;
import com.venky.swf.db.annotations.column.ui.CONTENT_TYPE;
import com.venky.swf.db.annotations.column.ui.HIDDEN;
import com.venky.swf.db.annotations.column.ui.PROTECTION;
import com.venky.swf.db.annotations.column.ui.PROTECTION.Kind;
import com.venky.swf.db.annotations.column.ui.mimes.MimeType;
import com.venky.swf.db.model.Model;

import java.io.InputStream;
import java.util.List;

public interface User extends com.venky.swf.plugins.security.db.model.User {

    @COLUMN_NAME("ID")
    @PROTECTION(Kind.DISABLED)
    @HIDDEN
    @PARTICIPANT
    @IS_NULLABLE
    public Long getSelfUserId();
    public void setSelfUserId(Long id);
    public User getSelfUser();


    @CONTENT_TYPE(MimeType.IMAGE_PNG)
    public InputStream getImage();
    public void setImage(InputStream in);

    @PROTECTION(Kind.NON_EDITABLE)
    public String getImageContentName();
    public void setImageContentName(String name);

    @PROTECTION(Kind.NON_EDITABLE)
    public String getImageContentType();
    public void setImageContentType(String contentType);

    @PROTECTION(Kind.NON_EDITABLE)
    @COLUMN_DEF(StandardDefault.ZERO)
    public int getImageContentSize();
    public void setImageContentSize(int size);

    @COLUMN_DEF(StandardDefault.ONE)
    public int getNumberOfRoundsPlannedPerDay();
    public void setNumberOfRoundsPlannedPerDay(int goal);

    public String getCity();
    public void setCity(String city);

    @IS_NULLABLE
    public String getPhoneNumber();
    public void setPhoneNumber(String phoneNumber);

    @IS_NULLABLE
    public String getEmail();
    public void setEmail(String email);


    public List<UserRound> getUserRounds();


}
