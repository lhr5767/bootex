<%@ page import="model.BoardDAO" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-10-24
  Time: 오후 9:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>

<%
    String pass = request.getParameter("password");
    int num = Integer.parseInt(request.getParameter("num"));

    BoardDAO boardDAO = new BoardDAO();
    String password = boardDAO.getPass(num);

    //기존 패스워드와 deleteform 에서 작성한 패스워드 비교
    if(pass.equals(password)) {

        //같으면 삭제
        boardDAO.deleteBoard(num);

        response.sendRedirect("BoardList.jsp");
    }else {
%>
<script type="text/javascript">
    alert("패스워드가 틀립니다.");
    history.go(-1);
</script>

<%
    }
%>

</body>
</html>
