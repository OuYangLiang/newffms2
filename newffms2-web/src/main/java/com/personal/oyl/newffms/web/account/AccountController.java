package com.personal.oyl.newffms.web.account;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.personal.oyl.newffms.account.domain.Account;
import com.personal.oyl.newffms.account.domain.AccountAuditVo;
import com.personal.oyl.newffms.account.domain.AccountException.AccountKeyEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountOwnerEmptyException;
import com.personal.oyl.newffms.account.domain.AccountKey;
import com.personal.oyl.newffms.account.domain.AccountRepos;
import com.personal.oyl.newffms.account.domain.AccountType;
import com.personal.oyl.newffms.common.NewffmsDomainException;
import com.personal.oyl.newffms.common.Tuple;
import com.personal.oyl.newffms.user.domain.User;
import com.personal.oyl.newffms.user.domain.UserException.UserKeyEmptyException;
import com.personal.oyl.newffms.user.domain.UserKey;
import com.personal.oyl.newffms.user.domain.UserRepos;
import com.personal.oyl.newffms.util.BootstrapTableJsonRlt;
import com.personal.oyl.newffms.util.SessionUtil;
import com.personal.oyl.newffms.web.BaseController;
import com.personal.oyl.newffms.web.user.UserDto;

@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    
    @Autowired
    private AccountRepos acntRepos;
    @Autowired
    private UserRepos userRepos;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new AccountDtoValidator());
    }

    @RequestMapping("summary")
    public String summary(HttpServletRequest request, Model model, HttpSession session) throws SQLException {
        return "account/summary";
    }

    @RequestMapping("/initAdd")
    public String initAdd(@RequestParam(value = "back", required = false) Boolean back, Model model,
            HttpSession session) throws SQLException {

        AccountDto form = null;

        if (null != back && back && null != session.getAttribute("acntForm")) {
            form = (AccountDto) session.getAttribute("acntForm");
        } else {
            form = new AccountDto();
        }
        
        List<User> users = userRepos.queryAllUser();
        List<UserDto> userList = new LinkedList<>();
        for (User user : users) {
            userList.add(new UserDto(user));
        }

        model.addAttribute("acntForm", form);
        model.addAttribute("acntTypes", AccountType.toMapValue());
        model.addAttribute("users", userList);

        return "account/add";
    }

    @RequestMapping("/confirmAdd")
    public String confirmAdd(@Valid @ModelAttribute("acntForm") AccountDto form, BindingResult result, Model model,
            HttpSession session) throws UserKeyEmptyException {
        if (result.hasErrors()) {
            List<User> users = userRepos.queryAllUser();
            List<UserDto> userList = new LinkedList<>();
            for (User user : users) {
                userList.add(new UserDto(user));
            }
            
            model.addAttribute("acntTypes", AccountType.toMapValue());
            model.addAttribute("users", userList);
            model.addAttribute("validation", false);
            
            return "account/add";
        }

        form.setOwner(new UserDto(userRepos.userOfId(new UserKey(form.getOwner().getUserOid()))));
        session.setAttribute("acntForm", form);

        return "account/confirmAdd";
    }

    @RequestMapping("/saveAdd")
    public String saveAdd(Model model, HttpSession session) {
        AccountDto form = (AccountDto) session.getAttribute("acntForm");
        
        Account account = form.toAccount();
        
        try {
            acntRepos.add(account, SessionUtil.getInstance().getLoginUser(session).getUserName());
        } catch (NewffmsDomainException e) {
            model.addAttribute("validation", false);
            model.addAttribute("errCode", e.getErrorCode());
            model.addAttribute("errMsg", e.getMessage());
            
            return "account/confirmAdd";
        }

        session.removeAttribute("acntForm");
        return "redirect:/account/summary?keepSp=Y";
    }

    @RequestMapping("/view")
    public String view(@RequestParam("acntOid") BigDecimal acntOid, Model model) throws AccountKeyEmptyException, UserKeyEmptyException {
        Account account = acntRepos.accountOfId(new AccountKey(acntOid));
        AccountDto form = new AccountDto(account);

        User user = userRepos.userOfId(account.getOwner());
        form.setOwner(new UserDto(user));

        model.addAttribute("acntForm", form);
        return "account/view";
    }

    @RequestMapping("/initTransfer")
    public String initTransfer(@RequestParam(value = "acntOid", required = false) BigDecimal acntOid, Model model,
            HttpSession session) throws AccountKeyEmptyException, UserKeyEmptyException {

        AccountDto form = null;

        if (null != session.getAttribute("acntForm")) {
            form = (AccountDto) session.getAttribute("acntForm");
        } else {
            Account account = acntRepos.accountOfId(new AccountKey(acntOid));
            form = new AccountDto(account);
            form.setOwner(new UserDto(userRepos.userOfId(account.getOwner())));
        }

        model.addAttribute("acntForm", form);
        return "account/transfer";
    }

    @RequestMapping("/confirmTransfer")
    public String confirmTransfer(@Valid @ModelAttribute("acntForm") AccountDto form, BindingResult result, Model model,
            HttpSession session) throws UserKeyEmptyException, AccountKeyEmptyException {
        form.setOwner(new UserDto(userRepos.userOfId(new UserKey(form.getOwner().getUserOid()))));
        form.setTarget(new AccountDto(acntRepos.accountOfId(new AccountKey(form.getTarget().getAcntOid()))));
        form.getTarget().setOwner(new UserDto(userRepos.userOfId(new UserKey(form.getTarget().getOwner().getUserOid()))));
        
        if (result.hasErrors()) {
            model.addAttribute("validation", false);
            return "account/transfer";
        }

        session.setAttribute("acntForm", form);
        return "account/confirmTransfer";
    }

    @RequestMapping("/saveTransfer")
    public String saveTransfer(Model model, HttpSession session) throws SQLException, AccountKeyEmptyException {
        AccountDto form = (AccountDto) session.getAttribute("acntForm");

        Account source = acntRepos.accountOfId(new AccountKey(form.getAcntOid()));
        Account target = acntRepos.accountOfId(new AccountKey(form.getTarget().getAcntOid()));

        try {
            source.getProxy().transfer(target, form.getPayment(), SessionUtil.getInstance().getLoginUser(session).getUserName());
        } catch (NewffmsDomainException e) {
            model.addAttribute("validation", false);
            model.addAttribute("errCode", e.getErrorCode());
            model.addAttribute("errMsg", e.getMessage());

            return "account/confirmTransfer";
        }

        session.removeAttribute("acntForm");
        return "redirect:/account/summary?keepSp=Y";
    }

    @RequestMapping("/listOfItemSummary")
    @ResponseBody
    public BootstrapTableJsonRlt listOfItemSummary(
            @RequestParam(value = "acntOid", required = true) BigDecimal acntOid,
            @RequestParam(value = "offset", required = true) int offset,
            @RequestParam(value = "limit", required = true) int limit,
            @RequestParam(value = "sort", required = true) String sort,
            @RequestParam(value = "order", required = true) String order, HttpSession session) throws AccountKeyEmptyException {

        int sizePerPage = limit;
        int requestPage = offset / limit + 1;
//        String sortField = sort;
//        String sortDir = order;

        Tuple<Integer, List<AccountAuditVo>> tuple = acntRepos.auditsOfAccount(new AccountKey(acntOid), requestPage, sizePerPage);
        return new BootstrapTableJsonRlt(tuple.first, tuple.second);
    }

    @RequestMapping("/alaxGetAllAccountsByUser")
    @ResponseBody
    public List<Map<String, Object>> alaxGetAllAccountsByUser(@RequestParam(value = "includeDisabled", required = true) boolean includeDisabled) {
        
        List<Map<String, Object>> rlt = new LinkedList<>();
        List<User> users = userRepos.queryAllUser();
        if (null != users) {
            for (User user : users) {
                try {
                    List<Account> accounts = acntRepos.accountsOfUser(user.getKey(), includeDisabled);
                    List<AccountDto> acnts = new LinkedList<>();
                    
                    Map<String, Object> item = new HashMap<>();
                    item.put("user", new UserDto(user));
                    item.put("accounts", acnts);
                    
                    BigDecimal totalBal = BigDecimal.ZERO;
                    BigDecimal totalDept= BigDecimal.ZERO;
                    Integer numOfAccounts = 0;

                    if (null != accounts) {
                        numOfAccounts = accounts.size();
                        for (Account account : accounts) {
                            acnts.add(new AccountDto(account));
                            if (AccountType.Creditcard.equals(account.getAcntType())) {
                                totalDept = totalDept.add(account.getDebt());
                            } else {
                                totalBal = totalBal.add(account.getBalance());
                            }
                        }
                    }
                    
                    item.put("numOfAccount", numOfAccounts);
                    item.put("totalBalance", totalBal);
                    item.put("totalDept", totalDept);
                    rlt.add(item);
                } catch (AccountOwnerEmptyException e) {
                    log.error(e.getErrorCode(), e);
                }
            }
        }

        return rlt;
    }
    
    @RequestMapping("/ajaxGetAllAccounts")
    @ResponseBody
    public List<AccountDto> alaxGetAllAccounts() throws AccountOwnerEmptyException {
        List<AccountDto> list = new LinkedList<>();
        List<User> userList = userRepos.queryAllUser();
        if (null != userList) {
            for (User user : userList) {
                List<Account> acntList = acntRepos.accountsOfUser(user.getKey(), false);
                if (null != acntList) {
                    for (Account acnt : acntList) {
                        AccountDto item = new AccountDto(acnt);
                        item.setOwner(new UserDto(user));
                        list.add(item);
                    }
                }
            }
        }
        return list;
    }

}
