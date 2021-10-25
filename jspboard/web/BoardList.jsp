<%@ page import="model.BoardDAO" %>
<%@ page import="java.util.Vector" %>
<%@ page import="model.BoardBean" %><%--
  Created by IntelliJ IDEA.
  User: dlghk
  Date: 2021-10-24
  Time: 오후 4:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>



<h2>전체 게시글 보기</h2>
<!-- 게시글 보기 페이정 처리하기-->
<%
    //화면에 보여질 게시글의 갯수 지정
    int pageSize=10;

    //현재 보고있는 페이지 번호
    String pageNum = request.getParameter("pageNum");

    if(pageNum==null) {
        pageNum="1";
    }

    int count = 0 ;   //전체 글의 갯수 저장
    int number = 0; //페이지 번호 넘버링


    int currentPage = Integer.parseInt(pageNum);



    BoardDAO boardDAO = new BoardDAO();

    //전체 개시글 갯수 가져오기
    count = boardDAO.getAllCount();

    //현재 페이지에 보여줄 시작번호 설정
    int startRow = (currentPage-1)*pageSize+1;
    int endRow = currentPage * pageSize;

    //최신글 10개 기준으로 게시글을 리턴
    Vector<BoardBean> vector = boardDAO.getAllBoard(startRow,endRow);

    number = count - (currentPage-1) * pageSize;


%>



<table width="700" border="1">
    <tr height="40">
        <td align="right" colspan="5">
            <input type="button" value="글쓰기" onclick="location.href='BoardWriteForm.jsp'">
        </td>
    </tr>
    <tr height="40">
        <td width="50" align="center"> 글 번호 </td>
        <td width="320" align="center"> 제목 </td>
        <td width="100" align="center"> 작성자 </td>
        <td width="150" align="center"> 작성일 </td>
        <td width="80" align="center"> 조회수 </td>
    </tr>
<%
    for(int i=0; i<vector.size(); i++) {
        BoardBean bean = vector.get(i);

%>
    <tr height="40">
        <td width="50" align="center"> <%=number--%> </td>
        <td width="320" align="left"><a href="BoardInfo.jsp?num=<%=bean.getNum()%>" style="text-decoration: none">
        <%  //답변 들여쓰기
            if(bean.getRe_step() > 1) {
                for(int j=0; j<(bean.getRe_step()-1)*5;j++) {
        %> &nbsp;
        <%    }

            }
        %>

            <%=bean.getSubject()%>
        </a> </td>
        <td width="100" align="center"> <%=bean.getWriter()%> </td>
        <td width="150" align="center"> <%=bean.getReg_date()%> </td>
        <td width="80" align="center"> <%=bean.getReadcount()%> </td>
    </tr>
<% } %>

</table>
<p>
<%
    if(count > 0) {
        int pageCount = count / pageSize +(count%pageSize == 0 ? 0: 1);

        int startPage = 1;

        if(currentPage % 10 != 0) {
            startPage = (int)(currentPage/10)*10+1;
        }else {
            startPage = ((int)(currentPage/10)-1)*10+1;
        }

        int pageBlock = 10;
        int endPage = startPage+pageBlock-1;

        if(endPage>pageCount) endPage = pageCount;

        if(startPage > 10) {
%>
        <a href="BoardList.jsp?pageNum=<%=startPage-10%>">[이전]</a>
<%
    }
        for(int i=startPage; i<=endPage ; i++) {
%>
        <a href="BoardList.jsp?pageNum=<%=i%>">[<%=i%>]</a>
<%
    }
        if(endPage < pageCount){
%>
        <a href="BoardList.jsp?pageNum=<%=startPage+10%>">[다음]</a>
<%
    }
    }
%>
</p>
</body>
</html>
