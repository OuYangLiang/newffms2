package com.personal.oyl.newffms.web;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.personal.oyl.newffms.util.BasePojo;
import com.personal.oyl.newffms.util.BootstrapTableJsonRlt;
import com.personal.oyl.newffms.util.JqGridJsonRlt;
import com.personal.oyl.newffms.util.PaginatingService;


public abstract class BaseController {
    /*@Autowired
    protected TransactionService transactionService;
    @Autowired
    protected UserProfileService userProfileService;
    @Autowired
    protected CategoryService categoryService;
    @Autowired
    protected ConsumptionService consumptionService;
    @Autowired
    protected ConsumptionItemService consumptionItemService;
    @Autowired
    protected AccountService accountService;
    @Autowired
    protected IncomingService incomingService;
    @Autowired
    protected AccountIncomingService accountIncomingService;
    @Autowired
    protected AccountAuditService accountAuditService;*/
    
    protected final boolean isKeepSearchParameter(HttpServletRequest request)
    {
        String keepSP = request.getParameter("keepSp");
        
        if ("Y".equalsIgnoreCase(keepSP)) {
            return true;
        }
        
        return false;

    }

    protected final void clearSearchParameter(HttpServletRequest request, HttpSession session, String sessionKey_)
    {
        if(!isKeepSearchParameter(request))
        {
            session.removeAttribute(sessionKey_);
        }
    }
    
    protected final <T extends BasePojo> JqGridJsonRlt<T> initPaging(PaginatingService<T> service, T param) throws SQLException {
        int count = service.getCountOfSummary(param);
        
        List<T> list = null;
        
        if (count == 0) {
        	list = new ArrayList<T>();
        } else {
        	list = service.getListOfSummary(param);
        }
        
        JqGridJsonRlt<T> rlt = new JqGridJsonRlt<T>();
        rlt.setRows(list);
        rlt.setPage(param.getRequestPage());
        rlt.setRecords(count);
        rlt.setTotal(BigDecimal.valueOf(count).divide(BigDecimal.valueOf(param.getSizePerPage()), BigDecimal.ROUND_UP).intValue());
        
        return rlt;
    }
    
    protected final <T extends BasePojo> BootstrapTableJsonRlt<T> initBootstrapPaging(PaginatingService<T> service, T param) throws SQLException {
        int count = service.getCountOfSummary(param);
        
        List<T> list = null;
        
        if (count == 0) {
        	list = new ArrayList<T>();
        } else {
        	list = service.getListOfSummary(param);
        }
        
        BootstrapTableJsonRlt<T> rlt = new BootstrapTableJsonRlt<T>();
        rlt.setRows(list);
        rlt.setTotal(count);
        
        return rlt;
    }
}
