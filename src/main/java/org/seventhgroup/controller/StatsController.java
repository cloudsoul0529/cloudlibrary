package org.seventhgroup.controller;

import org.seventhgroup.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.OutputStream;

@Controller
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @RequestMapping("/dashboard")
    public ModelAndView showDashboard(HttpServletResponse response) {
        // 防缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        ModelAndView mav = new ModelAndView("/stats_dashboard");

        // 1. 基础数据
        mav.addObject("data", statsService.getStatsData());

        // 2. 热门藏书 Top 5
        mav.addObject("top5", statsService.getTop5Books());

        // 3. [关键！] 每日趋势数据
        // 如果你的 Service 还没写 getDailyTrend 方法，请先去 Service 补上！
        List<Map<String, Object>> dailyData = statsService.getDailyTrend();
        mav.addObject("dailyData", dailyData);

        // 4. [之前写的] 借阅明细导出可能用到的数据（如果有的话）

        return mav;
    }

    /**
     * [su0Tmore] 功能2：导出Excel
     */
    @RequestMapping("/export")
    public void exportReport(HttpServletResponse response) {
        try {
            // 1. 获取数据
            List<Map<String, Object>> list = statsService.getReportList();

            // 2. 创建一个 Excel 工作簿
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("借阅报表");

            // 3. 设置表头样式 (加粗 + 居中)
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true); // 字体加粗
            font.setFontHeightInPoints((short) 12); // 字号
            headerStyle.setFont(font);
            headerStyle.setAlignment(HorizontalAlignment.CENTER); // 水平居中

            // 4. 创建表头行 (第0行)
            Row headerRow = sheet.createRow(0);
            String[] headers = {"图书名称", "标准ISBN", "借阅用户", "借阅时间", "应还时间"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // 5. 填充数据行
            int rowNum = 1;
            for (Map<String, Object> map : list) {
                Row row = sheet.createRow(rowNum++);

                // 获取数据 (防空指针)
                String bName = map.get("bName") != null ? map.get("bName").toString() : "";
                String isbn = map.get("isbn") != null ? map.get("isbn").toString() : "";
                String uName = map.get("uName") != null ? map.get("uName").toString() : "";
                String bTime = map.get("bTime") != null ? map.get("bTime").toString() : "";
                String rTime = map.get("rTime") != null ? map.get("rTime").toString() : "";

                row.createCell(0).setCellValue(bName);
                row.createCell(1).setCellValue(isbn);
                row.createCell(2).setCellValue(uName);
                row.createCell(3).setCellValue(bTime);
                row.createCell(4).setCellValue(rTime);
            }

            // 6. [关键一步] 自动调整列宽，让内容不被遮挡
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
                // 稍微再宽一点点，视觉更舒服
                sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1024);
            }

            // 7. 导出文件
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String fileName = URLEncoder.encode("借阅报表.xlsx", "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}