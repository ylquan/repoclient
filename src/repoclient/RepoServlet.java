/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repoclient;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 *
 * @author quanyuelong
 */
public class RepoServlet extends HttpServlet{
    
    public enum TARGET{
    
        LOCAL,TRANSFER
    }
     public enum LOCAL_OPERATION {

        list, create, delete, rename
    }

    public enum TRANSFER_OPERATION {

        upload, download
    }

    public RepoServlet() {
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
      
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        
    }
    
     protected void dispachMethod(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException {
        String methodName = request.getParameter("method");
        Class dispachAction = this.getClass();
        try {
            Class[] parametersTypes = new Class[]{HttpServletRequest.class, HttpServletResponse.class};
            Method method = dispachAction.getMethod(methodName, parametersTypes);

            Object[] parameterVlaues = new Object[]{request, response};
            method.invoke(this, parameterVlaues);

        } catch (SecurityException e) {
            //e.printStackTrace();
            //throw e;
            //System.out.println("");
            Logger.getLogger(RepoServlet.class.getName()).log(Level.SEVERE, null, e);
        } catch (NoSuchMethodException e) {
            //e.printStackTrace();
            Logger.getLogger(RepoServlet.class.getName()).log(Level.SEVERE, null, e);
        } catch (IllegalArgumentException e) {
            //e.printStackTrace();
            Logger.getLogger(RepoServlet.class.getName()).log(Level.SEVERE, null, e);
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
            Logger.getLogger(RepoServlet.class.getName()).log(Level.SEVERE, null, e);
        } catch (InvocationTargetException e) {
            //e.printStackTrace();
            Logger.getLogger(RepoServlet.class.getName()).log(Level.SEVERE, null, e);
        }
    }
     
     protected void setResponse(String res, HttpServletResponse response) throws Exception {
        response.getWriter().write(res);
    }

    protected void setJson(String jsonString, HttpServletResponse response) {
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.getWriter().write(jsonString);
        } catch (Exception e) {
            //e.printStackTrace();
            Logger.getLogger(RepoServlet.class.getName()).log(Level.SEVERE, null, e);
        }
    }
   
}
