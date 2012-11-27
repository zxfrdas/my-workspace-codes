package com.zt.lib.collect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;


/**
 * 本类将系统自带的Properties类进行修改、精简。实现了一个key为String，value为Set<String>的Properties类。
 * 方便进行数组的存取。一个数组value在属性文件中的表现形式为一系列key相同的value。
 * @author zhaotong
 */
public class SetValueProperties extends SetValueMap {

	private static final long serialVersionUID = -2083356585239652649L;

	private static final String NEW_LINE = System.getProperty("line.separator") + "";

    /**
     * The default values for keys not found in this {@code Properties}
     * instance.
     */
    protected SetValueProperties defaults;

    private static final int NONE = 0, SLASH = 1, UNICODE = 2, CONTINUE = 3,
            KEY_DONE = 4, IGNORE = 5;

    /**
     * Constructs a new {@code Properties} object.
     */
    public SetValueProperties() {
    }

    /**
     * Constructs a new {@code Properties} object using the specified default
     * {@code Properties}.
     *
     * @param properties
     *            the default {@code Properties}.
     */
    public SetValueProperties(SetValueProperties properties) {
        defaults = properties;
    }

    private void dumpString(StringBuilder buffer, String string, boolean key) {
        int i = 0;
        if (!key && i < string.length() && string.charAt(i) == ' ') {
            buffer.append("\\ ");
            i++;
        }

        for (; i < string.length(); i++) {
            char ch = string.charAt(i);
            switch (ch) {
            case '\t':
                buffer.append("\\t");
                break;
            case '\n':
                buffer.append("\\n");
                break;
            case '\f':
                buffer.append("\\f");
                break;
            case '\r':
                buffer.append("\\r");
                break;
            default:
                if ("\\#!=:".indexOf(ch) >= 0 || (key && ch == ' ')) {
                    buffer.append('\\');
                }
                if (ch >= ' ' && ch <= '~') {
                    buffer.append(ch);
                } else {
                    String hex = Integer.toHexString(ch);
                    buffer.append("\\u");
                    for (int j = 0; j < 4 - hex.length(); j++) {
                        buffer.append("0");
                    }
                    buffer.append(hex);
                }
            }
        }
    }

    /**
     * Searches for the property with the specified name. If the property is not
     * found, the default {@code Properties} are checked. If the property is not
     * found in the default {@code Properties}, {@code null} is returned.
     *
     * @param name
     *            the name of the property to find.
     * @return the named property value, or {@code null} if it can't be found.
     */
    public String getProperty(String name) {
        Set<String> result = super.get(name);
        String property = "";
        property = setToArray(result)[0];
        if (property == null && defaults != null) {
            property = defaults.getProperty(name);
        }
        return property;
    }

    /**
     * Searches for the property with the specified name. If the property is not
     * found, it looks in the default {@code Properties}. If the property is not
     * found in the default {@code Properties}, it returns the specified
     * default.
     * 
     * @param name
     *            the name of the property to find.
     * @param defaultValue
     *            the default value.
     * @return the named property value.
     */
    public String getProperty(String name, String defaultValue) {
        Set<String> result = super.get(name);
        String property = "";
        property = setToArray(result)[0];
        if (property == null && defaults != null) {
            property = defaults.getProperty(name);
        }
        if (property == null) {
            return defaultValue;
        }
        return property;
    }
    
    /**
     * 获取key对应的所有value
     * @param name key
     * @return {@code String[]} values
     */
    public String[] getPropertyAll(String name) {
        return setToArray(super.get(name));
    }
    
    public String[] setToArray(Set<?> set)
    {
    	String[] strings = null;
        if (null != set && 0 != set.size()) {
        	strings = new String[set.size()];
        	strings = set.toArray(strings);
        }
    	return strings;
    }

    /**
     * Loads properties from the specified {@code InputStream}, assumed to be ISO-8859-1.
     * See "<a href="#character_encoding">Character Encoding</a>".
     *
     * @param in the {@code InputStream}
     * @throws IOException
     */
    public synchronized void load(InputStream in) throws IOException {
        if (in == null) {
            throw new NullPointerException();
        }
        load(new InputStreamReader(in, "ISO-8859-1"));
    }

    /**
     * Loads properties from the specified {@code Reader}.
     * The properties file is interpreted according to the following rules:
     * <ul>
     * <li>Empty lines are ignored.</li>
     * <li>Lines starting with either a "#" or a "!" are comment lines and are
     * ignored.</li>
     * <li>A backslash at the end of the line escapes the following newline
     * character ("\r", "\n", "\r\n"). If there's whitespace after the
     * backslash it will just escape that whitespace instead of concatenating
     * the lines. This does not apply to comment lines.</li>
     * <li>A property line consists of the key, the space between the key and
     * the value, and the value. The key goes up to the first whitespace, "=" or
     * ":" that is not escaped. The space between the key and the value contains
     * either one whitespace, one "=" or one ":" and any amount of additional
     * whitespace before and after that character. The value starts with the
     * first character after the space between the key and the value.</li>
     * <li>Following escape sequences are recognized: "\ ", "\\", "\r", "\n",
     * "\!", "\#", "\t", "\b", "\f", and "&#92;uXXXX" (unicode character).</li>
     * </ul>
     *
     * @param in the {@code Reader}
     * @throws IOException
     * @since 1.6
     */
    @SuppressWarnings("fallthrough")
    public synchronized void load(Reader in) throws IOException {
        if (in == null) {
            throw new NullPointerException();
        }
        int mode = NONE, unicode = 0, count = 0;
        char nextChar, buf[] = new char[40];
        int offset = 0, keyLength = -1, intVal;
        boolean firstChar = true;

        BufferedReader br = new BufferedReader(in);

        while (true) {
            intVal = br.read();
            if (intVal == -1) {
                break;
            }
            nextChar = (char) intVal;

            if (offset == buf.length) {
                char[] newBuf = new char[buf.length * 2];
                System.arraycopy(buf, 0, newBuf, 0, offset);
                buf = newBuf;
            }
            if (mode == UNICODE) {
                int digit = Character.digit(nextChar, 16);
                if (digit >= 0) {
                    unicode = (unicode << 4) + digit;
                    if (++count < 4) {
                        continue;
                    }
                } else if (count <= 4) {
                    throw new IllegalArgumentException("Invalid Unicode sequence: illegal character");
                }
                mode = NONE;
                buf[offset++] = (char) unicode;
                if (nextChar != '\n') {
                    continue;
                }
            }
            if (mode == SLASH) {
                mode = NONE;
                switch (nextChar) {
                case '\r':
                    mode = CONTINUE; // Look for a following \n
                    continue;
                case '\n':
                    mode = IGNORE; // Ignore whitespace on the next line
                    continue;
                case 'b':
                    nextChar = '\b';
                    break;
                case 'f':
                    nextChar = '\f';
                    break;
                case 'n':
                    nextChar = '\n';
                    break;
                case 'r':
                    nextChar = '\r';
                    break;
                case 't':
                    nextChar = '\t';
                    break;
                case 'u':
                    mode = UNICODE;
                    unicode = count = 0;
                    continue;
                }
            } else {
                switch (nextChar) {
                case '#':
                case '!':
                    if (firstChar) {
                        while (true) {
                            intVal = br.read();
                            if (intVal == -1) {
                                break;
                            }
                            nextChar = (char) intVal;
                            if (nextChar == '\r' || nextChar == '\n') {
                                break;
                            }
                        }
                        continue;
                    }
                    break;
                case '\n':
                    if (mode == CONTINUE) { // Part of a \r\n sequence
                        mode = IGNORE; // Ignore whitespace on the next line
                        continue;
                    }
                    // fall into the next case
                case '\r':
                    mode = NONE;
                    firstChar = true;
                    if (offset > 0 || (offset == 0 && keyLength == 0)) {
                        if (keyLength == -1) {
                            keyLength = offset;
                        }
                        String temp = new String(buf, 0, offset);
                        put(temp.substring(0, keyLength), temp.substring(keyLength));
                    }
                    keyLength = -1;
                    offset = 0;
                    continue;
                case '\\':
                    if (mode == KEY_DONE) {
                        keyLength = offset;
                    }
                    mode = SLASH;
                    continue;
                case ':':
                case '=':
                    if (keyLength == -1) { // if parsing the key
                        mode = NONE;
                        keyLength = offset;
                        continue;
                    }
                    break;
                }
                if (Character.isWhitespace(nextChar)) {
                    if (mode == CONTINUE) {
                        mode = IGNORE;
                    }
                    // if key length == 0 or value length == 0
                    if (offset == 0 || offset == keyLength || mode == IGNORE) {
                        continue;
                    }
                    if (keyLength == -1) { // if parsing the key
                        mode = KEY_DONE;
                        continue;
                    }
                }
                if (mode == IGNORE || mode == CONTINUE) {
                    mode = NONE;
                }
            }
            firstChar = false;
            if (mode == KEY_DONE) {
                keyLength = offset;
                mode = NONE;
            }
            buf[offset++] = nextChar;
        }
        if (mode == UNICODE && count <= 4) {
            throw new IllegalArgumentException("Invalid Unicode sequence: expected format \\uxxxx");
        }
        if (keyLength == -1 && offset > 0) {
            keyLength = offset;
        }
        if (keyLength >= 0) {
            String temp = new String(buf, 0, offset);
            String key = temp.substring(0, keyLength);
            String value = temp.substring(keyLength);
            if (mode == SLASH) {
                value += "\u0000";
            }
            put(key, value);
        }
    }

    /**
     * Returns all of the property names (keys) in this {@code Properties} object.
     */
    public Enumeration<String> propertyNames() {
        Hashtable<String, Object> selected = new Hashtable<String, Object>();
        selectProperties(selected, false);
        return selected.keys();
    }

    /**
     * Returns those property names (keys) in this {@code Properties} object for which
     * both key and value are strings.
     *
     * @return a set of keys in the property list
     * @since 1.6
     */
    public Set<String> stringPropertyNames() {
        Hashtable<String, Object> stringProperties = new Hashtable<String, Object>();
        selectProperties(stringProperties, true);
        return Collections.unmodifiableSet(stringProperties.keySet());
    }

    private void selectProperties(Hashtable<String, Object> selectProperties, final boolean isStringOnly) {
        if (defaults != null) {
            defaults.selectProperties(selectProperties, isStringOnly);
        }
        Enumeration<String> keys = keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (isStringOnly && !(key instanceof String)) {
                // Only select property with string key and value
                continue;
            }
            Object value = get(key);
            selectProperties.put(key, value);
        }
    }

    /**
     * Saves the mappings in this {@code Properties} to the specified {@code
     * OutputStream}, putting the specified comment at the beginning. The output
     * from this method is suitable for being read by the
     * {@link #load(InputStream)} method.
     *
     * @param out the {@code OutputStream} to write to.
     * @param comment the comment to add at the beginning.
     * @throws ClassCastException if the key or value of a mapping is not a
     *                String.
     * @deprecated This method ignores any {@code IOException} thrown while
     *             writing -- use {@link #store} instead for better exception
     *             handling.
     */
    @Deprecated
    public void save(OutputStream out, String comment) {
        try {
            store(out, comment);
        } catch (IOException e) {
        }
    }

    /**
     * Maps the specified key to the specified value. If the key already exists,
     * the old value is replaced. The key and value cannot be {@code null}.
     *
     * @param name
     *            the key.
     * @param value
     *            the value.
     * @return the old value mapped to the key, or {@code null}.
     */
    public Object setProperty(String name, String value) {
        return put(name, value);
    }

    /**
     * Stores properties to the specified {@code OutputStream}, using ISO-8859-1.
     * See "<a href="#character_encoding">Character Encoding</a>".
     *
     * @param out the {@code OutputStream}
     * @param comment an optional comment to be written, or null
     * @throws IOException
     * @throws ClassCastException if a key or value is not a string
     */
    public synchronized void store(OutputStream out, String comment) throws IOException {
        store(new OutputStreamWriter(out, "ISO-8859-1"), comment);
    }

    /**
     * Stores the mappings in this {@code Properties} object to {@code out},
     * putting the specified comment at the beginning.
     *
     * @param writer the {@code Writer}
     * @param comment an optional comment to be written, or null
     * @throws IOException
     * @throws ClassCastException if a key or value is not a string
     * @since 1.6
     */
    public synchronized void store(Writer writer, String comment) throws IOException {
        if (comment != null) {
            writer.write("#");
            writer.write(comment);
            writer.write(NEW_LINE);
        }
        writer.write("#");
        writer.write(new Date().toString());
        writer.write(NEW_LINE);

        StringBuilder sb = new StringBuilder(200);
        for (Map.Entry<String, Set<String>> entry : entrySet()) {
            String key = entry.getKey();
            String[] values = setToArray(entry.getValue());
            for (String value : values) {
            	dumpString(sb, key, true);
            	sb.append('=');
            	dumpString(sb, value, false);
            	sb.append(NEW_LINE);
            	writer.write(sb.toString());
            	sb.setLength(0);
            }
        }
        writer.flush();
    }
	
}
