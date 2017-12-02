package com.personal.oyl.newffms.web.consumption;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.account.domain.AccountException.AccountKeyEmptyException;
import com.personal.oyl.newffms.account.domain.AccountKey;
import com.personal.oyl.newffms.account.domain.AccountRepos;
import com.personal.oyl.newffms.category.domain.CategoryKey;
import com.personal.oyl.newffms.category.domain.CategoryRepos;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryKeyEmptyException;
import com.personal.oyl.newffms.common.NewffmsDomainException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NewffmsSystemException;
import com.personal.oyl.newffms.common.Tuple;
import com.personal.oyl.newffms.common.Util;
import com.personal.oyl.newffms.consumption.domain.ConsumptionCondition;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionAlreadyConfirmedException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionKeyEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionNotExistException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionItemPaginationVo;
import com.personal.oyl.newffms.consumption.domain.ConsumptionKey;
import com.personal.oyl.newffms.consumption.domain.ConsumptionRepos;
import com.personal.oyl.newffms.consumption.domain.ConsumptionType;
import com.personal.oyl.newffms.user.domain.User;
import com.personal.oyl.newffms.user.domain.UserException.UserKeyEmptyException;
import com.personal.oyl.newffms.user.domain.UserKey;
import com.personal.oyl.newffms.user.domain.UserRepos;
import com.personal.oyl.newffms.util.BootstrapTableJsonRlt;
import com.personal.oyl.newffms.util.DateUtil;
import com.personal.oyl.newffms.util.SessionUtil;
import com.personal.oyl.newffms.web.BaseController;
import com.personal.oyl.newffms.web.account.AccountDto;
import com.personal.oyl.newffms.web.user.UserDto;

@Controller
@RequestMapping("/consumption")
public class ConsumptionController extends BaseController {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new ConsumptionDtoValidator());
    }

    private static final String SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM = "SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM";
    private static final Map<String, String> colMapping;

    static {
        colMapping = new HashMap<String, String>();
        colMapping.put("CPN_TIME", "CPN_TIME");
        colMapping.put("consumption.cpnTime", "CPN_TIME");
    }
    
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private AccountRepos acntRepos;
    @Autowired
    private CategoryRepos categoryRepos;
    @Autowired
    private ConsumptionRepos consumptionRepos;

    @RequestMapping("summary")
    public String summary(HttpServletRequest request, Model model, HttpSession session) throws SQLException {
        this.clearSearchParameter(request, session, SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM);

        // 初始化查询条件。
        List<User> users = userRepos.queryAllUser();
        List<UserDto> userList = new LinkedList<>();
        for (User user : users) {
            userList.add(new UserDto(user));
        }
        model.addAttribute("users", userList);

        // 设置默认查询条件值，并放入session中
        ConsumptionCondition searchParam = (ConsumptionCondition) session.getAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM);

        if (null == searchParam) {
            searchParam = new ConsumptionCondition();
            searchParam.setCpnTimeFrom(DateUtil.getInstance().getFirstTimeOfCurrentMonth());
            searchParam.setCpnTimeTo(DateUtil.getInstance().getEndTime(new Date()));
            searchParam.initPaginationParam(1, 10, "CPN_TIME", "desc");
            session.setAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM, searchParam);
        }

        return "consumption/summary";
    }

    @RequestMapping("search")
    @ResponseBody
    public String search(@RequestParam("cpnTimeFrom") Date cpnTimeFrom, @RequestParam("cpnTimeTo") Date cpnTimeTo,
            @RequestParam("confirmed") Boolean confirmed, @RequestParam("itemDesc") String itemDesc,
            @RequestParam("ownerOid") BigDecimal ownerOid, @RequestParam("categoryOid") BigDecimal categoryOid,
            @RequestParam("categoryDesc") String categoryDesc, HttpSession session) throws Exception {
        // 从页面接受查询参数，并放入session中。
        ConsumptionCondition searchParam = new ConsumptionCondition();
        searchParam.setOwnerOid(ownerOid);
        searchParam.setCategoryOid(categoryOid);
        searchParam.setCategoryDesc(categoryDesc);
        searchParam.setCpnTimeFrom(DateUtil.getInstance().getBeginTime(cpnTimeFrom));
        searchParam.setCpnTimeTo(DateUtil.getInstance().getEndTime(cpnTimeTo));
        searchParam.setConfirmed(confirmed);
        searchParam.setItemDesc(itemDesc);
        //searchParam.trimAllString();
        //searchParam.setAllEmptyStringToNull();
        session.setAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM, searchParam);

        return "OK";
    }

    @RequestMapping("/listOfSummary")
    @ResponseBody
    public BootstrapTableJsonRlt listOfSummary(
            @RequestParam(value = "offset", required = true) int offset,
            @RequestParam(value = "limit", required = true) int limit,
            @RequestParam(value = "sort", required = true) String sort,
            @RequestParam(value = "order", required = true) String order, HttpSession session) throws CategoryKeyEmptyException {

        int sizePerPage = limit;
        int requestPage = offset / limit + 1;
        String sortField = colMapping.get(sort);
        String sortDir = order;

        // 从session中取出查询对象并查询
        ConsumptionCondition searchParam = (ConsumptionCondition) session.getAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM);
        searchParam.initPaginationParam(requestPage, sizePerPage, sortField, sortDir);
        session.setAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM, searchParam);

        Tuple<Integer, List<ConsumptionItemPaginationVo>> tuple = consumptionRepos.queryConsumptionItems(searchParam);
        List<User> userList = userRepos.queryAllUser();
        Map<BigDecimal, UserDto> group = new HashMap<>();
        if (null != userList) {
            for (User user : userList) {
                group.put(user.getKey().getUserOid(), new UserDto(user));
            }
        }
        
        if (null != tuple.second) {
            for (ConsumptionItemPaginationVo item : tuple.second) {
                item.setOwnerName(group.get(item.getOwnerOid()).getUserName());
                item.setCategoryDesc(categoryRepos.categoryOfId(new CategoryKey(item.getCategoryOid())).getCategoryDesc());
            }
        }
        return new BootstrapTableJsonRlt(tuple.first, tuple.second);
    }
    
    @RequestMapping("/initAdd")
    public String initAdd(@RequestParam(value = "back", required = false) Boolean back, Model model,
            HttpSession session) throws SQLException {

        ConsumptionDto form = null;

        if (null != back && back && null != session.getAttribute("cpnForm")) {
            form = (ConsumptionDto) session.getAttribute("cpnForm");
        } else {
            form = new ConsumptionDto();
        }

        List<User> users = userRepos.queryAllUser();
        List<UserDto> userList = new LinkedList<>();
        for (User user : users) {
            userList.add(new UserDto(user));
        }

        model.addAttribute("cpnForm", form);
        model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
        model.addAttribute("users", userList);

        return "consumption/add";
    }

    @RequestMapping("/confirmAdd")
    public String confirmAdd(@Valid @ModelAttribute("cpnForm") ConsumptionDto form, BindingResult result, Model model,
            HttpSession session) throws AccountKeyEmptyException, UserKeyEmptyException {
        if (result.hasErrors()) {
            // 回显
            if (null != form.getPayments()) {
                List<AccountDto> accounts = new ArrayList<AccountDto>();
                for (AccountDto acnt : form.getPayments()) {
                    BigDecimal payment = acnt.getPayment();
                    acnt = new AccountDto(acntRepos.accountOfId(new AccountKey(acnt.getAcntOid())));
                    acnt.setOwner(new UserDto(userRepos.userOfId(new UserKey(acnt.getOwner().getUserOid()))));
                    acnt.setPayment(payment);
                    accounts.add(acnt);
                }
                form.setPayments(accounts);
            }

            List<User> users = userRepos.queryAllUser();
            List<UserDto> userList = new LinkedList<>();
            for (User user : users) {
                userList.add(new UserDto(user));
            }

            model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
            model.addAttribute("users", userList);
            model.addAttribute("validation", false);

            return "consumption/add";
        }

        for (ConsumptionItemDto item : form.getItems()) {
            item.setOwnerName(userRepos.userOfId(new UserKey(item.getOwnerOid())).getUserName());
            // TODO item.setCategoryFullDesc(categoryService.selectFullDescByKey(item.getCategoryOid()));
        }

        List<AccountDto> accounts = new ArrayList<AccountDto>();
        for (AccountDto acnt : form.getPayments()) {
            BigDecimal payment = acnt.getPayment();
            acnt = new AccountDto(acntRepos.accountOfId(new AccountKey(acnt.getAcntOid())));
            acnt.setOwner(new UserDto(userRepos.userOfId(new UserKey(acnt.getOwner().getUserOid()))));
            acnt.setPayment(payment);
            accounts.add(acnt);
        }
        form.setPayments(accounts);

        session.setAttribute("cpnForm", form);
        return "consumption/confirmAdd";
    }
    
    
    @RequestMapping("/saveAdd")
    public String saveAdd(Model model, HttpSession session) {
        ConsumptionDto form = (ConsumptionDto) session.getAttribute("cpnForm");
        form.setAmount(form.getTotalItemAmount());
        form.setConfirmed(false);
        form.setBatchNum(Util.getInstance().generateBatchNum());
        try {
            consumptionRepos.add(form.toConsumption(), SessionUtil.getInstance().getLoginUser(session).getUserName());
        } catch (NewffmsDomainException e) {
            model.addAttribute("validation", false);
            model.addAttribute("errCode", e.getErrorCode());
            model.addAttribute("errMsg", e.getMessage());
            
            return "consumption/confirmAdd";
        }
        
        session.removeAttribute("cpnForm");
        return "consumption/summary";
    }
    
    @RequestMapping("/view")
    public String view(@RequestParam("cpnOid") BigDecimal cpnOid, Model model)
            throws ConsumptionKeyEmptyException, CategoryKeyEmptyException, AccountKeyEmptyException {
        ConsumptionDto form = new ConsumptionDto(consumptionRepos.consumptionOfId(new ConsumptionKey(cpnOid)));

        List<User> userList = userRepos.queryAllUser();
        Map<BigDecimal, UserDto> group = new HashMap<>();
        if (null != userList) {
            for (User user : userList) {
                group.put(user.getKey().getUserOid(), new UserDto(user));
            }
        }

        for (ConsumptionItemDto dto : form.getItems()) {
            dto.setOwnerName(group.get(dto.getOwnerOid()).getUserName());
            dto.setCategoryDesc(categoryRepos.categoryOfId(new CategoryKey(dto.getCategoryOid())).getCategoryDesc());
        }

        List<AccountDto> list = new ArrayList<>(form.getPayments().size());
        for (AccountDto dto : form.getPayments()) {
            BigDecimal payment = dto.getPayment();
            dto = new AccountDto(acntRepos.accountOfId(new AccountKey(dto.getAcntOid())));
            dto.setPayment(payment);
            list.add(dto);
        }
        form.setPayments(list);

        model.addAttribute("cpnForm", form);
        return "consumption/view";
    }
    
    /*@RequestMapping("/initEdit")
	public String initEdit(@RequestParam(value = "back", required = false) Boolean back,
			@RequestParam(value = "cpnOid", required = false) BigDecimal cpnOid,
			Model model, HttpSession session) throws SQLException {
  
        ConsumptionForm form = null;
        
        if (null != back && back && null != session.getAttribute("cpnForm")) {
            form = (ConsumptionForm) session.getAttribute("cpnForm");
        }
        else {
            form = new ConsumptionForm();
            
            Consumption consumption = consumptionService.selectByKey(new ConsumptionKey(cpnOid));
            List<ConsumptionItem> cItems = consumptionItemService.queryConsumptionItemByCpn(cpnOid);
            List<Account> acntItems = accountService.queryAccountsByConsumption(cpnOid);
            
            form.setConsumption(consumption);
            form.setCpnItems(cItems);
            form.setAccounts(acntItems);
        }
        
        model.addAttribute("cpnForm", form);
        model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
        model.addAttribute("users", userProfileService.selectAllUsers());
        
        return "consumption/edit";
    }
    
    @RequestMapping("/confirmEdit")
    public String confirmEdit(@Valid @ModelAttribute("cpnForm") ConsumptionForm form, BindingResult result, Model model, HttpSession session) throws SQLException {
        if (result.hasErrors()) {
        	//回显
        	if (null != form.getAccounts()) {
        		List<Account> accounts = new ArrayList<Account>();
        		
        		for (Account acnt : form.getAccounts()) {
        			BigDecimal payment = acnt.getPayment();
        			
        			acnt = accountService.selectByKey(new AccountKey(acnt.getAcntOid()));
        			acnt.setOwner(userProfileService.selectByKey(new UserProfileKey(acnt.getOwnerOid())));
        			acnt.setPayment(payment);
        			
        			accounts.add(acnt);
        		}
        		form.setAccounts(accounts);
        	}
        	
            model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
            model.addAttribute("users", userProfileService.selectAllUsers());
            model.addAttribute("validation", false);
            
            return "consumption/edit";
        }
        
        form.getConsumption().setCpnTypeDesc(form.getConsumption().getCpnType().getDesc());
        
        for ( ConsumptionItem item : form.getCpnItems() ) {
            item.setUserName(userProfileService.selectByKey(new UserProfileKey(item.getOwnerOid())).getUserName());
            item.setCategoryFullDesc(categoryService.selectFullDescByKey(item.getCategoryOid()));
        }
        
        List<Account> accounts = new ArrayList<Account>();
		for (Account acnt : form.getAccounts()) {
			BigDecimal payment = acnt.getPayment();
			
			acnt = accountService.selectByKey(new AccountKey(acnt.getAcntOid()));
			acnt.setOwner(userProfileService.selectByKey(new UserProfileKey(acnt.getOwnerOid())));
			acnt.setPayment(payment);
			
			accounts.add(acnt);
		}
		form.setAccounts(accounts);
        
        session.setAttribute("cpnForm", form);
        
        return "consumption/confirmEdit";
    }
    
    @RequestMapping("/saveEdit")
    public String saveEdit(Model model, HttpSession session) throws SQLException {
        ConsumptionForm form = (ConsumptionForm) session.getAttribute("cpnForm");
        
        form.getConsumption().setAmount(form.getTotalItemAmount());
        
        Consumption oldObj = consumptionService.selectByKey(new ConsumptionKey(form.getConsumption().getCpnOid()));
        form.getConsumption().setBaseObject(new BaseObject());
        form.getConsumption().getBaseObject().setSeqNo(oldObj.getBaseObject().getSeqNo());
        form.getConsumption().getBaseObject().setUpdateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());
        form.getConsumption().getBaseObject().setUpdateTime(new Date());
        
        transactionService.updateConsumption(form);
        
        session.removeAttribute("cpnForm");
        
        return "consumption/summary";
    }*/

    @RequestMapping("/delete")
    public String delete(@RequestParam("cpnOid") BigDecimal cpnOid, Model model)
            throws ConsumptionKeyEmptyException, ConsumptionNotExistException,
            ConsumptionAlreadyConfirmedException, NewffmsSystemException {
        consumptionRepos.remove(new ConsumptionKey(cpnOid));
        return "redirect:/consumption/summary?keepSp=Y";
    }
    
    /*@RequestMapping("/confirm")
    public String confirm(@RequestParam("cpnOid") BigDecimal cpnOid, Model model, HttpSession session) throws SQLException {
        transactionService.confirmConsumption(cpnOid, SessionUtil.getInstance().getLoginUser(session).getUserName());
        
        return "redirect:/consumption/summary?keepSp=Y";
    }
    
    @RequestMapping("/rollback")
    public String rollback(@RequestParam("cpnOid") BigDecimal cpnOid, Model model, HttpSession session) throws SQLException {
        transactionService.rollbackConsumption(cpnOid, SessionUtil.getInstance().getLoginUser(session).getUserName());
        
        return "redirect:/consumption/summary?keepSp=Y";
    }*/
}
