package com.hkbu.comp7640.utils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "分页参数")
public class PageParam<T> extends Page<T> {

    /**
     * 每页显示条数，默认 10
     */
    @Schema(description = "每页大小，默认10")
    @NotNull
    private long size = 10;

    /**
     * 当前页
     */
    @Schema(description = "当前页，默认1")
    @NotNull
    private long current = 1;

    /**
     * 查询数据列表
     */
    @Hidden
    private List<T> records;

    @Hidden
    private long total = 0;

    @JsonIgnore
    private boolean isSearchCount = true;

    @JsonIgnore
    private String pages;
    @JsonIgnore
    private String countId;
    @JsonIgnore
    private Long maxLimit;
    @JsonIgnore
    private boolean optimizeCountSql;
    @JsonIgnore
    private boolean optimizeJoinOfCountSql;
    @JsonIgnore
    private List<OrderItem> orders;

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public Page<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public Page<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @JsonIgnore
    public boolean getSearchCount() {
        if (total < 0) {
            return false;
        }
        return isSearchCount;
    }

    @Override
    public Page<T> setSearchCount(boolean isSearchCount) {
        this.isSearchCount = isSearchCount;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public Page<T> setSize(long size) {
        int maxSize = 100;
        if (size > maxSize) {
            this.size = maxSize;
        } else {
            this.size = size;
        }
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public Page<T> setCurrent(long current) {
        this.current = current;
        return this;
    }
}
