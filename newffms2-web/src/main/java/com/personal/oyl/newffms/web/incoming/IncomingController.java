package com.personal.oyl.newffms.web.incoming;

import java.math.BigDecimal;
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
import com.personal.oyl.newffms.common.NewffmsDomainException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NewffmsSystemException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;
import com.personal.oyl.newffms.common.Tuple;
import com.personal.oyl.newffms.common.Util;
import com.personal.oyl.newffms.incoming.domain.Incoming;
import com.personal.oyl.newffms.incoming.domain.IncomingCondition;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingAlreadyConfirmedException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingKeyEmptyException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingNotConfirmedException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingNotExistException;
import com.personal.oyl.newffms.incoming.domain.IncomingKey;
import com.personal.oyl.newffms.incoming.domain.IncomingRepos;
import com.personal.oyl.newffms.incoming.domain.IncomingType;
import com.personal.oyl.newffms.user.domain.User;
import com.personal.oyl.newffms.user.domain.UserException.UserKeyEmptyException;
import com.personal.oyl.newffms.user.domain.UserKey;
import com.personal.oyl.newffms.user.domain.UserRepos;
import com.personal.oyl.newffms.util.BootstrapTableJsonRlt;
import com.personal.oyl.newffms.util.SessionUtil;
import com.personal.oyl.newffms.web.BaseController;
import com.personal.oyl.newffms.web.account.AccountDto;
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
    private AccountRepos acntRepos;
    @Autowired
    private IncomingRepos incomingRepos;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new IncomingValidator());
    }
    
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
    public String search(@RequestParam("ownerOid") BigDecimal ownerOid, @RequestParam("confirmed") Boolean confirmed,
            @RequestParam("incomingType") IncomingType incomingType, @RequestParam("incomingDesc") String incomingDesc,
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
        
        Tuple<Integer, List<Incoming>> tuple =
                incomingRepos.queryIncomings(searchParam, searchParam.getPaginationParameter());
        List<User> userList = userRepos.queryAllUser();
        Map<BigDecimal, UserDto> group = new HashMap<>();
        if (null != userList) {
            for (User user : userList) {
                group.put(user.getKey().getUserOid(), new UserDto(user));
            }
        }
        
        List<IncomingDto> list = new LinkedList<>();
        if (null != tuple.getSecond()) {
            for (Incoming incoming : tuple.getSecond()) {
                IncomingDto dto = new IncomingDto(incoming);
                dto.setOwner(group.get(incoming.getOwnerOid()));
                list.add(dto);
            }
        }
        return new BootstrapTableJsonRlt(tuple.getFirst(), list);
    }

    @RequestMapping("/initAdd")
    public String initAdd(@RequestParam(value = "back", required = false) Boolean back, Model model,
            HttpSession session) {

        IncomingDto form = null;

        if (null != back && back && null != session.getAttribute("incomingForm")) {
            form = (IncomingDto) session.getAttribute("incomingForm");
        } else {
            form = new IncomingDto();
        }
        
        List<User> users = userRepos.queryAllUser();
        List<UserDto> userList = new LinkedList<>();
        for (User user : users) {
            userList.add(new UserDto(user));
        }

        model.addAttribute("incomingForm", form);
        model.addAttribute("incomingTypes", IncomingType.toMapValue());
        model.addAttribute("users", userList);

        return "incoming/add";
    }

    @RequestMapping("/confirmAdd")
    public String confirmAdd(@Valid @ModelAttribute("incomingForm") IncomingDto form, BindingResult result, Model model,
            HttpSession session) throws UserKeyEmptyException, AccountKeyEmptyException {
        if (result.hasErrors()) {
            // 页面回显
            form.setTargetAccount(
                    new AccountDto(acntRepos.accountOfId(new AccountKey(form.getTargetAccount().getAcntOid()))));
            form.getTargetAccount().setOwner(
                    new UserDto(userRepos.userOfId(new UserKey(form.getTargetAccount().getOwner().getUserOid()))));

            List<User> users = userRepos.queryAllUser();
            List<UserDto> userList = new LinkedList<>();
            for (User user : users) {
                userList.add(new UserDto(user));
            }
            
            model.addAttribute("incomingTypes", IncomingType.toMapValue());
            model.addAttribute("users", userList);
            model.addAttribute("validation", false);

            return "incoming/add";
        }

        form.setOwner(new UserDto(userRepos.userOfId(new UserKey(form.getOwner().getUserOid()))));
        form.setTargetAccount(
                new AccountDto(acntRepos.accountOfId(new AccountKey(form.getTargetAccount().getAcntOid()))));
        form.getTargetAccount().setOwner(
                new UserDto(userRepos.userOfId(new UserKey(form.getTargetAccount().getOwner().getUserOid()))));

        session.setAttribute("incomingForm", form);
        return "incoming/confirmAdd";
    }

    @RequestMapping("/saveAdd")
    public String saveAdd(Model model, HttpSession session) {
        IncomingDto form = (IncomingDto) session.getAttribute("incomingForm");
        form.setBatchNum(Util.getInstance().generateBatchNum());
        Incoming incoming = form.toIncoming();
        try {
            incomingRepos.add(incoming, SessionUtil.getInstance().getLoginUser(session).getUserName());
        } catch (NewffmsDomainException e) {
            model.addAttribute("validation", false);
            model.addAttribute("errCode", e.getErrorCode());
            model.addAttribute("errMsg", e.getMessage());
            
            return "incoming/confirmAdd";
        }
        
        session.removeAttribute("incomingForm");
        return "redirect:/incoming/summary?keepSp=Y";
    }

    @RequestMapping("/view")
    public String view(@RequestParam("incomingOid") BigDecimal incomingOid, Model model)
            throws IncomingKeyEmptyException, UserKeyEmptyException, AccountKeyEmptyException {
        IncomingDto form = new IncomingDto(incomingRepos.incomingOfId(new IncomingKey(incomingOid)));

        form.setOwner(new UserDto(userRepos.userOfId(new UserKey(form.getOwner().getUserOid()))));
        form.setTargetAccount(new AccountDto(acntRepos.accountOfIncoming(new IncomingKey(incomingOid))));
        form.getTargetAccount().setOwner(
                new UserDto(userRepos.userOfId(new UserKey(form.getTargetAccount().getOwner().getUserOid()))));

        model.addAttribute("incomingForm", form);

        return "incoming/view";
    }

    @RequestMapping("/initEdit")
    public String initEdit(@RequestParam(value = "back", required = false) Boolean back,
            @RequestParam(value = "incomingOid", required = false) BigDecimal incomingOid, Model model,
            HttpSession session) throws IncomingKeyEmptyException, UserKeyEmptyException {

        IncomingDto form = null;

        if (null != back && back && null != session.getAttribute("incomingForm")) {
            form = (IncomingDto) session.getAttribute("incomingForm");
        } else {
            form = new IncomingDto(incomingRepos.incomingOfId(new IncomingKey(incomingOid)));
            form.setOwner(new UserDto(userRepos.userOfId(new UserKey(form.getOwner().getUserOid()))));
            form.setTargetAccount(new AccountDto(acntRepos.accountOfIncoming(new IncomingKey(incomingOid))));
            form.getTargetAccount().setOwner(
                    new UserDto(userRepos.userOfId(new UserKey(form.getTargetAccount().getOwner().getUserOid()))));
        }

        List<User> users = userRepos.queryAllUser();
        List<UserDto> userList = new LinkedList<>();
        for (User user : users) {
            userList.add(new UserDto(user));
        }
        
        model.addAttribute("incomingForm", form);
        model.addAttribute("incomingTypes", IncomingType.toMapValue());
        model.addAttribute("users", userList);

        return "incoming/edit";
    }

    @RequestMapping("/confirmEdit")
    public String confirmEdit(@Valid @ModelAttribute("incomingForm") IncomingDto form, BindingResult result,
            Model model, HttpSession session)
            throws UserKeyEmptyException, AccountKeyEmptyException, IncomingKeyEmptyException {
        if (result.hasErrors()) {
            // 页面回显
            form.setTargetAccount(new AccountDto(acntRepos.accountOfIncoming(new IncomingKey(form.getIncomingOid()))));
            form.getTargetAccount().setOwner(
                    new UserDto(userRepos.userOfId(new UserKey(form.getTargetAccount().getOwner().getUserOid()))));

            List<User> users = userRepos.queryAllUser();
            List<UserDto> userList = new LinkedList<>();
            for (User user : users) {
                userList.add(new UserDto(user));
            }
            
            model.addAttribute("incomingTypes", IncomingType.toMapValue());
            model.addAttribute("users", userList);
            model.addAttribute("validation", false);

            return "incoming/edit";
        }

        form.setOwner(new UserDto(userRepos.userOfId(new UserKey(form.getOwner().getUserOid()))));
        form.setTargetAccount(
                new AccountDto(acntRepos.accountOfId(new AccountKey(form.getTargetAccount().getAcntOid()))));
        form.getTargetAccount().setOwner(
                new UserDto(userRepos.userOfId(new UserKey(form.getTargetAccount().getOwner().getUserOid()))));

        session.setAttribute("incomingForm", form);
        return "incoming/confirmEdit";
    }

    @RequestMapping("/saveEdit")
    public String saveEdit(Model model, HttpSession session) throws IncomingKeyEmptyException {
        IncomingDto form = (IncomingDto) session.getAttribute("incomingForm");

        Incoming newObj = form.toIncoming();
        Incoming oldObj = incomingRepos.incomingOfId(newObj.getKey());
        newObj.setSeqNo(oldObj.getSeqNo());
        try {
            newObj.getProxy().updateAll(SessionUtil.getInstance().getLoginUser(session).getUserName());
        } catch (NewffmsDomainException e) {
            model.addAttribute("validation", false);
            model.addAttribute("errCode", e.getErrorCode());
            model.addAttribute("errMsg", e.getMessage());

            return "incoming/confirmEdit";
        }

        session.removeAttribute("incomingForm");
        return "redirect:/incoming/summary?keepSp=Y";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("incomingOid") BigDecimal incomingOid, Model model)
            throws IncomingKeyEmptyException, IncomingNotExistException, IncomingAlreadyConfirmedException,
            NewffmsSystemException {
        incomingRepos.remove(new IncomingKey(incomingOid));
        return "redirect:/incoming/summary?keepSp=Y";
    }

    @RequestMapping("/confirm")
    public String confirm(@RequestParam("incomingOid") BigDecimal incomingOid, Model model, HttpSession session)
            throws NoOperatorException, IncomingAlreadyConfirmedException, NewffmsSystemException,
            IncomingKeyEmptyException {
        Incoming incoming = incomingRepos.incomingOfId(new IncomingKey(incomingOid));
        incoming.getProxy().confirm(SessionUtil.getInstance().getLoginUser(session).getUserName());
        return "redirect:/incoming/summary?keepSp=Y";
    }

    @RequestMapping("/rollback")
    public String rollback(@RequestParam("incomingOid") BigDecimal incomingOid, Model model, HttpSession session)
            throws IncomingKeyEmptyException, NoOperatorException, IncomingNotConfirmedException,
            NewffmsSystemException {
        Incoming incoming = incomingRepos.incomingOfId(new IncomingKey(incomingOid));
        incoming.getProxy().unconfirm(SessionUtil.getInstance().getLoginUser(session).getUserName());
        return "redirect:/incoming/summary?keepSp=Y";
    }
}
