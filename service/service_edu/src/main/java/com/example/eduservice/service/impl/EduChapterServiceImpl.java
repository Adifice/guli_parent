package com.example.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eduservice.entity.EduChapter;
import com.example.eduservice.entity.EduVideo;
import com.example.eduservice.entity.chapter.ChapterVo;
import com.example.eduservice.entity.chapter.VideoVo;
import com.example.eduservice.mapper.EduChapterMapper;
import com.example.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-02
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> qw = new QueryWrapper<>();
        qw.eq("course_id",courseId);
        List<EduChapter> list = this.list(qw);
        List<ChapterVo> finalList = new ArrayList<>();
        for(EduChapter t : list){
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(t,chapterVo);
            QueryWrapper<EduVideo> lqw = new QueryWrapper<>();
            lqw.eq("chapter_id",t.getId());
            List<EduVideo> videoList = videoService.list(lqw);
            List<VideoVo> videoVoList = new ArrayList<>();
            for(EduVideo v : videoList){
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(v,videoVo);
                videoVoList.add(videoVo);
            }
            chapterVo.setChildren(videoVoList);
            finalList.add(chapterVo);
        }
        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> qw = new QueryWrapper<>();
        qw.eq("chapter_id",chapterId);
        int count = videoService.count(qw);
        if(count > 0){
            return false;
        }
        int result = baseMapper.deleteById(chapterId);
        return  result > 0;

    }
}
