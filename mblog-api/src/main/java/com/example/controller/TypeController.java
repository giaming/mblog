package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.lang.Result;
import com.example.config.RedisKeyConfig;
import com.example.entity.Type;
import com.example.service.RedisService;
import com.example.service.TypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
public class TypeController {
    private final TypeService typeService;
    private final RedisService redisService;

    /**
     * 查询所有分类
     */
    @GetMapping("/types")
    public Result blogs() {
        if(redisService.hasHashKey(RedisKeyConfig.CATEGORY_NAME_CACHE, RedisKeyConfig.All)){
            return Result.succ(redisService.getValueByHashKey(RedisKeyConfig.CATEGORY_NAME_CACHE, RedisKeyConfig.All));
        }
        List<Type> list = typeService.list(new QueryWrapper<Type>());
        redisService.saveKVToHash(RedisKeyConfig.CATEGORY_NAME_CACHE, RedisKeyConfig.All, list);
        return Result.succ(list);
    }


    /**
     * 分页查询分类
     */
    @RequiresAuthentication
    @RequiresPermissions("user:read")
    @GetMapping("/type/list")
    public Result typeList(@RequestParam(defaultValue = "1")Integer currentPage,
                           @RequestParam(defaultValue = "10") Integer pageSize){
        Page page = new Page(currentPage, pageSize);
        IPage pageDate = typeService.page(page, new QueryWrapper<Type>());
        return Result.succ(pageDate);
    }


    /**
     * 增加分类
     */
    @RequiresPermissions("user:create")
    @RequiresAuthentication
    @PostMapping("/type/create")
    public Result createType(@Validated @RequestBody Type type){
        if (type == null){
            return Result.fail("不能为空");
        }else {
            typeService.saveOrUpdate(type);
            redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_CACHE);
        }
        return Result.succ(null);
    }


    /**
     * 修改分类
     */
    @RequiresPermissions("user:update")
    @RequiresAuthentication
    @PostMapping("/type/update")
    public Result updateType(@Validated @RequestBody Type type){
        if (type == null){
            return Result.fail("不能为空");
        }else {
            typeService.saveOrUpdate(type);
            redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_CACHE);
        }
        return Result.succ(null);
    }


    /**
     * 删除分类
     */
    @RequiresRoles("role_root")
    @RequiresPermissions("user:delete")
    @RequiresAuthentication
    @GetMapping("/type/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id) {

        if (typeService.removeById(id)) {
            redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_CACHE);
            return Result.succ(null);
        } else {
            return Result.fail("删除失败");
        }

    }




}