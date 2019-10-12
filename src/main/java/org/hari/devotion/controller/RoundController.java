package org.hari.devotion.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.venky.core.date.DateUtils;
import com.venky.core.math.DoubleHolder;
import com.venky.core.math.DoubleUtils;
import com.venky.swf.controller.Controller;
import com.venky.swf.db.Database;
import com.venky.swf.db.annotations.column.ui.mimes.MimeType;
import com.venky.swf.db.model.SWFHttpResponse;
import com.venky.swf.db.model.reflection.ModelReflector;
import com.venky.swf.integration.FormatHelper;
import com.venky.swf.integration.IntegrationAdaptor;
import com.venky.swf.path.Path;
import com.venky.swf.sql.Conjunction;
import com.venky.swf.sql.Expression;
import com.venky.swf.sql.Operator;
import com.venky.swf.sql.Select;
import com.venky.swf.views.BytesView;
import com.venky.swf.views.View;
import org.hari.devotion.db.model.User;
import org.hari.devotion.db.model.UserRound;
import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class RoundController extends Controller {
    public RoundController(Path path) {
        super(path);
    }

    public View increment(long id){
        //id is user id.
        User user = Database.getTable(User.class).get(id);

        long startOfToday = DateUtils.getStartOfDay(System.currentTimeMillis());
        UserRound round = Database.getTable(UserRound.class).newRecord();
        round.setRoundCompletedAt(new Timestamp(startOfToday));
        round.setUserId(user.getId());
        round.save();

        return IntegrationAdaptor.instance(SWFHttpResponse.class, FormatHelper.getFormatClass(MimeType.APPLICATION_JSON)).createStatusResponse(getPath(),null) ;
    }
    public View status(long id){
        User user = Database.getTable(User.class).get(id);
        long startOfToday = DateUtils.getStartOfDay(System.currentTimeMillis());
        long endOfToday = DateUtils.getEndOfDay(System.currentTimeMillis());
        ModelReflector<UserRound> ref = ModelReflector.instance(UserRound.class);

        List<UserRound> rounds = new Select().from(UserRound.class).
                                where(new Expression(ref.getPool(), Conjunction.AND).
                                        add(new Expression(ref.getPool(), "USER_ID", Operator.EQ, id)).
                                        add(new Expression(ref.getPool(), "ROUND_COMPLETED_AT",Operator.GE, new Timestamp(startOfToday))).
                                        add(new Expression(ref.getPool(), "ROUND_COMPLETED_AT",Operator.LT, new Timestamp(endOfToday)))).execute();

        JSONObject object = new JSONObject();
        object.put("Count",rounds.size());
        object.put("Goal",user.getNumberOfRoundsPlannedPerDay());
        object.put("PctGoalReached", new DoubleHolder(rounds.size() * 100.0 / user.getNumberOfRoundsPlannedPerDay(),2));

        return new BytesView(getPath(),object.toString().getBytes());

    }
}
