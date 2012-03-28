package net.triptech.metahive.web.model;

/**
 * The Enum FilterAction.
 */
public enum FilterAction {

    ADD("label_net_triptech_metahive_web_model_filteraction_add"),
    REMOVE("label_net_triptech_metahive_web_model_filteraction_remove"),
    SUBSEARCH("label_net_triptech_metahive_web_model_filteraction_subsearch");

    /** The message key. */
    private String messageKey;

    /**
     * Instantiates a new filter action.
     *
     * @param name the name
     */
    private FilterAction(String name) {
        this.messageKey = name;
    }

    /**
     * Gets the message key.
     *
     * @return the message key
     */
    public String getMessageKey() {
        return messageKey;
    }

}
