package com.atcwl.gulicollege.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
public class CourseInfoVo {
    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程讲师ID")
    @NotBlank(message = "请选择课程的讲师")
    private String teacherId;

    @ApiModelProperty(value = "课程专业ID")
    @NotBlank(message = "请选择课程的分类")
    private String subjectId;

    @ApiModelProperty(value = "一级分类级ID")
    @NotBlank(message = "请选择课程的分类")
    private String subjectParentId;

    @ApiModelProperty(value = "课程标题")
    @NotBlank(message = "请输入课程的标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    @PositiveOrZero(message = "请输入正确的课程价格")
    // 0.01
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    @Positive(message = "请输入课程课时数（>=0)")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程简介")
    @NotNull
    private String description;
}
