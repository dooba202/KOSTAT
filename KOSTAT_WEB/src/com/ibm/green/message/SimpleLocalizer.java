package com.ibm.green.message;

import java.util.Locale;

/**
 * Simple message localizer.
 * 
 * Preferred Language can be configured for each thread.
 */
public class SimpleLocalizer {
	
	private static final String defaultLangKey = "default";
	
	private static ThreadLocal<String> prefLang = new ThreadLocal<String>() {
		@Override 
		protected String initialValue() {
			return Locale.getDefault().getLanguage();
		}
	};

	/**
	 * Set the preferred language on the current thread. 
	 * @param language
	 */
	public static void setPreferredLanguage(String language) {
		prefLang.set(language);
	};
	
	/**
	 * Set the preferred language on the current thread. 
	 * @param locale 
	 */
	public static void setPreferredLanguage(Locale locale) {
		prefLang.set(locale.getLanguage());
	}

	/**
	 * Remove the preferred language on the current thread
	 */
	public static void removePreferredLanguage() {
		prefLang.remove();
	}
	
	/**
	 * Choose and return the proper localized message from message catalog 'messages'.
	 * It will use preferred language info in the thread local if available.
	 * 
	 * @param messages message catalog. Array of String array
	 * @return localized message
	 */
	public static String getLocalizedMessage(String[][] messages) {
		return getLocalizedMessage(messages, prefLang.get());
	}
	
	/**
	 * Choose and return the proper localized message from 'messages' for the given locale.
	 * 
	 * @param messages message catalog. Array of String array
	 * @param locale preferred locale
	 * @return localized message
	 */
	public static String getLocalizedMessage(String[][] messages, Locale locale) {
		return getLocalizedMessage(messages, locale.getLanguage());
	}
	
	/**
	 * Choose and result the proper localized message from message catalog 'messages' for the given language.
	 * Message catalog 'messages' object look like the below.
	 * {{"default", "Building 1"}, {"Korean", "빌딩1"}, ... }
	 * 
	 * @param messages messages message catalog. Array of String array
	 * @param language preferred language
	 * @return localized message
	 */
	public static String getLocalizedMessage(String[][] messages, String language) {
		
		String defaultMsg = null;
		
		for (int i = 0; i < messages.length; i++) {
			String[] pair = messages[i];	
			if (language.equalsIgnoreCase(pair[0])) {
				if ((pair[1] == null) || pair[1].equals("")) {
					break; // use default
				}
				return pair[1];
			}
			
			if (defaultLangKey.equalsIgnoreCase(pair[0])) {
				defaultMsg = pair[1];
			}
		}
		
		return defaultMsg; // use default message
	}
	
}
