package org.seventhgroup.controller;

import org.seventhgroup.pojo.Book;
import org.seventhgroup.pojo.User;
import org.seventhgroup.service.BookService;
import org.seventhgroup.dto.PageResult;
import org.seventhgroup.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
图书信息Controller
 */
@Controller
@RequestMapping("/book")
public class BookController {
    //注入BookService对象
    @Autowired
    private BookService bookService;

    /**
     * 新书推荐页面
     */
    @RequestMapping("/selectNewbooks")
    public ModelAndView selectNewbooks() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/books_new");
        try {
            PageResult pageResult = bookService.selectNewBooks();
            modelAndView.addObject("pageResult", pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("errorMsg", "查询新书失败，请稍后重试");
        }
        return modelAndView;
    }

    /**
     * 根据ID查询图书 - 优化空值校验、代码注释
     */
    @ResponseBody
    @RequestMapping("/findById")
    public Result<Book> findById(String id) {
        try {
            // 空值校验
            if (id == null || id.trim().isEmpty()) {
                return new Result<>(false, "图书ID不能为空！");
            }
            Book book = bookService.findById(id);
            if (book == null) {
                return new Result<>(false, "查询图书失败！");
            }
            return new Result<>(true, "查询图书成功", book);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, "查询图书失败！");
        }
    }

    /**
     * 借阅图书 - 优化空值校验、代码注释
     */
    @ResponseBody
    @RequestMapping("/borrowBook")
    public Result borrowBook(Book book, HttpSession session) {
        try {
            // 校验登录用户
            User loginUser = (User) session.getAttribute("USER_SESSION");
            if (loginUser == null) {
                return new Result(false, "请先登录！");
            }
            // 校验图书ID
            if (book.getId() == null || book.getId().toString().trim().isEmpty()) {
                return new Result(false, "图书ID不能为空！");
            }
            // 设置借阅人
            book.setBorrower(loginUser.getName());
            Integer count = bookService.borrowBook(book);
            if (count != 1) {
                return new Result(false, "借阅图书失败!");
            }
            return new Result(true, "借阅成功，请到行政中心取书!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "借阅图书失败!");
        }
    }
    /**
     * 分页查询符合条件且未下架图书信息
     * @param book 查询的条件封装到book中
     * @param pageNum  数据列表的当前页码
     * @param pageSize 数据列表1页展示多少条数据
     */
    @RequestMapping("/search")
    public ModelAndView search(Book book, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        if (pageNum == null)
        {
            pageNum = 1;
        }
        if (pageSize == null)
        {
            pageSize = 10;
        }
        //查询到的图书信息
        PageResult pageResult = bookService.search(book, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/books");
        //将查询到的数据存放在 ModelAndView的对象中
        modelAndView.addObject("pageResult", pageResult);
        //将查询的参数返回到页面，用于回显到查询的输入框中
        modelAndView.addObject("search", book);
        //将当前页码返回到页面，用于分页插件的分页显示
        modelAndView.addObject("pageNum", pageNum);
        //将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }

    /**
     * 新增图书
     * @param book 页面表单提交的图书信息
     * 将新增的结果和向页面传递信息封装到Result对象中返回
     */
    @ResponseBody
    @RequestMapping("/addBook")
    public Result addBook(Book book)
    {
        try
        {
            Integer count=bookService.addBook(book);
            if(count!=1)
            {
                return new Result(false, "新增图书失败!");
            }
            return new Result(true, "新增图书成功!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new Result(false, "新增图书失败!");
        }
    }

    /**
     * 编辑图书信息
     * @param book 编辑的图书信息
     */
    @ResponseBody
    @RequestMapping("/editBook")
    public Result editBook(Book book) {
        try
        {
            Integer count= bookService.editBook(book);
            if(count!=1)
            {
                return new Result(false, "编辑失败!");
            }
            return new Result(true, "编辑成功!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new Result(false, "编辑失败!");
        }
    }

    /**
     *分页查询当前被借阅且未归还的图书信息
     * @param pageNum  数据列表的当前页码
     * @param pageSize 数据列表1页展示多少条数据
     */
    @RequestMapping("/searchBorrowed")
    // 1. 方法参数里加上 String showType
    public ModelAndView searchBorrowed(Book book, Integer pageNum, Integer pageSize, HttpServletRequest request, String showType) {
        if (pageNum == null) pageNum = 1;
        if (pageSize == null) pageSize = 10;
        User user = (User) request.getSession().getAttribute("USER_SESSION");

        // 2. 调用 Service 时传入 showType
        PageResult pageResult = bookService.searchBorrowed(book, user, pageNum, pageSize, showType);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/book_borrowed");
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.addObject("search", book);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("gourl", request.getRequestURI());

        // 3. 把 showType 传回给页面，用于判断哪个 Tab 应该是激活状态
        modelAndView.addObject("showType", showType);

        return modelAndView;
    }
    /**
     * 归还图书
     * @param id 归还的图书的id
     */
    @ResponseBody
    @RequestMapping("/returnBook")
    public Result returnBook(String id, HttpSession session) {
        //获取当前登录的用户信息
        User user = (User) session.getAttribute("USER_SESSION");
        try {
            boolean flag = bookService.returnBook(id, user);
            if (!flag) {
                return new Result(false, "还书失败!");
            }
            return new Result(true, "还书确认中，请先到行政中心还书!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "还书失败!");
        }
    }

    /**
     * 确认图书归还
     * @param id 确认归还的图书的id
     */
    @ResponseBody
    @RequestMapping("/returnConfirm")
    public Result returnConfirm(String id) {
        try {
            Integer count=bookService.returnConfirm(id);
            if(count!=1){
                return new Result(false, "确认失败!");
            }
            return new Result(true, "确认成功!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "确认失败!");
        }
    }

}