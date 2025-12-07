<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>数据统计</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/_all-skins.min.css">
    <!-- 引入 ECharts -->
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/echarts@5.4.3/dist/echarts.min.js"></script>
</head>

<body class="hold-transition skin-red sidebar-mini">
<!-- [su0Tmore]  -->
<section class="content">

    <!-- 顶部卡片 -->
    <div class="row">
        <div class="col-lg-4 col-xs-6">
            <div class="small-box bg-aqua">
                <div class="inner"><h3>${data.book}</h3><p>图书总数</p></div>
                <div class="icon"><i class="fa fa-book"></i></div>
                <a href="${pageContext.request.contextPath}/book/search" class="small-box-footer">去管理 <i class="fa fa-arrow-circle-right"></i></a>
            </div>
        </div>
        <div class="col-lg-4 col-xs-6">
            <div class="small-box bg-green">
                <div class="inner"><h3>${data.user}</h3><p>注册用户</p></div>
                <div class="icon"><i class="fa fa-user"></i></div>
                <a href="${pageContext.request.contextPath}/user/search" class="small-box-footer">去管理 <i class="fa fa-arrow-circle-right"></i></a>
            </div>
        </div>
        <div class="col-lg-4 col-xs-6">
            <div class="small-box bg-yellow">
                <div class="inner"><h3>${data.record}</h3><p>借阅记录</p></div>
                <div class="icon"><i class="fa fa-list-alt"></i></div>
                <a href="${pageContext.request.contextPath}/record/searchRecords" class="small-box-footer">去查看 <i class="fa fa-arrow-circle-right"></i></a>
            </div>
        </div>
    </div>

    <!-- 图表区 -->
    <div class="row">
        <div class="col-md-6">
            <div class="box box-primary">
                <div class="box-header with-border"><h3 class="box-title">数据占比</h3></div>
                <div class="box-body"><div id="chartPie" style="height:350px;"></div></div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="box box-success">
                <div class="box-header with-border">
                    <h3 class="box-title">数据对比</h3>
                    <div class="box-tools pull-right">
                        <a href="${pageContext.request.contextPath}/stats/export" class="btn btn-sm btn-success"><i class="fa fa-download"></i> 导出报表</a>
                    </div>
                </div>
                <div class="box-body"><div id="chartBar" style="height:350px;"></div></div>
            </div>
        </div>
    </div>

</section>

<!-- 脚本 -->
<script>
    $(function(){
        var chartPie = echarts.init(document.getElementById('chartPie'));
        var optionPie = {
            tooltip: { trigger: 'item' },
            legend: { bottom: '0%' },
            color: ['#00c0ef', '#00a65a', '#f39c12'],
            series: [{
                type: 'pie', radius: ['40%', '70%'], avoidLabelOverlap: false,
                itemStyle: { borderRadius: 10, borderColor: '#fff', borderWidth: 2 },
                label: { show: false },
                data: [{value: ${data.book}, name: '图书'}, {value: ${data.user}, name: '用户'}, {value: ${data.record}, name: '借阅'}]
            }]
        };
        chartPie.setOption(optionPie);

        var chartBar = echarts.init(document.getElementById('chartBar'));
        var optionBar = {
            tooltip: { trigger: 'axis' },
            grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
            xAxis: { type: 'category', data: ['图书库存', '注册用户', '借阅记录'] },
            yAxis: { type: 'value' },
            series: [{
                type: 'bar', barWidth: '40%',
                data: [{value: ${data.book}, itemStyle: {color: '#00c0ef'}}, {value: ${data.user}, itemStyle: {color: '#00a65a'}}, {value: ${data.record}, itemStyle: {color: '#f39c12'}}]
            }]
        };
        chartBar.setOption(optionBar);
        window.onresize = function() { chartPie.resize(); chartBar.resize(); };
    });
</script>
</body>
</html>