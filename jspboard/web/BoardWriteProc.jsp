<%@ page import="model.BoardDAO" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-10-23
  Time: ���� 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=euc-kr" language="java" pageEncoding="euc-kr" %>
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

    //������ ���� �޼ҵ� ȣ��
    boardDAO.insertBoard(boardbean);
    //�Խñ� ������ ��ü�Խñ� ����
    response.sendRedirect("BoardList.jsp");
%>

</body>
</html>
