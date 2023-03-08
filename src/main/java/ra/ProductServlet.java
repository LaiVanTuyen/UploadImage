package ra;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductServlet", value = "/ProductServlet")
@MultipartConfig(
        maxFileSize = 1024*1024*5,//5MB
        fileSizeThreshold = 1024 * 1024 * 1,//1MB
        maxRequestSize = 1024 * 1024 * 10//10MB
)
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action.equals("GetAll")){
            getAllProduct(request, response);
        }else if(action.equals("GetById")){
            int id = Integer.parseInt(request.getParameter("id"));
           //lay thong tin san pham theo id
            Connection conn = null;
            CallableStatement callSt = null;
            Product pro = null;
            try {
                conn = ConnectionDB.openConnection();
                callSt = conn.prepareCall("{call getProductById(?)}");
                callSt.setInt(1, id);
                ResultSet rs = callSt.executeQuery();
                while (rs.next()){
                    pro = new Product();
                    pro.setId(rs.getString("id"));
                    pro.setName(rs.getString("name"));
                    pro.setImage(rs.getString("image"));
                    pro.setStatus(rs.getBoolean("status"));
                }
                CallableStatement callSt2 = conn.prepareCall("{call getSubImageById(?)}");
                callSt2.setInt(1,id );
                ResultSet rs2 = callSt2.executeQuery();
                while (rs2.next()){
                    pro.getListImage().add(rs2.getString("imageLink"));
                }
                callSt2.close();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                ConnectionDB.closeConnection(conn, callSt);
            }
            //set pro vao request de forward sang trang productDetail.jsp
            request.setAttribute("pro", pro);
            RequestDispatcher rd = request.getRequestDispatcher("/views/detail.jsp");
            rd.forward(request, response);
        }
    }
    public void getAllProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Product> listPro = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call getAllProduct()}");
            listPro = new ArrayList<>();
            ResultSet rs = callSt.executeQuery();
            while (rs.next()){
                Product pro = new Product();
                pro.setId(rs.getString("id"));
                pro.setName(rs.getString("name"));
                pro.setImage(rs.getString("image"));
                pro.setStatus(rs.getBoolean("status"));
                listPro.add(pro);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        request.setAttribute("listPro", listPro);
        RequestDispatcher rd = request.getRequestDispatcher("/views/product.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action.equals("Create")){
            Product pro = new Product();
            pro.setName(request.getParameter("name"));
            pro.setImage(request.getParameter("image"));
            pro.setStatus(Boolean.parseBoolean(request.getParameter("status")));
            //lay du lieu anh tu request day vao db va folder
            //db: chua link den anh
            //folder: chua anh
            //b1: tao thu muc chua anh trong webapp
            String path = "E:/JSP_Servlet/UpdateImageDemo/src/main/webapp/images";
            File file = new File(path);//tạo đối tượng file
            if(!file.exists()){//nếu chưa tồn tại thư mục
                file.mkdir();//tạo thư mục
            }
            //b2: lay anh tu request,add duong dan vao doi tuong pro, ghi anh vao folder
            for (Part part : request.getParts()) {
//                String fileName = extractFileName(part);
//                if(fileName != null && fileName.length() > 0){
                if (part.getName().equals("image")) {
                    //part nay chua anh chinh cua product
                    //set duong dan anh chinh cho product
                    pro.setImage(part.getSubmittedFileName());
                    //ghi anh chinh vao folder
                    part.write(path + File.separator + part.getSubmittedFileName());
                } else if (part.getName().equals("subImages")) {
                    //part nay chua anh phu cua product
                    //set duong dan anh phu cho product
                    pro.getListImage().add(part.getSubmittedFileName());
                    //ghi anh phu vao folder
                    part.write(path + File.separator + part.getSubmittedFileName());
                }
            }
            //b3: luu product vao db
           Connection conn = null;
           CallableStatement callSt = null;
            CallableStatement callSt2 = null;
              try {
                conn = ConnectionDB.openConnection();
                //insert du lieu vao product
                callSt = conn.prepareCall("{call insertProduct(?,?,?,?)}");
                callSt.setString(1, pro.getName());
                callSt.setString(2, pro.getImage());
                callSt.setBoolean(3, pro.isStatus());
                callSt.registerOutParameter(4, java.sql.Types.INTEGER);
                callSt.execute();
                //lay id cua product vua insert
                int id = callSt.getInt(4);
                //insert du lieu vao image

                for (String image : pro.getListImage()) {
                    callSt2 = conn.prepareCall("{call insertImage(?,?)}");
                    callSt2.setString(1, image);
                    callSt2.setInt(2, id);
                    callSt2.executeUpdate();
                    callSt2.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
              }
            finally {
                ConnectionDB.closeConnection(conn, callSt);
        }
              //lay lai danh sach product
            getAllProduct(request, response);
    }

    }
}
