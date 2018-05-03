package it_blog_net.ITBlogNet.Services;

import it_blog_net.ITBlogNet.Enums.NotificationMessageType;
import it_blog_net.ITBlogNet.Interfaces.INotificationService;
import it_blog_net.ITBlogNet.Models.NotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service()
public class NotificationService implements INotificationService {

    public static final String NOTIFY_MSG_SESSION_KEY = "siteNotificationMessages";

    @Autowired
    private HttpSession httpSession;

    @Override
    public void addInfoMessage(String message) {
        addNotificationMessage(NotificationMessageType.INFO, message);
    }

    private void addNotificationMessage(NotificationMessageType info, String message) {
        List<NotificationMessage> notifyMessages = (List<NotificationMessage>)httpSession.getAttribute(NOTIFY_MSG_SESSION_KEY);
        if(notifyMessages == null)
        {
            notifyMessages = new ArrayList<>();
        }
        notifyMessages.add(new NotificationMessage(info,message));
        httpSession.setAttribute(NOTIFY_MSG_SESSION_KEY, notifyMessages);
    }

    @Override
    public void addErrorMessage(String message) {
        addNotificationMessage(NotificationMessageType.ERROR,message);
    }

}
