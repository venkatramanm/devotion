package org.hari.devotion.db.model;

import com.venky.swf.db.annotations.model.ORDER_BY;
import com.venky.swf.db.model.Model;

import java.sql.Timestamp;

@ORDER_BY("DATE DESC")
public interface UserRound extends Model {
    public long getUserId();
    public void setUserId(long id);
    public User getUser();

    public Timestamp getRoundCompletedAt();
    public void setRoundCompletedAt(Timestamp date);


}
