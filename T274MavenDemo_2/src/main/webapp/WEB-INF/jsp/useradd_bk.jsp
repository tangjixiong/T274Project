<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fm" uri="http://www.springframework.org/tags/form" %>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>

<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>用户管理页面 >> 用户添加页面</span>
        </div>
        <div class="providerAdd">
            <fm:form modelAttribute="user" enctype="multipart/form-data" method="post" action="${pageContext.request.contextPath }/useradd.html">

                用户编码：<fm:input path="userCode"  /><fm:errors path="userCode" /> <br />

                用户名称：<fm:input path="userName" /><fm:errors path="userName" /> <br />

                用户密码：<fm:password path="userPassword" /> <fm:errors path="userPassword" /> <br />

                用户生日：<fm:input path="birthday" /> <fm:errors path="birthday" /> <br />

                用户角色：<fm:radiobutton path="userRole" value="1" />系统管理员
                <fm:radiobutton path="userRole" value="2" />经理
                <fm:radiobutton path="userRole" value="3" />普通员工  <fm:errors path="userRole" /><br />
               证件照：	<input type="file" name="attachs" id="a_idPicPath"/>
                <br />
                工作证照：	<input type="file" name="attachs" id="a_idPicPath"/>
                <br />
                <input type="submit" value="保存" />
            </fm:form>
        </div>
</div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/useradd.js"></script>
