<%@ page import="model.BoardDAO" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-10-24
  Time: 오후 8:42
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
    //db 연결후 password 가져오기
    BoardDAO boardDAO = new BoardDAO();
    String pass = boardDAO.getPass(boardbean.getNum());

    //기존 password와 update시 작성한 password가 같은지 비교
    if(pass.equals(boardbean.getPassword())) {

        //데이터 수정 메소드 호출
        boardDAO.updateBoard(boardbean);

        response.sendRedirect("BoardList.jsp");
    }else {
%>
<script type="text/javascript">
    alert("패스워드가 일치하지 않습니다");
    history.go(-1);
</script>
<%
    }
%>

</body>
</html>
