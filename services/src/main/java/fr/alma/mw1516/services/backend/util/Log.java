package fr.alma.mw1516.services.backend.util;

import java.util.Date;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Log {

	private static Log instance;
	
	private static final String MACHINE = "HectorNouamanThierry";

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }

        return instance;
    }
    
    public void pushDrink(String uid, String item, double cost, double prevSolde) {
    	try {
    	// get the initial context
            InitialContext ctx = new InitialContext();
                                                                               
            // lookup the queue object
            Queue queue = (Queue) ctx.lookup("queue/queue0");
                                                                               
            // lookup the queue connection factory
            QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup("iiop://localhost:53506/queue/connectionFactory");
                                                                               
            // create a queue connection
            QueueConnection queueConn = connFactory.createQueueConnection();
                                                                               
            // create a queue session
            QueueSession queueSession = queueConn.createQueueSession(false,
                Session.DUPS_OK_ACKNOWLEDGE);
                                                                               
            // create a queue sender
            QueueSender queueSender = queueSession.createSender(queue);
            queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            
            // create a log message 
            MapMessage logMessage = queueSession.createMapMessage();
            logMessage.setString("actionType", "drink");
            logMessage.setString("vendingMachineId", MACHINE);
            logMessage.setString("vendingMachineType", MACHINE);
            logMessage.setString("userType", "mobilePhone");
            logMessage.setString("userId", uid);
            logMessage.setString("isoDate", new Date().toString() );
            logMessage.setDouble("previousBalance", prevSolde);
            logMessage.setDouble("currentBalance", prevSolde-cost);
            logMessage.setDouble("cost", cost);
            logMessage.setString("itemId", item);
                                                                               
            // send the message
        	queueSender.send(logMessage);
                                                                              
        	// print what we did
        	System.out.println("sent: " + logMessage.toString());
                                                                          
        	// close the queue connection
        	queueConn.close();
    	} catch (JMSException | NamingException e) {
    		e.printStackTrace();
    	}

    }
    public void pushRefill(String uid, double amount, double prevSolde) {
    	try {
    	// get the initial context
            InitialContext ctx = new InitialContext();
                                                                               
            // lookup the queue object
            Queue queue = (Queue) ctx.lookup("queue/queue0");
                                                                               
            // lookup the queue connection factory
            QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup("iiop://localhost:53506/queue/connectionFactory");
                                                                               
            // create a queue connection
            QueueConnection queueConn = connFactory.createQueueConnection();
                                                                               
            // create a queue session
            QueueSession queueSession = queueConn.createQueueSession(false,
                Session.DUPS_OK_ACKNOWLEDGE);
                                                                               
            // create a queue sender
            QueueSender queueSender = queueSession.createSender(queue);
            queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            
            // create a log message 
            MapMessage logMessage = queueSession.createMapMessage();
            logMessage.setString("actionType", "drink");
            logMessage.setString("vendingMachineId", MACHINE);
            logMessage.setString("vendingMachineType", MACHINE);
            logMessage.setString("userType", "mobilePhone");
            logMessage.setString("userId", uid);
            logMessage.setString("isoDate", new Date().toString() );
            logMessage.setDouble("previousBalance", prevSolde);
            logMessage.setDouble("currentBalance", prevSolde-amount);
            logMessage.setDouble("amount", amount);
            logMessage.setString("refillType", "carte bancaire");
                                                                               
            // send the message
        	queueSender.send(logMessage);
                                                                              
        	// print what we did
        	System.out.println("sent: " + logMessage.toString());
                                                                          
        	// close the queue connection
        	queueConn.close();
    	} catch (JMSException | NamingException e) {
    		e.printStackTrace();
    	}

    }

}