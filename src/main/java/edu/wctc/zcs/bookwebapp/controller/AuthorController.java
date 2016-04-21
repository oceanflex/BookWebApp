package edu.wctc.zcs.bookwebapp.controller;


//import edu.wctc.zcs.bookwebapp.model.AuthorService;
import edu.wctc.zcs.bookwebapp.entity.Author;
import edu.wctc.zcs.bookwebapp.service.AuthorService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 *
 * @author Zachary
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/index","/authors","/AuthorController"})
public class AuthorController extends HttpServlet {
    private static final String TYPE = "text/html;charset=UTF-8";
    private static final String ATTR = "author";
    private static final String PAGE = "/authors.jsp";
    private static final String MODE = "submit";
    private static final String INSERT = "insert";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String ID = "aId";
    private static final String NAME = ATTR + "_name";
    private static final String DEFAULT_NAME = "empty";
    private static final String DATE = "date_added";
    private static final String USER_NAME = "userName";
    private static final String DEFAULT_USER_NAME = "-1";
    
    
    //@Inject 
    private AuthorService aServe;
    //@Inject
    //private BookFacade bServe;
    
    //db config init params from web.xml
    private String driver;
    private String driverUrl;
    private String username;
    private String password;
    private String dbJndiName;

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
        /*try {
            configDbConnection();
        } catch (NamingException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        String mode = request.getParameter(MODE) != null ? request.getParameter(MODE) : MODE ;
        
        //List colDesc = new ArrayList();
        //List colVal = new ArrayList();
        //colVal.add(request.getParameter(NAME)!=null ? request.getParameter(NAME):DEFAULT_NAME);
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String now = df.format(new Date());
        //colVal.add(request.getParameter(DATE)!=null ? request.getParameter(DATE):now);
        
        //colDesc.add(NAME);
        //colDesc.add(DATE);
        
        HttpSession session = request.getSession();
        String userName = session.getAttribute(USER_NAME) != null ? 
                session.getAttribute(USER_NAME).toString() : DEFAULT_USER_NAME;
        request.setAttribute(USER_NAME, userName);
        switch (mode){
            case INSERT : {
            try {
                Author temp = new Author();
                temp.setAuthorName(userName);
                temp.setDateAdded(new Date());
                aServe.edit(temp); //insertRecord(ATTR,colDesc,colVal);
            } catch (Exception ex) {
                Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
            }
                loadPage(request,response);
                break;
            }
            case UPDATE : {
            try {
                Author temp = new Author(Integer.parseInt(request.getParameter(ID)));
                temp.setAuthorName(request.getParameter(NAME));
                temp.setDateAdded(df.parse(request.getParameter(DATE)));
                aServe.edit(temp);
                //aServe.updateAuthorById(request.getParameter(ID), colDesc, colVal);
            } catch (NumberFormatException | ParseException ex) {
                Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
            }
                loadPage(request,response);
                break;
            }
            case DELETE : {
            try {
                Author author;
                author = aServe.findById(request.getParameter(ID));
                aServe.remove(author);
                
                //aServe.deleteAuthorById(request.getParameter(ID));
            } catch (Exception ex) {
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
            request.setAttribute(ATTR,aServe.findAll());
        } catch (Exception ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            view = request.getRequestDispatcher(response.encodeURL(PAGE));
            view.forward(request, response);
        }
        
    }

    /*private void configDbConnection() throws NamingException{
        if(dbJndiName == null) {
        aServe.getDao().initDao(driver, driverUrl, username, password);  
        } else {
            //Lookup the JNDI name of the Glassfish connection pool and then
            //use it to create a DataSource object.
             
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(dbJndiName);
            aServe.getDao().initDao(ds);
        }
    }*/
    
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
         ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        aServe = (AuthorService) ctx.getBean("authorService");
//        driver = getServletContext().getInitParameter("db.driver.class");
//        driverUrl = getServletContext().getInitParameter("db.url");
//        username = getServletContext().getInitParameter("db.username");
//        password = getServletContext().getInitParameter("db.password");
    }// </editor-fold>

}
