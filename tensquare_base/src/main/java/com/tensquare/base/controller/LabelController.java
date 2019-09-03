package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liuhua
 * @date 2019/9/3 21:34
 */
@CrossOrigin // 设置跨域
@RestController
@RequestMapping(value = "/label")
public class LabelController {
    @Autowired
    private LabelService labelService;
    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询成功。", labelService.findAll());
    }

    @GetMapping(value = "/{labelId}")
    public Result findById(@PathVariable String labelId){
        return new Result(true, StatusCode.OK, "通过id查询单条成功。", labelService.findById(labelId));
    }

    // 添加新的标签
    @PostMapping()
    public Result save(@RequestBody Label label){
        labelService.save(label);
        return new Result(true, StatusCode.OK, "保存成功。");
    }

    @PutMapping(value = "/{labelId}")
    public Result update(@PathVariable String labelId, @RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "更新成功。");
    }

    @DeleteMapping(value = "/{labelId}")
    public Result delete(@PathVariable String labelId){
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK, "删除成功。");
    }

    @PostMapping(value = "/search")
    public Result findSearch(@RequestBody Label label){
        List<Label> labelList = labelService.findSearch(label);
        return new Result(true, StatusCode.OK, "查询成功。", labelList);
    }

    @PostMapping(value = "/search/{page}/{size}")
    public Result pageQuery(@PathVariable int page, @PathVariable int size, @RequestBody Label label){
        Page<Label> pageData = labelService.pageQuery(label, page, size);
        return new Result(true, StatusCode.OK, "查询成功。", new PageResult<Label>(pageData.getTotalElements(), pageData.getContent()));
    }
}
