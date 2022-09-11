package com.sw.novel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.common.utils.R;
import com.sw.novel.entity.User;
import com.sw.novel.service.UserService;
import com.sw.novel.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author makejava
 * @since 2022-09-06 16:09:39
 */

@Api(tags = "UserController",description = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController extends ApiController {

    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;


     //权限标识
     //  @PreAuthorize("hasAuthority('权限')")
    /**
     * 登录接口
     */
    //@PreAuthorize("hasAuthority('abc')")
    @PostMapping("/login")
    public R Login(
            @RequestBody User user
    ){

        return  userService.login(user);

    }

    @RequestMapping("/test02")
    public R test02() {
        return R.ok();
    }


    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param user 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<User> page, User user) {
        return R.ok();
    }

//    /**
//     * 通过主键查询单条数据
//     *
//     * @param id 主键
//     * @return 单条数据
//     */
//    @GetMapping("{id}")
//    public R selectOne(@PathVariable Serializable id) {
//        return success(this.userService.getById(id));
//    }
//
//    /**
//     * 新增数据
//     *
//     * @param user 实体对象
//     * @return 新增结果
//     */
//    @PostMapping
//    public R insert(@RequestBody User user) {
//        return success(this.userService.save(user));
//    }
//
//    /**
//     * 修改数据
//     *
//     * @param user 实体对象
//     * @return 修改结果
//     */
//    @PutMapping
//    public R update(@RequestBody User user) {
//        return success(this.userService.updateById(user));
//    }
//
//    /**
//     * 删除数据
//     *
//     * @param idList 主键结合
//     * @return 删除结果
//     */
//    @DeleteMapping
//    public R delete(@RequestParam("idList") List<Long> idList) {
//        return success(this.userService.removeByIds(idList));
//    }
}

