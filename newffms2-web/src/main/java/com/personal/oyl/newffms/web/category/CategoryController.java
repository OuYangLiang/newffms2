package com.personal.oyl.newffms.web.category;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

import com.personal.oyl.newffms.category.domain.Category;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryKeyEmptyException;
import com.personal.oyl.newffms.category.domain.CategoryKey;
import com.personal.oyl.newffms.category.domain.CategoryRepos;
import com.personal.oyl.newffms.util.SessionUtil;
import com.personal.oyl.newffms.web.BaseController;

@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryRepos categoryRepos;
    /*@Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryValidator categoryValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(categoryValidator);
    }*/

    @RequestMapping("summary")
    public String summary(@RequestParam(value = "parentOid", required = false) BigDecimal parentOid, Model model)
            throws CategoryKeyEmptyException {
        model.addAttribute("parentOid", parentOid);

        if (null != parentOid) {
            Category category = categoryRepos.categoryOfId(new CategoryKey(parentOid));
            if (null != category.getParentKey()) {
                model.addAttribute("parentParentOid", category.getParentKey().getCategoryOid());
            }
        }

        return "category/summary";
    }

    /*@RequestMapping("/initAdd")
    public String initAdd(@RequestParam(value = "back", required = false) Boolean back,
            @RequestParam(value = "parentOid", required = false) BigDecimal parentOid, Model model, HttpSession session)
            throws SQLException {

        Category form = null;

        if (null != back && back && null != session.getAttribute("catForm")) {
            form = (Category) session.getAttribute("catForm");
        } else {
            form = new Category();
        }

        if (null != parentOid) {
            Category parent = categoryService.selectByKey(new CategoryKey(parentOid));
            form.setParent(parent);
            parent.setCategoryDesc(categoryService.selectFullDescByKey(parent.getCategoryOid()));
        }

        model.addAttribute("catForm", form);

        return "category/add";
    }

    @RequestMapping("/confirmAdd")
    public String confirmAdd(@Valid @ModelAttribute("catForm") Category form, BindingResult result, Model model,
            HttpSession session) throws SQLException {
        if (result.hasErrors()) {
            if (null != form.getParentOid()) {
                Category parent = categoryService.selectByKey(new CategoryKey(form.getParentOid()));
                form.setParent(parent);
            }

            model.addAttribute("validation", false);

            return "category/add";
        }

        if (null != form.getParentOid()) {
            Category parent = categoryService.selectByKey(new CategoryKey(form.getParentOid()));
            form.setParent(parent);
            parent.setCategoryDesc(categoryService.selectFullDescByKey(parent.getCategoryOid()));
        }

        session.setAttribute("catForm", form);

        return "category/confirmAdd";
    }

    @RequestMapping("/saveAdd")
    public String saveAdd(Model model, HttpSession session) throws SQLException {
        Category form = (Category) session.getAttribute("catForm");

        BaseObject base = new BaseObject();
        base.setCreateTime(new Date());
        base.setCreateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());

        form.setBaseObject(base);

        transactionService.createCategory(form);

        session.removeAttribute("catForm");

        return "category/summary";
    }*/

    @RequestMapping("/view")
    public String view(@RequestParam("categoryOid") BigDecimal categoryOid, Model model)
            throws CategoryKeyEmptyException {

        CategoryDto form = new CategoryDto(categoryRepos.categoryOfId(new CategoryKey(categoryOid)));

        model.addAttribute("catForm", form);
        model.addAttribute("removable",
                false/* !categoryService.isCategoryUsed(categoryOid) */);

        return "category/view";
    }

    /*@RequestMapping("/initEdit")
    public String initEdit(@RequestParam(value = "back", required = false) Boolean back,
            @RequestParam(value = "categoryOid", required = false) BigDecimal categoryOid, Model model,
            HttpSession session) throws SQLException {

        Category form = null;

        if (null != back && back && null != session.getAttribute("catForm")) {
            form = (Category) session.getAttribute("catForm");
        } else {
            form = categoryService.selectByKey(new CategoryKey(categoryOid));
            if (null != form.getParentOid()) {
                Category parent = categoryService.selectByKey(new CategoryKey(form.getParentOid()));
                form.setParent(parent);
                parent.setCategoryDesc(categoryService.selectFullDescByKey(parent.getCategoryOid()));
            }
        }

        model.addAttribute("catForm", form);
        return "category/edit";
    }

    @RequestMapping("/confirmEdit")
    public String confirmEdit(@Valid @ModelAttribute("catForm") Category form, BindingResult result, Model model,
            HttpSession session) throws SQLException {
        if (result.hasErrors()) {

            Category oldObj = categoryService.selectByKey(new CategoryKey(form.getCategoryOid()));
            form.setIsLeaf(oldObj.getIsLeaf());
            form.setCategoryLevel(oldObj.getCategoryLevel());
            if (!form.getIsLeaf()) {
                form.setMonthlyBudget(oldObj.getMonthlyBudget());
            }

            if (null != form.getParentOid()) {
                Category parent = categoryService.selectByKey(new CategoryKey(form.getParentOid()));
                form.setParent(parent);
                parent.setCategoryDesc(categoryService.selectFullDescByKey(parent.getCategoryOid()));
            }

            model.addAttribute("validation", false);
            return "category/edit";
        }

        Category oldObj = categoryService.selectByKey(new CategoryKey(form.getCategoryOid()));
        form.setIsLeaf(oldObj.getIsLeaf());
        form.setCategoryLevel(oldObj.getCategoryLevel());
        if (!form.getIsLeaf()) {
            form.setMonthlyBudget(oldObj.getMonthlyBudget());
        }

        if (null != form.getParentOid()) {
            Category parent = categoryService.selectByKey(new CategoryKey(form.getParentOid()));
            form.setParent(parent);
            parent.setCategoryDesc(categoryService.selectFullDescByKey(parent.getCategoryOid()));
        }

        session.setAttribute("catForm", form);

        return "category/confirmEdit";
    }

    @RequestMapping("/saveEdit")
    public String saveEdit(Model model, HttpSession session) throws SQLException {
        Category form = (Category) session.getAttribute("catForm");

        Category oldObj = categoryService.selectByKey(new CategoryKey(form.getCategoryOid()));
        form.setBaseObject(new BaseObject());
        form.getBaseObject().setSeqNo(oldObj.getBaseObject().getSeqNo());
        form.getBaseObject().setUpdateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());
        form.getBaseObject().setUpdateTime(new Date());

        if (!oldObj.getIsLeaf()) {
            form.setMonthlyBudget(null);
        }
        form.setCategoryLevel(null);
        form.setIsLeaf(null);

        transactionService.updateCategory(form);

        session.removeAttribute("catForm");

        return "category/summary";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("categoryOid") BigDecimal categoryOid, Model model, HttpSession session)
            throws SQLException {
        transactionService.deleteCategory(categoryOid, SessionUtil.getInstance().getLoginUser(session).getUserName());

        return "redirect:/category/summary";
    }*/

    @RequestMapping("/ajaxGetCategoriesByParent")
    @ResponseBody
    public List<CategoryDto> ajaxGetCategoriesByParent(
            @RequestParam(value = "parentOid", required = false) BigDecimal parentOid)
            throws CategoryKeyEmptyException {
        List<CategoryDto> list = new LinkedList<>();
        List<Category> cats = null;
        if (null == parentOid) {
            cats = categoryRepos.rootCategories();
        } else {
            cats = categoryRepos.categoriesOfParent(new CategoryKey(parentOid));
        }

        if (null != cats) {
            for (Category cat : cats) {
                list.add(new CategoryDto(cat));
            }
        }

        return list;
    }

    @RequestMapping("/ajaxGetAllCategories")
    @ResponseBody
    public List<CategoryDto> alaxGetAllAccounts() {
        List<Category> catList = categoryRepos.allCategories();
        List<CategoryDto> list = new LinkedList<>();
        for (Category cat : catList) {
            list.add(new CategoryDto(cat));
        }

        Map<BigDecimal, List<CategoryDto>> catMap = new HashMap<>();

        BigDecimal key = null;
        for (CategoryDto cat : list) {
            if (null == cat.getParentOid()) {
                key = BigDecimal.valueOf(-1);
            } else {
                key = cat.getParentOid();
            }

            if (catMap.containsKey(key)) {
                List<CategoryDto> cList = catMap.get(key);
                cList.add(cat);
            } else {
                List<CategoryDto> cList = new ArrayList<>();
                cList.add(cat);
                catMap.put(key, cList);
            }
        }

        for (CategoryDto cat : list) {
            if (null == cat.getParentOid()) {
                continue;
            }

            if (!cat.getLeaf()) {
                cat.setSubCategories(catMap.get(cat.getCategoryOid()));
            }
        }

        List<CategoryDto> rlt = new ArrayList<>();

        for (CategoryDto root : catMap.get(BigDecimal.valueOf(-1))) {
            rlt.add(root);
            root.setSubCategories(catMap.get(root.getCategoryOid()));
        }
        return rlt;
    }
}
