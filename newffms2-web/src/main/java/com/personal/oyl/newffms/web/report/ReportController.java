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
    
    
    /*@RequestMapping("/consumptionDataSource")
    @ResponseBody
    public HighChartResult consumptionDataSource(
    		@RequestParam(value = "mode", required = false) String mode,
    		@RequestParam(value = "year", required = false) Integer year,
    		@RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "excludeCategories", required = false) String excludeCategories) throws Exception {
        
        Date startParam = null;
        Date endParam   = null;
        String title 	= null;
        
        if ("monthly".equals(mode) ) {
        	startParam = DateUtil.getInstance().getFirstTimeOfMonth(year, month);
            endParam   = DateUtil.getInstance().getLastTimeOfMonth(year, month);
            title	   = year + "-" + month;
        } else if ("annually".equals(mode)) {
        	title	   = Integer.toString(year);
        	startParam = DateUtil.getInstance().getFirstTimeOfYear(title);
            endParam   = DateUtil.getInstance().getLastTimeOfYear(title);
        } else {
        	throw new Exception ("不可识别的参数mode: " + mode);
        }
        
        List<UserProfile> allUsers = userProfileService.selectAllUsers();
        List<Category> allCategories = null;
        List<CategoryConsumption> categoryConsumptions = null;
        
        if (null == excludeCategories) {
            allCategories = categoryService.selectAllCategories();
            categoryConsumptions = reportService.queryCategoryConsumptions(startParam, endParam, null);
        } else {
            String[] excludeCategoryOidStrs = excludeCategories.split("\\|");
            Set<BigDecimal> excludeCategoryOids = new HashSet<BigDecimal>();
            for (String excludeCategoryOidStr : excludeCategoryOidStrs) {
                excludeCategoryOids.add(new BigDecimal(excludeCategoryOidStr));
            }
            
            allCategories = categoryService.selectAllCategoriesWithExclusion(excludeCategoryOids);
            categoryConsumptions = reportService.queryCategoryConsumptions(startParam, endParam, excludeCategoryOids);
        }
        
        HighChartResult rlt = new HighChartResult();
        HighChartGraphResult colRlt = this.columnResult(categoryConsumptions, allCategories, allUsers, false);
        HighChartGraphResult pieRltOfAll = this.pieResultOfAll(categoryConsumptions, allCategories);
        HighChartGraphResult pieRltOfUser = this.pieResultOfUser(categoryConsumptions);
        HighChartGraphResult colRltOfAmount = this.columnResultOfAmount(categoryConsumptions);
        
        colRlt.setTitle(title);
        pieRltOfAll.setTitle(title);
        pieRltOfUser.setTitle(title);
        colRltOfAmount.setTitle(title);
        
        rlt.setColRlt(colRlt);
        rlt.setPieRltOfAll(pieRltOfAll);
        rlt.setPieRltOfUser(pieRltOfUser);
        rlt.setColRltOfAmount(colRltOfAmount);
        
        return rlt;
    }
    
    
    @RequestMapping("/incoming")
    public String incoming(Model model) throws SQLException {
        
        String[] years = new String[10];
        
        for (int i = 0; i < 10; i++)
            years[i] = Integer.toString(2010 + i);
        
        model.addAttribute("years", years);
        model.addAttribute("curYear", DateUtil.getInstance().getYear(new Date()));
        
        return "/report/incoming";
    }
    
    
    @RequestMapping("/incomingDataSource")
    @ResponseBody
    public HighChartResult incomingDataSource(@RequestParam("start") String start, @RequestParam("end") String end)
            throws SQLException {
        
        Date startParam = DateUtil.getInstance().getFirstTimeOfYear(start);
        Date endParam   = DateUtil.getInstance().getLastTimeOfYear(end);
        
        List<Incoming> incomings = incomingService.selectByIncomingDateRange(startParam, endParam);
        List<String> allMonths = months(start, end);
        
        //按月分总额，每个用户的总额，每个收入类别的总额
        BigDecimal totalAmt = BigDecimal.ZERO;
        Map<String, BigDecimal> total = new HashMap<String, BigDecimal>();//key: yyMM
        Map<String, BigDecimal> userMonthTotal = new HashMap<String, BigDecimal>();//key: userOid-yyMM
        Map<String, BigDecimal> typeMonthTotal = new HashMap<String, BigDecimal>();//key: type-yyMM
        Map<String, BigDecimal> userTotal = new HashMap<String, BigDecimal>();//key: userOid
        Map<String, BigDecimal> typeTotal = new HashMap<String, BigDecimal>();//key: type
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        for (Incoming incoming : incomings) {
        	if (!incoming.getConfirmed())
        		continue;//过滤掉未确认的收入记录。
        	
        	totalAmt = totalAmt.add(incoming.getAmount());
        	
            String date = sdf.format(incoming.getIncomingDate());
            
            String key = date;
            if (total.containsKey(key)) {
                total.put(key, total.get(key).add(incoming.getAmount()));
            } else {
                total.put(key, incoming.getAmount());
            }
            
            key = incoming.getOwnerOid().toString();
            if (userTotal.containsKey(key)) {
            	userTotal.put(key, userTotal.get(key).add(incoming.getAmount()));
            } else {
            	userTotal.put(key, incoming.getAmount());
            }
            
            key = incoming.getOwnerOid() + "-" + date;
            if (userMonthTotal.containsKey(key)) {
                userMonthTotal.put(key, userMonthTotal.get(key).add(incoming.getAmount()));
            } else {
                userMonthTotal.put(key, incoming.getAmount());
            }
            
            key = incoming.getIncomingType().getDesc() + "-" + date;
            if (typeMonthTotal.containsKey(key)) {
                typeMonthTotal.put(key, typeMonthTotal.get(key).add(incoming.getAmount()));
            } else {
                typeMonthTotal.put(key, incoming.getAmount());
            }
            
            key = incoming.getIncomingType().getDesc();
            if (typeTotal.containsKey(key)) {
            	typeTotal.put(key, typeTotal.get(key).add(incoming.getAmount()));
            } else {
            	typeTotal.put(key, incoming.getAmount());
            }
        }
        
        
        String title = start.equals(end) ? end + "年收入情况" : start + "年 ~ " + end + "年收入情况";
        List<UserProfile> allUsers = userProfileService.selectAllUsers();
        
        HighChartResult rlt = new HighChartResult();
        rlt.setIncomingOfUser(this.incomingResultOfUser(allUsers, title, allMonths, total, userMonthTotal));
        rlt.setIncomingOfType(this.incomingResultOfType(title, allMonths, total, typeMonthTotal));
        rlt.setIncomingOfAll(this.incomingResultOfAll(allUsers, title, totalAmt, userTotal, typeTotal));
        
        return rlt;
    }
    
    
	private HighChartGraphResult incomingResultOfType(String title,
			List<String> allMonths, Map<String, BigDecimal> total,
			Map<String, BigDecimal> typeMonthTotal) {
    	
        HighChartGraphResult rlt = new HighChartGraphResult();
        rlt.setTitle(title);
        rlt.setSeries(new ArrayList<HightChartSeries>());
        
        //处理总入收
        HightChartSeries series = new HightChartSeries();
        series.setName("总收入");
        series.setData(new ArrayList<HightChartSeries>());
        
        for (String month : allMonths) {
            HightChartSeries innerSeries = new HightChartSeries();
            innerSeries.setName(month.endsWith("01") ? month.substring(0, 2) + "年1月" : month.substring(2, 4) + "月");
            BigDecimal y = total.get(month);
            innerSeries.setY(null == y ? BigDecimal.ZERO : y);
            series.getData().add(innerSeries);
        }
        
        rlt.getSeries().add(series);
        
        //按收入类别分组
        for (Map.Entry<String, String> entry : IncomingType.toMapValue().entrySet()) {
            series = new HightChartSeries();
            series.setName(entry.getValue());
            series.setData(new ArrayList<HightChartSeries>());
            
            for (String month : allMonths) {
                HightChartSeries innerSeries = new HightChartSeries();
                innerSeries.setName(month.endsWith("01") ? month.substring(0, 2) + "年1月" : month.substring(2, 4) + "月");
                BigDecimal y = typeMonthTotal.get(entry.getValue() + "-" + month);
                innerSeries.setY(null == y ? BigDecimal.ZERO : y);
                series.getData().add(innerSeries);
            }
            
            rlt.getSeries().add(series);
        }
        
        return rlt;
    }
    
    
	private HighChartGraphResult incomingResultOfUser(
			List<UserProfile> allUsers, String title, List<String> allMonths,
			Map<String, BigDecimal> total,
			Map<String, BigDecimal> userMonthTotal) {
		
		HighChartGraphResult rlt = new HighChartGraphResult();
        rlt.setTitle(title);
        rlt.setSeries(new ArrayList<HightChartSeries>());
        
        //处理总入收
        HightChartSeries series = new HightChartSeries();
        series.setName("总收入");
        series.setData(new ArrayList<HightChartSeries>());
        
        for (String month : allMonths) {
            HightChartSeries innerSeries = new HightChartSeries();
            innerSeries.setName(month.endsWith("01") ? month.substring(0, 2) + "年1月" : month.substring(2, 4) + "月");
            BigDecimal y = total.get(month);
            innerSeries.setY(null == y ? BigDecimal.ZERO : y);
            series.getData().add(innerSeries);
        }
        
        rlt.getSeries().add(series);
        
        //按人分组
        for (UserProfile user : allUsers) {
            series = new HightChartSeries();
            series.setName(user.getUserName());
            series.setData(new ArrayList<HightChartSeries>());
            
            for (String month : allMonths) {
                HightChartSeries innerSeries = new HightChartSeries();
                innerSeries.setName(month.endsWith("01") ? month.substring(0, 2) + "年1月" : month.substring(2, 4) + "月");
                BigDecimal y = userMonthTotal.get(user.getUserOid() + "-" + month);
                innerSeries.setY(null == y ? BigDecimal.ZERO : y);
                series.getData().add(innerSeries);
            }
            
            rlt.getSeries().add(series);
        }
        
        return rlt;
    }
    
    
	private HighChartGraphResult incomingResultOfAll(
			List<UserProfile> allUsers, String title, BigDecimal totalAmt,
			Map<String, BigDecimal> userTotal, Map<String, BigDecimal> typeTotal) {
		
		HighChartGraphResult rlt = new HighChartGraphResult();
    	rlt.setTitle(title);
    	rlt.setSeries(new ArrayList<HightChartSeries>());
    	
    	HightChartSeries series = new HightChartSeries();
        series.setName("收入额");
        series.setType("column");
        series.setData(new ArrayList<HightChartSeries>());
        rlt.getSeries().add(series);
        
        series = new HightChartSeries();
        series.setName("总收入");
        series.setY(totalAmt);
        rlt.getSeries().get(0).getData().add(series);
        
        for (UserProfile user : allUsers) {
			String key = user.getUserOid().toString();
			BigDecimal amount = userTotal.get(key);
			
			if (null == amount)
				continue;
			
			series = new HightChartSeries();
            series.setName(user.getUserName());
            series.setY(amount);
            
            rlt.getSeries().get(0).getData().add(series);
		}
        
        for (Map.Entry<String, String> entry : IncomingType.toMapValue().entrySet()) {
        	String key = entry.getValue();
        	BigDecimal amount = typeTotal.get(key);
        	
        	if (null == amount)
				continue;
			
			series = new HightChartSeries();
            series.setName(key);
            series.setY(amount);
            
            rlt.getSeries().get(0).getData().add(series);
        }
		
		return rlt;
    }
    
    
    private HighChartGraphResult pieResultOfUser(List<CategoryConsumption> categoryConsumptions) {
        BigDecimal total = BigDecimal.ZERO;
        Map<String, BigDecimal> userSumMap = new HashMap<String, BigDecimal>();
        
        for (CategoryConsumption item : categoryConsumptions) {
            if (item.getCategoryLevel() == 0 && !BigDecimal.valueOf(-1).equals(item.getUserOid())) {
                total = total.add(item.getTotal());
                
                if (userSumMap.containsKey(item.getUserName())) {
                    BigDecimal oldValue = userSumMap.get(item.getUserName());
                    userSumMap.put(item.getUserName(), oldValue.add(item.getTotal()));
                } else {
                    userSumMap.put(item.getUserName(), item.getTotal());
                }
            }
        }
        
        //初始化返回对象
        HighChartGraphResult rlt = new HighChartGraphResult();
        List<HightChartSeries> seriesList = new ArrayList<HightChartSeries>();
        rlt.setSeries(seriesList);
        
        if (total.compareTo(BigDecimal.ZERO) == 0) {
            //总消费为0的话，值图无意义。
            return rlt;
        }
        
        HightChartSeries series = new HightChartSeries();
        series.setName("消费比");
        series.setType("pie");
        series.setData(new ArrayList<HightChartSeries>());
        seriesList.add(series);
        
        for (Map.Entry<String, BigDecimal> entry : userSumMap.entrySet() ) {
            BigDecimal userTotal = entry.getValue();
            
            HightChartSeries innerSeries = new HightChartSeries();
            innerSeries.setName(entry.getKey());
            innerSeries.setType("pie");
            innerSeries.setY(userTotal.divide(total, 4, RoundingMode.HALF_UP));
            
            series.getData().add(innerSeries);
        }
        
        return rlt;
    }
    
    
    private HighChartGraphResult columnResultOfAmount(List<CategoryConsumption> categoryConsumptions) {
        BigDecimal total = BigDecimal.ZERO;
        Map<String, BigDecimal> userSumMap = new HashMap<String, BigDecimal>();
        
        for (CategoryConsumption item : categoryConsumptions) {
            if (item.getCategoryLevel() == 0 && !BigDecimal.valueOf(-1).equals(item.getUserOid())) {
                total = total.add(item.getTotal());
                
                if (userSumMap.containsKey(item.getUserName())) {
                    BigDecimal oldValue = userSumMap.get(item.getUserName());
                    userSumMap.put(item.getUserName(), oldValue.add(item.getTotal()));
                } else {
                    userSumMap.put(item.getUserName(), item.getTotal());
                }
            }
        }
        
        //初始化返回对象
        HighChartGraphResult rlt = new HighChartGraphResult();
        List<HightChartSeries> seriesList = new ArrayList<HightChartSeries>();
        rlt.setSeries(seriesList);
        
        if (total.compareTo(BigDecimal.ZERO) == 0) {
            //总消费为0的话，值图无意义。
            return rlt;
        }
        
        HightChartSeries series = new HightChartSeries();
        series.setName("消费金额");
        series.setType("column");
        series.setData(new ArrayList<HightChartSeries>());
        seriesList.add(series);
        
        HightChartSeries innerSeries = new HightChartSeries();
        innerSeries.setName("全部");
        innerSeries.setY(total);
        series.getData().add(innerSeries);
        
        for (Map.Entry<String, BigDecimal> entry : userSumMap.entrySet() ) {
            innerSeries = new HightChartSeries();
            innerSeries.setName(entry.getKey());
            innerSeries.setY(entry.getValue());
            
            series.getData().add(innerSeries);
        }
        
        return rlt;
    }
    
    
    private HighChartGraphResult pieResultOfAll(List<CategoryConsumption> categoryConsumptions, List<Category> allCategories) {
        Map<String, CategoryConsumption> categoryConsumptionsMap = new HashMap<String, CategoryConsumption>();
        Map<BigDecimal, BigDecimal> sumMap = new HashMap<BigDecimal, BigDecimal>();
        for (CategoryConsumption item : categoryConsumptions) {
            categoryConsumptionsMap.put(item.getCategoryOid() + "_" + item.getUserOid(), item);
            
            if (BigDecimal.valueOf(-1).equals(item.getUserOid())) {
                BigDecimal key = null;
                if (item.getCategoryLevel() == 0) {
                    key = BigDecimal.valueOf(-1);
                }else {
                    key = item.getParentOid();
                }
                
                if (sumMap.containsKey(key)) {
                    BigDecimal oldSum = sumMap.get(key);
                    oldSum = oldSum.add(item.getTotal());
                    sumMap.put(key, oldSum);
                } else {
                    sumMap.put(key, item.getTotal());
                }
            }
        }
        
        //初始化返回对象
        HighChartGraphResult rlt = new HighChartGraphResult();
        List<HightChartSeries> seriesList = new ArrayList<HightChartSeries>();
        List<HightChartSeries> drilldownList = new ArrayList<HightChartSeries>();
        rlt.setSeries(seriesList);
        rlt.setDrilldown(drilldownList);
        
        HightChartSeries series = new HightChartSeries();
        series.setName("比例");
        series.setType("pie");
        series.setData(new ArrayList<HightChartSeries>());
        seriesList.add(series);
        
        
        for (Category category : allCategories) {
            //处理series
            if (category.getCategoryLevel() == 0) {
                BigDecimal usedAmt = categoryConsumptionsMap.get(category.getCategoryOid() + "_-1").getTotal();
                if (usedAmt.compareTo(BigDecimal.ZERO) == 0) {
                    //金额为0的不需要在pie图呈现。
                    continue;
                }
                
                BigDecimal totalAmt = sumMap.get(BigDecimal.valueOf(-1));
                BigDecimal percent  = usedAmt.divide(totalAmt, 4, RoundingMode.HALF_UP);
                
                HightChartSeries innerSeries = new HightChartSeries();
                innerSeries.setName(category.getCategoryDesc());
                innerSeries.setType("pie");
                innerSeries.setY(percent);
                if (!category.getIsLeaf()) {
                    innerSeries.setDrilldown(category.getCategoryOid().toString());
                }
                
                series.getData().add(innerSeries);
            }
            
            //处理drilldown
            if (!category.getIsLeaf()) {
                if (categoryConsumptionsMap.get(category.getCategoryOid() + "_-1").getTotal().compareTo(BigDecimal.ZERO) == 0) {
                    continue;
                }
                
                HightChartSeries drillDownSeries = new HightChartSeries();
                drillDownSeries.setId(category.getCategoryOid().toString());
                drillDownSeries.setType("pie");
                drillDownSeries.setName(category.getCategoryDesc());
                drillDownSeries.setData(new ArrayList<HightChartSeries>());
                
                for (CategoryConsumption item : categoryConsumptions) {
                    if (category.getCategoryOid().equals(item.getParentOid()) && BigDecimal.valueOf(-1).equals(item.getUserOid())) {
                        BigDecimal usedAmt = item.getTotal();
                        if (usedAmt.compareTo(BigDecimal.ZERO) == 0) {
                            //金额为0的不需要在pie图呈现。
                            continue;
                        }
                        
                        BigDecimal totalAmt = sumMap.get((item.getParentOid() == null ? BigDecimal.valueOf(-1) : item.getParentOid()));
                        BigDecimal percent  = usedAmt.divide(totalAmt, 4, RoundingMode.HALF_UP);
                        
                        HightChartSeries innerSeries = new HightChartSeries();
                        innerSeries.setName(item.getCategoryDesc());
                        innerSeries.setY(percent);
                        if (!item.getIsLeaf()) {
                            innerSeries.setDrilldown(item.getCategoryOid().toString());
                        }
                        
                        drillDownSeries.getData().add(innerSeries);
                    }
                }
                
                drilldownList.add(drillDownSeries);
            }
        }
        
        return rlt;
    }
    
    
    private HighChartGraphResult columnResult(List<CategoryConsumption> categoryConsumptions, List<Category> allCategories, List<UserProfile> allUsers, boolean excludeBudget) {
        //List转Map，key为categoryOid + "_" + userOid，userOid为-1表示所有人
        Map<String, CategoryConsumption> categoryConsumptionsMap = new HashMap<String, CategoryConsumption>();
        for (CategoryConsumption item : categoryConsumptions) {
            categoryConsumptionsMap.put(item.getCategoryOid() + "_" + item.getUserOid(), item);
        }
        
        //初始化返回对象
        HighChartGraphResult rlt = new HighChartGraphResult();
        List<HightChartSeries> seriesList = new ArrayList<HightChartSeries>();
        List<HightChartSeries> drilldownList = new ArrayList<HightChartSeries>();
        rlt.setSeries(seriesList);
        rlt.setDrilldown(drilldownList);
        
        
        //处理series
        HightChartSeries series = new HightChartSeries();
        series.setName("全部");
        series.setData(new ArrayList<HightChartSeries>());
        for (Category category : allCategories) {
            if (category.getCategoryLevel() == 0) {
                CategoryConsumption categoryConsumption = categoryConsumptionsMap.get(category.getCategoryOid() + "_-1");
                HightChartSeries innerSeries = new HightChartSeries();
                innerSeries.setName(categoryConsumption.getCategoryDesc());
                innerSeries.setY(categoryConsumption.getTotal());
                innerSeries.setDrilldown(category.getCategoryOid() + "_-1");
                
                series.getData().add(innerSeries);
            }
        }
        seriesList.add(series);
        
        for (UserProfile user : allUsers) {
            series = new HightChartSeries();
            series.setName(user.getUserName());
            series.setData(new ArrayList<HightChartSeries>());
            
            for (Category category : allCategories) {
                if (category.getCategoryLevel() == 0) {
                    CategoryConsumption categoryConsumption = categoryConsumptionsMap.get(category.getCategoryOid() + "_" + user.getUserOid());
                    HightChartSeries innerSeries = new HightChartSeries();
                    innerSeries.setName(categoryConsumption.getCategoryDesc());
                    innerSeries.setY(categoryConsumption.getTotal());
                    innerSeries.setDrilldown(category.getCategoryOid() + "_" + user.getUserOid());
                    
                    series.getData().add(innerSeries);
                }
            }
            seriesList.add(series);
        }
        
        if (!excludeBudget) {
        	series = new HightChartSeries();
            series.setName("预算");
            series.setType("spline");
            series.setData(new ArrayList<HightChartSeries>());
            for (Category category : allCategories) {
                if (category.getCategoryLevel() == 0) {
                    HightChartSeries innerSeries = new HightChartSeries();
                    innerSeries.setName(category.getCategoryDesc());
                    innerSeries.setY(category.getMonthlyBudget());
                    innerSeries.setDrilldown(category.getCategoryOid().toString());
                    
                    series.getData().add(innerSeries);
                }
            }
            seriesList.add(series);
        }
        
        //处理drilldown
        for (Category category : allCategories) {
            if (category.getIsLeaf()) {
            	continue;
            }
            
            if (!excludeBudget) {
        		series = new HightChartSeries();
                series.setType("spline");
                series.setId(category.getCategoryOid().toString());
                series.setName("预算");
                series.setData(new ArrayList<HightChartSeries>());
                
                for (Category inner : allCategories) {
                    if (category.getCategoryOid().equals(inner.getParentOid())) {
                        HightChartSeries innerSeries = new HightChartSeries();
                        innerSeries.setType("spline");
                        innerSeries.setName(inner.getCategoryDesc());
                        innerSeries.setY(inner.getMonthlyBudget());
                        if (!inner.getIsLeaf()) {
                            innerSeries.setDrilldown(inner.getCategoryOid().toString());
                        }
                        
                        series.getData().add(innerSeries);
                    }
                }
                drilldownList.add(series);
        	}
            
            //先处理所有人的情况
            series = new HightChartSeries();
            series.setId(category.getCategoryOid() + "_-1");
            series.setName(category.getCategoryDesc());
            series.setData(new ArrayList<HightChartSeries>());
            
            for (CategoryConsumption item : categoryConsumptions) {
                if (category.getCategoryOid().equals(item.getParentOid()) && BigDecimal.valueOf(-1).equals(item.getUserOid())) {
                    HightChartSeries innerSeries = new HightChartSeries();
                    innerSeries.setName(item.getCategoryDesc());
                    innerSeries.setY(item.getTotal());
                    if (!item.getIsLeaf()) {
                        innerSeries.setDrilldown(item.getCategoryOid() + "_-1");
                    }
                    
                    series.getData().add(innerSeries);
                }
            }
            
            drilldownList.add(series);
            
            for (UserProfile user : allUsers) {
                //再处理每个人的情况
                series = new HightChartSeries();
                series.setId(category.getCategoryOid() + "_" + user.getUserOid());
                series.setName(user.getUserName());
                series.setData(new ArrayList<HightChartSeries>());
                
                for (CategoryConsumption item : categoryConsumptions) {
                    if (category.getCategoryOid().equals(item.getParentOid()) && user.getUserOid().equals(item.getUserOid())) {
                        HightChartSeries innerSeries = new HightChartSeries();
                        innerSeries.setName(item.getCategoryDesc());
                        innerSeries.setY(item.getTotal());
                        if (!item.getIsLeaf()) {
                            innerSeries.setDrilldown(item.getCategoryOid() + "_" + user.getUserOid());
                        }
                        
                        series.getData().add(innerSeries);
                    }
                }
                
                drilldownList.add(series);
            }
        }
        
        return rlt;
    }
    
    
    private List<String> months(String start, String end) {
        Date startParam = DateUtil.getInstance().getFirstTimeOfYear(start);
        Date endParam   = DateUtil.getInstance().getLastTimeOfYear(end);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        
        List<String> rlt = new ArrayList<String>();
        
        while (startParam.before(endParam)) {
            rlt.add(sdf.format(startParam));
            
            startParam = DateUtil.getInstance().getNextMonthTime(startParam);
        }
        
        return rlt;
    }*/
}
