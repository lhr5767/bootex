package model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class BoardDAO {

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;

    public void getCon() {

        try{
            Context initctx = new InitialContext();
            Context emvctx = (Context) initctx.lookup("java:comp/env");
            DataSource ds = (DataSource) emvctx.lookup("jdbc/mysql");

            con = ds.getConnection();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //게시글 등록
    public void insertBoard(BoardBean bean) {

        getCon();

        //빈클래스에 넘어오지 않은 데이터 초기화 해주어야함
        int ref=0; //글그룹을 의미함 가장큰 ref 가져와서 +1 해주기
        int re_step=1; //새글은 1
        int re_level=1;

        try{
            //가장큰 ref값을 읽어오기
            String refsql = "select max(ref) from board";

            pstmt = con.prepareStatement(refsql);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                ref = rs.getInt(1)+1;
            }
            //auto_increment 컬럼엔 null 처리
            String sql = "insert into board values(NULL,?,?,?,?,now(),?,?,?,0,?)";

            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, bean.getWriter());
            pstmt.setString(2, bean.getEmail());
            pstmt.setString(3, bean.getSubject());
            pstmt.setString(4, bean.getPassword());
            pstmt.setInt(5,ref);
            pstmt.setInt(6,re_step);
            pstmt.setInt(7,re_level);
            pstmt.setString(8, bean.getContent());

            pstmt.executeUpdate();

            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //전체 게시글 가져오기
    public Vector<BoardBean> getAllBoard(int start, int end) {
        Vector<BoardBean> v = new Vector<>();

        getCon();


        try{
            //최신글 순서대로 나오게끔
            String sql = "select * from board order by ref desc, re_step asc limit ?,?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,start-1);
            pstmt.setInt(2,end);

            rs = pstmt.executeQuery();

            while (rs.next()){
                BoardBean bean = new BoardBean();
                bean.setNum(rs.getInt(1));
                bean.setWriter(rs.getString(2));
                bean.setEmail(rs.getString(3));
                bean.setSubject(rs.getString(4));
                bean.setPassword(rs.getString(5));
                bean.setReg_date(rs.getDate(6).toString());
                bean.setRef(rs.getInt(7));
                bean.setRe_step(rs.getInt(8));
                bean.setRe_level(rs.getInt(9));
                bean.setReadcount(rs.getInt(10));
                bean.setContent(rs.getString(11));

                //패키징후 벡터에 저장
                v.add(bean);
            }
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    //하나의 게시글 가져오기(BoardInfo에 사용)
    public BoardBean getOneBoard(int num) {

        BoardBean bean = new BoardBean();
        getCon();

        try {
            //조회수 증가
            String readsql = "update board set readcount = readcount+1 where num=?";
            pstmt = con.prepareStatement(readsql);
            pstmt.setInt(1,num);
            pstmt.executeUpdate();

            //게시글 가져오기
            String sql = "select * from board where num=?";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,num);

            rs = pstmt.executeQuery();
            if(rs.next()) {
                bean.setNum(rs.getInt(1));
                bean.setWriter(rs.getString(2));
                bean.setEmail(rs.getString(3));
                bean.setSubject(rs.getString(4));
                bean.setPassword(rs.getString(5));
                bean.setReg_date(rs.getDate(6).toString());
                bean.setRef(rs.getInt(7));
                bean.setRe_step(rs.getInt(8));
                bean.setRe_level(rs.getInt(9));
                bean.setReadcount(rs.getInt(10));
                bean.setContent(rs.getString(11));
            }

            con.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    //답변글 저장
    public void reWriteBoard(BoardBean bean) {

        int ref = bean.getRef();
        int re_step = bean.getRe_step();
        int re_level = bean.getRe_level();

        getCon();

        try{
            //부모 글보다 큰 re_level값을 전부 1씩 증가
            String levelsql = "update board set re_level=re_level+1 where ref=? and re_level >?";
            pstmt=con.prepareStatement(levelsql);
            pstmt.setInt(1,ref);
            pstmt.setInt(2,re_level);
            pstmt.executeUpdate();

            String sql = "insert into board values(null,?,?,?,?,now(),?,?,?,0,?)";
            pstmt=con.prepareStatement(sql);

            pstmt.setString(1, bean.getWriter());
            pstmt.setString(2,bean.getEmail());
            pstmt.setString(3, bean.getSubject());
            pstmt.setString(4, bean.getPassword());
            pstmt.setInt(5,ref); //부모의 ref값 그대로 넣어줌
            pstmt.setInt(6,re_step+1); //답글이므로 부모의 re_step에 +1
            pstmt.setInt(7,re_level+1);
            pstmt.setString(8, bean.getContent());

            pstmt.executeUpdate();
            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //하나의 게시글 가져오기(BoardUpdate/delete용)
    public BoardBean getOneUpdateBoard(int num) {

        BoardBean bean = new BoardBean();
        getCon();

        try {

            //게시글 가져오기
            String sql = "select * from board where num=?";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,num);

            rs = pstmt.executeQuery();
            if(rs.next()) {
                bean.setNum(rs.getInt(1));
                bean.setWriter(rs.getString(2));
                bean.setEmail(rs.getString(3));
                bean.setSubject(rs.getString(4));
                bean.setPassword(rs.getString(5));
                bean.setReg_date(rs.getDate(6).toString());
                bean.setRef(rs.getInt(7));
                bean.setRe_step(rs.getInt(8));
                bean.setRe_level(rs.getInt(9));
                bean.setReadcount(rs.getInt(10));
                bean.setContent(rs.getString(11));
            }

            con.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    //update 와 delete시 필요한 password 가져오기
    public String getPass(int num) {
        String pass = "";

        getCon();

        try{
            String sql = "select password from board where num=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,num);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                pass = rs.getString(1);
            }
            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return pass;
    }

    //하나의 게시글 수정
    public void updateBoard(BoardBean bean) {
        getCon();

        try {
            String sql = "update board set subject=? , content=? where num=?";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, bean.getSubject());
            pstmt.setString(2, bean.getContent());
            pstmt.setInt(3,bean.getNum());

            pstmt.executeUpdate();

            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //하나의 게시글 삭제
    public void deleteBoard(int num) {
        getCon();

        try{
            String sql="delete from board where num=?";
            pstmt=con.prepareStatement(sql);

            pstmt.setInt(1,num);

            pstmt.executeUpdate();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //전체 게시글 갯수 가져오기
    public int getAllCount() {
        getCon();

        int count = 0;

        try{

            String sql = "select count(*) from board";
            pstmt= con.prepareStatement(sql);

            rs= pstmt.executeQuery();
            if(rs.next()) {
                count = rs.getInt(1);
            }
            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }
}
