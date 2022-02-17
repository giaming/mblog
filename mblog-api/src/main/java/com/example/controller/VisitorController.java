package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.lang.Result;
import com.example.common.lang.vo.VisitorNum;
import com.example.config.RedisKeyConfig;
import com.example.entity.Visitor;
import com.example.service.RedisService;
import com.example.service.VisitorService;
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
public class VisitorController {
    private final VisitorService visitorService;

    private final RedisService redisService;

    /**
     * 获取总uv和pv
     */
    @GetMapping("/visitornum")
    public Result getPvAndUv() {
        if (redisService.hasKey(RedisKeyConfig.PV_UV)) {
            return Result.succ(redisService.getValueByHashKey(RedisKeyConfig.PV_UV, RedisKeyConfig.All));
        }
        int uv = visitorService.list().size();
        int pv = visitorService.getPv();
        VisitorNum visitorNum = new VisitorNum(uv,pv);

        redisService.saveKVToHash(RedisKeyConfig.PV_UV, RedisKeyConfig.All, visitorNum);
        return Result.succ(visitorNum);
    }


    /**
     * 查询所有游客
     */
    @RequiresPermissions("user:read")
    @RequiresAuthentication
    @RequestMapping("/visitor")
    public Result getAllVisiorList(){
        List<Visitor> list = visitorService.lambdaQuery().list();

        return Result.succ(list);
    }


    /**
     * 分页查询所有游客
     */
    @RequiresAuthentication
    @RequiresPermissions("user:read")
    @GetMapping("/visitorList")
    public Result getVisitorList(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize) {

        Page page = new Page(currentPage, pageSize);
        IPage pageData = visitorService.page(page, new QueryWrapper<Visitor>().orderByDesc("create_time"));
        return Result.succ(pageData);
    }


    /**
     * 根据访问时间 分页查询所有游客
     */
    @RequiresAuthentication
    @RequiresPermissions("user:read")
    @GetMapping("/visitor/part")
    public Result getVisitorListByTime(@RequestParam(defaultValue = "") String time,@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "10") Integer pageSize) {
        String[] endStartTime = time.split(",");
        if(endStartTime.length!=2){
            return Result.fail("时间设置错误");
        }
        Page page = new Page(currentPage, pageSize);
        IPage pageData = visitorService.page(page, new QueryWrapper<Visitor>().le("last_time",endStartTime[1]).ge("last_time",endStartTime[0]).orderByDesc("create_time"));
        return Result.succ(pageData);
    }


    /**
     * 修改某个游客信息
     */
    @RequiresAuthentication
    @PostMapping("/visitor/update")
    public Result updateVisitLog(@Validated @RequestBody Visitor visitor){
        if(visitor ==null){
            return Result.fail("不能为空");
        }
        else{
            if(visitor.getId()==null){
                visitor.setLastTime(LocalDateTime.now());
                visitor.setCreateTime(LocalDateTime.now());
            }
            visitorService.saveOrUpdate(visitor);
        }
        return Result.succ(null);
    }


    /**
     * 删除某个游客
     */
    @RequiresRoles("role_root")
    @RequiresAuthentication
    @RequiresPermissions("user:delete")
    @GetMapping("/visitor/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id) {

        if (visitorService.removeById(id)) {
            return Result.succ(null);
        } else {
            return Result.fail("删除失败");
        }
    }
}