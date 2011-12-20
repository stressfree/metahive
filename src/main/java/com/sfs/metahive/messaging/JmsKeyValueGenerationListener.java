package com.sfs.metahive.messaging;


/**
 * The listener interface for receiving jmsKeyValueGeneration events.
 * The class that is interested in processing a jmsKeyValueGeneration
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addJmsKeyValueGenerationListener<code> method. When
 * the jmsKeyValueGeneration event occurs, that object's appropriate
 * method is invoked.
 *
 * @see JmsKeyValueGenerationEvent
 */
public class JmsKeyValueGenerationListener {

    /**
     * On message.
     *
     * @param message the message
     */
    public void onMessage(Object message) {
        System.out.println("JMS message received: " + message);
        
        if (message instanceof JmsRecalculateRequest) {
        	JmsRecalculateRequest req = (JmsRecalculateRequest) message;
        	
        	        	
        	System.out.println("Primary Id: " + req.getPrimaryRecordId());
        	System.out.println("Secondary Id: " + req.getSecondaryRecordId());
        	System.out.println("Tertiary Id: " + req.getTertiaryRecordId());
        	System.out.println("Definition Id: " + req.getDefinitionId());
        }
    }
}
