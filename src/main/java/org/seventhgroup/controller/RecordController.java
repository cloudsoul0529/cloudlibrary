package org.seventhgroup.controller;

import org.seventhgroup.dto.PageResult;
import org.seventhgroup.pojo.Record;
import org.seventhgroup.pojo.User;
import org.seventhgroup.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 借阅记录Controller
 * @author sooooya838
 */
@Controller
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    /**
     * 查询借阅记录
     *
     * @param record   查询条件
     * @param pageNum  当前页码（默认为1）
     * @param pageSize 每页显示数量（默认为10）
     * @param request  请求对象
     * @param session  会话对象
     * @return 视图模型
     */
    @RequestMapping("/searchRecords")
    public ModelAndView searchRecords(Record record,
                                      @RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      HttpServletRequest request,
                                      HttpSession session) {

        // 1. 获取当前登录用户
        User user = (User) session.getAttribute("USER_SESSION");

        // 2. 调用业务层查询
        PageResult pageResult = recordService.searchRecords(record, user, pageNum, pageSize);

        // 3. 封装视图
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/record");
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.addObject("search", record);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("gourl", request.getRequestURI());

        return modelAndView;
    }
}