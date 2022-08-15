package com.example.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.R;
import com.example.eduservice.entity.EduTeacher;
import com.example.eduservice.entity.vo.TeacherQuery;
import com.example.eduservice.service.EduTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-07-27
 */
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("findAll")
    public R list(){
        return R.ok().data("items",eduTeacherService.list(null));
    }

    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id){
        boolean b = eduTeacherService.removeById(id);
        if(b){
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("page/{cur}/{limit}")
    public R page(@PathVariable long cur,@PathVariable long limit){
        Page<EduTeacher> page1 = new Page<>(cur,limit);
        eduTeacherService.page(page1,null);
        long total = page1.getTotal();
        List<EduTeacher> records = page1.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @PostMapping("pageTeacherCondition/{cur}/{limit}")
    public R pageTeacherCondition(@PathVariable long cur, @PathVariable long limit,@RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> page1 = new Page<>(cur,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.like(teacherQuery.getName() != null,"name",teacherQuery.getName());
        wrapper.eq(teacherQuery.getLevel() != null,"level",teacherQuery.getLevel());
        wrapper.ge(teacherQuery.getBegin() != null,"gmt_create",teacherQuery.getBegin());
        wrapper.le(teacherQuery.getEnd() != null,"gmt_modified",teacherQuery.getEnd());
        wrapper.orderByDesc("gmt_modified",teacherQuery.getEnd());
        eduTeacherService.page(page1,wrapper);
        long total = page1.getTotal();
        List<EduTeacher> records = page1.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        log.info(eduTeacher.toString());
        boolean b = eduTeacherService.save(eduTeacher);
        return b ? R.ok() : R.error();
    }

    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        return b ? R.ok() : R.error();
    }
}

