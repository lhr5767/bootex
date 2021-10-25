<%@ page import="model.BoardDAO" %>
<%@ page import="model.BoardBean" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-10-24
  Time: 오후 9:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>

<%
    BoardDAO boardDAO = new BoardDAO();

    int num = Integer.parseInt(request.getParameter("num"));

    BoardBean bean = boardDAO.getOneUpdateBoard(num);
%>

<h2> 게시글 삭제</h2>
<form action="BoardDeleteProc.jsp" method="post">
    <table width="600" border="1">
        <tr height="40">
            <td width="120" align="center">작성자</td>
            <td width="180" align="center"><%=bean.getWriter()%></td>
            <td width="120" align="center">작성일</td>
            <td width="180" align="center"><%=bean.getReg_date()%></td>
        </tr>
        <tr height="40">
            <td width="120" align="center">제목</td>
            <td align="left" colspan="3"><%=bean.getSubject()%></td>
        </tr>
        <tr height="40">
            <td width="120" align="center">패스워드</td>
            <td align="left" colspan="3"><input size="60" type="password" name="password"></td>
        </tr>
        <tr height="40">
            <td align="center" colspan="4">
                <input type="hidden" name="num" value="<%=bean.getNum()%>">
                <input type="submit" value="글삭제"> &nbsp;&nbsp;
                <input type="button" onclick="location.href='BoardList.jsp'" value="목록보기">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
