package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.annotation.VisitLogger;
import com.example.common.lang.Result;
import com.example.config.RedisKeyConfig;
import com.example.entity.Friend;
import com.example.service.FriendService;
import com.example.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 服务控制器
 *
 * @author giaming
 * @since 2022-02-12 18:04:11
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class FriendController {
    private final FriendService friendService;
    private final RedisService redisService;


    /**
     * 查询所有公开的友链
     */
    @RequestMapping("/friend/all")
    public Result getFriendList() {
        if (redisService.hasHashKey(RedisKeyConfig.FRIEND_INFO_CACHE, RedisKeyConfig.All)) {
            return Result.succ(redisService.getValueByHashKey(RedisKeyConfig.FRIEND_INFO_CACHE, RedisKeyConfig.All));
        }
        List<Friend> list = friendService.lambdaQuery().eq(Friend::getIsPublished, 1).list();
        redisService.saveKVToHash(RedisKeyConfig.FRIEND_INFO_CACHE, RedisKeyConfig.All,list);
        return Result.succ(list);
    }


    /**
     * 分页查询所有友链
     */
    @RequiresAuthentication
    @RequiresPermissions("user:read")
    @GetMapping("/friendList")
    public Result friendList(@RequestParam(defaultValue = "1") Integer currentPage,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        Page page = new Page(currentPage, pageSize);
        IPage pageData = friendService.page(page, new QueryWrapper<Friend>().orderByDesc("create_time"));
        return Result.succ(pageData);
    }


    /**
     * 友链浏览次数加一
     */
    @VisitLogger(behavior = "点击友链")
    @RequestMapping("/friend/onclick")
    public Result addView(@RequestParam(name = "")String nickname){
        if (nickname.equals("")){
            return Result.fail("访问出错");
        }
        Friend friend = friendService.getOne(new QueryWrapper<Friend>().eq("nickname", nickname));
        friend.setViews(friend.getViews()+1);
        friendService.saveOrUpdate(friend);
        return Result.succ(null);
    }


    /**
     * 修改友链的状态
     */
    @RequiresAuthentication
    @RequiresPermissions("user:update")
    @RequestMapping("/friend/publish/{id}")
    public Result publish(@PathVariable(name = "id")Long id){
        Friend friend = friendService.getById(id);
        friend.setIsPublished(!friend.getIsPublished());
        friendService.saveOrUpdate(friend);
        redisService.deleteCacheByKey(RedisKeyConfig.FRIEND_INFO_CACHE);
        return Result.succ(null);
    }


    /**
     * 修改友链
     */
    @RequiresPermissions("user:update")
    @RequiresAuthentication
    @PostMapping("/friend/udpate")
    public Result updateFriend(@Validated @RequestBody Friend friend){
        if (friend == null) {
            return Result.fail("不能为空");
        }else {
            if (friend.getId()==null) {
                friend.setCreateTime(LocalDateTime.now());
            }
            friendService.saveOrUpdate(friend);
            redisService.deleteCacheByKey(RedisKeyConfig.FRIEND_INFO_CACHE);
        }
        return Result.succ(null);
    }


    /**
     * 增加友链
     */
    @RequiresPermissions("user:create")
    @RequiresAuthentication
    @PostMapping("/friend/create")
    public Result createFriend(@Validated @RequestBody Friend friend){
        if (friend == null) {
            return Result.fail("不能为空");
        }else {
            if (friend.getId()==null) {
                friend.setCreateTime(LocalDateTime.now());
            }
            friendService.saveOrUpdate(friend);
            redisService.deleteCacheByKey(RedisKeyConfig.FRIEND_INFO_CACHE);
        }
        return Result.succ(null);
    }


    /**
     * 删除友链
     */
    @RequiresRoles("role_root")
    @RequiresPermissions("user:delete")
    @RequiresAuthentication
    @GetMapping("/friend/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id){
        if (friendService.removeById(id)){
            redisService.deleteCacheByKey(RedisKeyConfig.FRIEND_INFO_CACHE);
            return Result.succ(null);
        }else {
            return Result.fail("删除失败");
        }
    }
}