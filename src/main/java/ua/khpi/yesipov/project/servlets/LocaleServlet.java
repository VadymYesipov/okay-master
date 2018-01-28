package ua.khpi.yesipov.project.servlets;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Locale;

public class LocaleServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(LocaleServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Starting changing locale");
        String loc = req.getParameter("loc");
        Locale locale = null;
        HttpSession session = req.getSession();
        // System.out.println(session==null);
        if (loc.equals("en")) {
            log.debug("English locale is starting");
            session.setAttribute("language", "en");
            Config.set(session, Config.FMT_LOCALE, locale = new Locale("en"));
        }
        if (loc.equals("ru")) {
            log.debug("Russian locale is starting");
            session.setAttribute("language", "ru");
            //req.getSession().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", "ru");
            Config.set(session, Config.FMT_LOCALE, locale = new Locale("ru"));
        }

        //    req.getRequestDispatcher("/main.jsp").forward(req , resp );
        String referer = req.getHeader("Referer");
        log.debug("Redirect to " + referer);
        resp.sendRedirect(referer);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }
}