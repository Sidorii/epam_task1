<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="language" scope="session" value="${empty sessionScope.lang ? 'en_EN' : sessionScope.lang}"/>
<fmt:setLocale value="${language}"/>
<fmt:bundle basename="MessageBundle" prefix="header.">
    <li class="central">
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
            <li style="text-align: center"><a href="<c:url value="/locale?lang=en_EN"/>">English</a></li>
            <li style="text-align: center"><a href="<c:url value="/locale?lang=uk_UA"/>">Українська</a></li>
            <li style="text-align: center"><a href="<c:url value="/locale?lang=ru_RU"/>">Русский</a></li>
        </ul>
    </li>
</fmt:bundle>