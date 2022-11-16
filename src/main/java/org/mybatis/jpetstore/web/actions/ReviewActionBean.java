package org.mybatis.jpetstore.web.actions;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.SessionScope;

@SessionScope
public class ReviewActionBean extends AbstractActionBean{

    private static final String REVIEW_LIST = "/WEB-INF/jsp/review/ReviewList.jsp";

    public ForwardResolution reviewList(){

        return new ForwardResolution(REVIEW_LIST);
    }
}
