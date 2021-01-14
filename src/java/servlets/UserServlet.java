/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Book;
import entity.History;
import entity.Reader;
import entity.User;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.BookFacade;
import session.HistoryFacade;
import session.ReaderFacade;
import session.UserFacade;

/**
 *
 * @author user
 */
@WebServlet(name = "UserServlet", urlPatterns = {
    "/giveBookToReader",
    "/giveBook",
    "/returnBookToLibrary",
    "/returnBook",
    
    
})
public class UserServlet extends HttpServlet {
    @EJB 
    private BookFacade bookFacade;
    @EJB 
    private ReaderFacade readerFacade;
    @EJB 
    private HistoryFacade historyFacade;
    @EJB 
    private UserFacade userFacade;

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null) {
            request.getRequestDispatcher("/WEB-INF/showLoginForm.jsp").forward(request, response);
            request.setAttribute("info", "Войдите в систему");
            return;
        }
        User user = (User) session.getAttribute("user");
        if (user==null) {
            request.getRequestDispatcher("/WEB-INF/showLoginForm.jsp").forward(request, response);
            request.setAttribute("info", "Войдите в систему");
            return;
        }
        String path = request.getServletPath();
        
        switch (path) {
               
             case "/giveBookToReader":
                
                List<Book> listBooks = bookFacade.findAll();
                request.setAttribute("listBooks", listBooks);
                request.getRequestDispatcher("/WEB-INF/giveBookToReader.jsp").forward(request, response);
                break;
             case "/giveBook":
                String bookId = request.getParameter("bookId");
                Book book = bookFacade.find(Long.parseLong(bookId));
                Reader reader = user.getReader();
                History history = new History(book, reader, new GregorianCalendar().getTime(), null);
                historyFacade.create(history);
                request.setAttribute("info","Книга выдана");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
             case "/returnBookToLibrary":
                 List<History> listHistoriesWithReadBook = historyFacade.findHistoriesWithReadBook(user.getReader());
                 request.setAttribute("listHistoriesWithReadBook", listHistoriesWithReadBook);
                 request.getRequestDispatcher("/WEB-INF/returnBookToLibrary.jsp").forward(request, response);
                 break;
             case "/returnBook":
                 String historyId = request.getParameter("historyId");
                 if("".equals(historyId) || historyId == null || historyId == "-1"){
                    request.setAttribute("info", "Выберите книгу");
                    request.getRequestDispatcher("/returnBookForm").forward(request, response);
                    break;
                }
                 
                 history = historyFacade.find(Long.parseLong(historyId));
                 history.setReturnDate(new GregorianCalendar().getTime());
                 historyFacade.edit(history);
                 request.setAttribute("info","Книга возвращена");
                 request.getRequestDispatcher("/index.jsp").forward(request, response);
                 break;
        }
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
    }// </editor-fold>

}
