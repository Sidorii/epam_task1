<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:bundle basename="MessageBundle" prefix="header.">
    <div class=" row page-header">
        <div class="col-sm-3">
            <h1><fmt:message key="chef"/></h1>
        </div>
        <div class="col-sm-9">
            <ol class="breadcrumb" style="position: relative; margin-top: 25px; float: right">
                <li><a href="<c:url value="/"/>"><fmt:message key="home"/></a></li>
                <li><a href="<c:url value="/salads"/>"><fmt:message key="order"/></a></li>
                <li>
                    <a href="<c:url value="/ingredients"/>"><fmt:message key="ingredients"/></a>
                </li>
                <c:if test="${not empty sessionScope.auth}">
                    <li>
                        <a href="<c:url value="/storekeeper/create/ingredient"/>"><fmt:message
                                key="ingredient"/></a>
                    </li>
                    <li><a href="<c:url value="/chef/create/salad"/>"><fmt:message key="recipe"/></a>
                    </li>
                </c:if>
                <li>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span
                            class="glyphicon glyphicon-menu-hamburger"></span></a>
                    <ul class="dropper dropdown-menu dropdown-menu-sw" role="menu">

                        <c:choose>
                            <c:when test="${not empty sessionScope.auth}">
                                <li style="text-align: center"><fmt:message key="you"/>,
                                    <b>${sessionScope.auth.name}</b>!
                                </li>
                                <li class="divider"></li>
                                <li style="text-align: center"><a
                                        href="<c:url value="/logout"/>"><fmt:message
                                        key="logout"/></a></li>
                            </c:when>
                            <c:otherwise>
                                <li style="text-align: center"><a
                                        href="<c:url value="/login"/>"><fmt:message
                                        key="login"/></a></li>
                                <li style="text-align: center"><a
                                        href="<c:url value="/registration"/>"><fmt:message
                                        key="signup"/></a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                        <li class="divider"></li>
                        <li style="text-align: center"><b><fmt:message key="lang.caption"/></b></li>
                        <li class="divider"></li>
                        <li style="text-align: center"><a href="/locale?lang=en_EN">English</a></li>
                        <li style="text-align: center"><a href="/locale?lang=uk_UA">Українська</a></li>
                        <li style="text-align: center"><a href="/locale?lang=ru_RU">Русский</a></li>
                    </ul>
                </li>
            </ol>
        </div>
    </div>
</fmt:bundle>
