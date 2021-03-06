package com.personal.oyl.newffms.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.category.domain.Category;
import com.personal.oyl.newffms.category.domain.CategoryRepos;
import com.personal.oyl.newffms.consumption.domain.ConsumptionRepos;
import com.personal.oyl.newffms.consumption.domain.PersonalConsumptionVo;
import com.personal.oyl.newffms.incoming.domain.Incoming;
import com.personal.oyl.newffms.incoming.domain.IncomingRepos;
import com.personal.oyl.newffms.incoming.domain.IncomingType;
import com.personal.oyl.newffms.user.domain.User;
import com.personal.oyl.newffms.user.domain.UserRepos;

public class ReportService {
    @Autowired
    private ConsumptionRepos consumptionRepos;
    @Autowired
    private CategoryRepos categoryRepos;
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private IncomingRepos incomingRepos;
    
    private String getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return Integer.toString(c.get(Calendar.MONTH) + 1);
    }
    
    public HighChartResult queryIncomingByMonth(Date start, Date end) {
        List<Incoming> list = incomingRepos.queryIncomingsByDateRange(start, end);
        List<User> users = userRepos.queryAllUser();
        
        Map<String, BigDecimal> map = null;
        if (null == list) {
            map = new HashMap<>();
        } else {
            map = list.stream().collect(
                    Collectors.groupingBy((item) -> item.getOwnerOid() + "-" + this.getMonth(item.getIncomingDate()), 
                            Collectors.reducing(BigDecimal.ZERO, Incoming::getAmount, BigDecimal::add)));
        }

        HighChartResult rlt = new HighChartResult();
        rlt.setSeries(new ArrayList<HightChartSeries>());
        
        for (User user : users) {
            HightChartSeries series = new HightChartSeries();
            series.setName(user.getUserName());
            //series.setType("column");
            series.setData(new ArrayList<HightChartSeries>());
            rlt.getSeries().add(series);
            
            for (int i = 1; i <= 12; i++) {
                HightChartSeries inner = new HightChartSeries();
                inner.setName(i + "月");
                BigDecimal y = map.get(user.getKey().getUserOid() + "-" + i);
                inner.setY(null == y ? BigDecimal.ZERO : y);
                series.getData().add(inner);
            }
        }

        return rlt;
    }
    
    public HighChartResult queryTotalIncomingByType(Date start, Date end) {
        List<Incoming> list = incomingRepos.queryIncomingsByDateRange(start, end);
        BigDecimal total = BigDecimal.ZERO;
        Map<IncomingType, BigDecimal> typeAmt = null;
        if (null == list) {
            typeAmt = IncomingType.initAmtMap();
        } else {
            typeAmt = list.stream()
                    .filter(item -> item.getConfirmed())
                    .collect(Collectors.groupingBy(Incoming::getIncomingType, 
                            Collectors.reducing(BigDecimal.ZERO, Incoming::getAmount, BigDecimal::add)));
            
            total = list.stream()
                    .filter(item -> item.getConfirmed())
                    .map(item -> item.getAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        
        HighChartResult rlt = new HighChartResult();
        rlt.setSeries(new ArrayList<HightChartSeries>());

        HightChartSeries series = new HightChartSeries();
        series.setName("分类收入情况");
        series.setType("column");
        series.setData(new ArrayList<HightChartSeries>());
        rlt.getSeries().add(series);

        series = new HightChartSeries();
        series.setName("总收入");
        series.setY(total);
        rlt.getSeries().get(0).getData().add(series);
        
        for (Map.Entry<IncomingType, BigDecimal> entry : typeAmt.entrySet()) {
            series = new HightChartSeries();
            series.setName(entry.getKey().getDesc());
            series.setY(entry.getValue());

            rlt.getSeries().get(0).getData().add(series);
        }

        return rlt;
    }
    
    public HighChartResult queryTotalIncoming(Date start, Date end) {
        List<Incoming> list = incomingRepos.queryIncomingsByDateRange(start, end);
        List<User> users = userRepos.queryAllUser();

        BigDecimal total = BigDecimal.ZERO;
        
        Map<BigDecimal, BigDecimal> userAmt = null;
        if (null == list) {
            userAmt = new HashMap<>();
        } else {
            userAmt = list.stream()
                    .filter(item -> item.getConfirmed())
                    .collect(Collectors.groupingBy(Incoming::getOwnerOid,
                                    Collectors.reducing(BigDecimal.ZERO, Incoming::getAmount, BigDecimal::add)));
            
            total = list.stream()
                    .filter(item -> item.getConfirmed())
                    .map(item -> item.getAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        HighChartResult rlt = new HighChartResult();
        rlt.setSeries(new ArrayList<HightChartSeries>());

        HightChartSeries series = new HightChartSeries();
        series.setName("各人收入情况");
        series.setType("column");
        series.setData(new ArrayList<HightChartSeries>());
        rlt.getSeries().add(series);

        series = new HightChartSeries();
        series.setName("总收入");
        series.setY(total);
        rlt.getSeries().get(0).getData().add(series);

        for (User user : users) {
            BigDecimal key = user.getKey().getUserOid();
            BigDecimal amt = userAmt.get(key);
            if (null == amt) {
                amt = BigDecimal.ZERO;
            }

            series = new HightChartSeries();
            series.setName(user.getUserName());
            series.setY(amt);

            rlt.getSeries().get(0).getData().add(series);
        }

        return rlt;
    }

    public HighChartResult queryUserAmtConsumption(Date start, Date end, Set<BigDecimal> excludedRootCategories) {
        List<PersonalConsumptionVo> pVos = consumptionRepos.queryPersonalConsumption(start, end);
        List<CategoryConsumptionVo> list = this.initCategoryConsumption(excludedRootCategories);
        this.merge(list, pVos);
        Map<String, BigDecimal> userAmtMapping = new HashMap<>();

        BigDecimal total = BigDecimal.ZERO;
        if (null != list) {
            for (CategoryConsumptionVo item : list) {
                if (item.getCategoryLevel() == 0 && !BigDecimal.valueOf(-1).equals(item.getUserOid())) {
                    String username = item.getUserName();
                    BigDecimal itemTotal = item.getTotal();
                    total = total.add(itemTotal);
                    if (userAmtMapping.containsKey(username)) {
                        userAmtMapping.put(username, userAmtMapping.get(username).add(itemTotal));
                    } else {
                        userAmtMapping.put(username, itemTotal);
                    }
                }
            }
        }

        // 初始化返回对象
        HighChartResult rlt = new HighChartResult();
        List<HightChartSeries> seriesList = new ArrayList<HightChartSeries>();
        rlt.setSeries(seriesList);

        HightChartSeries series = new HightChartSeries();
        series.setName("消费情况");
        series.setType("column");
        series.setData(new ArrayList<HightChartSeries>());
        seriesList.add(series);

        HightChartSeries innerSeries = new HightChartSeries();
        innerSeries.setName("汇总");
        innerSeries.setY(total);
        series.getData().add(innerSeries);

        for (Map.Entry<String, BigDecimal> entry : userAmtMapping.entrySet()) {
            innerSeries = new HightChartSeries();
            innerSeries.setName(entry.getKey());
            innerSeries.setY(entry.getValue());

            series.getData().add(innerSeries);
        }

        return rlt;
    }
    
    public HighChartResult queryUserRatioConsumption(Date start, Date end, Set<BigDecimal> excludedRootCategories) {
        List<PersonalConsumptionVo> pVos = consumptionRepos.queryPersonalConsumption(start, end);
        List<CategoryConsumptionVo> list = this.initCategoryConsumption(excludedRootCategories);
        this.merge(list, pVos);
        Map<String, BigDecimal> userAmtMapping = new HashMap<>();

        BigDecimal total = BigDecimal.ZERO;
        if (null != list) {
            for (CategoryConsumptionVo item : list) {
                if (item.getCategoryLevel() == 0 && !BigDecimal.valueOf(-1).equals(item.getUserOid())) {
                    String username = item.getUserName();
                    BigDecimal itemTotal = item.getTotal();
                    total = total.add(itemTotal);
                    if (userAmtMapping.containsKey(username)) {
                        userAmtMapping.put(username, userAmtMapping.get(username).add(itemTotal));
                    } else {
                        userAmtMapping.put(username, itemTotal);
                    }
                }
            }
        }

        // 初始化返回对象
        HighChartResult rlt = new HighChartResult();
        List<HightChartSeries> seriesList = new ArrayList<HightChartSeries>();
        rlt.setSeries(seriesList);

        HightChartSeries series = new HightChartSeries();
        series.setName("消费比");
        series.setType("pie");
        series.setData(new ArrayList<HightChartSeries>());
        seriesList.add(series);

        for (Map.Entry<String, BigDecimal> entry : userAmtMapping.entrySet()) {
            HightChartSeries innerSeries = new HightChartSeries();
            innerSeries.setName(entry.getKey());
            innerSeries.setType("pie");
            if (BigDecimal.ZERO.compareTo(total) == 0) {
                innerSeries.setY(BigDecimal.ZERO);
            } else {
                innerSeries.setY(entry.getValue().divide(total, 4, RoundingMode.HALF_UP));
            }

            series.getData().add(innerSeries);
        }

        return rlt;
    }
    
    public HighChartResult querycategoryRatioConsumption(Date start, Date end, Set<BigDecimal> excludedRootCategories) {
        List<PersonalConsumptionVo> pVos = consumptionRepos.queryPersonalConsumption(start, end);
        List<CategoryConsumptionVo> list = this.initCategoryConsumption(excludedRootCategories);
        this.merge(list, pVos);
        Map<String, CategoryConsumptionVo> categoryConsumptionsMap = list.stream().collect(
                Collectors.toMap(item -> item.getCategoryOid() + "_" + item.getUserOid(), Function.identity()));
        List<Category> allCategories = categoryRepos.allCategories(excludedRootCategories);
        
        Map<BigDecimal, BigDecimal> parentCategoryAmtMap = new HashMap<BigDecimal, BigDecimal>();
        for (CategoryConsumptionVo item : list) {
            if (BigDecimal.valueOf(-1).equals(item.getUserOid())) {
                BigDecimal key = item.getCategoryLevel() == 0 ? BigDecimal.valueOf(-1) : item.getParentOid();
                if (parentCategoryAmtMap.containsKey(key)) {
                    BigDecimal oldSum = parentCategoryAmtMap.get(key);
                    oldSum = oldSum.add(item.getTotal());
                    parentCategoryAmtMap.put(key, oldSum);
                } else {
                    parentCategoryAmtMap.put(key, item.getTotal());
                }
            }
        }
        
        //初始化返回对象
        HighChartResult rlt = new HighChartResult();
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
            if (category.getCategoryLevel() == Category.CATEGORY_ROOT_LEVEL) {
                BigDecimal usedAmt = categoryConsumptionsMap.get(category.getKey().getCategoryOid() + "_-1").getTotal();
                if (usedAmt.compareTo(BigDecimal.ZERO) == 0) {
                    //金额为0的不需要在pie图呈现。
                    continue;
                }
                
                BigDecimal totalAmt = parentCategoryAmtMap.get(BigDecimal.valueOf(-1));
                BigDecimal percent  = usedAmt.divide(totalAmt, 4, RoundingMode.HALF_UP);
                
                HightChartSeries innerSeries = new HightChartSeries();
                innerSeries.setName(category.getCategoryDesc());
                innerSeries.setType("pie");
                innerSeries.setY(percent);
                if (!category.getLeaf()) {
                    innerSeries.setDrilldown(category.getKey().getCategoryOid().toString());
                }
                
                series.getData().add(innerSeries);
            }
            
            //处理drilldown
            if (!category.getLeaf()) {
                if (categoryConsumptionsMap.get(category.getKey().getCategoryOid() + "_-1").getTotal()
                        .compareTo(BigDecimal.ZERO) == 0) {
                    continue;
                }
                
                HightChartSeries drillDownSeries = new HightChartSeries();
                drillDownSeries.setId(category.getKey().getCategoryOid().toString());
                drillDownSeries.setType("pie");
                drillDownSeries.setName(category.getCategoryDesc());
                drillDownSeries.setData(new ArrayList<HightChartSeries>());
                
                for (CategoryConsumptionVo item : list) {
                    if (category.getKey().getCategoryOid().equals(item.getParentOid())
                            && BigDecimal.valueOf(-1).equals(item.getUserOid())) {
                        BigDecimal usedAmt = item.getTotal();
                        if (usedAmt.compareTo(BigDecimal.ZERO) == 0) {
                            //金额为0的不需要在pie图呈现。
                            continue;
                        }
                        
                        BigDecimal totalAmt = parentCategoryAmtMap
                                .get((item.getParentOid() == null ? BigDecimal.valueOf(-1) : item.getParentOid()));
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
    
    public HighChartResult queryDetailConsumption(Date start, Date end, Set<BigDecimal> excludedRootCategories) {
        List<PersonalConsumptionVo> pVos = consumptionRepos.queryPersonalConsumption(start, end);
        List<CategoryConsumptionVo> list = this.initCategoryConsumption(excludedRootCategories);
        this.merge(list, pVos);
        Map<String, CategoryConsumptionVo> categoryConsumptionsMap = list.stream().collect(
                Collectors.toMap(item -> item.getCategoryOid() + "_" + item.getUserOid(), Function.identity()));
        List<Category> allCategories = categoryRepos.allCategories(excludedRootCategories);
        List<User> allUsers = userRepos.queryAllUser();

        // 初始化返回对象
        HighChartResult rlt = new HighChartResult();
        List<HightChartSeries> seriesList = new ArrayList<HightChartSeries>();
        List<HightChartSeries> drilldownList = new ArrayList<HightChartSeries>();
        rlt.setSeries(seriesList);
        rlt.setDrilldown(drilldownList);

        // 处理series
        HightChartSeries series = new HightChartSeries();
        series.setName("全部");
        series.setData(new ArrayList<HightChartSeries>());
        for (Category category : allCategories) {
            if (category.getCategoryLevel() == Category.CATEGORY_ROOT_LEVEL) {
                CategoryConsumptionVo categoryConsumption = categoryConsumptionsMap
                        .get(category.getKey().getCategoryOid() + "_-1");
                HightChartSeries innerSeries = new HightChartSeries();
                innerSeries.setName(categoryConsumption.getCategoryDesc());
                innerSeries.setY(categoryConsumption.getTotal());
                innerSeries.setDrilldown(category.getKey().getCategoryOid() + "_-1");

                series.getData().add(innerSeries);
            }
        }
        seriesList.add(series);

        for (User user : allUsers) {
            series = new HightChartSeries();
            series.setName(user.getUserName());
            series.setData(new ArrayList<HightChartSeries>());

            for (Category category : allCategories) {
                if (category.getCategoryLevel() == Category.CATEGORY_ROOT_LEVEL) {
                    CategoryConsumptionVo categoryConsumption = categoryConsumptionsMap
                            .get(category.getKey().getCategoryOid() + "_" + user.getKey().getUserOid());
                    HightChartSeries innerSeries = new HightChartSeries();
                    innerSeries.setName(categoryConsumption.getCategoryDesc());
                    innerSeries.setY(categoryConsumption.getTotal());
                    innerSeries.setDrilldown(category.getKey().getCategoryOid() + "_" + user.getKey().getUserOid());

                    series.getData().add(innerSeries);
                }
            }
            seriesList.add(series);
        }

        /*
         * if (!excludeBudget) { series = new HightChartSeries();
         * series.setName("预算"); series.setType("spline"); series.setData(new
         * ArrayList<HightChartSeries>()); for (Category category :
         * allCategories) { if (category.getCategoryLevel() == 0) {
         * HightChartSeries innerSeries = new HightChartSeries();
         * innerSeries.setName(category.getCategoryDesc());
         * innerSeries.setY(category.getMonthlyBudget());
         * innerSeries.setDrilldown(category.getCategoryOid().toString());
         *
         * series.getData().add(innerSeries); } } seriesList.add(series); }
         */

        // 处理drilldown
        for (Category category : allCategories) {
            if (category.getLeaf()) {
                continue;
            }

            /*
             * if (!excludeBudget) { series = new HightChartSeries();
             * series.setType("spline");
             * series.setId(category.getCategoryOid().toString());
             * series.setName("预算"); series.setData(new
             * ArrayList<HightChartSeries>());
             *
             * for (Category inner : allCategories) { if
             * (category.getCategoryOid().equals(inner.getParentOid())) {
             * HightChartSeries innerSeries = new HightChartSeries();
             * innerSeries.setType("spline");
             * innerSeries.setName(inner.getCategoryDesc());
             * innerSeries.setY(inner.getMonthlyBudget()); if
             * (!inner.getIsLeaf()) {
             * innerSeries.setDrilldown(inner.getCategoryOid().toString()); }
             *
             * series.getData().add(innerSeries); } } drilldownList.add(series);
             * }
             */

            // 先处理所有人的情况
            series = new HightChartSeries();
            series.setId(category.getKey().getCategoryOid() + "_-1");
            series.setName(category.getCategoryDesc());
            series.setData(new ArrayList<HightChartSeries>());

            for (CategoryConsumptionVo item : list) {
                if (category.getKey().getCategoryOid().equals(item.getParentOid())
                        && BigDecimal.valueOf(-1).equals(item.getUserOid())) {
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

            for (User user : allUsers) {
                // 再处理每个人的情况
                series = new HightChartSeries();
                series.setId(category.getKey().getCategoryOid() + "_" + user.getKey().getUserOid());
                series.setName(user.getUserName());
                series.setData(new ArrayList<HightChartSeries>());

                for (CategoryConsumptionVo item : list) {
                    if (category.getKey().getCategoryOid().equals(item.getParentOid())
                            && user.getKey().getUserOid().equals(item.getUserOid())) {
                        HightChartSeries innerSeries = new HightChartSeries();
                        innerSeries.setName(item.getCategoryDesc());
                        innerSeries.setY(item.getTotal());
                        if (!item.getIsLeaf()) {
                            innerSeries.setDrilldown(item.getCategoryOid() + "_" + user.getKey().getUserOid());
                        }

                        series.getData().add(innerSeries);
                    }
                }

                drilldownList.add(series);
            }
        }

        return rlt;
    }

    private void merge(List<CategoryConsumptionVo> categoryConsumptionVos,
            List<PersonalConsumptionVo> personalConsumptionVos) {
        Map<BigDecimal, Category> catMap = categoryRepos.allCategories().stream().collect(
                Collectors.toMap(cat -> cat.getKey().getCategoryOid(), Function.identity()));
        Map<String, CategoryConsumptionVo> categoryConsumptionsMap = categoryConsumptionVos.stream().collect(
                Collectors.toMap(item -> item.getCategoryOid() + "_" + item.getUserOid(), Function.identity()));
        
        for (PersonalConsumptionVo personalConsumption : personalConsumptionVos) {
            BigDecimal key = personalConsumption.getCategoryOid();

            if (!categoryConsumptionsMap.containsKey(key + "_-1")) {
                // 对应的category已经被排除。
                continue;
            }

            do {
                Category category = catMap.get(key);
                CategoryConsumptionVo categoryConsumption = categoryConsumptionsMap.get(key + "_-1");
                categoryConsumption.setTotal(categoryConsumption.getTotal().add(personalConsumption.getTotal()));
                categoryConsumption = categoryConsumptionsMap.get(key + "_" + personalConsumption.getUserOid());
                categoryConsumption.setTotal(categoryConsumption.getTotal().add(personalConsumption.getTotal()));
                key = null == category.getParentKey() ? null : category.getParentKey().getCategoryOid();
            } while (key != null);
        }
    }

    private List<CategoryConsumptionVo> initCategoryConsumption(Set<BigDecimal> excludedRootCategories) {
        List<User> allUsers = userRepos.queryAllUser();
        List<Category> categoryList = categoryRepos.allCategories(excludedRootCategories);
        List<CategoryConsumptionVo> rlt = new ArrayList<>();
        
        for (Category category : categoryList) {
            rlt.add(CategoryConsumptionVo.init(category));
            for (User user : allUsers) {
                rlt.add(CategoryConsumptionVo.init(category, user));
            }
        }
        return rlt;
    }
}
