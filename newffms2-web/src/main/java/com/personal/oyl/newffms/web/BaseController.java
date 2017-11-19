package com.personal.oyl.newffms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class BaseController {
    protected final boolean isKeepSearchParameter(HttpServletRequest request) {
        String keepSP = request.getParameter("keepSp");

        if ("Y".equalsIgnoreCase(keepSP)) {
            return true;
        }

        return false;

    }

    protected final void clearSearchParameter(HttpServletRequest request, HttpSession session, String sessionKey_) {
        if (!isKeepSearchParameter(request)) {
            session.removeAttribute(sessionKey_);
        }
    }

}
