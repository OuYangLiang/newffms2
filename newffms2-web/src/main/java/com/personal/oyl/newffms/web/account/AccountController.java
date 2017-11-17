package com.personal.oyl.newffms.web.account;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.account.domain.Account;
import com.personal.oyl.newffms.account.domain.AccountException.AccountOwnerEmptyException;
import com.personal.oyl.newffms.account.domain.AccountRepos;
import com.personal.oyl.newffms.account.domain.AccountType;
import com.personal.oyl.newffms.user.domain.User;
import com.personal.oyl.newffms.user.domain.UserRepos;
import com.personal.oyl.newffms.web.BaseController;
import com.personal.oyl.newffms.web.user.UserDto;

@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    //private static final String SESSION_KEY_SEARCH_PARAM_ACCOUNT = "SESSION_KEY_SEARCH_PARAM_ACCOUNT";
    //private static final Map<String, String> colMapping;

    /*static {
        colMapping = new HashMap<String, String>();
        colMapping.put("owner.userName", "OWNER_OID");
        colMapping.put("acntType", "ACNT_TYPE");
    }*/
    
    @Autowired
    private AccountRepos acntRepos;
    @Autowired
    private UserRepos userRepos;

    /*@Autowired
    private AccountValidator accountValidator;*/

    /*@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(accountValidator);
    }*/

    @RequestMapping("summary")
    public String summary(HttpServletRequest request, Model model, HttpSession session) throws SQLException {
        return "account/summary";
    }

   /* @RequestMapping("/initAdd")
    public String initAdd(@RequestParam(value = "back", required = false) Boolean back, Model model,
            HttpSession session) throws SQLException {

        Account form = null;

        if (null != back && back && null != session.getAttribute("acntForm")) {
            form = (Account) session.getAttribute("acntForm");
        } else {
            form = new Account();
        }

        model.addAttribute("acntForm", form);
        model.addAttribute("acntTypes", AccountType.toMapValue());
        model.addAttribute("users", userProfileService.selectAllUsers());

        return "account/add";
    }

    @RequestMapping("/confirmAdd")
    public String confirmAdd(@Valid @ModelAttribute("acntForm") Account form, BindingResult result, Model model,
            HttpSession session) throws SQLException {
        if (result.hasErrors()) {
            model.addAttribute("acntTypes", AccountType.toMapValue());
            model.addAttribute("users", userProfileService.selectAllUsers());
            model.addAttribute("validation", false);

            return "account/add";
        }

        form.setOwner(userProfileService.selectByKey(new UserProfileKey(form.getOwnerOid())));

        session.setAttribute("acntForm", form);

        return "account/confirmAdd";
    }

    @RequestMapping("/saveAdd")
    public String saveAdd(Model model, HttpSession session) throws SQLException {
        Account form = (Account) session.getAttribute("acntForm");

        BaseObject base = new BaseObject();
        base.setCreateTime(new Date());
        base.setCreateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());

        form.setBaseObject(base);

        transactionService.createAccount(form);

        session.removeAttribute("acntForm");

        return "redirect:/account/summary?keepSp=Y";
    }

    @RequestMapping("/view")
    public String view(@RequestParam("acntOid") BigDecimal acntOid, Model model) throws SQLException {
        Account form = accountService.selectByKey(new AccountKey(acntOid));

        form.setOwner(userProfileService.selectByKey(new UserProfileKey(form.getOwnerOid())));

        model.addAttribute("acntForm", form);
        model.addAttribute("isAccountSafeToRemove", accountService.isAccountSafeToRemove(acntOid));

        return "account/view";
    }

    @RequestMapping("/initEdit")
    public String initEdit(@RequestParam(value = "back", required = false) Boolean back,
            @RequestParam(value = "acntOid", required = false) BigDecimal acntOid, Model model, HttpSession session)
            throws SQLException {

        Account form = null;

        if (null != back && back && null != session.getAttribute("acntForm")) {
            form = (Account) session.getAttribute("acntForm");
        } else {
            form = accountService.selectByKey(new AccountKey(acntOid));
            form.setOwner(userProfileService.selectByKey(new UserProfileKey(form.getOwnerOid())));
        }

        model.addAttribute("acntForm", form);
        model.addAttribute("acntTypes", AccountType.toMapValue());
        model.addAttribute("users", userProfileService.selectAllUsers());

        return "account/edit";
    }

    @RequestMapping("/confirmEdit")
    public String confirmEdit(@Valid @ModelAttribute("acntForm") Account form, BindingResult result, Model model,
            HttpSession session) throws SQLException {
        if (result.hasErrors()) {
            model.addAttribute("acntTypes", AccountType.toMapValue());
            model.addAttribute("users", userProfileService.selectAllUsers());
            model.addAttribute("validation", false);

            return "account/edit";
        }

        form.setOwner(userProfileService.selectByKey(new UserProfileKey(form.getOwnerOid())));

        session.setAttribute("acntForm", form);

        return "account/confirmEdit";
    }

    @RequestMapping("/saveEdit")
    public String saveEdit(Model model, HttpSession session) throws SQLException {
        Account form = (Account) session.getAttribute("acntForm");

        Account oldObj = accountService.selectByKey(new AccountKey(form.getAcntOid()));
        form.setBaseObject(new BaseObject());
        form.getBaseObject().setSeqNo(oldObj.getBaseObject().getSeqNo());
        form.getBaseObject().setUpdateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());
        form.getBaseObject().setUpdateTime(new Date());

        transactionService.updateAccount(form);

        session.removeAttribute("acntForm");

        return "redirect:/account/summary?keepSp=Y";
    }

    @RequestMapping("/initTransfer")
    public String initTransfer(@RequestParam(value = "acntOid", required = false) BigDecimal acntOid, Model model,
            HttpSession session) throws SQLException {

        Account form = null;

        if (null != session.getAttribute("acntForm")) {
            form = (Account) session.getAttribute("acntForm");
        } else {
            form = accountService.selectByKey(new AccountKey(acntOid));
            form.setOwner(userProfileService.selectByKey(new UserProfileKey(form.getOwnerOid())));
        }

        model.addAttribute("acntForm", form);

        return "account/transfer";
    }

    @RequestMapping("/confirmTransfer")
    public String confirmTransfer(@Valid @ModelAttribute("acntForm") Account form, BindingResult result, Model model,
            HttpSession session) throws SQLException {
        if (result.hasErrors()) {
            form.setOwner(userProfileService.selectByKey(new UserProfileKey(form.getOwnerOid())));
            form.setTarget(accountService.selectByKey(new AccountKey(form.getTarget().getAcntOid())));
            form.getTarget()
                    .setOwner(userProfileService.selectByKey(new UserProfileKey(form.getTarget().getOwnerOid())));

            model.addAttribute("validation", false);

            return "account/transfer";
        }

        form.setOwner(userProfileService.selectByKey(new UserProfileKey(form.getOwnerOid())));

        form.setTarget(accountService.selectByKey(new AccountKey(form.getTarget().getAcntOid())));
        form.getTarget().setOwner(userProfileService.selectByKey(new UserProfileKey(form.getTarget().getOwnerOid())));

        session.setAttribute("acntForm", form);

        return "account/confirmTransfer";
    }

    @RequestMapping("/saveTransfer")
    public String saveTransfer(Model model, HttpSession session) throws SQLException {
        Account form = (Account) session.getAttribute("acntForm");

        transactionService.doAccountTransfer(form.getAcntOid(), form.getTarget().getAcntOid(), form.getPayment(),
                SessionUtil.getInstance().getLoginUser(session).getUserName());

        session.removeAttribute("acntForm");

        return "redirect:/account/summary?keepSp=Y";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("acntOid") BigDecimal acntOid, Model model) throws SQLException {
        transactionService.deleteAccount(acntOid);

        return "redirect:/account/summary?keepSp=Y";
    }

    @RequestMapping("/listOfItemSummary")
    @ResponseBody
    public BootstrapTableJsonRlt<AccountAudit> listOfItemSummary(
            @RequestParam(value = "acntOid", required = true) BigDecimal acntOid,
            @RequestParam(value = "offset", required = true) int offset,
            @RequestParam(value = "limit", required = true) int limit,
            @RequestParam(value = "sort", required = true) String sort,
            @RequestParam(value = "order", required = true) String order, HttpSession session) throws SQLException {

        int sizePerPage = limit;
        int requestPage = offset / limit + 1;
        String sortField = sort;
        String sortDir = order;

        // 从session中取出查询对象并查询
        AccountAudit searchParam = new AccountAudit();
        searchParam.setAcntOid(acntOid);
        searchParam.setStart((requestPage - 1) * sizePerPage);
        searchParam.setSizePerPage(sizePerPage);
        searchParam.setRequestPage(requestPage);

        if (sortField != null && !sortField.trim().isEmpty()) {
            searchParam.setSortField(sortField);
            searchParam.setSortDir(sortDir);
        }

        return this.initBootstrapPaging(accountAuditService, searchParam);
    }*/

    @RequestMapping("/ajaxGetAllAccounts")
    @ResponseBody
    public List<Map<String, Object>> alaxGetAllAccounts() {
        List<Map<String, Object>> rlt = new LinkedList<>();
        
        List<User> users = userRepos.queryAllUser();
        if (null != users) {
            for (User user : users) {
                try {
                    List<Account> accounts = acntRepos.accountsOfUser(user.getKey());
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
                            }
                            totalBal = totalBal.add(account.getBalance());
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

}
