package com.personal.oyl.newffms;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.personal.oyl.newffms.category.domain.Category;
import com.personal.oyl.newffms.category.domain.CategoryKey;
import com.personal.oyl.newffms.category.domain.CategoryRepos;
import com.personal.oyl.newffms.category.store.mapper.CategoryMapper;
import com.personal.oyl.newffms.common.NewffmsDomainException;

import junit.framework.TestCase;

public class CategoryTest extends TestCase {
	
	private static ApplicationContext ctx = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		CategoryMapper mapper = ctx.getBean(CategoryMapper.class);
		mapper.delete(null);
	}

	public void testCreation() throws NewffmsDomainException {
		CategoryRepos repos = ctx.getBean(CategoryRepos.class);
		
		Category bean = new Category();
		bean.setCategoryDesc("日常消费");
		bean.setCategoryLevel(Integer.valueOf(1));
		bean.setLeaf(true);
		bean.setMonthlyBudget(BigDecimal.valueOf(100));
		
		repos.add(bean, "欧阳亮");
		
		assertNotNull(bean.getKey());
		assertNotNull(bean.getKey().getCategoryOid());
	}
	
	public void testChangingDesc() throws NewffmsDomainException {
		CategoryRepos repos = ctx.getBean(CategoryRepos.class);
		
		Category bean = new Category();
		bean.setCategoryDesc("日常消费");
		bean.setCategoryLevel(Integer.valueOf(1));
		bean.setLeaf(true);
		bean.setMonthlyBudget(BigDecimal.valueOf(100));
		
		repos.add(bean, "欧阳亮");
		
		assertNotNull(bean.getKey());
		assertNotNull(bean.getKey().getCategoryOid());
		
		bean.changeCategoryDesc("消费", "hehe");
		
		assertEquals("消费", bean.getCategoryDesc());
		
		Category bean2 = repos.categoryOfId(bean.getKey());
		
		assertEquals("消费", bean2.getCategoryDesc());
		assertEquals("hehe", bean2.getUpdateBy());
		assertEquals("欧阳亮", bean2.getCreateBy());
	}
	
	public void testChangingBudget() throws NewffmsDomainException {
		CategoryRepos repos = ctx.getBean(CategoryRepos.class);
		
		Category bean = new Category();
		bean.setCategoryDesc("日常消费");
		bean.setCategoryLevel(Integer.valueOf(1));
		bean.setLeaf(true);
		bean.setMonthlyBudget(BigDecimal.valueOf(100));
		
		repos.add(bean, "欧阳亮");
		
		assertNotNull(bean.getKey().getCategoryOid());
		
		bean.changeBudget(BigDecimal.valueOf(200), "hehe");
		
		assertTrue(0 == BigDecimal.valueOf(200).compareTo(bean.getMonthlyBudget()));
		
		Category bean2 = repos.categoryOfId(bean.getKey());
		
		assertEquals("日常消费", bean2.getCategoryDesc());
		assertEquals("hehe", bean2.getUpdateBy());
		assertEquals("欧阳亮", bean2.getCreateBy());
		assertTrue(0 == BigDecimal.valueOf(200).compareTo(bean.getMonthlyBudget()));
		assertTrue(2 == bean2.getSeqNo());
		assertNull(bean2.getParentKey());
		assertTrue(bean2.getLeaf());
	}
	
	public void testAddingAndRemovingChild() throws NewffmsDomainException{
		CategoryRepos repos = ctx.getBean(CategoryRepos.class);
		
		Category bean = new Category();
		bean.setCategoryDesc("日常消费");
		bean.setCategoryLevel(Integer.valueOf(1));
		bean.setLeaf(true);
		bean.setMonthlyBudget(BigDecimal.valueOf(1000));
		
		repos.add(bean, "欧阳亮");
		
		assertNotNull(bean.getKey().getCategoryOid());
		BigDecimal parentOid = bean.getKey().getCategoryOid();
		
		Category child = bean.addChild("伙食", BigDecimal.valueOf(500), "欧阳亮");
		
		assertNotNull(child.getKey().getCategoryOid());
		assertEquals("伙食", child.getCategoryDesc());
		assertTrue(0 == BigDecimal.valueOf(500).compareTo(child.getMonthlyBudget()));
		assertEquals(2, child.getCategoryLevel().intValue());
		assertTrue(child.getLeaf());
		assertEquals(bean.getKey().getCategoryOid(), child.getParentKey().getCategoryOid());
		assertEquals("欧阳亮", child.getCreateBy());
		assertEquals(1, child.getSeqNo().intValue());
		CategoryKey firstChildKey = child.getKey();
		
		bean = repos.categoryOfId(new CategoryKey(parentOid));
		assertNotNull(bean.getKey().getCategoryOid());
		assertEquals("日常消费", bean.getCategoryDesc());
		assertTrue(0 == BigDecimal.valueOf(500).compareTo(bean.getMonthlyBudget()));
		assertEquals(1, bean.getCategoryLevel().intValue());
		assertFalse(bean.getLeaf());
		assertNull(bean.getParentKey());
		assertEquals("欧阳亮", bean.getCreateBy());
		assertEquals(2, bean.getSeqNo().intValue());
		
		child = bean.addChild("酒水", BigDecimal.valueOf(300), "欧阳亮");
		
		assertNotNull(child.getKey().getCategoryOid());
		assertEquals("酒水", child.getCategoryDesc());
		assertTrue(0 == BigDecimal.valueOf(300).compareTo(child.getMonthlyBudget()));
		assertEquals(2, child.getCategoryLevel().intValue());
		assertTrue(child.getLeaf());
		assertEquals(bean.getKey().getCategoryOid(), child.getParentKey().getCategoryOid());
		assertEquals("欧阳亮", child.getCreateBy());
		assertEquals(1, child.getSeqNo().intValue());
		CategoryKey secondChildKey = child.getKey();
		
		bean = repos.categoryOfId(new CategoryKey(parentOid));
		assertNotNull(bean.getKey().getCategoryOid());
		assertEquals("日常消费", bean.getCategoryDesc());
		assertTrue(0 == BigDecimal.valueOf(800).compareTo(bean.getMonthlyBudget()));
		assertEquals(1, bean.getCategoryLevel().intValue());
		assertFalse(bean.getLeaf());
		assertNull(bean.getParentKey());
		assertEquals("欧阳亮", bean.getCreateBy());
		assertEquals(3, bean.getSeqNo().intValue());
		
		repos.remove(firstChildKey, "欧阳亮");
		bean = repos.categoryOfId(new CategoryKey(parentOid));
		assertNotNull(bean.getKey().getCategoryOid());
		assertEquals("日常消费", bean.getCategoryDesc());
		assertTrue(0 == BigDecimal.valueOf(300).compareTo(bean.getMonthlyBudget()));
		assertEquals(1, bean.getCategoryLevel().intValue());
		assertFalse(bean.getLeaf());
		assertNull(bean.getParentKey());
		assertEquals("欧阳亮", bean.getCreateBy());
		assertEquals(4, bean.getSeqNo().intValue());
		
		List<Category> children = repos.categoriesOfParent(new CategoryKey(parentOid));
		assertNotNull(children);
		assertEquals(1, children.size());
		child = children.get(0);
		assertNotNull(child.getKey());
		assertEquals(secondChildKey.getCategoryOid(), child.getKey().getCategoryOid());
		assertEquals("酒水", child.getCategoryDesc());
		assertTrue(0 == BigDecimal.valueOf(300).compareTo(child.getMonthlyBudget()));
		assertEquals(2, child.getCategoryLevel().intValue());
		assertTrue(child.getLeaf());
		assertEquals(bean.getKey().getCategoryOid(), child.getParentKey().getCategoryOid());
		assertEquals("欧阳亮", child.getCreateBy());
		assertEquals(1, child.getSeqNo().intValue());
		
		repos.remove(secondChildKey, "欧阳亮");
		bean = repos.categoryOfId(new CategoryKey(parentOid));
		assertNotNull(bean.getKey().getCategoryOid());
		assertEquals("日常消费", bean.getCategoryDesc());
		assertTrue(0 == BigDecimal.valueOf(0).compareTo(bean.getMonthlyBudget()));
		assertEquals(1, bean.getCategoryLevel().intValue());
		assertTrue(bean.getLeaf());
		assertNull(bean.getParentKey());
		assertEquals("欧阳亮", bean.getCreateBy());
		assertEquals(5, bean.getSeqNo().intValue());
		
	}
	
	public void testChangingChildBudget() throws NewffmsDomainException{
		CategoryRepos repos = ctx.getBean(CategoryRepos.class);
		
		Category bean = new Category();
		bean.setCategoryDesc("日常消费");
		bean.setCategoryLevel(Integer.valueOf(1));
		bean.setLeaf(true);
		bean.setMonthlyBudget(BigDecimal.valueOf(1000));
		
		repos.add(bean, "欧阳亮");
		
		assertNotNull(bean.getKey().getCategoryOid());
		BigDecimal parentOid = bean.getKey().getCategoryOid();
		
		Category child = bean.addChild("伙食", BigDecimal.valueOf(500), "欧阳亮");
		
		assertNotNull(child.getKey().getCategoryOid());
		assertEquals("伙食", child.getCategoryDesc());
		assertTrue(0 == BigDecimal.valueOf(500).compareTo(child.getMonthlyBudget()));
		assertEquals(2, child.getCategoryLevel().intValue());
		assertTrue(child.getLeaf());
		assertEquals(bean.getKey().getCategoryOid(), child.getParentKey().getCategoryOid());
		assertEquals("欧阳亮", child.getCreateBy());
		assertEquals(1, child.getSeqNo().intValue());
		CategoryKey firstChildKey = child.getKey();
		
		bean = repos.categoryOfId(new CategoryKey(parentOid));
		assertNotNull(bean.getKey().getCategoryOid());
		assertEquals("日常消费", bean.getCategoryDesc());
		assertTrue(0 == BigDecimal.valueOf(500).compareTo(bean.getMonthlyBudget()));
		assertEquals(1, bean.getCategoryLevel().intValue());
		assertFalse(bean.getLeaf());
		assertNull(bean.getParentKey());
		assertEquals("欧阳亮", bean.getCreateBy());
		assertEquals(2, bean.getSeqNo().intValue());
		
		child = bean.addChild("酒水", BigDecimal.valueOf(300), "欧阳亮");
		
		assertNotNull(child.getKey().getCategoryOid());
		assertEquals("酒水", child.getCategoryDesc());
		assertTrue(0 == BigDecimal.valueOf(300).compareTo(child.getMonthlyBudget()));
		assertEquals(2, child.getCategoryLevel().intValue());
		assertTrue(child.getLeaf());
		assertEquals(bean.getKey().getCategoryOid(), child.getParentKey().getCategoryOid());
		assertEquals("欧阳亮", child.getCreateBy());
		assertEquals(1, child.getSeqNo().intValue());
		CategoryKey secondChildKey = child.getKey();
		
		child.changeBudget(BigDecimal.valueOf(350), "欧阳亮");
		
		bean = repos.categoryOfId(new CategoryKey(parentOid));
		assertNotNull(bean.getKey().getCategoryOid());
		assertEquals("日常消费", bean.getCategoryDesc());
		assertTrue(0 == BigDecimal.valueOf(850).compareTo(bean.getMonthlyBudget()));
		assertEquals(1, bean.getCategoryLevel().intValue());
		assertFalse(bean.getLeaf());
		assertNull(bean.getParentKey());
		assertEquals("欧阳亮", bean.getCreateBy());
		assertEquals(4, bean.getSeqNo().intValue());
		
		repos.remove(firstChildKey, "欧阳亮");
		bean = repos.categoryOfId(new CategoryKey(parentOid));
		assertNotNull(bean.getKey().getCategoryOid());
		assertEquals("日常消费", bean.getCategoryDesc());
		assertTrue(0 == BigDecimal.valueOf(350).compareTo(bean.getMonthlyBudget()));
		assertEquals(1, bean.getCategoryLevel().intValue());
		assertFalse(bean.getLeaf());
		assertNull(bean.getParentKey());
		assertEquals("欧阳亮", bean.getCreateBy());
		assertEquals(5, bean.getSeqNo().intValue());
		
		List<Category> children = repos.categoriesOfParent(new CategoryKey(parentOid));
		assertNotNull(children);
		assertEquals(1, children.size());
		child = children.get(0);
		assertNotNull(child.getKey());
		assertEquals(secondChildKey.getCategoryOid(), child.getKey().getCategoryOid());
		assertEquals("酒水", child.getCategoryDesc());
		assertTrue(0 == BigDecimal.valueOf(350).compareTo(child.getMonthlyBudget()));
		assertEquals(2, child.getCategoryLevel().intValue());
		assertTrue(child.getLeaf());
		assertEquals(bean.getKey().getCategoryOid(), child.getParentKey().getCategoryOid());
		assertEquals("欧阳亮", child.getCreateBy());
		assertEquals(2, child.getSeqNo().intValue());
		
		repos.remove(secondChildKey, "欧阳亮");
		bean = repos.categoryOfId(new CategoryKey(parentOid));
		assertNotNull(bean.getKey().getCategoryOid());
		assertEquals("日常消费", bean.getCategoryDesc());
		assertTrue(0 == BigDecimal.valueOf(0).compareTo(bean.getMonthlyBudget()));
		assertEquals(1, bean.getCategoryLevel().intValue());
		assertTrue(bean.getLeaf());
		assertNull(bean.getParentKey());
		assertEquals("欧阳亮", bean.getCreateBy());
		assertEquals(6, bean.getSeqNo().intValue());
		
	}
	
	public void testQueryRootCategories() throws NewffmsDomainException{
		CategoryMapper mapper = ctx.getBean(CategoryMapper.class);
		CategoryRepos repos = ctx.getBean(CategoryRepos.class);
		mapper.delete(null);
		
		Category bean = new Category();
		bean.setCategoryDesc("日常消费");
		bean.setCategoryLevel(Integer.valueOf(1));
		bean.setLeaf(true);
		bean.setMonthlyBudget(BigDecimal.valueOf(1000));
		
		repos.add(bean, "欧阳亮");
		
		assertNotNull(bean.getKey().getCategoryOid());
		BigDecimal parentOid1 = bean.getKey().getCategoryOid();
		
		Category child = bean.addChild("伙食", BigDecimal.valueOf(500), "欧阳亮");
		
		assertNotNull(child.getKey().getCategoryOid());
		assertEquals("伙食", child.getCategoryDesc());
		assertTrue(0 == BigDecimal.valueOf(500).compareTo(child.getMonthlyBudget()));
		assertEquals(2, child.getCategoryLevel().intValue());
		assertTrue(child.getLeaf());
		assertEquals(bean.getKey().getCategoryOid(), child.getParentKey().getCategoryOid());
		assertEquals("欧阳亮", child.getCreateBy());
		assertEquals(1, child.getSeqNo().intValue());
		
		child = bean.addChild("酒水", BigDecimal.valueOf(300), "欧阳亮");
		
		bean = new Category();
		bean.setCategoryDesc("汽车");
		bean.setCategoryLevel(Integer.valueOf(1));
		bean.setLeaf(true);
		bean.setMonthlyBudget(BigDecimal.valueOf(2500));
		
		repos.add(bean, "喻敏");
		BigDecimal parentOid2 = bean.getKey().getCategoryOid();
		
		List<Category> list = repos.rootCategories();
		
		assertNotNull(list);
		assertEquals(2, list.size());
		
		if (parentOid1.equals(list.get(0).getKey().getCategoryOid())) {
			bean = list.get(0);
			assertNotNull(bean.getKey().getCategoryOid());
			assertEquals(parentOid1, bean.getKey().getCategoryOid());
			assertEquals("日常消费", bean.getCategoryDesc());
			assertTrue(0 == BigDecimal.valueOf(800).compareTo(bean.getMonthlyBudget()));
			assertEquals(1, bean.getCategoryLevel().intValue());
			assertFalse(bean.getLeaf());
			assertNull(bean.getParentKey());
			assertEquals("欧阳亮", bean.getCreateBy());
			assertEquals(3, bean.getSeqNo().intValue());
			
			bean = list.get(1);
			assertNotNull(bean.getKey().getCategoryOid());
			assertEquals(parentOid2, bean.getKey().getCategoryOid());
			assertEquals("汽车", bean.getCategoryDesc());
			assertTrue(0 == BigDecimal.valueOf(2500).compareTo(bean.getMonthlyBudget()));
			assertEquals(1, bean.getCategoryLevel().intValue());
			assertTrue(bean.getLeaf());
			assertNull(bean.getParentKey());
			assertEquals("喻敏", bean.getCreateBy());
			assertEquals(1, bean.getSeqNo().intValue());
			
		} else {
			bean = list.get(1);
			assertNotNull(bean.getKey().getCategoryOid());
			assertEquals(parentOid1, bean.getKey().getCategoryOid());
			assertEquals("日常消费", bean.getCategoryDesc());
			assertTrue(0 == BigDecimal.valueOf(800).compareTo(bean.getMonthlyBudget()));
			assertEquals(1, bean.getCategoryLevel().intValue());
			assertFalse(bean.getLeaf());
			assertNull(bean.getParentKey());
			assertEquals("欧阳亮", bean.getCreateBy());
			assertEquals(3, bean.getSeqNo().intValue());
			
			bean = list.get(2);
			assertNotNull(bean.getKey().getCategoryOid());
			assertEquals(parentOid2, bean.getKey().getCategoryOid());
			assertEquals("汽车", bean.getCategoryDesc());
			assertTrue(0 == BigDecimal.valueOf(2500).compareTo(bean.getMonthlyBudget()));
			assertEquals(1, bean.getCategoryLevel().intValue());
			assertTrue(bean.getLeaf());
			assertNull(bean.getParentKey());
			assertEquals("喻敏", bean.getCreateBy());
			assertEquals(1, bean.getSeqNo().intValue());
		}
	}
	
}
