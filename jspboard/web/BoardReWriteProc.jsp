<%@ page import="model.BoardDAO" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-10-24
  Time: 오후 7:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=euc-kr" language="java" pageEncoding="EUC-KR" %>
<html>

<body>
<%
    request.setCharacterEncoding("euc-kr");
%>
<jsp:useBean id="boardbean" class="model.BoardBean">
    <jsp:setProperty name="boardbean" property="*"/>
</jsp:useBean>

<%
    BoardDAO boardDAO = new BoardDAO();
    boardDAO.reWriteBoard(boardbean);

    //답변 데이터 저장후 전체 게시글 보기로 가기
    response.sendRedirect("BoardList.jsp");
%>

</body>
</html>
