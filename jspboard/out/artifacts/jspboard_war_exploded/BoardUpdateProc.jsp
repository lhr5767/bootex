<%@ page import="model.BoardDAO" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-10-24
  Time: ���� 8:42
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
    //db ������ password ��������
    BoardDAO boardDAO = new BoardDAO();
    String pass = boardDAO.getPass(boardbean.getNum());

    //���� password�� update�� �ۼ��� password�� ������ ��
    if(pass.equals(boardbean.getPassword())) {

        //������ ���� �޼ҵ� ȣ��
        boardDAO.updateBoard(boardbean);

        response.sendRedirect("BoardList.jsp");
    }else {
%>
<script type="text/javascript">
    alert("�н����尡 ��ġ���� �ʽ��ϴ�");
    history.go(-1);
</script>
<%
    }
%>

</body>
</html>
