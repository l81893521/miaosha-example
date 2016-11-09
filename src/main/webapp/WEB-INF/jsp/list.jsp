<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀列表</title>
    <%@include file="common/head.jsp"%>
</head>
<body>
    <!-- 页面显示部分 -->
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h1>秒杀列表</h1>
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>名称</th>
                            <th>库存</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>创建时间</th>
                            <th>详情</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="seckills" items="${seckills}">
                            <tr>
                                <td>${seckills.name}</td>
                                <td>${seckills.number}</td>
                                <td><fmt:formatDate value="${seckills.startTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                                <td><fmt:formatDate value="${seckills.endTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                                <td><fmt:formatDate value="${seckills.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                                <td><a class="btn btn-info" href="/seckill/${seckills.seckillId}/detail" target="_blank">详情</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>
