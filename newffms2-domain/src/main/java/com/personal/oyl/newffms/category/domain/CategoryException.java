package com.personal.oyl.newffms.category.domain;

import com.personal.oyl.newffms.common.NewffmsDomainException;

@SuppressWarnings("serial")
public class CategoryException {
    
    public static class CategoryDescEmptyException extends NewffmsDomainException {
        public CategoryDescEmptyException() {
            super("EFFMS201", "类别描述不能为空。");
        }
    }
    
    public static class CategoryDescTooLongException extends NewffmsDomainException {
        public CategoryDescTooLongException() {
            super("EFFMS202", "类别描述最大不能超过30个字。");
        }
    }
    
    public static class CategoryBudgetEmptyException extends NewffmsDomainException {
        public CategoryBudgetEmptyException() {
            super("EFFMS203", "类别月度预算不能为空。");
        }
    }
    
    public static class CategoryBudgetInvalidException extends NewffmsDomainException {
        public CategoryBudgetInvalidException() {
            super("EFFMS204", "类别月度预算必须大于0。");
        }
    }
    
    public static class CategoryNotLeafException extends NewffmsDomainException {
        public CategoryNotLeafException() {
            super("EFFMS205", "非页子节点类别不能执行操作。");
        }
    }
    
    public static class CategoryKeyEmptyException extends NewffmsDomainException {
        public CategoryKeyEmptyException() {
            super("EFFMS206", "类别id不能为空。");
        }
    }
    
    public static class CategoryNotRootException extends NewffmsDomainException {
        public CategoryNotRootException() {
            super("EFFMS207", "只能创建一级类别。");
        }
    }
    
    public static class CategoryNotExistException extends NewffmsDomainException {
        public CategoryNotExistException() {
            super("EFFMS208", "类别不存在。");
        }
    }
}
