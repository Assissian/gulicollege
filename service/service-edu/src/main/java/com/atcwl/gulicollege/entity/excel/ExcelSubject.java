package com.atcwl.gulicollege.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Data
public class ExcelSubject {
    @ExcelProperty(value = "一级分类", index = 0)
    private String oneSubjectName;
    @ExcelProperty(value = "二级分类 ", index = 1)
    private String twoSubjectName;
}
