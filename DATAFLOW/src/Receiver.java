import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;



public class Receiver {
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        String subject = "queue0";
        Destination destination = session.createQueue(subject);
        MessageConsumer consumer = session.createConsumer(destination);
        Message message = consumer.receive();
        new SecondThreadMessageReceiver("Second Message Receiver").start();
        new ThirdThreadMessageReceiver("Third Message Receiver").start();
        if (message instanceof TextMessage textMessage) {
            System.out.println("Received message (main thread) : \n" + textMessage.getText());
            connection.close();
        }
    }
}
class SecondThreadMessageReceiver extends Thread {

    SecondThreadMessageReceiver(String name) {
        super(name);
    }

    public void run() {
        String url = ActiveMQConnection.DEFAULT_BROKER_URL;
        String subject = "queue1";
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subject);
            MessageConsumer consumer = session.createConsumer(destination);
            Message message = consumer.receive();
            if (message instanceof TextMessage textMessage) {
                System.out.println("Received message (thread 2) : \n" + textMessage.getText());
                connection.close();
            }

        } catch (JMSException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}
class ThirdThreadMessageReceiver extends Thread {

    ThirdThreadMessageReceiver(String name) {
        super(name);
    }

    public void run() {
        String url = ActiveMQConnection.DEFAULT_BROKER_URL;
        String subject = "queue2";
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subject);
            MessageConsumer consumer = session.createConsumer(destination);
            Message message = consumer.receive();
            if (message instanceof TextMessage textMessage) {
                System.out.println("Received message (thread 3) : \n" + textMessage.getText());
                connection.close();
            }

        } catch (JMSException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

}

