<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>数据统计</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/_all-skins.min.css">

    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/echarts@5.4.3/dist/echarts.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
</head>

<body class="hold-transition skin-green sidebar-mini">
<section class="content">

    <div class="row" style="margin-bottom: 15px;">
        <div class="col-md-12 text-right">
            <a href="${pageContext.request.contextPath}/stats/export" class="btn btn-success">
                <i class="fa fa-file-excel-o"></i> 导出完整借阅明细 (Excel)
            </a>
        </div>
    </div>

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

    <div class="row">
        <div class="col-md-6">
            <div class="box box-success" id="dailyBox">
                <div class="box-header with-border">
                    <h3 class="box-title"><i class="fa fa-line-chart"></i> 每日借阅趋势</h3>
                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-sm btn-success" onclick="exportPDF('dailyBox', '每日借阅趋势')">
                            <i class="fa fa-file-pdf-o"></i> 导出 PDF
                        </button>
                    </div>
                </div>
                <div class="box-body" style="background-color: #fff;">
                    <div id="chartDaily" style="height:380px;"></div>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="box box-danger" id="top5Box">
                <div class="box-header with-border">
                    <h3 class="box-title"><i class="fa fa-trophy"></i> 热门藏书排行榜 (Top 5)</h3>
                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-sm btn-danger" onclick="exportPDF('top5Box', '热门藏书Top5')">
                            <i class="fa fa-file-pdf-o"></i> 导出 PDF
                        </button>
                    </div>
                </div>
                <div class="box-body" style="background-color: #fff;">
                    <div id="chartBar" style="height:380px;"></div>
                </div>
            </div>
        </div>
    </div>
</section>

<script>
    $(function(){
        var chartLine = echarts.init(document.getElementById('chartDaily'));
        var days = []; var counts = [];
        <c:forEach items="${dailyData}" var="item">
        days.push('${item.name}'); counts.push(${item.value});
        </c:forEach>
        if(days.length === 0) { days=["暂无数据"]; counts=[0]; }

        var optionLine = {
            tooltip: { trigger: 'axis' },
            grid: { left: '3%', right: '5%', bottom: '15%', containLabel: true },
            dataZoom: [{
                type: 'slider', show: true, height: 20, bottom: 10,
                borderColor: 'transparent', backgroundColor: '#f5f5f5',
                fillerColor: 'rgba(0, 192, 239, 0.2)',
                handleStyle: { color: '#00c0ef', shadowBlur: 3, shadowColor: 'rgba(0, 0, 0, 0.2)' },
                textStyle: { color: '#666' },
                labelFormatter: function (value) { return days[value] ? days[value] : ""; }
            }],
            toolbox: { show: true, feature: { magicType: { show: true, type: ['line', 'bar'] }, saveAsImage: { show: true } }, right: '5%' },
            xAxis: { type: 'category', boundaryGap: false, data: days },
            yAxis: { type: 'value', minInterval: 1 },
            series: [{
                name: '借阅量', type: 'line', smooth: true,
                itemStyle: { color: '#00c0ef' },
                lineStyle: { width: 3, color: '#00c0ef' },
                areaStyle: {
                    color: new echarts.graphic.LinearGradient(0,0,0,1,[
                        {offset:0, color:'rgba(0, 192, 239, 0.5)'},
                        {offset:1, color:'rgba(0, 192, 239, 0.1)'}
                    ])
                },
                data: counts
            }]
        };
        chartLine.setOption(optionLine);


        var bookNames = []; var bookValues = [];
        <c:forEach items="${top5}" var="item">
        bookNames.push('${item.name}'); bookValues.push(${item.value});
        </c:forEach>
        if(bookNames.length === 0){ bookNames=["暂无"]; bookValues=[0]; }

        var chartBar = echarts.init(document.getElementById('chartBar'));
        var optionBar = {
            tooltip: { trigger: 'item', formatter: '{b}: {c} 次' },
            grid: { left: '3%', right: '10%', bottom: '3%', containLabel: true },
            toolbox: { show: true, feature: { magicType: { show: true, type: ['line', 'bar'] }, saveAsImage: { show: true } }, right: '5%' },
            xAxis: { type: 'value', minInterval: 1 },
            yAxis: { type: 'category', data: bookNames, inverse: true },
            series: [{
                name: '借阅量', type: 'bar', data: bookValues,
                label: { show: true, position: 'right', color: '#666' },
                itemStyle: {
                    color: new echarts.graphic.LinearGradient(0,0,1,0,[
                        {offset:0,color:'#ff7f50'},
                        {offset:1,color:'#ff4500'}
                    ]),
                    borderRadius: [0, 10, 10, 0]
                }
            }]
        };
        chartBar.setOption(optionBar);

        chartBar.on('click', function(params) {
            var bookName = params.name;
            var url = "${pageContext.request.contextPath}/book/search?name=" + encodeURIComponent(bookName);
            window.location.href = url;
        });

        window.onresize = function() { chartLine.resize(); chartBar.resize(); };
    });

    function exportPDF(elementId, fileName) {
        var element = document.getElementById(elementId);
        html2canvas(element, { scale: 2, backgroundColor: "#ffffff" }).then(function(canvas) {
            var imgData = canvas.toDataURL('image/png');
            var pdf = new jspdf.jsPDF('l', 'mm', 'a4');
            var imgWidth = 280;
            var imgHeight = canvas.height * imgWidth / canvas.width;
            pdf.addImage(imgData, 'PNG', 10, 10, imgWidth, imgHeight);
            pdf.save(fileName + "_su0Tmore.pdf");
        });
    }
</script>
</body>
</html>