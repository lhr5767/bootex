<%@ page import="model.BoardDAO" %>
<%@ page import="model.BoardBean" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-10-24
  Time: 오후 8:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=euc-kr" language="java" pageEncoding="EUC-KR" %>
<html>

<body>
<%
    request.setCharacterEncoding("euc-kr");
%>
<h2>게시글 수정</h2>
<%
    //num(pk) 값 가져와서 게시글 수정
    int num = Integer.parseInt(request.getParameter("num").trim());
    //해당 num에 대한 정보 가져오기
    BoardDAO boardDAO = new BoardDAO();
    BoardBean bean = boardDAO.getOneUpdateBoard(num);
%>
<form action="BoardUpdateProc.jsp" method="post">
    <table width="600" border="1">
        <tr height="40">
            <td width="120" align="center"> 작성자 </td>
            <td width="180" align="center"><%=bean.getWriter()%></td>
            <td width="120" align="center">작성일</td>
            <td width="180" align="center"><%=bean.getReg_date()%></td>
        </tr>
        <tr height="40">
            <td width="120" align="center">제목</td>
            <td width="480" colspan="3"> &nbsp; <input size="60" type="text" name="subject" value="<%=bean.getSubject()%>"></td>
        </tr>
        <tr height="40">
            <td width="120" align="center">패스워드</td>
            <td width="480" colspan="3"> &nbsp; <input size="60" type="password" name="password"></td>
        </tr>
        <tr height="40">
            <td width="120" align="center">글 내용</td>
            <td width="480" colspan="3"><textarea name="content" cols="60" rows="10" align="left"> <%=bean.getContent()%></textarea></td>
        </tr>
        <tr height="40">
            <td colspan="4" align="center">
                <input type="hidden" name="num" value="<%=bean.getNum()%>">
                <input type="submit" value="글 수정"> &nbsp;&nbsp;
                <input type="button" onclick="location.href='BoardList.jsp'" value="전체글보기">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
