package com.personal.oyl.newffms.web.report;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.category.domain.Category;
import com.personal.oyl.newffms.category.domain.CategoryRepos;
import com.personal.oyl.newffms.report.HighChartResult;
import com.personal.oyl.newffms.report.ReportService;
import com.personal.oyl.newffms.common.DateUtil;
import com.personal.oyl.newffms.web.category.CategoryDto;

@Controller
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private CategoryRepos categoryRepos;
    
    @RequestMapping("/consumption")
    public String consumption(Model model) {
        List<Category> categories = categoryRepos.rootCategories();
        if (null != categories) {
            List<CategoryDto> categoryList = new ArrayList<>(categories.size());
            for (Category cat : categories) {
                categoryList.add(new CategoryDto(cat));
            }
            model.addAttribute("rootCategories", categoryList);
        } else {
            model.addAttribute("rootCategories", Collections.EMPTY_LIST);
        }
        
        return "/report/consumption";
    }
    
    @RequestMapping("/incoming")
    public String incoming(Model model) throws SQLException {
        String[] years = new String[10];
        for (int i = 0; i < 10; i++) {
            years[i] = Integer.toString(2010 + i);
        }

        model.addAttribute("years", years);
        model.addAttribute("curYear", DateUtil.getInstance().getYear(new Date()));
        return "/report/incoming";
    }
    
    @RequestMapping("/queryIncomingByMonth")
    @ResponseBody
    public HighChartResult queryIncomingByMonth(@RequestParam("year") String year) {
        Date startParam = DateUtil.getInstance().getFirstTimeOfYear(year);
        Date endParam = DateUtil.getInstance().getLastTimeOfYear(year);
        HighChartResult rlt = reportService.queryIncomingByMonth(startParam, endParam);
        rlt.setTitle(year + "年收入明细");
        return rlt;
    }
    
    @RequestMapping("/queryTotalIncoming")
    @ResponseBody
    public HighChartResult queryTotalIncoming(@RequestParam("year") String year) {
        Date startParam = DateUtil.getInstance().getFirstTimeOfYear(year);
        Date endParam = DateUtil.getInstance().getLastTimeOfYear(year);
        HighChartResult rlt = reportService.queryTotalIncoming(startParam, endParam);
        rlt.setTitle(year + "年各人收入汇总");
        return rlt;
    }
    
    @RequestMapping("/queryTotalIncomingByType")
    @ResponseBody
    public HighChartResult queryTotalIncomingByType(@RequestParam("year") String year) {
        Date startParam = DateUtil.getInstance().getFirstTimeOfYear(year);
        Date endParam = DateUtil.getInstance().getLastTimeOfYear(year);
        HighChartResult rlt = reportService.queryTotalIncomingByType(startParam, endParam);
        rlt.setTitle(year + "年分类别收入汇总");
        return rlt;
    }

    @RequestMapping("/queryUserAmtConsumption")
    @ResponseBody
    public HighChartResult queryUserAmtConsumption(
                @RequestParam(value = "mode", required = false) String mode,
                @RequestParam(value = "year", required = false) Integer year,
                @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "excludeCategories", required = false) String excludeCategories) throws Exception {
        
        Date startParam = null;
        Date endParam   = null;
        String title    = null;
        
        if ("monthly".equals(mode)) {
            startParam = DateUtil.getInstance().getFirstTimeOfMonth(year, month);
            endParam = DateUtil.getInstance().getLastTimeOfMonth(year, month);
            title = year + "年" + month + "月";
        } else if ("annually".equals(mode)) {
            title = Integer.toString(year) + "年";
            startParam = DateUtil.getInstance().getFirstTimeOfYear(Integer.toString(year));
            endParam = DateUtil.getInstance().getLastTimeOfYear(Integer.toString(year));
        } else {
            throw new Exception("不可识别的参数mode: " + mode);
        }
        
        HighChartResult result = null;
        if (null == excludeCategories) {
            result = reportService.queryUserAmtConsumption(startParam, endParam, null);
        } else {
            String[] excludeCategoryOidStrs = excludeCategories.split("\\|");
            Set<BigDecimal> excludeCategoryOids = new HashSet<>();
            for (String excludeCategoryOidStr : excludeCategoryOidStrs) {
                excludeCategoryOids.add(new BigDecimal(excludeCategoryOidStr));
            }
            result = reportService.queryUserAmtConsumption(startParam, endParam, excludeCategoryOids);
        }
        result.setTitle(title);
        return result;
    }
    
    @RequestMapping("/queryUserRatioConsumption")
    @ResponseBody
    public HighChartResult queryUserRatioConsumption(
                @RequestParam(value = "mode", required = false) String mode,
                @RequestParam(value = "year", required = false) Integer year,
                @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "excludeCategories", required = false) String excludeCategories) throws Exception {
        
        Date startParam = null;
        Date endParam   = null;
        String title    = null;
        
        if ("monthly".equals(mode)) {
            startParam = DateUtil.getInstance().getFirstTimeOfMonth(year, month);
            endParam = DateUtil.getInstance().getLastTimeOfMonth(year, month);
            title = year + "年" + month + "月";
        } else if ("annually".equals(mode)) {
            title = Integer.toString(year) + "年";
            startParam = DateUtil.getInstance().getFirstTimeOfYear(Integer.toString(year));
            endParam = DateUtil.getInstance().getLastTimeOfYear(Integer.toString(year));
        } else {
            throw new Exception("不可识别的参数mode: " + mode);
        }
        
        HighChartResult result = null;
        if (null == excludeCategories) {
            result = reportService.queryUserRatioConsumption(startParam, endParam, null);
        } else {
            String[] excludeCategoryOidStrs = excludeCategories.split("\\|");
            Set<BigDecimal> excludeCategoryOids = new HashSet<>();
            for (String excludeCategoryOidStr : excludeCategoryOidStrs) {
                excludeCategoryOids.add(new BigDecimal(excludeCategoryOidStr));
            }
            result = reportService.queryUserRatioConsumption(startParam, endParam, excludeCategoryOids);
        }
        result.setTitle(title);
        return result;
    }
    
    @RequestMapping("/queryCategoryRatioConsumption")
    @ResponseBody
    public HighChartResult queryCategoryRatioConsumption(
                @RequestParam(value = "mode", required = false) String mode,
                @RequestParam(value = "year", required = false) Integer year,
                @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "excludeCategories", required = false) String excludeCategories) throws Exception {
        
        Date startParam = null;
        Date endParam   = null;
        String title    = null;
        
        if ("monthly".equals(mode)) {
            startParam = DateUtil.getInstance().getFirstTimeOfMonth(year, month);
            endParam = DateUtil.getInstance().getLastTimeOfMonth(year, month);
            title = year + "年" + month + "月";
        } else if ("annually".equals(mode)) {
            title = Integer.toString(year) + "年";
            startParam = DateUtil.getInstance().getFirstTimeOfYear(Integer.toString(year));
            endParam = DateUtil.getInstance().getLastTimeOfYear(Integer.toString(year));
        } else {
            throw new Exception("不可识别的参数mode: " + mode);
        }
        
        HighChartResult result = null;
        if (null == excludeCategories) {
            result = reportService.querycategoryRatioConsumption(startParam, endParam, null);
        } else {
            String[] excludeCategoryOidStrs = excludeCategories.split("\\|");
            Set<BigDecimal> excludeCategoryOids = new HashSet<>();
            for (String excludeCategoryOidStr : excludeCategoryOidStrs) {
                excludeCategoryOids.add(new BigDecimal(excludeCategoryOidStr));
            }
            result = reportService.querycategoryRatioConsumption(startParam, endParam, excludeCategoryOids);
        }
        result.setTitle(title);
        return result;
    }
    
    @RequestMapping("/queryDetailConsumption")
    @ResponseBody
    public HighChartResult queryDetailConsumption(
                @RequestParam(value = "mode", required = false) String mode,
                @RequestParam(value = "year", required = false) Integer year,
                @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "excludeCategories", required = false) String excludeCategories) throws Exception {
        
        Date startParam = null;
        Date endParam   = null;
        String title    = null;
        
        if ("monthly".equals(mode)) {
            startParam = DateUtil.getInstance().getFirstTimeOfMonth(year, month);
            endParam = DateUtil.getInstance().getLastTimeOfMonth(year, month);
            title = year + "年" + month + "月";
        } else if ("annually".equals(mode)) {
            title = Integer.toString(year) + "年";
            startParam = DateUtil.getInstance().getFirstTimeOfYear(Integer.toString(year));
            endParam = DateUtil.getInstance().getLastTimeOfYear(Integer.toString(year));
        } else {
            throw new Exception("不可识别的参数mode: " + mode);
        }
        
        HighChartResult result = null;
        if (null == excludeCategories) {
            result = reportService.queryDetailConsumption(startParam, endParam, null);
        } else {
            String[] excludeCategoryOidStrs = excludeCategories.split("\\|");
            Set<BigDecimal> excludeCategoryOids = new HashSet<>();
            for (String excludeCategoryOidStr : excludeCategoryOidStrs) {
                excludeCategoryOids.add(new BigDecimal(excludeCategoryOidStr));
            }
            result = reportService.queryDetailConsumption(startParam, endParam, excludeCategoryOids);
        }
        result.setTitle(title);
        return result;
    }
}
