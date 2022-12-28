import java.sql.SQLException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


public class Sender {
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) throws JMSException {
        new SecondThreadMessageSender("Second Message Sender").start();
        new ThirdThreadMessageSender("Third Message Sender").start();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        String subject = "queue0";
        Destination destination = session.createQueue(subject);
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage(getAllData());
        producer.send(message);
        System.out.println("First message done sending");
        connection.close();
    }

    public static String getAllData() {
        try {
            DATABASE DATABASE = new DATABASE();
            return DATABASE.passer();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
class SecondThreadMessageSender extends Thread {

    SecondThreadMessageSender(String name){
        super(name);
    }

    public void run() {
        String url = ActiveMQConnection.DEFAULT_BROKER_URL;
        String subject = "queue1";
        try {
            DATABASE DATABASE = new DATABASE();
            String result = DATABASE.LimitSpaceLeft();
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subject);
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage(result);
            producer.send(message);
            System.out.println("Second message done sending");
            connection.close();
        } catch (SQLException | JMSException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

}

class ThirdThreadMessageSender extends Thread {

    ThirdThreadMessageSender(String name){
        super(name);
    }

    public void run() {
        String url = ActiveMQConnection.DEFAULT_BROKER_URL;
        String subject = "queue2";
        try {
            DATABASE DATABASE = new DATABASE();
            String result = DATABASE.numberOfPositions();
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subject);
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage(result);
            producer.send(message);
            System.out.println("Third message done sending");
            connection.close();
        } catch (SQLException | JMSException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

}