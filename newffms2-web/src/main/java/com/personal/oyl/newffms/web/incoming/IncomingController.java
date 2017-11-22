package com.personal.oyl.newffms.web.incoming;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.account.domain.Account;
import com.personal.oyl.newffms.common.Tuple;
import com.personal.oyl.newffms.incoming.domain.Incoming;
import com.personal.oyl.newffms.incoming.domain.IncomingCondition;
import com.personal.oyl.newffms.incoming.domain.IncomingRepos;
import com.personal.oyl.newffms.incoming.domain.IncomingType;
import com.personal.oyl.newffms.user.domain.User;
import com.personal.oyl.newffms.user.domain.UserRepos;
import com.personal.oyl.newffms.util.BootstrapTableJsonRlt;
import com.personal.oyl.newffms.web.BaseController;
import com.personal.oyl.newffms.web.user.UserDto;

@Controller
@RequestMapping("/incoming")
public class IncomingController extends BaseController {
    private static final String SESSION_KEY_SEARCH_PARAM_INCOMING = "SESSION_KEY_SEARCH_PARAM_INCOMING";
    private static final Map<String, String> colMapping;

    static {
        colMapping = new HashMap<String, String>();
        colMapping.put("INCOMING_DATE", "INCOMING_DATE");
    }
    
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private IncomingRepos incomingRepos;
    /*@Autowired
    private IncomingValidator incomingValidator;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(incomingValidator);
    }*/
    
    @RequestMapping("summary")
    public String summary(HttpServletRequest request, Model model, HttpSession session) {
        this.clearSearchParameter(request, session, SESSION_KEY_SEARCH_PARAM_INCOMING);
        
        //初始化查询条件。
        List<User> users = userRepos.queryAllUser();
        List<UserDto> userList = new LinkedList<>();
        for (User user : users) {
            userList.add(new UserDto(user));
        }
        model.addAttribute("users", userList);
        model.addAttribute("incomingTypes", IncomingType.toMapValue());
        
        //设置默认查询条件值，并放入session中
        IncomingCondition searchParam = (IncomingCondition) session.getAttribute(SESSION_KEY_SEARCH_PARAM_INCOMING);
        
        if (null == searchParam) {
            searchParam = new IncomingCondition();
            searchParam.initPaginationParam(1, 10, "INCOMING_DATE", "desc");
            
            session.setAttribute(SESSION_KEY_SEARCH_PARAM_INCOMING, searchParam);
        }

        return "incoming/summary";
    }
    
    @RequestMapping("search")
    @ResponseBody
	public String search(@RequestParam("ownerOid") BigDecimal ownerOid,
			@RequestParam("confirmed") Boolean confirmed,
			@RequestParam("incomingType") IncomingType incomingType,
			@RequestParam("incomingDesc") String incomingDesc,
			HttpSession session) {
    	//从页面接受查询参数，并放入session中。
        IncomingCondition searchParam = new IncomingCondition();
        searchParam.setOwnerOid(ownerOid);
        searchParam.setConfirmed(confirmed);
        searchParam.setIncomingType(incomingType);
        searchParam.setIncomingDesc(incomingDesc);
        
        session.setAttribute(SESSION_KEY_SEARCH_PARAM_INCOMING, searchParam);
        
        return "OK";
    }
    
    @RequestMapping("/listOfSummary")
    @ResponseBody
    public BootstrapTableJsonRlt listOfSummary(@RequestParam(value = "offset", required = true) int offset,
            @RequestParam(value = "limit", required = true) int limit,
            @RequestParam(value = "sort", required = true) String sort,
            @RequestParam(value = "order", required = true) String order, HttpSession session) {

        int sizePerPage = limit;
        int requestPage = offset / limit + 1;
        String sortField = colMapping.get(sort);
        String sortDir = order;
    	
        //从session中取出查询对象并查询
        IncomingCondition searchParam = (IncomingCondition) session.getAttribute(SESSION_KEY_SEARCH_PARAM_INCOMING);
        searchParam.initPaginationParam(requestPage, sizePerPage, sortField, sortDir);
        session.setAttribute(SESSION_KEY_SEARCH_PARAM_INCOMING, searchParam);
        
        Tuple<Integer, List<Incoming>> tuple = incomingRepos.queryIncomings(searchParam, searchParam.getPaginationParameter());
        List<User> userList = userRepos.queryAllUser();
        Map<BigDecimal, UserDto> group = new HashMap<>();
        if (null != userList) {
            for (User user : userList) {
                group.put(user.getKey().getUserOid(), new UserDto(user));
            }
        }
        
        List<IncomingDto> list = new LinkedList<>();
        if (null != tuple.second) {
            for (Incoming incoming : tuple.second) {
                IncomingDto dto = new IncomingDto(incoming);
                dto.setOwner(group.get(incoming.getOwnerOid()));
                list.add(dto);
            }
        }
        return new BootstrapTableJsonRlt(tuple.first, list);
    }
    
    /*@RequestMapping("/initAdd")
    public String initAdd(@RequestParam(value = "back", required = false) Boolean back, Model model, HttpSession session) throws SQLException {
        
        Incoming form = null;
        
        if (null != back && back && null != session.getAttribute("incomingForm")) {
            form = (Incoming) session.getAttribute("incomingForm");
        }
        else {
            form = new Incoming();
        }
        
        model.addAttribute("incomingForm", form);
        model.addAttribute("incomingTypes", IncomingType.toMapValue());
        model.addAttribute("users", userProfileService.selectAllUsers());
        
        return "incoming/add";
    }
    
    
    @RequestMapping("/confirmAdd")
    public String confirmAdd(@Valid @ModelAttribute("incomingForm") Incoming form, BindingResult result, Model model, HttpSession session) throws SQLException {
        if (result.hasErrors()) {
        	//页面回显
        	form.setTargetAccount(accountService.selectByKey(new AccountKey(form.getTargetAccount().getAcntOid())));
            form.getTargetAccount().setOwner(userProfileService.selectByKey(new UserProfileKey(form.getTargetAccount().getOwnerOid())));
        	
            model.addAttribute("incomingTypes", IncomingType.toMapValue());
            model.addAttribute("users", userProfileService.selectAllUsers());
            model.addAttribute("validation", false);
            
            return "incoming/add";
        }
        
        form.setOwner(userProfileService.selectByKey(new UserProfileKey(form.getOwnerOid())));
        form.setTargetAccount(accountService.selectByKey(new AccountKey(form.getTargetAccount().getAcntOid())));
        form.getTargetAccount().setOwner(userProfileService.selectByKey(new UserProfileKey(form.getTargetAccount().getOwnerOid())));
        
        session.setAttribute("incomingForm", form);
        
        return "incoming/confirmAdd";
    }
    
    
    @RequestMapping("/saveAdd")
    public String saveAdd(Model model, HttpSession session) throws SQLException {
        Incoming form = (Incoming) session.getAttribute("incomingForm");
        
        form.setConfirmed(false);
        BaseObject base = new BaseObject();
        base.setCreateTime(new Date());
        base.setCreateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());
        
        form.setBaseObject(base);
        
        transactionService.createIncoming(form);
        
        session.removeAttribute("incomingForm");
        
        return "redirect:/incoming/summary?keepSp=Y";
    }
    
    @RequestMapping("/view")
    public String view(@RequestParam("incomingOid") BigDecimal incomingOid, Model model) throws SQLException {
        Incoming form = incomingService.selectByKey(new IncomingKey(incomingOid));
        
        
        form.setOwner(userProfileService.selectByKey(new UserProfileKey(form.getOwnerOid())));
        form.setTargetAccount(accountService.queryAccountsByIncoming(incomingOid));
        form.getTargetAccount().setOwner(userProfileService.selectByKey(new UserProfileKey(form.getTargetAccount().getOwnerOid())));
        
        model.addAttribute("incomingForm", form);
        
        return "incoming/view";
    }
    
    @RequestMapping("/initEdit")
	public String initEdit(@RequestParam(value = "back", required = false) Boolean back,
			@RequestParam(value = "incomingOid", required = false) BigDecimal incomingOid,
			Model model, HttpSession session) throws SQLException {

        Incoming form = null;
        
        if (null != back && back && null != session.getAttribute("incomingForm")) {
            form = (Incoming) session.getAttribute("incomingForm");
        }
        else {
            form = incomingService.selectByKey(new IncomingKey(incomingOid));
            form.setTargetAccount(accountService.queryAccountsByIncoming(incomingOid));
            form.getTargetAccount().setOwner(userProfileService.selectByKey(new UserProfileKey(form.getTargetAccount().getOwnerOid())));
        }
        
        model.addAttribute("incomingForm", form);
        model.addAttribute("incomingTypes", IncomingType.toMapValue());
        model.addAttribute("users", userProfileService.selectAllUsers());
        
        return "incoming/edit";
    }
    
    @RequestMapping("/confirmEdit")
    public String confirmEdit(@Valid @ModelAttribute("incomingForm") Incoming form, BindingResult result, Model model, HttpSession session) throws SQLException {
        if (result.hasErrors()) {
        	//页面回显
        	form.setTargetAccount(accountService.selectByKey(new AccountKey(form.getTargetAccount().getAcntOid())));
            form.getTargetAccount().setOwner(userProfileService.selectByKey(new UserProfileKey(form.getTargetAccount().getOwnerOid())));
            
            model.addAttribute("incomingTypes", IncomingType.toMapValue());
            model.addAttribute("users", userProfileService.selectAllUsers());
            model.addAttribute("validation", false);
            
            return "incoming/edit";
        }
        
        form.setOwner(userProfileService.selectByKey(new UserProfileKey(form.getOwnerOid())));
        form.setTargetAccount(accountService.selectByKey(new AccountKey(form.getTargetAccount().getAcntOid())));
        form.getTargetAccount().setOwner(userProfileService.selectByKey(new UserProfileKey(form.getTargetAccount().getOwnerOid())));
        
        session.setAttribute("incomingForm", form);
        
        return "incoming/confirmEdit";
    }
    
    @RequestMapping("/saveEdit")
    public String saveEdit(Model model, HttpSession session) throws SQLException {
        Incoming form = (Incoming) session.getAttribute("incomingForm");
        
        Incoming oldObj = incomingService.selectByKey(new IncomingKey(form.getIncomingOid()));
        form.setBaseObject(new BaseObject());
        form.getBaseObject().setSeqNo(oldObj.getBaseObject().getSeqNo());
        form.getBaseObject().setUpdateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());
        form.getBaseObject().setUpdateTime(new Date());
        
        transactionService.updateIncoming(form);
        
        session.removeAttribute("incomingForm");
        
        return "redirect:/incoming/summary?keepSp=Y";
    }
    
    @RequestMapping("/delete")
    public String delete(@RequestParam("incomingOid") BigDecimal incomingOid, Model model) throws SQLException {
        transactionService.deleteIncoming(incomingOid);
        
        return "redirect:/incoming/summary?keepSp=Y";
    }
    
    @RequestMapping("/confirm")
    public String confirm(@RequestParam("incomingOid") BigDecimal incomingOid, Model model, HttpSession session) throws SQLException {
        transactionService.confirmIncoming(incomingOid, SessionUtil.getInstance().getLoginUser(session).getUserName());
        
        return "redirect:/incoming/summary?keepSp=Y";
    }
    
    @RequestMapping("/rollback")
    public String rollback(@RequestParam("incomingOid") BigDecimal incomingOid, Model model, HttpSession session) throws SQLException {
        transactionService.rollbackIncoming(incomingOid, SessionUtil.getInstance().getLoginUser(session).getUserName());
        
        return "redirect:/incoming/summary?keepSp=Y";
    }*/
}
