package org.saltations.domain.error;

import java.text.MessageFormat;

/**
 * Error base class that allows creating a message using MessageFormat based templates and variable arguments
 */

public abstract class FormattedCheckedError extends Exception
{
    /**
     * An exception really should have a serialization version.
     */

    private static final long serialVersionUID = 1L;


    public FormattedCheckedError() {
        super();
    }

    public FormattedCheckedError(Throwable e) {
        super(e);
    }

    /**
     * Constructor that takes {@link MessageFormat} format strings and parameters
     *
     * @param template Formatting message. Uses {@link MessageFormat#format} notation.
     * @param args Objects as message parameters
     */

    public FormattedCheckedError(String template, Object... args) {
        super(MessageFormat.format(template, args));
    }

    /**
     * Constructor that takes {@link MessageFormat} format strings and parameters
     *
     * @param e Root cause exception. Non-null.
     * @param template Formatting message. Uses {@link MessageFormat#format} notation.
     * @param args Objects as message parameters
     */

    public FormattedCheckedError(Throwable e, String template, Object... args) {
        super(MessageFormat.format(template, args), e);
    }
}
