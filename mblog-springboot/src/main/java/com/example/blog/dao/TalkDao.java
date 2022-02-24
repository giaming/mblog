package com.example.blog.dao;

import com.example.blog.dto.TalkBackDTO;
import com.example.blog.dto.TalkDTO;
import com.example.blog.entity.Talk;
import com.example.blog.vo.ConditionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说说
 */
@Repository
public interface TalkDao extends BaseMapper<Talk> {

    /**
     * 获取说说列表
     *
     * @param current 页码
     * @param size    大小
     * @return {@link List< TalkDTO >}
     */
    List<TalkDTO> listTalks(@Param("current") Long current, @Param("size") Long size);

    /**
     * 查看后台说说
     *
     * @param current 页码
     * @param size    大小
     * @return {@link List< TalkBackDTO >}
     */
    List<TalkBackDTO> listBackTalks(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVO condition);


    /**
     * 根据id查看说说
     *
     * @param talkId 说说id
     * @return {@link TalkDTO} 说说信息
     */
    TalkDTO getTalkById(@Param("talkId") Integer talkId);


    /**
     * 根据id查看后台说说
     *
     * @param talkId 说说id
     * @return {@link TalkBackDTO} 说说信息
     */
    TalkBackDTO getBackTalkById(@Param("talkId") Integer talkId);

}




