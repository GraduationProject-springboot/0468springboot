package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.JiankangxinxiEntity;
import com.entity.view.JiankangxinxiView;

import com.service.JiankangxinxiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;
import java.io.IOException;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import scala.Tuple2;

/**
 * 健康信息
 * 后端接口
 * @author 
 * @email 
 * @date 2023-05-05 14:39:18
 */
@RestController
@RequestMapping("/jiankangxinxi")
public class JiankangxinxiController {
    @Autowired
    private JiankangxinxiService jiankangxinxiService;
    @Autowired
    private JavaSparkContext javaSparkContext;


    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,JiankangxinxiEntity jiankangxinxi,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			jiankangxinxi.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<JiankangxinxiEntity> ew = new EntityWrapper<JiankangxinxiEntity>();

		PageUtils page = jiankangxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiankangxinxi), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,JiankangxinxiEntity jiankangxinxi, 
		HttpServletRequest request){
        EntityWrapper<JiankangxinxiEntity> ew = new EntityWrapper<JiankangxinxiEntity>();

		PageUtils page = jiankangxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiankangxinxi), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( JiankangxinxiEntity jiankangxinxi){
       	EntityWrapper<JiankangxinxiEntity> ew = new EntityWrapper<JiankangxinxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( jiankangxinxi, "jiankangxinxi")); 
        return R.ok().put("data", jiankangxinxiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(JiankangxinxiEntity jiankangxinxi){
        EntityWrapper< JiankangxinxiEntity> ew = new EntityWrapper< JiankangxinxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( jiankangxinxi, "jiankangxinxi")); 
		JiankangxinxiView jiankangxinxiView =  jiankangxinxiService.selectView(ew);
		return R.ok("查询健康信息成功").put("data", jiankangxinxiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        JiankangxinxiEntity jiankangxinxi = jiankangxinxiService.selectById(id);
        return R.ok().put("data", jiankangxinxi);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        JiankangxinxiEntity jiankangxinxi = jiankangxinxiService.selectById(id);
        return R.ok().put("data", jiankangxinxi);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody JiankangxinxiEntity jiankangxinxi, HttpServletRequest request){
    	jiankangxinxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(jiankangxinxi);
        jiankangxinxiService.insert(jiankangxinxi);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody JiankangxinxiEntity jiankangxinxi, HttpServletRequest request){
    	jiankangxinxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(jiankangxinxi);
        jiankangxinxiService.insert(jiankangxinxi);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody JiankangxinxiEntity jiankangxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jiankangxinxi);
        jiankangxinxiService.updateById(jiankangxinxi);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        jiankangxinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	









}
