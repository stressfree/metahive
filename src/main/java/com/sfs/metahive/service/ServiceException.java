package com.sfs.metahive.service;

public class ServiceException extends Exception {

    /** The unique serial version UID for the class. */
    private static final long serialVersionUID = 2003990420192945563L;

    /**
     * Constructs an instance of <code>ServiceException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ServiceException(final String msg) {
        super(msg);
    }

}
