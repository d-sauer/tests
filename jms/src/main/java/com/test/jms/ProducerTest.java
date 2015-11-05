package com.test.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.net.URI;

/**
 * @author Davor Sauer
 */
public class ProducerTest {

    private static final String HOST = "192.168.99.100";
    private static final String QUEUE = "activemq:queue:Backbase.cxp.CreateAuditEventCommand";
    private static final String MESSAGE = "{\"hash\": 1861,    \"salt\": 24,    \"action\": \"UPDATE\",    \"userName\": \"Tamra Hendricks\",    \"itemName\": \"Patti Bean\",    \"itemTitle\": \"Paula Donaldson\",    \"itemType\": \"widget\",    \"context\": \"INSOURCE\",    \"description\": \"Excepteur et aliqua quis officia cupidatat magna voluptate. Eu irure proident irure sit deserunt pariatur nulla pariatur nisi enim magna irure incididunt. Commodo dolore consectetur mollit minim ut id occaecat officia. Ullamco laborum nulla fugiat minim labore officia occaecat dolore ad aute. Sunt cillum eiusmod commodo mollit aliqua exercitation eu magna quis eiusmod aute. Consequat culpa ut ullamco veniam aliquip fugiat duis aute anim id occaecat exercitation ex.\\r\\n\",    \"timestamp\": \"2014-07-15\"  }";

    public static void main(String[] args) throws InterruptedException {
        produce();
//        TimeUnit.SECONDS.sleep(5);
//
//        thread(new HelloWorldConsumer(), false);
    }


    private static void produce() {
        try {
            // Create a ConnectionFactory
            URI uri = new URI("tcp://" + HOST + ":61616");
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", uri);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(QUEUE);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            TextMessage message = session.createTextMessage(MESSAGE);

            // Tell the producer to send the message
            System.out.println("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
            producer.send(message);

            // Clean up
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }


    private static void consume() {
        try {
            // Create a ConnectionFactory
            URI uri = new URI("tcp://" + HOST + ":61616");
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", uri);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

//            connection.setExceptionListener(this);

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(QUEUE);

            // Create a MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(destination);

            // Wait for a message
            Message message = consumer.receive(1000);

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println("Received: " + text);
            } else {
                System.out.println("Received: " + message);
            }

            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }


    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }

    public static class HelloWorldProducer implements Runnable {
        public void run() {
            produce();
        }
    }

    public static class HelloWorldConsumer implements Runnable, ExceptionListener {
        public void run() {
            consume();
        }

        public synchronized void onException(JMSException ex) {
            System.out.println("JMS Exception occured.  Shutting down client.");
        }
    }

}
