package com.personal.oyl.newffms.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.category.domain.Category;
import com.personal.oyl.newffms.category.domain.CategoryRepos;
import com.personal.oyl.newffms.consumption.domain.ConsumptionRepos;
import com.personal.oyl.newffms.consumption.domain.PersonalConsumptionVo;
import com.personal.oyl.newffms.user.domain.User;
import com.personal.oyl.newffms.user.domain.UserRepos;

public class ReportService {
    @Autowired
    private ConsumptionRepos consumptionRepos;
    @Autowired
    private CategoryRepos categoryRepos;
    @Autowired
    private UserRepos userRepos;

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

    private void merge(List<CategoryConsumptionVo> categoryConsumptionVos,
            List<PersonalConsumptionVo> personalConsumptionVos) {
        Map<BigDecimal, Category> catMap = categoryRepos.allCategoriesById();
        Map<String, CategoryConsumptionVo> categoryConsumptionsMap = this.group(categoryConsumptionVos);

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

    private Map<String, CategoryConsumptionVo> group(List<CategoryConsumptionVo> param) {
        Map<String, CategoryConsumptionVo> rlt = new HashMap<>();
        for (CategoryConsumptionVo item : param) {
            rlt.put(item.getCategoryOid() + "_" + item.getUserOid(), item);
        }
        return rlt;
    }

    private List<CategoryConsumptionVo> initCategoryConsumption(Set<BigDecimal> excludedRootCategories) {
        List<User> allUsers = userRepos.queryAllUser();
        List<Category> excludedCategories = categoryRepos.rootCategoriesCascaded();
        if (null != excludedRootCategories) {
            Iterator<Category> it = excludedCategories.iterator();
            while (it.hasNext()) {
                Category item = it.next();
                if (excludedRootCategories.contains(item.getKey().getCategoryOid())) {
                    it.remove();
                }
            }
        }

        List<Category> categoryList = new LinkedList<>();
        for (Category item : excludedCategories) {
            categoryList.addAll(item.toFlatList());
        }

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
