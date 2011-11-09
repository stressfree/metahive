/*******************************************************************************
 * Copyright (c) 2009 David Harrison.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl-3.0.html
 *
 * Contributors:
 *     David Harrison - initial API and implementation
 ******************************************************************************/
package com.sfs.metahive;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * The Class ConvertHtmlToText converts text/html into text/plain.
 * http://www.codecodex.com/wiki/index.php?title=Convert_HTML_to_text
 *
 * @author Krishna Singhania
 * @author David Harrison (modifications)
 *
 * @version 1.2 $Date: July 31, 2008
 */
public class ConvertHtmlToText {

    /** The Constant ALT_INDENT. */
    private static final int ALT_INDENT = 5;

    /** The Constant HTML_INDENT. */
    private static final int HTML_INDENT = 6;

    /** The body_found. */
    private boolean bodyFound = false;

    /** The pre. */
    private boolean pre = false;

    /** The href. */
    private String href = "";

    /**
     * Convert.
     *
     * @param source the source
     *
     * @return the string
     *
     * @throws Exception the exception
     */
    public final String convert(final String source) throws Exception {

        StringBuffer result = new StringBuffer();
        StringBuffer result2 = new StringBuffer();
        StringReader input = new StringReader(source);

        try {
            String text = null;
            int c = input.read();

            while (c != -1) {
                text = "";
                if (c == '<') {
                    // Get the rest of the tag
                    String currentTag = getTag(input);
                    text = convertTag(currentTag);
                } else if (c == '&') {
                    String specialchar = getSpecial(input);
                    if (specialchar.equals("lt;") || specialchar.equals("#60")) {
                        text = "<";
                    } else if (specialchar.equals("gt;")
                            || specialchar.equals("#62")) {
                        text = ">";
                    } else if (specialchar.equals("amp;")
                            || specialchar.equals("#38")) {
                        text = "&";
                    } else if (specialchar.equals("nbsp;")) {
                        text = " ";
                    } else if (specialchar.equals("quot;")
                            || specialchar.equals("#34")) {
                        text = "\"";
                    } else if (specialchar.equals("copy;")
                            || specialchar.equals("#169")) {
                        text = "[Copyright]";
                    } else if (specialchar.equals("reg;")
                            || specialchar.equals("#174")) {
                        text = "[Registered]";
                    } else if (specialchar.equals("trade;")
                            || specialchar.equals("#153")) {
                        text = "[Trademark]";
                    } else {
                        text = "&" + specialchar;
                    }
                } else if (!pre && Character.isWhitespace((char) c)) {
                    StringBuffer sb = null;
                    if (bodyFound) {
                        sb = result;
                    } else {
                        sb = result2;
                    }
                    if (sb.length() > 0
                            && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
                        text = "";
                    } else {
                        text = " ";
                    }
                } else {
                    text = "" + (char) c;
                }

                StringBuffer sb = null;
                if (bodyFound) {
                    sb = result;
                } else {
                    sb = result2;
                }
                sb.append(text);

                c = input.read();
            }
        } catch (Exception e) {
            input.close();
            throw e;
        }

        StringBuffer sb = null;
        if (bodyFound) {
            sb = result;
        } else {
            sb = result2;
        }
        return sb.toString().trim();
    }

    /**
     * Gets the tag.
     *
     * @param r the r
     *
     * @return the tag
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private String getTag(final Reader r) throws IOException {
        StringBuffer result = new StringBuffer();
        int level = 1;

        result.append('<');
        while (level > 0) {
            int c = r.read();
            if (c == -1) {
                break; // EOF
            }
            result.append((char) c);
            if (c == '<') {
                level++;
            } else if (c == '>') {
                level--;
            }
        }

        return result.toString();
    }

    /**
     * Gets the special.
     *
     * @param r the r
     *
     * @return the special
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private String getSpecial(final Reader r) throws IOException {
        StringBuffer result = new StringBuffer();
        r.mark(1); // Mark the present position in the stream
        int c = r.read();

        while (Character.isLetter((char) c)) {
            result.append((char) c);
            r.mark(1);
            c = r.read();
        }

        if (c == ';') {
            result.append(';');
        } else {
            r.reset();
        }
        return result.toString();
    }

    /**
     * Checks if is tag.
     *
     * @param s1 the s1
     * @param s2 the s2
     *
     * @return true, if is tag
     */
    private boolean isTag(final String s1, final String s2) {
        String lcString = s1.toLowerCase();
        String t1 = "<" + s2.toLowerCase() + ">";
        String t2 = "<" + s2.toLowerCase() + " ";

        return lcString.startsWith(t1) || lcString.startsWith(t2);
    }

    /**
     * Convert tag.
     *
     * @param t the t
     *
     * @return the string
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private String convertTag(final String t) throws IOException {
        String result = "";

        if (isTag(t, "body")) {
            bodyFound = true;
        } else if (isTag(t, "/body")) {
            result = "";
        } else if (isTag(t, "center")) {
            result = "";
        } else if (isTag(t, "/center")) {
            result = "";
        } else if (isTag(t, "pre")) {
            result = "";
            pre = true;
        } else if (isTag(t, "/pre")) {
            result = "";
            pre = false;
        } else if (isTag(t, "p")) {
            result = "\n";
        } else if (isTag(t, "/p")) {
            result = "\n\n";
        } else if (isTag(t, "br") || isTag(t, "br/") || isTag(t, "br /")) {
            result = "\n";
        } else if (isTag(t, "h1") || isTag(t, "h2") || isTag(t, "h3")
                || isTag(t, "h4") || isTag(t, "h5") || isTag(t, "h6")
                || isTag(t, "h7")) {
            result = "\n";
        } else if (isTag(t, "/h1") || isTag(t, "/h2")) {
            result = "\n\n";
        } else if (isTag(t, "/h3") || isTag(t, "/h4") || isTag(t, "/h5")
                || isTag(t, "/h6") || isTag(t, "/h7")) {
            result = "\n";
        } else if (isTag(t, "/dl")) {
            result = "";
        } else if (isTag(t, "dd")) {
            result = "  * ";
        } else if (isTag(t, "dt")) {
            result = "      ";
        } else if (isTag(t, "li")) {
            result = "  * ";
        } else if (isTag(t, "/li")) {
            result = "\n";
        } else if (isTag(t, "ul") || isTag(t, "/ul")) {
            result = "\n";
        } else if (isTag(t, "ol") || isTag(t, "/ol")) {
            result = "\n";
        } else if (isTag(t, "hr") || isTag(t, "hr/") || isTag(t, "hr /")) {
            result = "_________________________________________\n\n";
        } else if (isTag(t, "table")) {
            result = "";
        } else if (isTag(t, "/table")) {
            result = "";
        } else if (isTag(t, "form")) {
            result = "";
        } else if (isTag(t, "/form")) {
            result = "";
        } else if (isTag(t, "b")) {
            result = "*";
        } else if (isTag(t, "/b")) {
            result = "*";
        } else if (isTag(t, "i")) {
            result = "\"";
        } else if (isTag(t, "/i")) {
            result = "\"";
        } else if (isTag(t, "img")) {
            int idx = t.indexOf("alt=\"");
            try {
                if (idx != -1) {
                    idx += ALT_INDENT;
                    int idx2 = t.indexOf("/\"", idx);
                    result = t.substring(idx, idx2);
                }
            } catch (Exception e) {
                href = "";
            }
        } else if (isTag(t, "a")) {
            int idx = t.indexOf("href=\"");
            try {
                if (idx != -1) {
                    idx += HTML_INDENT;
                    final int idx2 = t.indexOf("\"", idx);
                    href = t.substring(idx, idx2);
                } else {
                    href = "";
                }
            } catch (Exception e) {
                href = "";
            }
        } else if (isTag(t, "/a")) {
            if (href.length() > 0) {
                result = " [" + href + "] ";
                href = "";
            }
        }
        return result;
    }
}
