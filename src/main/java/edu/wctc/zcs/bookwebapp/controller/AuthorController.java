package edu.wctc.zcs.bookwebapp.controller;


import edu.wctc.zcs.bookwebapp.model.AuthorService;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Zachary
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/index","/AuthorController"})
public class AuthorController extends HttpServlet {
    private static final String TYPE = "text/html;charset=UTF-8";
    private static final String ATTR = "author";
    private static final String PAGE = "/index.jsp";
    private static final String MODE = "submit";
    private static final String INSERT = "insert";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String ID = "aId";
    private static final String NAME = ATTR + "_name";
    private static final String DATE = "date_added";
    
    
    @Inject 
    private AuthorService aServe;
    
    //db config init params from web.xml
    private String driver;
    private String driverUrl;
    private String username;
    private String password;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType(TYPE);
        configDbConnection();
        
        String mode = request.getParameter(MODE) != null ? request.getParameter(MODE) : MODE ;
        List colDesc = new ArrayList();
        List colVal = new ArrayList();
        colVal.add(request.getParameter(NAME)!=null?request.getParameter(NAME):"empty");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String now = df.format(new Date());
        colVal.add(request.getParameter(DATE)!=null ? request.getParameter(DATE):now);
        colDesc.add(NAME);
        colDesc.add(DATE);
        
        switch (mode){
            case INSERT : {
            try {
                aServe.insertRecord(ATTR,colDesc,colVal);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
            }
                loadPage(request,response);
                break;
            }
            case UPDATE : {
                
                loadPage(request,response);
                break;
            }
            case DELETE : {
            try {
                aServe.deleteAuthorById(request.getParameter(ID));
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
            }
                loadPage(request,response);
                break;
            }
            default: {
                loadPage(request,response);
            }
        }
        
    }
    
    private void loadPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        RequestDispatcher view;
        try {
            request.setAttribute(ATTR,aServe.getAuthorList());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            view = request.getRequestDispatcher(PAGE);
            view.forward(request, response);
        }
        
    }

    private void configDbConnection(){
        aServe.getDao().initDao(driver, driverUrl, username, password);
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    /**
     * Method called after the servlet becomes exist
     * @throws ServletException 
     */
    @Override
    public void init() throws ServletException{
        driver = getServletContext().getInitParameter("db.driver.class");
        driverUrl = getServletContext().getInitParameter("db.url");
        username = getServletContext().getInitParameter("db.username");
        password = getServletContext().getInitParameter("db.password");
    }// </editor-fold>

}
