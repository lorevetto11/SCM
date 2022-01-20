package com.gpi.scm.generic.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class StringUtils {
	
	private static final Logger log = Logger.getLogger(StringUtils.class);

	public static final String STR_SLASH = "/";
	public static final String STR_UNDERSCORE = "_";
	public static final String STR_AMPERSEAND = "&";
	public static final String STR_EMPTY = "";
	public static final String STR_SPACE = " ";
	public static final String STR_QUERY_STRING_BEGINNER = "?";
	public static final String STR_DOUBLE_BACKSLASH = "\\";
	public static final String STR_COMMA_SPACE = ", ";
	public static final String STR_COMMA = ",";
	public static final String STR_ZERO = "0";
	public static final String STR_BLANK = " ";
	public static final String STR_TAB = "\t";
	public static final String STR_NEWLINE = "\n";
	public static final String STR_EQUAL = "=";
	public static final String STR_PACKAGE_SEPARATOR = ".";
	public static final String STR_LIKE_JOLLY = "%";
	public static final String STR_UTF8 = "UTF-8";
	public static final String STR_DOT = ".";
	public static final String STR_DASH = "-";
	
	public static final String URL_PARAM_NAVIGATION_TO = "navto";
	
	public static final char CHR_ZERO = '0';

//	static private final int BASELENGTH = 128;
	static private final int LOOKUPLENGTH = 64;
	static private final int TWENTYFOURBITGROUP = 24;
	static private final int EIGHTBIT = 8;
	static private final int SIXTEENBIT = 16;
//	static private final int FOURBYTE = 4;
	static private final int SIGN = -128;
	static private final char PAD = '=';
	static private final boolean fDebug = false;
	static final private char[] lookUpBase64Alphabet = new char[LOOKUPLENGTH];

	/**
	 * Left and Right padding
	 * 
	 * @param s
	 *            String value to pad
	 * @param width
	 * @param filler
	 *            filler character
	 * @return
	 */
	public static String leftPad(String s, int width, char filler) {
		return String.format("%" + width + "s", s).replace(' ', filler);
	}

	public static String rightPad(String s, int width, char filler) {
		return String.format("%-" + width + "s", s).replace(' ', filler);
	}

	/**
	 * check if the string is empty
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().equals(STR_EMPTY)
				|| str.trim().length() == 0;
	}

	/**
	 * @return concatenation of args
	 */
	public static String concat(Object... args) {
		StringBuilder sb = new StringBuilder();
		for (Object val : args)
			if (val == null)
				sb.append("(null)");
			else
				sb.append(val.toString());

		return sb.toString();
	}

	public static String getLastTokenOf(String value, String tokensSeparator) {
		String[] tokens = value.split(tokensSeparator);
		if (tokens.length > 0)
			return tokens[tokens.length - 1];
		else
			return STR_EMPTY;
	}

	public static String getFirstTokenOf(String value, String tokensSeparator) {
		String[] tokens = value.split(tokensSeparator);
		if (tokens.length > 0)
			return tokens[0];
		else
			return STR_EMPTY;
	}

	public static String getSpecificTokenOf(String value,
			String tokensSeparator, int wichToken) {
		String[] tokens = value.split(tokensSeparator);
		if (tokens.length > wichToken)
			return tokens[wichToken];
		else
			return STR_EMPTY;
	}
	
	public static String objToString(Object o) {
		ArrayList<String> list = new ArrayList<String>();
		objToString(o, o.getClass(), list);
		return o.getClass().getName().concat(list.toString());
	}

	private static void objToString(Object o, Class<?> clazz, List<String> list) {
		Field f[] = clazz.getDeclaredFields();
		AccessibleObject.setAccessible(f, true);
		String token = STR_EMPTY;
		String fname = STR_EMPTY;
		
		for (int i = 0; i < f.length; i++) {
			try {
				fname = f[i].getName();
				
				token = concat(fname,STR_EQUAL,f[i].get(o));
			} catch (Exception e) {
				token = concat("Error converting toString class ", o.getClass().getName(), "[ field ", f[i].toString(), "]");
				log.error(token, e);
			}
			list.add(token);
		}
		if (clazz.getSuperclass().getSuperclass() != null) {
			objToString(o, clazz.getSuperclass(), list);
		}
	}
	
	public static boolean isLongValue(String value) {
		try {
	         @SuppressWarnings("unused")
			 long l = Long.parseLong(value);
	         return true;
	      } catch (NumberFormatException nfe) {
	         return false;
	      }
	}
	
	/**
     * An interpreter for strings with named placeholders.
     *
     * For example given the string "hello %(myName)" and the map <code>
     *      <p>Map<String, Object> map = new HashMap<String, Object>();</p>
     *      <p>map.put("myName", "world");</p>
     * </code>
     *
     * the call {@code format("hello %(myName)", map)} returns "hello world"
     *
     * It replaces every occurrence of a named placeholder with its given value
     * in the map. If there is a named place holder which is not found in the
     * map then the string will retain that placeholder. Likewise, if there is
     * an entry in the map that does not have its respective placeholder, it is
     * ignored.
     *
     * @param str
     *            string to format
     * @param values
     *            to replace
     * @return formatted string
     */
    public static String format(String str, Map<String, Object> values) {
        StringBuilder builder = new StringBuilder(str);

        for (Entry<String, Object> entry : values.entrySet()) {

            int start;
            String pattern = "%(" + entry.getKey() + ")";
            String value = entry.getValue().toString();

            // Replace every occurence of %(key) with value
            while ((start = builder.indexOf(pattern)) != -1) {
                builder.replace(start, start + pattern.length(), value);
            }
        }
        return builder.toString();
    }	
    
    public static String join(List<String> stringList, char symbol) {

    	//The string builder used to construct the string
    	StringBuilder commaSepValueBuilder = new StringBuilder();


    	//Looping through the list

    	for ( int i = 0; i< stringList.size(); i++){
    	//append the value into the builder
    		commaSepValueBuilder.append(stringList.get(i));


    		//if the value is not the last element of the list
    		//then append the comma(,) as well
    		if ( i != stringList.size()-1){
    			commaSepValueBuilder.append(symbol);
    		}
    	}
    
    	return commaSepValueBuilder.toString();
  
    }
    
	
	/**
	 * Replace encoded XML entities into rispective elements
	 */
	public static String replaceEntities(String src) {
		src = replaceString(src, "%3C", "<");
		src = replaceString(src, "%3E%20%20", ">");

		return src;
	}
	
	/**
	 * Replace src characters into dest characters in orig string
	 */
	public static final String replaceString(String orig, String src,
			String dest) {
		if (orig == null)
			return null;
		if (src == null || dest == null)
			throw new NullPointerException();
		if (src.length() == 0)
			return orig;

		StringBuffer res = new StringBuffer(orig.length() + 20);
		int start = 0;
		int end = 0;
		int last = 0;

		while ((start = orig.indexOf(src, end)) != -1) {
			res.append(orig.substring(last, start));
			res.append(dest);
			end = start + src.length();
			last = start + src.length();
		}

		res.append(orig.substring(end));

		return res.toString();
	}
	
	public static String generateEntryUUID(){
		return UUID.randomUUID().toString();
	}
	
	public static boolean isUUID(String uuid){
		return uuid != null && !StringUtils.isEmpty(uuid) && uuid.matches("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");
	}
	
	/**
	 * Encodes hex octects into Base64
	 * 
	 * @param binaryData
	 *            Array containing binaryData
	 * @return Encoded Base64 array
	 */
	public static String encodeB64(byte[] binaryData) {

		if (binaryData == null)
			return null;

		int lengthDataBits = binaryData.length * EIGHTBIT;
		if (lengthDataBits == 0) {
			return "";
		}

		int fewerThan24bits = lengthDataBits % TWENTYFOURBITGROUP;
		int numberTriplets = lengthDataBits / TWENTYFOURBITGROUP;
		int numberQuartet = fewerThan24bits != 0 ? numberTriplets + 1 : numberTriplets;
		char encodedData[] = null;

		encodedData = new char[numberQuartet * 4];

		byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;

		int encodedIndex = 0;
		int dataIndex = 0;
		if (fDebug) {
			System.out.println("number of triplets = " + numberTriplets);
		}

		for (int i = 0; i < numberTriplets; i++) {
			b1 = binaryData[dataIndex++];
			b2 = binaryData[dataIndex++];
			b3 = binaryData[dataIndex++];

			if (fDebug) {
				System.out.println("b1= " + b1 + ", b2= " + b2 + ", b3= " + b3);
			}

			l = (byte) (b2 & 0x0f);
			k = (byte) (b1 & 0x03);

			byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);

			byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);
			byte val3 = ((b3 & SIGN) == 0) ? (byte) (b3 >> 6) : (byte) ((b3) >> 6 ^ 0xfc);

			if (fDebug) {
				System.out.println("val2 = " + val2);
				System.out.println("k4   = " + (k << 4));
				System.out.println("vak  = " + (val2 | (k << 4)));
			}

			encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | (k << 4)];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[(l << 2) | val3];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 0x3f];
		}

		// form integral number of 6-bit groups
		if (fewerThan24bits == EIGHTBIT) {
			b1 = binaryData[dataIndex];
			k = (byte) (b1 & 0x03);
			if (fDebug) {
				System.out.println("b1=" + b1);
				System.out.println("b1<<2 = " + (b1 >> 2));
			}
			byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
			encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[k << 4];
			encodedData[encodedIndex++] = PAD;
			encodedData[encodedIndex++] = PAD;
		} else if (fewerThan24bits == SIXTEENBIT) {
			b1 = binaryData[dataIndex];
			b2 = binaryData[dataIndex + 1];
			l = (byte) (b2 & 0x0f);
			k = (byte) (b1 & 0x03);

			byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
			byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);

			encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | (k << 4)];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2];
			encodedData[encodedIndex++] = PAD;
		}

		return new String(encodedData);
	}


}

