<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="language" scope="session" value="${empty sessionScope.lang ? 'en_EN' : sessionScope.lang}"/>
<fmt:setLocale value="${language}"/>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="/resources/login.js"></script>
    <link rel="stylesheet" href="/resources/style.css" type="text/css">
</head>
<body>
<div class="container">
    <jsp:include page="elements/statelessHeader.jsp"/>
    <div style="color: red;text-align: center">
        <h4>
            <c:if test="${not empty requestScope.invalid}">
                ${requestScope.invalid}
            </c:if>
        </h4>
    </div>
    <fmt:bundle basename="MessageBundle" prefix="header.">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <div class="panel panel-login">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-6">
                                <a href="#" class="active" id="login-form-link"><fmt:message key="login"/></a>
                            </div>
                            <div class="col-xs-6">
                                <a href="#" id="register-form-link"><fmt:message key="registration"/></a>
                            </div>
                        </div>
                        <hr>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <form id="login-form" action="<c:url value="/login"/>" method="post" role="form"
                                      style="display: block;">
                                    <div class="form-group">
                                        <input type="email" name="email" id="email" tabindex="1" class="form-control"
                                               placeholder="Email Address">
                                    </div>
                                    <div class="form-group">
                                        <input type="password" name="password" id="password" tabindex="2"
                                               class="form-control" placeholder="Password">
                                    </div>

                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-sm-6 col-sm-offset-3">
                                                <input type="submit" name="login-submit" id="login-submit" tabindex="4"
                                                       class="form-control btn btn-login"
                                                       value="<fmt:message key="login"/>">
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <form id="register-form" action="<c:url value="/registration"/>" method="post"
                                      role="form"
                                      style="display: none;">
                                    <div class="form-group">
                                        <input type="text" name="name" id="username" class="form-control"
                                               placeholder="Username">
                                    </div>
                                    <div class="form-group">
                                        <input type="email" name="email" id="email" class="form-control"
                                               placeholder="Email Address">
                                    </div>
                                    <div class="form-group">
                                        <input type="password" name="password" id="password"
                                               class="form-control" placeholder="Password">
                                    </div>
                                    <div class="form-group">
                                        <input type="password" name="confirm-password" id="confirm-password"
                                               class="form-control" placeholder="Confirm Password">
                                    </div>
                                    <div class="form-group">
                                        <div class="checkbox" style=" text-align: center;">
                                            <c:forEach var="r" items="${requestScope.roles}">
                                                <div class="dlk-radio btn-group" style="margin: 0 auto;">
                                                    <label class="btn btn-success">
                                                        <input name="roles" class="form-control" type="checkbox"
                                                               value="${r.name()}">
                                                        <i class="fa fa-check glyphicon glyphicon-ok"></i>
                                                            ${r.name()}
                                                    </label>
                                                </div>
                                            </c:forEach>
                                        </div>

                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-sm-6 col-sm-offset-3">
                                                <input type="submit" name="register-submit" id="register-submit"
                                                       tabindex="4" class="form-control btn btn-register"
                                                       value="<fmt:message key="signup"/>">
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </fmt:bundle>
</div>
</body>
</html>
