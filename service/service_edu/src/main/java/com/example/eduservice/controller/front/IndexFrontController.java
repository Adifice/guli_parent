package com.example.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.commonutils.R;
import com.example.eduservice.entity.EduCourse;
import com.example.eduservice.entity.EduTeacher;
import com.example.eduservice.service.EduCourseService;
import com.example.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("index")
    public R index(){
        QueryWrapper<EduCourse> cqw = new QueryWrapper<>();
        cqw.orderByAsc("gmt_create");
        cqw.last("limit 8");
        List<EduCourse> courseList = courseService.list(cqw);

        QueryWrapper<EduTeacher> tqw = new QueryWrapper<>();
        tqw.orderByAsc("gmt_create");
        tqw.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(tqw);

        return R.ok().data("eduList",courseList).data("teacherList",teacherList);
    }
}
