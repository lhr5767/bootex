<%@ page import="model.BoardDAO" %>
<%@ page import="model.BoardBean" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-10-24
  Time: 오후 4:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>

<%
    //공백제거후 정수형으로 바꿈
    int num = Integer.parseInt(request.getParameter("num").trim());

    //DB 접근
    BoardDAO boardDAO = new BoardDAO();

    BoardBean bean = boardDAO.getOneBoard(num);
%>

<h2>게시글 보기</h2>
<table width="600" border="1" >
    <tr height="40">
        <td align="center" width="120"> 글번호 </td>
        <td align="center" width="180"> <%=bean.getNum()%></td>
        <td align="center" width="120"> 조회수 </td>
        <td align="center" width="180"> <%=bean.getReadcount()%></td>
    </tr>
    <tr height="40">
        <td align="center" width="120">작성자</td>
        <td align="center" width="180"><%=bean.getWriter()%></td>
        <td align="center" width="120"> 작성일</td>
        <td align="center" width="180"><%=bean.getReg_date()%></td>
    </tr>
    <tr height="40">
        <td align="center" width="120">이메일</td>
        <td align="center" colspan="3"><%=bean.getEmail()%></td>
    </tr>
    <tr height="40">
        <td align="center" width="120">제목</td>
        <td align="center" colspan="3"><%=bean.getSubject()%></td>
    </tr>
    <tr height="40">
        <td align="center" width="120">글 내용</td>
        <td align="center" colspan="3"><%=bean.getContent()%></td>
    </tr>
    <tr height="40">
        <td align="center" colspan="4">
            <input type="button" value="답글쓰기" onclick="location.href='BoardReWriteForm.jsp?num=<%=bean.getNum()%>&ref=<%=bean.getRef()%>&re_step=<%=bean.getRe_step()%>&re_level=<%=bean.getRe_level()%>'">
            <input type="button" value="수정하기" onclick="location.href='BoardUpdateForm.jsp?num=<%=bean.getNum()%>'">
            <input type="button" value="삭제하기" onclick="location.href='BoardDeleteForm.jsp?num=<%=bean.getNum()%>'">
            <input type="button" value="목록보기" onclick="location.href='BoardList.jsp'">
        </td>
    </tr>
</table>

</body>
</html>
